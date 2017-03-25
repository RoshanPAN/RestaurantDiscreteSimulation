package com.lowson.Util;

import com.lowson.Role.Cook;
import com.lowson.Role.Diner;
import com.lowson.Role.Table;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class Environment {
    static RelativeTimeClock clock;

    public void initClock(){
        clock = RelativeTimeClock.getInstance();
        clock.initClock(Config.SimulationDuration);
//        clock.initClock(0, Conf.SimulationDuration, 1);
    }

    public static void initAllRoles(){
        RestaurantInputReader reader = new RestaurantInputReader(System.in);
        int num_dinner = reader.readLineAsNum();
        int num_table = reader.readLineAsNum();
        int num_cook = reader.readLineAsNum();
        // Diner
        reader.readLinesAsDiner();
        assert Diner.dinerList.size() == num_dinner;

        // Cook
        for(int i = 0; i < num_cook; i++){
            new Cook();
        }
        // Machine - enum
        // Table
        for(int i = 0; i < num_table; i++){
            new Table();
        }

    }

    public static void initThreads(){

    }

}
