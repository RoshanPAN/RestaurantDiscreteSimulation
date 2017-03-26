package com.lowson.Threads;

import com.lowson.Role.Cook;
import com.lowson.Util.RelativeTimeClock;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public class CookThread extends Thread{

    Cook cook;
    RelativeTimeClock clock;

    public CookThread(Cook cook){
        this.cook = cook;
        this.clock = RelativeTimeClock.getInstance();
    }

    @Override
    public void run() {
        synchronized (cook){

        }
    }
}
