package com.lowson.Role;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public enum CookState {
    IDLE("Idle"),
    WORK_ON_MACHINE("Working On Machine");

    String state;

    CookState(String state){ this.state = state; }

    @Override
    public String toString(){
        return state;
    }

}
