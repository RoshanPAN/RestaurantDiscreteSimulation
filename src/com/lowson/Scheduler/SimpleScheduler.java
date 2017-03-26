package com.lowson.Scheduler;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class SimpleScheduler extends Scheduler{
    private SimpleScheduler(){
        super();
    }

    // Singleton - IoDH
    public static SimpleScheduler getInstance() {
        return SimpleScheduler.SchedulerHolder.scheduler;
    }

    public static class SchedulerHolder{
        private final static SimpleScheduler scheduler = new SimpleScheduler();
    }

}
