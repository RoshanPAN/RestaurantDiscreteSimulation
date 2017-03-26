package com.lowson.Util;

import com.lowson.Role.Cook;
import com.lowson.Role.Diner;
import com.lowson.Role.Machine;
import com.lowson.Role.Table;
import com.lowson.Scheduler.Scheduler;
import com.lowson.Scheduler.SimpleScheduler;
import com.lowson.Threads.CookThread;
import com.lowson.Threads.DinerThread;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class Environment {
    public static final int SimulationDuration = 120;
    public static final Scheduler scheduler = SimpleScheduler.getInstance();
    public static RelativeTimeClock clock;
    public static final LinkedList<Table> availTables = new LinkedList<>();
    public static final HashSet<Machine> availMachines = new HashSet<>();
    public static final LinkedList<CookThread> cookThreadPool = new LinkedList<>();
    public static final LinkedList<DinerThread> dinerThreadPool = new LinkedList<>();
    public static int num_dinner;
    public static int num_table;
    public static int num_cook;
    public static int dinnerEatDuration = 30;

    public void initClock(){
        clock = RelativeTimeClock.getInstance();
        clock.initClock(SimulationDuration);
//        clock.initClock(0, Conf.SimulationDuration, 1);
    }

    public static void initAllRoles(){
        RestaurantInputReader reader = new RestaurantInputReader(System.in);
        num_dinner = reader.readLineAsNum();
        num_table = reader.readLineAsNum();
        num_cook = reader.readLineAsNum();
        // Diner
        reader.readLinesAsDiner();
        assert Diner.dinerList.size() == num_dinner;

        // Cook
        Cook c;
        for(int i = 0; i < num_cook; i++){
            c = new Cook();
        }
        // Machine - enum
        for(Machine m: Machine.values()){
            availMachines.add(m);
        }

        // Table
        Table t;
        for(int i = 0; i < num_table; i++){
            t = new Table();
            availTables.offer(t);
        }
    }

    public static void initThreads(){

    }

}
