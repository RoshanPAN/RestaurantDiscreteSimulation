package com.lowson.Threads;

import com.lowson.Role.Diner;
import com.lowson.Util.Environment;
import com.lowson.Util.RelativeTimeClock;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class ClockThread implements Runnable{
    public static RelativeTimeClock clock = RelativeTimeClock.getInstance();

    @Override
    public void run() {
        // Before end time, exists as a time provider.
        while(clock.getCurrentTime() < clock.getEndTime()){
            /*
                At the end of time = 0,     some cook finish cooking for an order and served food to diner,
                At the start of time = 1,   some dinner is served and left.(some table is freed by dinner).
                At the start of time = 0,   some dinner arrived, some dinners occupy tables.
                                            some cook get order & get machine & start to work.
             */
            //TODO check the ordering of updates here after cook & diner implementation
            try {
                Environment.availTables.notifyAll(); // arrived diner try to occupy table & start to eat
                updateCooks(); // cook try to work
                updateDiners(); // diner try to
                Thread.sleep(100); // InterruptedException
                clock.increment();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // At end time, interrupt all live DinerThread & CookThread.
        for(DinerThread t: Environment.dinerThreadPool){
            if(t.getState() == Thread.State.TERMINATED) continue;
            t.interrupt();
        }
        for(CookThread t: Environment.cookThreadPool){
            if(t.getState() == Thread.State.TERMINATED) continue;
            t.interrupt();
        }
    }

    //TODO
    private void updateDiners() {
        // wake up each diner thread by notifying each Dinner
        synchronized (Diner.dinerList){
            for(Diner d: Diner.dinerList){
                synchronized (d){
                    d.notifyAll();
                }
            }
        }
    }

    //TODO
    private void updateCooks(){
        // wake up each cook thread by notifying each Cook

    }

}
