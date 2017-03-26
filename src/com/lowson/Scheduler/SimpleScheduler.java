package com.lowson.Scheduler;

import com.lowson.Role.Food;
import com.lowson.Role.Order;
import com.lowson.Util.Environment;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class SimpleScheduler extends Scheduler{
    LinkedList<Order> orderPool;

    private SimpleScheduler(){
        super();
        orderPool = new LinkedList<>();
        failedScheduleOrderPool = new LinkedList<>();
    }

    // Singleton - IoDH
    public static SimpleScheduler getInstance() {
        return SimpleScheduler.SchedulerHolder.scheduler;
    }

    public static class SchedulerHolder{
        private final static SimpleScheduler scheduler = new SimpleScheduler();
    }


    // Core of scheduling logic.
    @Override
    // FIFO
    public void submitOrder(Order order) {
        orderPool.offerLast(order);
        assert orderPool.size() <= Environment.num_table;
    }

    @Override
    // FIFO
    protected Order getOrder() {
        return this.orderPool.pollFirst();
    }

    @Override
    protected void applyTaskOrderingLogic(ArrayList<Food> taskList) {
        // do nothing.
    }

    /*
    Task count = Prepare all the foods for the 1st task.
    */
    @Override
    protected int getTaskCnt(Order curOrder, Food task) {
        return curOrder.getNotPreparedCnt(task);
    }


}
