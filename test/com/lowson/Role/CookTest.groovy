package com.lowson.Role

import com.lowson.Scheduler.Schedule

/**
 * Created by lenovo1 on 2017/3/26.
 */
class CookTest extends GroovyTestCase {

    void testGetID() {
        Cook c1 = new Cook()
        assertSame(Cook.cookList.size(), 1)
        assertSame(c1.getID(), 0)
        Cook c2 = new Cook()
        assertSame(Cook.cookList.size(), 2)
        assertSame(c2.getID(), 1)
        assertSame(Cook.cookList.get(1), c2)
        assertSame(Cook.cookList.get(0), c1)
        c1.setState(CookState.IDLE)
        assertSame(c1.getState(), CookState.IDLE)
        c2.setState(CookState.WORKING)
        assertSame(c2.getState(), CookState.WORKING)

        // StartWorkingTime
        c1.setStartWorkingTime(1)
        assertSame(c1.getStartWorkingTime(), 1)
        c2.setStartWorkingTime(1000)
        assertTrue(c2.getStartWorkingTime() == 1000)
        println c1
        LinkedList<Task> taskList = new LinkedList<>();
        taskList.add(new Task(Food.Burger, 1))
        taskList.add(new Task(Food.Fries, 1))
        c1.setSchedule(new Schedule(new Order(1, 1, 1, 1, 1), taskList))
        println c1
    }


    /*
    public boolean isTaskFinished(Schedule schedule) {
        int cookTimePerFood = schedule.getTask().getCookTime();
        int taskCnt = schedule.getTaskCnt();
        int endTime = this.startWorkingTime + cookTimePerFood * taskCnt;
        if(Environment.clock.getCurrentTime() < endTime){
            return false;
        }
        return true;
    }
     */
}
