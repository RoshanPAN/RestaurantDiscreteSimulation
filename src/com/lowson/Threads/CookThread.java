package com.lowson.Threads;

import com.lowson.Role.Cook;
import com.lowson.Role.CookState;
import com.lowson.Role.Machine;
import com.lowson.Scheduler.Schedule;
import com.lowson.Scheduler.Scheduler;
import com.lowson.Util.Environment;
import com.lowson.Util.RelativeTimeClock;

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
                // Try to get a schedule from scheduler
                isSchedulingSuccessful = false;
                cook.setState(CookState.IDLE);
                while(!this.isInterrupted() &&  isSchedulingSuccessful == false)
                {
                    // Try to get a work schedule(Food to prepare + Machine to work on)
                    synchronized (scheduler)
                    {
                        schedule = scheduler.getSchedule();
                    }
                    synchronized (cook){
                        if(schedule == null) // no schedule available
                            cook.wait();
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



            }
        }catch (InterruptedException ie){
            System.out.println(String.format("Cook#%-4d get interrupted when %s.", cook.getID(), cook.getState().toString()));
        }

    }
}
