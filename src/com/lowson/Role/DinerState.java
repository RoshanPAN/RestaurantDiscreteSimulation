package com.lowson.Role;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public enum DinerState {
    NOT_ARRIVED("Not Arrived'"),
    WAIT_FOR_TABLE("Wait For Table"),
    WAIT_FOR_FOOD("Wait For Food"),
    EATING("Eating"),
    LEFT("Left");

    String state;

    DinerState(String state){ this.state = state; }

    @Override
    public String toString(){
        return state;
    }
}
