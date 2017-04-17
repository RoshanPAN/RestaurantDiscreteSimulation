package com.lowson.Threads;

import com.lowson.Role.*;
import com.lowson.Scheduler.Schedule;
import com.lowson.Util.Environment;
import com.lowson.Util.RelativeTimeClock;

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
        System.out.println(String.format("[Cook Thread Start] %s", cook));
        Schedule schedule;
        boolean isSchedulingSuccessful;
        try{
            System.out.println("[Cook Wait for Scheduling] " + cook.toString());
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
                schedule.getOrder().setState(OrderState.PROCESSING);

                // Try to get idel machine from scheduler and finish Task one by one
                Machine curMachine = null;
                Task curTask = null;
                while(!this.isInterrupted() && !schedule.isAllTaskFinished()){
                    // Get a machine
                    System.out.println("[Cook Wait for Machine] " + cook.toString());
                    cook.setState(CookState.WAIT_FOR_MACHINE);
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
                        System.out.println(String.format("[Cook Work on machine] -Cook: %s. \n    -Machine:%s,   -Task:%s",
                                cook.toString(),curMachine.toString(), curTask.toString()));
                        cook.setStartWorkingTime(Environment.clock.getCurrentTime());
                        while(!this.isInterrupted() &&
                                //TODO How to decide the event ending time
                                Environment.clock.getCurrentTime() < cook.getStartWorkingTime() + curTask.getProcessingTime()){
                            System.out.println(String.format("Cook#%d  %d<%d",
                                    cook.getID(), Environment.clock.getCurrentTime(), cook.getStartWorkingTime() + curTask.getProcessingTime()));
                            cook.wait();
                        }
                        // Release Machine and Remove task
                        synchronized (scheduler){
                            scheduler.releaseMachine(curMachine);
                        }
                        cook.setFinishedTask(curTask);
                        System.out.println(String.format("[Cook finish current Task.] -Cook: %s. \n    -Machine:%s,   -Task:%s",
                                cook.toString(),curMachine.toString(), curTask.toString()));
                        cook.setStartWorkingTime(-1);
                    }
                }

                // Finish
                System.out.println(String.format("[Cook Finished & Restart] -Cook: %s. \n    -Machine:%s,   -Task:%s",
                        cook.toString(),curMachine.toString(), curTask.toString()));
                synchronized (scheduler){
                    //  1. Notify scheduler:
                    //      1) Update Word load
                    //      2) ...
                    scheduler.finishSchedule(schedule);
                }
                // reset cook
                cook.setSchedule(null);
                // TODO order finished, Serve food to customer. Mark order as finished.
                schedule.getOrder().setState(OrderState.FINISHED);
            }
        }catch (InterruptedException ie){
            System.out.println(String.format("Cook#%-4d get interrupted when %s.", cook.getID(), cook.getState().toString()));
        }

    }
}
