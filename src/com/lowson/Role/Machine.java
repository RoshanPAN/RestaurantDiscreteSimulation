package com.lowson.Role;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public enum Machine {
    BurgerMach("Burger Machine", 5),
    FriesMach("Fries Machine", 3),
    CokeMach("Coke Machine", 2),
    SundaeMach("Sundae Machine", 1);

    private int cookTime;
    private String foodMachineName;

    Machine(String foodMachineName, int cookTime){
        this.foodMachineName = foodMachineName;
        this.cookTime = cookTime;
    }

    public int getCookTimeMin(){
        return this.cookTime;
    }

    public int getCookTimeSec(){
        return this.cookTime * 60;
    }

    public String getfoodMachineName(){
        return this.foodMachineName;
    }

    @Override
    public String toString(){
        return String.format("%s(%dmin)", foodMachineName, cookTime);
    }

}
