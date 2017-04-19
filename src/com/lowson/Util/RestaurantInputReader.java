package com.lowson.Util;

import com.lowson.Role.Diner;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by lenovo1 on 2017/3/25.
 */
public class RestaurantInputReader {
    BufferedReader br =null;

    public RestaurantInputReader(String fileName)
            throws IOException {
        FileReader fr = new FileReader(fileName);
        br = new BufferedReader(fr);
    }

    public RestaurantInputReader(InputStream in)
            throws IOException {
        br = new BufferedReader(new InputStreamReader(in));
    }

    //TODO
    public int readLineAsNum() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    public Diner readLinesAsDiner() throws IOException {
        String s = br.readLine();
        if (s == null){
            return null;
        }

        StringTokenizer tokenizer = new StringTokenizer(s);
        int arrival_time = Integer.parseInt(tokenizer.nextToken());
        int num_burger = Integer.parseInt(tokenizer.nextToken());
        int num_fry = Integer.parseInt(tokenizer.nextToken());
        int num_coke = Integer.parseInt(tokenizer.nextToken());
        int num_sundane = Integer.parseInt(tokenizer.nextToken());
        Diner d = new Diner(arrival_time, num_burger, num_fry, num_coke, num_sundane);
//        System.out.println(d);
        return d;
    }
}
