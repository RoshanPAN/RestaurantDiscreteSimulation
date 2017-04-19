package com.lowson.Threads;

import com.lowson.Role.*;
import com.lowson.Scheduler.Schedule;
import com.lowson.Util.Environment;
import com.lowson.Role.RelativeTimeClock;

import static com.lowson.Util.Environment.scheduler;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public class CookThread extends Thread{
    public final Cook cook;
    private RelativeTimeClock clock;

    public CookThread(Cook cook){
        this.cook = cook;
        this.clock = RelativeTimeClock.getInstance();
    }

//    @Override
    public void run() {
//        System.out.println(String.format("Cook#%d start.", cook.getID()));
        Schedule schedule;
        boolean isSchedulingSuccessful;
        try{
            System.out.println(String.format("Cook#%d waiting to be scheduled.", cook.getID()));
            while(!this.isInterrupted()){
                // Try to get a schedule from scheduler
                schedule = null;
                isSchedulingSuccessful = false;
                cook.setState(CookState.IDLE);
                while(!this.isInterrupted() && !isSchedulingSuccessful)
                {
                    // Try to get a work schedule(Food to prepare + Machine to work on)
                    synchronized (scheduler)
                    {
                        schedule = scheduler.getSchedule();
                    }
                    synchronized (cook){
                        if(schedule == null) {// no schedule available
                            cook.wait();
                        }else{
                            isSchedulingSuccessful = true;
                            cook.setSchedule(schedule);
                        }
                    }
                }
                assert isSchedulingSuccessful;
                String.format("Cook#%d scheduled for order#%d from diner#%d. %s",
                        cook.getID(), schedule.getOrder().getOrderID(), schedule.getOrder().getDinerID(),
                        schedule.getOrder());
                schedule.getOrder().setState(OrderState.PROCESSING);

                // Try to get idle machine from scheduler and finish Task one by one
                Machine curMachine = null;
                Task curTask = null;
                cook.setState(CookState.WAIT_FOR_MACHINE);
                while(!this.isInterrupted() && !schedule.isAllTaskFinished()){
                    // Get a machine
                    synchronized (scheduler){
                        for(Task task: cook.getSchedule().getTaskList()){
                            curMachine = scheduler.getAvailableMachine(task.getFood().getCorrespondingMachine());
                            if(curMachine != null) {
                                curTask = task;
                                break;
                            }
                        }
                    }

                    synchronized (cook){
                        if(curMachine == null){ // If failed to get machine, wait and retry
                            cook.wait();
                            continue;
                        }


                        // If get a machine, work on this machine until this food in current task is finished.
                        cook.setState(CookState.WORKING);
                        cook.setStartWorkingTime(Environment.clock.getCurrentTime());
                        System.out.println(String.format("Cook#%d work on %s for order#%d from diner#%d. %d",
                                cook.getID(), curMachine, schedule.getOrder().getOrderID(),
                                schedule.getOrder().getDinerID(), cook.getStartWorkingTime()));

                        while(!this.isInterrupted() &&
                                //TODO How to decide the event ending time
                                Environment.clock.getCurrentTime() < cook.getStartWorkingTime() + curTask.getProcessingTime() - 1){
//                            System.out.println(String.format("Cook#%d  %d<%d",
//                                    cook.getID(), Environment.clock.getCurrentTime(), cook.getStartWorkingTime() + curTask.getProcessingTime()));
                            cook.wait();
                        }

                        // Release Machine and Remove task
                        synchronized (scheduler){
                            scheduler.releaseMachine(curMachine);
                        }
                        cook.setFinishedTask(curTask);
//                        System.out.println(String.format("[Cook finish current Task.] -Cook: %s. \n    -Machine:%s,   -Task:%s",
//                                cook.toString(),curMachine.toString(), curTask.toString()));
                        cook.setStartWorkingTime(-1);
                        synchronized (cook){
                            if(cook.getSchedule().getTaskList().size() > 0){
                                cook.wait();
                            }
                        }

                    }
                }

                // Finish
                System.out.println(String.format("Cook#%d finishes preparation for order#%d from diner#%d.",
                        cook.getID(),  schedule.getOrder().getOrderID(), schedule.getOrder().getDinerID()));
                synchronized (scheduler){
                    //  1. Notify scheduler:
                    //      1) Update Word load
                    //      2) ...
                    scheduler.finishSchedule(schedule);
                }
                // reset cook
                cook.setSchedule(null);
                schedule.getOrder().setState(OrderState.FINISHED);
            }
        }catch (InterruptedException ie){
            System.out.println(String.format("Cook#%-4d get interrupted when %s.", cook.getID(), cook.getState().toString()));
        }

    }
}
