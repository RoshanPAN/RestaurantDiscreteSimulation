package com.lowson.Scheduler;

import com.lowson.Role.Food;
import com.lowson.Role.Machine;
import com.lowson.Role.Order;

/**
 * Created by lenovo1 on 2017/3/26.
 */
public class Schedule {
    private Order order;
    private Food task;
    private Machine machine;
    private int taskCnt;

    public Schedule(Order order, Food task, Machine machine, int taskCnt){
        assert task.getCorrespondingMachine() == machine;
        this.order = order;
        this.machine = machine;
        this.task = task;
        this.taskCnt = taskCnt;
    }

    public Order getOrder() {
        return order;
    }

    public int getTaskCnt(){
        return taskCnt;
    }

    public Machine getMachine() {
        return machine;
    }

    public Food getTask() { return task; }
}
