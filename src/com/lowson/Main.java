package com.lowson;

import com.lowson.Util.Environment;
import com.lowson.Util.RestaurantInputReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //        LinkedList<Integer> taskList = new LinkedList<>();
//        taskList.add(5);
//        taskList.add(2);
//        taskList.add(3);
//        taskList.add(6);
//        taskList.add(1);
//        Collections.sort(taskList, (a, b) -> a > b ? -1 : (a == b ? 0 : 1));
//        System.out.println(taskList.toString());


        //Step1: Environment Initialization from file
        System.out.println("######## Step1: Environment Initialization from file######\n\n");
        RestaurantInputReader reader = null;
        try {
//            reader = new RestaurantInputReader("./resource/data1.txt");
            reader = new RestaurantInputReader(System.in);
            Environment.initAllRoles(reader);
        } catch (IOException e) {
            System.out.println("Failed reading file.");
            e.printStackTrace();
            System.exit(-1);
        }

        //Step2: Threads initialization
        System.out.println("######## Step2: Threads initialization ######\n\n");
        Environment.initThreads();

        System.out.println(Environment.scheduler);
        /*
        Step3: Start Simulation.
         */
        System.out.println("######## Step3: Start Simulation. ######\n\n");
        Environment.startAllThreads();

        /*
        Step4: Clock Thread will be in charge.
         */

    }

}
