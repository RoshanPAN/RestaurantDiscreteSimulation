package com.lowson.Util;

import com.lowson.Role.Diner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class RestaurantInputReader {
    BufferedReader br =null;

    public RestaurantInputReader(String fileName)
            throws FileNotFoundException {
        FileReader fr = new FileReader(fileName);
        br = new BufferedReader(fr);
        // parse content


    }

    //TODO
    public int readLineAsNum(){
        int num = 0;

        return num;
    }

    public Diner readLinesAsDiner(){
        Diner d = null;
        // read until there is no valid line in the file
        // TODO

        return d;
    }

    public boolean hashNext() {
        // TODO
        return false;
    }
}
