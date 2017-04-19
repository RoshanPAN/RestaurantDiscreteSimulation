package com.lowson.Threads;

import com.lowson.Role.*;
import com.lowson.Util.Environment;

import static com.lowson.Util.Environment.tableScheduler;
import static java.lang.Thread.sleep;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class ClockThread implements Runnable{
    public static RelativeTimeClock clock = RelativeTimeClock.getInstance();

    @Override
    public void run() {
        while(!isAllThreadWaitingOrFinished()){
            System.out.println("[Clock] Wait for other threads to be blocked.");
            try {
                sleep(30); // InterruptedException
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.format("[Clock Thread Start] %s", clock));
        // Before end time, exists as a time provider.
        while(!isAllDinerFinished()){
            System.out.println(">>>>>>>>>>>>[Clock]" + clock.toString());
            try {
                // Diner arriving
                updateDiners(DinerState.NOT_ARRIVED);
                while(!isAllThreadWaitingOrFinished()){
//                    System.out.println("[Clock] Wait for other threads to be blocked.");
                    sleep(5); // InterruptedException
                }

                // Scheduling about table, let some diner get seated & submit order
                synchronized (tableScheduler){
                    for(Diner d: tableScheduler.getScheduledDiners()){
                        synchronized (d){
                            d.notifyAll();  // notify those diner(based on scheduling) who is wait for table
                        }
                    }
                }

                while(!isAllThreadWaitingOrFinished()){
//                    System.out.println("[Clock] Wait for other threads to be blocked.");
                    sleep(5); // InterruptedException
                }

                // diner order submitted, waiting for food
                updateDiners(DinerState.WAIT_FOR_FOOD);
                while(!isAllThreadWaitingOrFinished()){
                    sleep(5); // InterruptedException
                }

                // Let working cook continue to work or finish
                updateCooks(CookState.WORKING);
                while(!isAllThreadWaitingOrFinished()){
                    sleep(5); // InterruptedException
                }

                // Cook with schedule try to get a machine to work on.
                updateCooks(CookState.WAIT_FOR_MACHINE);
                while(!isAllThreadWaitingOrFinished()){
                    sleep(5); // InterruptedException
                }

                // IDLE Cook try to get a schedule and machine
                updateCooks(CookState.IDLE);
                while(!isAllThreadWaitingOrFinished()){
                    sleep(5); // InterruptedException
                }

                // Dinners keep eating or leave
                updateDiners(DinerState.EATING);
                while(!isAllThreadWaitingOrFinished()){
                    sleep(5); // InterruptedException
                }

                clock.increment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        // At end time, interrupt all live DinerThread & CookThread.
//        for(DinerThread t: Environment.dinerThreadPool){
//            if(t.getState() == Thread.State.TERMINATED) continue;
//            t.interrupt();
//        }
        for(CookThread t: Environment.cookThreadPool){
            if(t.getState() == Thread.State.TERMINATED) continue;
            t.interrupt();
        }
    }

    private boolean isAllDinerFinished() {
        boolean isAllFinished = true;
        for(Diner d: Environment.dinerList){
            if(d.getState() != DinerState.LEFT){
                isAllFinished = false;
            }
        }
        return isAllFinished;
    }

    private boolean isAllThreadWaitingOrFinished() {
        Thread.State s;
        for(DinerThread t: Environment.dinerThreadPool){
            s = t.getState();
            if( s == Thread.State.TERMINATED || s == Thread.State.WAITING) {
                continue;
            }else{
//                System.out.println("Wait for a while ... " + s.toString() +  "  " + t.diner.toString());
                return false;
            }
        }
        for(CookThread t: Environment.cookThreadPool){
            s = t.getState();
            if(s == Thread.State.TERMINATED || s == Thread.State.WAITING) {
                continue;
            }else{
//                System.out.println("Wait for a while ... " + s.toString() +  "  " + t.cook.toString());
                return false;
            }
        }
        return true;
    }

    private void updateDiners(DinerState state) {
        // wake up each diner thread by notifying each Dinner
        synchronized (Diner.dinerMap){
            for(Diner d: Diner.dinerMap.values()){
                if(d.getState() != state) continue;
                synchronized (d){
                    d.notifyAll();
                }
            }
        }
    }

    private void updateCooks(CookState state){
        // wake up each cook thread in this state
        for(Cook c: Cook.cookList){
            if(c.getState() == state)
                synchronized (c){
                    c.notifyAll();
                }
        }
    }



}
