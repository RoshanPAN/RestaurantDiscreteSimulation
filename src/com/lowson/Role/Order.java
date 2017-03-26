package com.lowson.Role;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by lenovo1 on 2017/3/24.
 */
public class Order {
    static ArrayList<Order> orderList = new ArrayList<>();
    static int nextOrderID = 0;
    int orderID;
    int dinerID;
    HashMap<Food, Integer> orderedCnt = new HashMap<>();
    HashMap<Food, Integer> finishedCnt = new HashMap<>();

    public Order(int numBurger, int numFries, int numCoke, int numSundae, int dinerID){
        this.orderID = nextOrderID;
        this.dinerID = dinerID;
        nextOrderID = nextOrderID + 1;
        orderedCnt.put(Food.Burger, numBurger);
        orderedCnt.put(Food.Fries, numFries);
        orderedCnt.put(Food.Coke, numCoke);
        orderedCnt.put(Food.Sundae, numSundae);
        finishedCnt.put(Food.Burger, 0);
        finishedCnt.put(Food.Fries, 0);
        finishedCnt.put(Food.Coke, 0);
        finishedCnt.put(Food.Sundae, 0);
        orderList.add(this);
    }

    public int getOrderID(){ return orderID;}

    public boolean isReady(){
        int o, f;
        for(Food food: Food.values()){
            o = orderedCnt.get(food);
            f = finishedCnt.get(food);
            assert f <= o;
            if(f < o)
                return false;
        }
        return true;
    }

    public void addFinishedFood(Food food, int n){
        finishedCnt.put(food, finishedCnt.get(food) + n);
    }

    public int getNotPreparedCnt(Food food){
        return orderedCnt.get(food) - finishedCnt.get(food);
    }

    @Override
    public String toString(){
        return String.format("Order#%-4d\n -Ordered:%s\n -Finished: %s\n",
                orderID, orderedCnt, finishedCnt);
    }
}
