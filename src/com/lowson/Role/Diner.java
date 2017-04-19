package com.lowson.Role;

import com.lowson.Util.Environment;

import java.util.HashMap;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public class Diner {
    public static HashMap<Integer, Diner> dinerMap = new HashMap<>();  // dinerID -> diner object
    private static int eatDuration = Environment.dinnerEatDuration;
    static int nextDinerID = 0;

    private int dinerID;
    private int arrivalTime;
    private Table table;
    private Order myOrder;
    private DinerState state;
    private int startEatTime;


    public Diner(int arrivalTime, int numBurger, int numFries, int numCoke, int numSundae){
        this.dinerID = nextDinerID;
        nextDinerID ++;
        state = DinerState.NOT_ARRIVED;
        this.arrivalTime = arrivalTime;
        this.table = null;
        this.myOrder = new Order(numBurger, numFries, numCoke, numSundae, dinerID);
        dinerMap.put(dinerID, this);
    }
    public boolean isArrived() {
        assert getState() == DinerState.NOT_ARRIVED;
        if(Environment.clock.getCurrentTime() < this.arrivalTime){
            return false;
        }
        return true;
    }

    public boolean isFinishedEat(){
//        System.out.println(String.format("Is Finished Eat: %d < %d",
//                Environment.clock.getCurrentTime(), startEatTime + eatDuration));
        if(Environment.clock.getCurrentTime() >= startEatTime + eatDuration){
            return true;
        }
        return false;
    }

    public void occupyTable(Table t) {
        this.table = t;
    }

    public Order getOrder(){
        return this.myOrder;
    }

    public int getID(){
        return this.dinerID;
    }

    public DinerState getState() {
        return state;
    }

    public void setState(DinerState state) {
        this.state = state;
    }

    public void setStartEatTime(int startEatTime) {
        this.startEatTime = startEatTime;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Diner#%3d:  -State: %s, -Arrived@ %3d min, -Table: %s -Ordered %s, ",
                dinerID, state,arrivalTime, myOrder.toString(), table));
        return builder.toString();
    }

}
