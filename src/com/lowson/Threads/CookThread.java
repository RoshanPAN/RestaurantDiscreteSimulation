package com.lowson.Threads;

import com.lowson.Role.Cook;
import com.lowson.Role.CookState;
import com.lowson.Scheduler.Schedule;
import com.lowson.Util.Environment;
import com.lowson.Util.RelativeTimeClock;

import static com.lowson.Util.Environment.scheduler;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public class CookThread extends Thread{
    private final Cook cook;
    private RelativeTimeClock clock;

    public CookThread(Cook cook){
        this.cook = cook;
        this.clock = RelativeTimeClock.getInstance();
    }

    @Override
    public void run() {
        Schedule schedule;
        boolean isSchedulingSuccessful;
        try{
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
                        }
                    }
                }
                assert isSchedulingSuccessful;

                // Working on the machine
                cook.setState(CookState.WORKING);
                cook.setSchedule(schedule);
                cook.setStartWorkingTime(Environment.clock.getCurrentTime());
                while(!this.isInterrupted() && !cook.isTaskFinished()){
                    synchronized (cook){
                        cook.wait();
                    }
                }

                // Finish ->
                //  Notify scheduler:
                //      1. Release machine
                //      2. do sth about order
                //          Case 1. order finished, Serve food to customer.
                //          Case 2. order not finished, put back into orderPool
                synchronized (scheduler){
                    scheduler.finishExecution(schedule);
                    cook.setSchedule(null);
                }

            }
        }catch (InterruptedException ie){
            System.out.println(String.format("Cook#%-4d get interrupted when %s.", cook.getID(), cook.getState().toString()));
        }

    }
}
