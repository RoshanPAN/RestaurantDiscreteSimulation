package com.lowson.Threads;

import com.lowson.Role.Cook;
import com.lowson.Role.CookState;
import com.lowson.Role.Diner;
import com.lowson.Role.DinerState;
import com.lowson.Util.Environment;
import com.lowson.Util.RelativeTimeClock;

import static java.lang.Thread.sleep;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class ClockThread implements Runnable{
    public static RelativeTimeClock clock = RelativeTimeClock.getInstance();

    @Override
    public void run() {
        System.out.println(String.format("[Clock Thread Start] %s", clock));
        // Before end time, exists as a time provider.
        while(!isAllDinerFinished()){
            /*
                At the end of time = 0,     some cook finish cooking for an order and served food to diner,
                At the start of time = 1,   some dinner is served and left.(some table is freed by dinner).
                At the start of time = 0,   some dinner arrived, some dinners occupy tables.
                                            some cook get order & get machine & start to work.
             */
            //TODO the order may not like below, and in this case, it will waste 1 minute.
            try {
                // threads working
                updateCooks(CookState.WORKING);
                sleep(5);
                updateCooks(CookState.WAIT_FOR_MACHINE);
                sleep(5);
                updateDiners(DinerState.EATING);
                sleep(10);
                synchronized (Environment.availTables){
                    Environment.availTables.notifyAll(); // arrived diner try to occupy empty table & submit order
                }
                sleep(10);
                updateDiners(DinerState.NOT_ARRIVED);
                sleep(10);
                updateDiners(DinerState.WAIT_FOR_FOOD);  // will be notified by WORKING cooks who finished.
                sleep(10);
                updateCooks(CookState.IDLE);
                sleep(10);
                while(!isAllThreadBlockedOrFinished()){
                    System.out.println("[Clock] Wait for other threads to be blocked.");
                    sleep(30); // InterruptedException
                }
                System.out.println(Environment.scheduler.availMachines.toString());
                clock.increment();
                System.out.println("[Clock]" + clock.toString());
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

    private boolean isAllThreadBlockedOrFinished() {
        Thread.State s;
        for(DinerThread t: Environment.dinerThreadPool){
            s = t.getState();
            if( s == Thread.State.BLOCKED || s == Thread.State.TERMINATED || s == Thread.State.WAITING) {
                continue;
            }else{
                System.out.println("[Not blocked] " + s.toString() + t.diner.toString());
                return false;
            }
        }
        for(CookThread t: Environment.cookThreadPool){
            s = t.getState();
            if( s == Thread.State.BLOCKED || s == Thread.State.TERMINATED || s == Thread.State.WAITING) {
                continue;
            }else{
                System.out.println("[Not blocked] " + s.toString() + t.cook.toString());
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
