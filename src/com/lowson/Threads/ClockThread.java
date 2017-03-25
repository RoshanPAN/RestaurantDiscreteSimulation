package com.lowson.Threads;

import com.lowson.Util.RelativeTimeClock;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class ClockThread implements Runnable{
    public static RelativeTimeClock clock = RelativeTimeClock.getInstance();

    @Override
    public void run() {
        while(clock.getCurrentTime() < clock.getEndTime()){
            /*
                At the start of time = 0,   some dinner arrived, some dinners occupy tables.
                                            some cook get order & get machine & start to work.
                At the end of time = 0,     some cook finish cooking for an order and served food to diner,
                                            some dinner is served and left.(some table is freed by dinner)
             */
            try {
                updateCooks();
                updateDiner();
                Thread.sleep(100); // InterruptedException
                clock.increment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    //TODO
    private void updateDiner() {
        // wake up each diner thread by notifying each Dinner


    }

    //TODO
    private void updateCooks(){
        // wake up each cook thread by notifying each Cook

    }

}
