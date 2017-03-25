package com.lowson.Role;

import com.sun.javafx.binding.StringFormatter;

import java.util.ArrayList;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public class Diner {
    public static ArrayList<Diner> dinerList = new ArrayList<>();
    static int nextDinerID = 0;
    private int dinerID;
    private int arrivalTime;
    private Order myOrder;
    private DinerState state;

    public Diner(int arrivalTime, int numBurger, int numFries, int numCoke, int numSundae){
        this.dinerID = nextDinerID;
        nextDinerID ++;
        state = DinerState.NOT_ARRIVED;
        this.arrivalTime = arrivalTime;
        this.myOrder = new Order(numBurger, numFries, numCoke, numSundae, dinerID);
        dinerList.add(this);
    }

    public Order myOrder(){
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

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(StringFormatter.format("Diner#%03d: Arrived@%dmin, ordered %s",
                dinerID, arrivalTime, myOrder.toString()));
        return builder.toString();
    }
}
