package com.lowson.Threads;

import com.lowson.Role.Diner;
import com.lowson.Role.DinerState;
import com.lowson.Role.Order;
import com.lowson.Scheduler.Scheduler;
import com.lowson.Util.Environment;
import com.lowson.Util.RelativeTimeClock;

import static com.lowson.Util.Environment.availTables;


/**
 * Created by lenovo1 on 2017/3/24.
 */
public class DinerThread extends Thread {
    Diner diner;
    RelativeTimeClock clock;
    private static Scheduler scheduler = Environment.scheduler;
    private boolean isThreadFinished;

    public DinerThread(Diner diner){
        this.diner = diner;
        this.clock = RelativeTimeClock.getInstance();
        this.isThreadFinished = false;
    }

    @Override
    public void run() {
        try {
            // dinner not arrived
            diner.setState(DinerState.NOT_ARRIVED);
            synchronized (diner)
            {
                while(diner.isArrived()){
                    diner.wait();
                }
            }


            // wait for an table to sit down
            diner.setState(DinerState.WAIT_FOR_TABLE);
            synchronized (availTables)
            {
                while(!Thread.currentThread().isInterrupted() && availTables.size() <= 0){
                    availTables.wait();
                }
                diner.setTable(availTables.peek());
            }

            // Wait for food to be served
            Order myOrder = diner.getOrder();
            scheduler.submitOrder(myOrder);
            diner.setState(DinerState.WAIT_FOR_FOOD);
            synchronized (diner)
            {
                while( !Thread.currentThread().isInterrupted() && !myOrder.isReady()){
                    diner.wait();
                }
            }

            // Start eat
            diner.setState(DinerState.EATING);
            diner.setStartEatTime(clock.getCurrentTime());
            synchronized (diner){
                while(!Thread.currentThread().isInterrupted() && !diner.isFinishedEat()){
                    diner.wait();
                }
            }

            // Free table & left restaurant
            synchronized (availTables){
                availTables.offer(diner.getTable());
            }
            diner.setState(DinerState.LEFT);
            this.isThreadFinished = true;
        } catch (InterruptedException e) {
            System.out.println(String.format("Dinner#%-4d get interrupted when %s.", diner.getID(), diner.getState().toString()));
        }

    }

    public boolean isThreadFinished() {
        return isThreadFinished;
    }
}
