package com.lowson.Role;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public enum Food {
    Burger("Burger", 5),
    Fries("Fries", 3),
    Coke("Coke", 2),
    Sundae("Sundae", 1);


    private final Object cookTime;
    private final String foodName;

    Food(String foodName, int cookTime) {
        this.foodName = foodName;
        this.cookTime= cookTime;
    }
}
