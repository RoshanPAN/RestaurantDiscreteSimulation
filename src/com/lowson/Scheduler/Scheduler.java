package com.lowson.Scheduler;

import com.lowson.Role.Food;
import com.lowson.Role.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public abstract class Scheduler {
    protected Queue<Order> orderPool;
    protected Queue<Order> failedScheduleOrderPool;

    public Schedule getSchedule(){
        Order nextOrder = getOrder();
        if(nextOrder == null)
            return null;

        ArrayList<Food> taskList = getOrderedTaskList(nextOrder);
        HashMap<Food, Integer> taskCntMap = getTaskCntMap(nextOrder, taskList);
        Schedule schedule = new Schedule(nextOrder, taskList, taskCntMap);
        return schedule;
    }


    public abstract void submitOrder(Order order);


    public void rescheduleForNextRound(Order order) {
        failedScheduleOrderPool.offer(order);
    }

    protected abstract Order getOrder();




    /*
    Organize the foods to be prepared into a list.
    applySchedulingLogic() helps to apply the scheduling logic to the list.
     */
    protected ArrayList<Food> getOrderedTaskList(Order order) {
        ArrayList<Food> taskList = new ArrayList<>();
        for(Food f: Food.values()){
            if(order.getNotPreparedCnt(f) > 0){
                taskList.add(f);
            }
        }
        applyTaskOrderingLogic(taskList); // adjust the ordering of task execution
        return taskList;
    }

    protected HashMap<Food, Integer> getTaskCntMap(Order order, ArrayList<Food> taskList) {
        HashMap<Food, Integer> taskCntmap = new HashMap<>();
        applyTaskCntLogic(taskCntmap, order, taskList);  // adjust the task count
        return taskCntmap;
    }
    /*
    The method implements the scheduling logic about
        which order to execute first.
    A concrete scheduler need to override this.
     */
    protected abstract void applyTaskOrderingLogic(ArrayList<Food> taskList);

    /*
    The method implements the scheduling logic about
        how many food to process on each machine.
    A concrete scheduler need to override this.
     */
    protected abstract void applyTaskCntLogic(HashMap<Food, Integer> taskCntmap, Order order, ArrayList<Food> taskList);


    /*
    get the count of orders in this scheduler.
     */
    public int getTotalUnfinishedOrderCnt() {
        return orderPool.size() + failedScheduleOrderPool.size();
    }

    /*
    At the start of each minute, put all orders in failedScheduleOrderPool into orderPool
     */
    public void resetOrderPool() {
        orderPool.addAll(failedScheduleOrderPool);
    }
}
