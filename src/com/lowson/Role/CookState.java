package com.lowson.Role;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public enum CookState {
    IDLE("Idle"),
    WORKING("Working");

    String state;

    CookState(String state){ this.state = state; }

    @Override
    public String toString(){
        return state;
    }

}
