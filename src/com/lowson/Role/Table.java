package com.lowson.Role;

import java.util.ArrayList;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public class Table {
    static ArrayList<Table> tableList = new ArrayList<>();
    static int nextTableID = 0;
    private int tableID;

    public Table(){
        this.tableID = nextTableID;
        nextTableID ++;
        tableList.add(this);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(String.format("Table#%d", tableID));
        return b.toString();
    }
}
