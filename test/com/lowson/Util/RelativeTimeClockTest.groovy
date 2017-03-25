package com.lowson.Util

/**
 * Created by lenovo1 on 2017/3/25.
 */
class RelativeTimeClockTest extends GroovyTestCase {
    void testClock(){
        RelativeTimeClock clock = RelativeTimeClock.getInstance();
        printf clock.toString()
        assertSame(clock.getCurrentTime(), 0)
        RelativeTimeClock clock2 = RelativeTimeClock.getInstance()
        assertSame(clock, clock2)
        clock.increment();
        assertSame(clock.getCurrentTime(), 1)
        clock.increment()
        clock.increment()
        assertSame(clock.getCurrentTime(), 3)
        println clock2.toString()
    }
}
