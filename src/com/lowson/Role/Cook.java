package com.lowson.Role;

import com.lowson.Scheduler.Schedule;
import com.lowson.Exception.UnexpectedBehaviorException;

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

    public void setFinishedTask(Task task){
        this.schedule.getTaskList().remove(task);
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

    public boolean isScheduleFinished() {
        if(schedule == null)
            throw new UnexpectedBehaviorException("Checking Schedule status when no schedule set");

        if(schedule.getTaskList().size() == 0){
            return true;
        }else{
            return false;
        }
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule getSchedule(){
        return this.schedule;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Cook#%-3d: State=%s, schedule: %s", cookID, state, schedule));
        return builder.toString();
    }
}
