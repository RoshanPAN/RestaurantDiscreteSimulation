package com.lowson.Role;

import com.lowson.Scheduler.Schedule;
import com.lowson.Util.Environment;

import java.util.ArrayList;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public class Cook {
    public static ArrayList<Cook> cookList = new ArrayList<>();
    static int nextCookID = 0;

    private int cookID;
    private CookState state;
    private int startWorkingTime;
    private Schedule schedule;

    public Cook(){
        cookID = nextCookID;
        state = CookState.IDLE;
        nextCookID = nextCookID + 1;
        cookList.add(this);
        schedule = null;
    }

    public int getID(){ return cookID; }


    public CookState getState() {
        return state;
    }

    public void setState(CookState state) {
        this.state = state;
    }

    public int getStartWorkingTime() {
        return startWorkingTime;
    }

    public void setStartWorkingTime(int startWorkingTime) {
        this.startWorkingTime = startWorkingTime;
    }

    public boolean isTaskFinished() {
        int cookTimePerFood = schedule.getTask().getCookTime();
        int taskCnt = schedule.getTaskCnt();
        int endTime = this.startWorkingTime + cookTimePerFood * taskCnt;
        if(Environment.clock.getCurrentTime() < endTime){
            return false;
        }
        return true;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
