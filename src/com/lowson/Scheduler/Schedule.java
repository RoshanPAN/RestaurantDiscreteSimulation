package com.lowson.Scheduler;

import com.lowson.Role.Order;
import com.lowson.Role.Task;

import java.util.LinkedList;

/**
 * Created by lenovo1 on 2017/3/26.
 */
public class Schedule {
    private Order order;
    private LinkedList<Task> taskList;

    public Schedule(Order order, LinkedList<Task> taskList){
        this.order = order;
        this.taskList = taskList;
    }

    public Order getOrder() {
        return order;
    }

    public LinkedList<Task> getTaskList() {
        return taskList;
    }

    public Task getNextTask(){
        if (taskList.size() > 0)
            return taskList.peekFirst();
        else
            return null; // all tasks finished
    }

    public boolean isAllTaskFinished(){
        return taskList.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Schedule: order%s, taskList: %s", order.getOrderID(), taskList.toString()));
        return builder.toString();
    }
}
