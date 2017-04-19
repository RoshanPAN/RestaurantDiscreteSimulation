package com.lowson.Threads;

import com.lowson.Role.Diner;
import com.lowson.Role.DinerState;
import com.lowson.Role.Order;
import com.lowson.Role.OrderState;
import com.lowson.Scheduler.Scheduler;
import com.lowson.Util.Environment;
import com.lowson.Role.RelativeTimeClock;

import static com.lowson.Util.Environment.tableScheduler;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public class DinerThread extends Thread {
    public final Diner diner;
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
            System.out.println(String.format("Diner%d start.", diner.getID()));
            // dinner not arrived
            diner.setState(DinerState.NOT_ARRIVED);
            synchronized (diner)
            {
                while(!diner.isArrived()){
                    diner.wait();
                }
            }

            // wait for an table to sit down
            diner.setState(DinerState.WAIT_FOR_TABLE);
            System.out.println(String.format("Diner#%d start.", diner.getID()));
            synchronized (diner){
                diner.wait(); // will be wake us by Clock thread. When TableScheduler let it to go.
            }
            synchronized (tableScheduler){
                diner.setTable(tableScheduler.getTable());
                assert diner.getTable() != null;
            }

            // Wait for food to be served
            Order myOrder = diner.getOrder();
            synchronized (scheduler){
                scheduler.submitOrder(myOrder);
            }
            myOrder.setState(OrderState.NOT_SCHEDULED);
            diner.setState(DinerState.WAIT_FOR_FOOD);
            System.out.println(String.format("Diner#%d get seated, submitted order#%d, on %s.",
                    diner.getID(), diner.getOrder().getOrderID(), diner.getTable()));
            synchronized (diner)
            {
                while( !Thread.currentThread().isInterrupted() && !myOrder.isReady()){
                    diner.wait();
                }
            }

            // Start eat
            diner.setState(DinerState.EATING);
            System.out.println(String.format("Diner#%d start eating on %s.", diner.getID(), diner.getTable()));
            diner.setStartEatTime(clock.getCurrentTime());
            synchronized (diner){
                while(!Thread.currentThread().isInterrupted() && !diner.isFinishedEat()){
                    diner.wait();
                }
            }

            // Free table & left restaurant
            synchronized (tableScheduler){
                tableScheduler.releaseTable(diner.getTable());
                diner.setTable(null);
            }
            diner.setState(DinerState.LEFT);
            System.out.println(String.format("Diner#%d left.", diner.getID(), diner.getTable()));
            this.isThreadFinished = true;
        } catch (InterruptedException e) {
            System.out.println(String.format("Dinner#%-4d get interrupted when %s.", diner.getID(), diner.getState().toString()));
        }

    }

    public boolean isThreadFinished() {
        return isThreadFinished;
    }
}
