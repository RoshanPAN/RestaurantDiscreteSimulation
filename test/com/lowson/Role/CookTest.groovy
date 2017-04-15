package com.lowson.Role

import com.lowson.Scheduler.Scheduler
import com.lowson.Util.Environment
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
        assertSame(1000, c1.getStartWorkingTime())


    }


    void testIsTaskFinished() {
//        Diner d = new Diner(1, 1, 1, 1, 1)
//        Schedule s1 = new Schedule(d.getOrder(), Food.Burger, Machine.BurgerMach, 1)
//        Schedule s2 = new Schedule(d.getOrder(), Food.Coke, Machine.CokeMach, 1)
//        Cook c0 = new Cook()
//        Cook c1 = new Cook()
//
        Environment.initAllRolesForTest(2, 2, 2)
        assertSame(Diner.dinerMap.size(), 2)
        assertSame(Cook.cookList.size(), 2)
        assertSame(Table.tableList.size(), 2)
        assertSame(Environment.availTables.size(), 2)
        Cook c0 = Cook.cookList.get(0)
        Cook c1 = Cook.cookList.get(1)
        Diner d0 = Diner.dinerMap.get(0)
        Diner d1 = Diner.dinerMap.get(1)
        assertFalse(c0.isTaskFinished())
        assertFalse(c1.isTaskFinished())
        Scheduler scheduler = Environment.scheduler
        assertSame(scheduler.getTotalUnfinishedOrderCnt(), 0)
        scheduler.submitOrder(d0.getOrder())
        assertSame(scheduler.getTotalUnfinishedOrderCnt(), 1)
        scheduler.submitOrder(d1.getOrder())
        scheduler.submitOrder(d1.getOrder())



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
