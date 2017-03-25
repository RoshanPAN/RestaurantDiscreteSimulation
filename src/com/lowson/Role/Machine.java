package com.lowson.Role;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public enum Machine {
    BurgerMach("Burger", 5),
    FriesMach("Fries", 3),
    SodaMach("Soda", 2),
    SundaeMach("Sundae", 1);

    private int cookTimeMin;
    private String foodName;

    Machine(String foodName, int cookTime){
        this.foodName = foodName;
        this.cookTimeMin = cookTime;
    }

    public int getCookTimeMin(){
        return this.cookTimeMin;
    }

    public int getCookTimeSec(){
        return this.cookTimeMin * 60;
    }

    public String getFoodName(){
        return this.foodName;
    }

}

class FoodMachine{
    String foodName;
    int cookTime;
    public FoodMachine(String foodName, int cookTimeMin){
        this.foodName = foodName;
        this.cookTime = cookTimeMin;
    }
}