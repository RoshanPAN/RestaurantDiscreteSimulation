package com.lowson.Role;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by lenovo1 on 2017/3/24.
 */
public class Order {
    static ArrayList<Order> orderList = new ArrayList<>();
    static int nextOrderID = 0;
    private int orderID;
    private int dinerID;
    private OrderState state;
    HashMap<Food, Integer> orderedCnt = new HashMap<>();
//    HashMap<Food, Integer> finishedCnt = new HashMap<>();

    public Order(int numBurger, int numFries, int numCoke, int numSundae, int dinerID){
        this.orderID = nextOrderID;
        this.dinerID = dinerID;
        nextOrderID = nextOrderID + 1;
        state = OrderState.NOT_SUBMITTED;
        orderedCnt.put(Food.Burger, numBurger);
        orderedCnt.put(Food.Fries, numFries);
        orderedCnt.put(Food.Coke, numCoke);
        orderedCnt.put(Food.Sundae, numSundae);
        orderList.add(this);
    }

    public int getOrderedFoodCnt(Food food){
        return orderedCnt.getOrDefault(food, 0);
    }

    public int getOrderID(){ return orderID;}

    public boolean isReady(){
//        System.out.println((state == OrderState.FINISHED) + "   Diner's order state" );
        return state == OrderState.FINISHED;
    }

    @Override
    public String toString(){
        return String.format("Order#%-4d -State: %s  -Ordered:%s",
                orderID, state, orderedCnt);
    }

    public int getDinerID() {
        return dinerID;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

}
