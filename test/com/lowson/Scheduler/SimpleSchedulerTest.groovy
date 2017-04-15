package com.lowson.Scheduler

import com.lowson.Role.Order

/**
 * Created by lenovo1 on 2017/3/26.
 */
class SimpleSchedulerTest extends GroovyTestCase {
    void testGetInstance() {
        SimpleScheduler scheduler = SimpleScheduler.getInstance()
        Order order0 = new Order(1, 1, 1, 1, 1)
        scheduler.submitOrder(order0)
        scheduler

        Schedule schedule = scheduler.getSchedule()

    }

    void testSubmitOrder() {

    }

    void testGetOrder() {

    }

    void testApplyTaskOrderingLogic() {

    }

    void testGetTaskCnt() {

    }
}
