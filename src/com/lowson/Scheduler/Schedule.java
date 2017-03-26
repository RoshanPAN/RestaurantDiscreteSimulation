package com.lowson.Scheduler;

import com.lowson.Role.Food;
import com.lowson.Role.Order;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lenovo1 on 2017/3/26.
 */
public class Schedule {
    private Order order;
    private ArrayList<Food> taskList;
    private HashMap<Food, Integer> taskCntmap;

    public Schedule(Order order, ArrayList<Food> taskList, HashMap<Food, Integer> taskCntmap){
        this.order = order;
        this.taskList = taskList;
        this.taskCntmap  = taskCntmap;
    }

    public ArrayList<Food> getTaskList() {
        return taskList;
    }


    public int getTaskCnt(Food food){
        return taskCntmap.get(food);
    }

    public Order getOrder() {
        return order;
    }
}
