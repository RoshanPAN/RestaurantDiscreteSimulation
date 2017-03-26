package com.lowson.Threads;

import com.lowson.Role.Cook;
import com.lowson.Role.CookState;
import com.lowson.Role.Food;
import com.lowson.Role.Machine;
import com.lowson.Scheduler.Schedule;
import com.lowson.Scheduler.Scheduler;
import com.lowson.Util.Environment;
import com.lowson.Util.RelativeTimeClock;

import static com.lowson.Util.Environment.availMachines;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public class CookThread extends Thread{
    private Cook cook;
    private RelativeTimeClock clock;
    private static Scheduler scheduler = Environment.scheduler;

    public CookThread(Cook cook){
        this.cook = cook;
        this.clock = RelativeTimeClock.getInstance();
    }

    @Override
    public void run() {
        Schedule schedule;
        Machine machine;
        boolean isSchedulingSuccessful;
        try{
            while(!this.isInterrupted()){
                // Match cook with machine according to schedule
                isSchedulingSuccessful = false;
                cook.setState(CookState.IDLE);
                while(!this.isInterrupted() &&  isSchedulingSuccessful == false)
                {
                    // Try to get a work schedule(Food to prepare + Machine to work on)
                    synchronized (scheduler)
                    {
                        schedule = scheduler.getSchedule();
                    }
                    if(schedule == null && scheduler.getTotalUnfinishedOrderCnt() > 0){ // no schedule available for this cycle
                        break;
                    }

                    // Try to acquire one of the available machine
                    synchronized (availMachines)
                    {
                        for(Food f: schedule.getTaskList()){
                            machine = f.getCorrespondingMachine();
                            if( availMachines.contains(machine) ) {
                                // this machine is available.
                                cook.setMachine(machine);
                                availMachines.remove(machine);
                                isSchedulingSuccessful = true;
                                break;
                            }
                        }
                    }

                    if(isSchedulingSuccessful == false){ // failed to get a machine
                        scheduler.rescheduleForNextRound(schedule.getOrder());
                    }
                }


                // Working on the machine
                cook.setState(CookState.WORK_ON_MACHINE);
                cook.setStartWorkingTime(Environment.clock.getCurrentTime());
                while(!this.isInterrupted() && isSchedulingSuccessful && cook.isFoodProcessingFinished() ){
                    synchronized (cook){
                        cook.wait();
                    }
                }

                // Finish ->
                //  Release machine
                //  Notify customer.
                //      Case 1. Serve food to customer.
                //      Case 2. Return the order to scheduler.
                while()


            }
        }catch (InterruptedException ie){
            System.out.println(String.format("Cook#%-4d get interrupted when %s.", cook.getID(), cook.getState().toString()));
        }

    }
}
