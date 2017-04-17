package com.lowson.Scheduler;

import com.lowson.Role.Diner;
import com.lowson.Role.DinerState;
import com.lowson.Role.Food;
import com.lowson.Role.Table;
import com.lowson.Util.Environment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lenovo1 on 2017/4/16.
 */
public class TableScheduler {
    public static final LinkedList<Table> availTables = new LinkedList<>();

    public List<Diner> getScheduledDiners(){
        Food bottleNeck = Environment.scheduler.getBottleNeck();
        ArrayList<Diner> waitingTableDiner = new ArrayList<>();
        for(Diner d: Diner.dinerMap.values()){
            if(d.getState() == DinerState.WAIT_FOR_TABLE){
                waitingTableDiner.add(d);
            }
        }
        waitingTableDiner.sort(Comparator.comparingInt(a -> -1 * a.getOrder().getOrderedFoodCnt(bottleNeck)));
        return waitingTableDiner.subList(0, Math.min(availTables.size(), waitingTableDiner.size()));
    }

    public Table getTable(){
        return availTables.pollFirst();
    }

    public void releaseTable(Table table){
        availTables.offer(table);
    }
}
