package com.lowson.Role;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public enum Food {
    Burger("Burger", 5, Machine.BurgerMach),
    Fries("Fries", 3, Machine.FriesMach),
    Coke("Coke", 2, Machine.CokeMach),
    Sundae("Sundae", 1, Machine.SundaeMach);

    private final Object cookTime;
    private final String foodName;
    private final Machine machine;

    Food(String foodName, int cookTime, Machine machine) {
        this.foodName = foodName;
        this.cookTime= cookTime;
        this.machine = machine;
    }

    public Machine getCorrespondingMachine() {
        return machine;
    }

}
