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

    public void initClock(){
        this.initClock(Environment.SimulationDuration);
//        clock.initClock(0, Conf.SimulationDuration, 1);
    }

    // Initialize clock
    private void initClock(int duration){
        initClock(0, duration, 1);
    }

    private void initClock(int startTime, int duration, int timeUnit){
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
        return String.format("Relative Time:%4d min\n", currentTime).toString();
    }
}
