package com.lowson.Role

/**
 * Created by lenovo1 on 2017/3/25.
 */
class DinerTest extends GroovyTestCase {
    void testDinner(){
        Diner d = new Diner(0, 1, 1, 1, 1)
        assertSame(d.getState(), DinerState.NOT_ARRIVED)
        assertTrue(d.getOrder().dinerID == d.getID())
        assertSame(Diner.dinerMap[d.getID()], d)
        println d

    }

}
