package com.lowson.Scheduler;

import com.lowson.Role.*;
import com.lowson.Util.Environment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Created by lenovo1 on 2017/3/25.
 */
public abstract class Scheduler {
    public final HashSet<Machine> availMachines = new HashSet<>();
    protected HashMap<Food, Integer> workLoadStats = new HashMap<>();
    protected Queue<Order> orderPool;

    /*
    Cook will call this method to get an order.
    @return:
            schedule - task execution sequence pair.
            null - when there is no schedule available.
     */
    public Schedule getSchedule(){
//        System.out.println(orderPool);
        if (orderPool.size() == 0)
            return null;
        // get order from orderPool
        Order curOrder = getOrder();
        // get the task list for this order
        LinkedList<Task> taskList = getOrderedTaskList(curOrder);
       assert curOrder != null;
       assert taskList != null;
       return new Schedule(curOrder, taskList);
    }

    public Machine getAvailableMachine(Machine m){
        if(availMachines.contains(m)){
            availMachines.remove(m);
            return m;
        }else{
            return null;
        }
    }

    public void releaseMachine(Machine m){
        assert !availMachines.contains(m);
        availMachines.add(m);
    }

    /*
    At the start of each minute, put all orders in failedScheduleOrderPool into orderPool
     */
//    public void resetOrderPool() {
//        for(Order o: failedScheduleOrderPool){
//            orderPool.offer(o);
//        }
//    }

    /*
    Submit order to orderPool.
    Need to update the word load stats.
     */
    public void submitOrder(Order order){
        updateWordLoad(order, true);
        orderPool.offer(order);
        assert orderPool.size() <= TableScheduler.availTables.size();
    };


    protected void updateWordLoad(Order order, boolean isAdd){
        int n;
        for(Food f: Food.values()){
            n = workLoadStats.getOrDefault(f, 0);
            if (isAdd){
                n += order.getOrderedFoodCnt(f) * f.getCookTime();
            }else{
                n -= order.getOrderedFoodCnt(f) * f.getCookTime();
                assert n >= 0;
            }
            workLoadStats.put(f, n);
        }
    }



    /*
    Get a order for scheduling
     */
    protected abstract Order getOrder();

    /*
    When a certain order finished,
    inform scheduler to update it's work load.
     */
    public void finishSchedule(Schedule schedule){
        Order order = schedule.getOrder();
        LinkedList<Task> taskList = schedule.getTaskList();
        assert order.getState() == OrderState.FINISHED;
        assert taskList.size() == 0;
        updateWordLoad(order, false);
    }

    /*
    Organize the foods to be prepared into a list.
    applySchedulingLogic() helps to apply the scheduling logic to the list.
     */
    protected LinkedList<Task> getOrderedTaskList(Order order) {
        LinkedList<Task> taskList = new LinkedList<>();
        for(Food f: Food.values()){
            if(order.getOrderedFoodCnt(f) > 0){
                taskList.add(new Task(f, order.getOrderedFoodCnt(f)));
            }
        }
        applyTaskOrderingLogic(taskList); // get ordered Task list
        return taskList;
    }

    /*
    The method implements the scheduling logic about
        which order to execute first.
    A concrete scheduler need to override this.
     */
    protected abstract void applyTaskOrderingLogic(LinkedList<Task> taskList);


    public Food getBottleNeck(){
        Food bottleNeck = null;
        int maxLoad = 0;
        int load;
        for(Food f: Food.values()){
            load = workLoadStats.getOrDefault(f, 0);
            if(load > maxLoad){
                bottleNeck = f;
                maxLoad = load;
            }
        }
        return bottleNeck;
    }
//    /*
//    The method implements the scheduling logic about
//        how many food to process on each machine.
//    A concrete scheduler need to override this.
//     */
//    protected abstract int getTaskCnt(Order curOrder, Food task);

//    protected HashMap<Food, Integer> getTaskCntMap(Order order, ArrayList<Food> taskList) {
//        HashMap<Food, Integer> taskCntmap = new HashMap<>();
//        applyTaskCntLogic(taskCntmap, order, taskList);  // adjust the task count
//        return taskCntmap;
//    }

//    /*
//    get the count of orders in this scheduler.
//     */
//    public int getTotalUnfinishedOrderCnt() {
//        return orderPool.size() + failedScheduleOrderPool.size();
//    }


//    public void rescheduleForNextRound(Order order) {
//        failedScheduleOrderPool.offer(order);
//    }

}
