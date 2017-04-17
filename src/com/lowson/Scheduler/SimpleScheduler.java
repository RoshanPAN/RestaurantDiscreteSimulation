package com.lowson.Scheduler;

import com.lowson.Exception.UnexpectedBehaviorException;
import com.lowson.Role.Food;
import com.lowson.Role.Order;
import com.lowson.Role.Task;
import com.lowson.Util.Environment;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class SimpleScheduler extends Scheduler{
//    LinkedList<Order> orderPool;

    private SimpleScheduler(){
        super();
        orderPool = new LinkedList<>();
        for(Food f: Food.values()){
            workLoadStats.put(f, 0);
        }
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
        updateWordLoad(order, true);
        LinkedList<Order> op = (LinkedList<Order>) this.orderPool;
        if(op.contains(order)){
            throw new UnexpectedBehaviorException("");
        }
        op.offerLast(order);
        assert op.size() <= Environment.num_table;
    }

    @Override
    /*
    Get the one which have least bottle neck food(task)
     */
    protected Order getOrder() {
        Food bottleNeck = getBottleNeck();
        Collections.sort((LinkedList<Order>)orderPool,
                            Comparator.comparingInt(a -> a.getOrderedFoodCnt(bottleNeck)));
        System.out.println();
        System.out.println();
        System.out.println("Here is ordered result");
        for(Order o: orderPool){
            System.out.println(o);
        }
        return ((LinkedList<Order>)this.orderPool).pollFirst();
    }


    // Ordering the tasks according to  work load statistics(currently submitted order).
    @Override
    protected void applyTaskOrderingLogic(LinkedList<Task> taskList){
        HashSet<Task> taskSet = new HashSet<>(taskList);
        System.out.println(taskList);
        System.out.println(workLoadStats);
        Collections.sort(taskList, (a, b) -> workLoadStats.get(a.getFood()) > workLoadStats.get(b.getFood()) ?
                        -1 : (workLoadStats.get(a.getFood()) == workLoadStats.get(b.getFood()) ? 0 : 1));
        System.out.println(taskList);
        System.out.println(workLoadStats);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(String.format("[SimpleScheduler] \n    -Available Machine: %s, \n    -Order: %s",
                availMachines.toString(), orderPool.toString()));
        b.append(String.format("\n    -WorkLoad: %s", workLoadStats.toString()));
        return b.toString();
    }
}
