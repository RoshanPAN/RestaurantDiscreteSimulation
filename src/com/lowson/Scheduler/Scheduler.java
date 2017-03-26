package com.lowson.Scheduler;

import com.lowson.Role.Food;
import com.lowson.Role.Machine;
import com.lowson.Role.Order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public abstract class Scheduler {
    public static final HashSet<Machine> availMachines = new HashSet<>();
    protected Queue<Order> orderPool;
    protected Queue<Order> failedScheduleOrderPool;

    /*
    @return:
            schedule - machine and task execution sequence pair.
            null - when there is no schedule available.
     */
    public Schedule getSchedule(){
        Order curOrder = null;
        Machine machine = null;
        ArrayList<Food> taskList = null;
        Food task = null;
        boolean findMachineSucceed = false;
        while(orderPool.size() > 0 && !findMachineSucceed){
            // get order from orderPool
            curOrder = getOrder();
            if(curOrder == null) // no order in the pool -> no schedule
               return null;

            // get the task list for this order
            taskList = getOrderedTaskList(curOrder);
            // get a machine to work on

            synchronized (availMachines)
            {
                for(Food f: taskList){
                   if(availMachines.contains(f.getCorrespondingMachine())){
                       machine = f.getCorrespondingMachine();
                       findMachineSucceed = true;
                       task = f;
                       break;
                   }
                }
                if(! findMachineSucceed){ // no machine available for this task(order)
                    rescheduleForNextRound(curOrder);  // put into failedScheduleOrderPool
                }
            }
       }
        // Already checked through all orders, can not find a working schedule.
       if(!findMachineSucceed){
            return null;
       }

       assert machine != null;
       assert curOrder != null;
       assert task != null;

//       HashMap<Food, Integer> taskCntMap = getTaskCntMap(curOrder, taskList);
       int taskCnt = getTaskCnt(curOrder, task);
       return new Schedule(curOrder, task, machine,taskCnt);
    }




    /*
    At the start of each minute, put all orders in failedScheduleOrderPool into orderPool
     */
    public void resetOrderPool() {
        orderPool.addAll(failedScheduleOrderPool);
    }

    /*
    Submit order to orderPool.
     */
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
    protected abstract int getTaskCnt(Order curOrder, Food task);

//    protected HashMap<Food, Integer> getTaskCntMap(Order order, ArrayList<Food> taskList) {
//        HashMap<Food, Integer> taskCntmap = new HashMap<>();
//        applyTaskCntLogic(taskCntmap, order, taskList);  // adjust the task count
//        return taskCntmap;
//    }

    /*
    get the count of orders in this scheduler.
     */
    public int getTotalUnfinishedOrderCnt() {
        return orderPool.size() + failedScheduleOrderPool.size();
    }

}
