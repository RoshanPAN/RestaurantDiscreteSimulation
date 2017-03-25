package com.lowson.Role;

import java.util.ArrayList;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public class Cook {
    public static ArrayList<Cook> cookList = new ArrayList<>();
    static int nextCookID = 0;
    private int cookID;
    private CookState state;

    public Cook(){
        cookID = nextCookID;
        state = CookState.IDLE;
        nextCookID = nextCookID + 1;
        cookList.add(this);
    }

    public CookState getState() {
        return state;
    }

    public void setState(CookState state) {
        this.state = state;
    }
}
