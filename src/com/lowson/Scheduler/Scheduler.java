package com.lowson.Scheduler;

import com.lowson.Role.Order;
import com.lowson.Util.Environment;

import java.util.LinkedList;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public abstract class Scheduler {
    private LinkedList<Order> orderPool = new LinkedList<>();
    public Scheduler(){}

    public void submitOrder(Order order){
        orderPool.offer(order);
        assert orderPool.size() <= Environment.num_table;
    }


}
