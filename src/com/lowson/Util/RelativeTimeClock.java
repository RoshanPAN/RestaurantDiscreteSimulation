package com.lowson.Util;

/**
 * The Relative time clock.
 * Singleton using IoDH.
 */
public class RelativeTimeClock{
    private int endTime;
    private int currentTime;
    private int timeUnit;
    private RelativeTimeClock(){}

    // Singleton - IoDH
    public static RelativeTimeClock getInstance(){
        return ClockHolder.clock;
    }

    public static class ClockHolder{
        private final static RelativeTimeClock clock = new RelativeTimeClock();
    }

    // Initialize clock
    public void initClock(int duration){
        initClock(0, duration, 1);
    }

    public void initClock(int startTime, int duration, int timeUnit){
        this.currentTime = startTime;
        this.endTime = duration + startTime;
        this.timeUnit = timeUnit;
    }


    public int getCurrentTime(){
        return this.currentTime;
    }
    public int getEndTime(){ return this.endTime; }


    public void increment(){
        this.currentTime ++;
    }

    @Override
    public String toString(){
        return String.format("Time:%4d min\n", currentTime).toString();
    }
}
