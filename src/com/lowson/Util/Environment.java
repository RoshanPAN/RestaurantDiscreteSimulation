package com.lowson.Util;

import com.lowson.Role.*;
import com.lowson.Scheduler.Scheduler;
import com.lowson.Scheduler.SimpleScheduler;
import com.lowson.Scheduler.TableScheduler;
import com.lowson.Threads.ClockThread;
import com.lowson.Threads.CookThread;
import com.lowson.Threads.DinerThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class Environment {
    public static final int SimulationDuration = 120;
    public static final Scheduler scheduler = SimpleScheduler.getInstance();
    public static final TableScheduler tableScheduler = new TableScheduler();
    public static RelativeTimeClock clock;
//    public static final LinkedList<Table> availTables = new LinkedList<>();
    public static final LinkedList<CookThread> cookThreadPool = new LinkedList<>();
    public static final LinkedList<DinerThread> dinerThreadPool = new LinkedList<>();
    public static ClockThread clockThread = null;
    public static final ArrayList<Diner> dinerList = new ArrayList<>();
    public static final ArrayList<Cook> cookList = new ArrayList<>();
    public static int num_dinner;
    public static int num_table;
    public static int num_cook;
    public static int dinnerEatDuration = 30;


    public static void initAllRoles(RestaurantInputReader reader)
            throws IOException {
        num_dinner = reader.readLineAsNum();
        num_table = reader.readLineAsNum();
        num_cook = reader.readLineAsNum();
        System.out.println(String.format("[Environment Init] Diner:%d, Table:%d, Cook:%d.", num_dinner, num_table, num_cook));
        // Diner
        for(int i=0; i<num_dinner; i++){
            dinerList.add(reader.readLinesAsDiner());
        }
        assert reader.readLinesAsDiner() == null;

        // Cook
        for(int i = 0; i < num_cook; i++){
            cookList.add(new Cook());
        }

        // Machine - enum
        for(Machine m: Machine.values()){
            scheduler.availMachines.add(m);
        }

        // Table
        Table t;
        for(int i = 0; i < num_table; i++){
            t = new Table();
            TableScheduler.availTables.offer(t);
        }

        // Scheduler - nothing to do
        System.out.print(Environment.tableScheduler);
        System.out.println(Environment.scheduler);

        // Clock
        clock = RelativeTimeClock.getInstance();
        clock.initClock();
        assert  clock.getCurrentTime() == 0;

    }


    public static void initThreads(){
        // Init Clock Thread
        clockThread = new ClockThread();
        // Init Diner Thread
        assert dinerList.size() > 0;
        for(Diner d: dinerList){
            dinerThreadPool.offer(new DinerThread(d));
        }
        // Init Cook Thread
        assert cookList.size() > 0;
        for(Cook c: cookList){
            cookThreadPool.offer(new CookThread(c));
        }
    }


    public static void startAllThreads() {
        // start diner
        for(DinerThread dt: dinerThreadPool){
            dt.start();
        }

        try {
            sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // start cook
        for(CookThread ct: cookThreadPool){
            ct.start();
        }

        try {
            sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // start clock
        clockThread.run();
    }
}
