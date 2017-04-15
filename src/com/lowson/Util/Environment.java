package com.lowson.Util;

import com.lowson.Role.Cook;
import com.lowson.Role.Diner;
import com.lowson.Role.Machine;
import com.lowson.Role.Table;
import com.lowson.Scheduler.Scheduler;
import com.lowson.Scheduler.SimpleScheduler;
import com.lowson.Threads.CookThread;
import com.lowson.Threads.DinerThread;

import java.util.LinkedList;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class Environment {
    public static final int SimulationDuration = 120;
    public static final Scheduler scheduler = SimpleScheduler.getInstance();
    public static RelativeTimeClock clock;
    public static final LinkedList<Table> availTables = new LinkedList<>();
    public static final LinkedList<CookThread> cookThreadPool = new LinkedList<>();
    public static final LinkedList<DinerThread> dinerThreadPool = new LinkedList<>();
    public static int num_dinner;
    public static int num_table;
    public static int num_cook;
    public static int dinnerEatDuration = 30;


    public static void initAllRoles(){
        RestaurantInputReader reader = new RestaurantInputReader(System.in);
        num_dinner = reader.readLineAsNum();
        num_table = reader.readLineAsNum();
        num_cook = reader.readLineAsNum();
        // Diner
//        while(reader.hashNext()){
//            // TODO read each line as diner & add into the dinerList
//        }
//        assert Diner.dinerMap.size() == num_dinner;

        // Cook
        Cook c;
        for(int i = 0; i < num_cook; i++){
            c = new Cook();
        }

        // Machine - enum
        for(Machine m: Machine.values()){
            scheduler.availMachines.add(m);
        }

        // Table
        Table t;
        for(int i = 0; i < num_table; i++){
            t = new Table();
            availTables.offer(t);
        }

        // Scheduler
        scheduler.resetOrderPool();

        // Clock
        clock = RelativeTimeClock.getInstance();
        clock.initClock();
        assert  clock.getCurrentTime() == 0;
    }


    public static void initAllRolesForTest(int num_dinner, int num_table, int num_cook){
        RestaurantInputReader reader = new RestaurantInputReader(System.in);
        num_dinner = num_dinner;
        num_table = num_table;
        num_cook = num_cook;

        // Diner
        for(int i = 0; i < num_dinner; i++){
            new Diner(i, i, i, i, i);
        }
        assert Diner.dinerMap.size() == num_dinner;

        // Cook
        Cook c;
        for(int i = 0; i < num_cook; i++){
            c = new Cook();
        }

        // Machine - enum
        for(Machine m: Machine.values()){
            scheduler.availMachines.add(m);
        }

        // Table
        Table t;
        for(int i = 0; i < num_table; i++){
            t = new Table();
            availTables.offer(t);
        }

        // Scheduler
        scheduler.resetOrderPool();

        // Clock
        clock = RelativeTimeClock.getInstance();
        clock.initClock();
        assert  clock.getCurrentTime() == 0;
    }

    public static void initThreads(){

    }

}
