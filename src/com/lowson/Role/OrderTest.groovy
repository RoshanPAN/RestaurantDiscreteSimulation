package com.lowson.Role

/**
 * Created by lenovo1 on 2017/3/25.
 */
class OrderTest extends GroovyTestCase {
    void testOrder() {
        Order order1 = new Order(5, 5, 5, 5, 0)
        assertSame(order1.getOrderID(), 0)
        printf order1.toString()
        assertSame(order1.isReady(), false);
        order1.addFinishedFood(Food.Burger, 5)
        assertFalse(order1.isReady())
        order1.addFinishedFood(Food.Coke, 5)
        assertFalse(order1.isReady())
        order1.addFinishedFood(Food.Fries, 5)
        assertFalse(order1.isReady())
        order1.addFinishedFood(Food.Sundae, 5)
        assertTrue(order1.isReady())
        println order1.toString()
    }

}
