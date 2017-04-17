package com.lowson.Role;

/**
 * Created by lenovo1 on 2017/4/15.
 */
public class Task {
    private Food food;
    private int foodCnt;

    public Task(Food f, int foodCnt){
        this.food = f;
        this.foodCnt = foodCnt;
    }

    public int getProcessingTime(){
        return food.getCookTime() * foodCnt;
    }

    public Food getFood() {
        return food;
    }

    public int getFoodCnt() {
        return foodCnt;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(String.format("Task(%s x %d)", food, foodCnt));
        return b.toString();
    }
}
