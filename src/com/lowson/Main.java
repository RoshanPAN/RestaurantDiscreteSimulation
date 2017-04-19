package com.lowson;

import com.lowson.Util.Environment;
import com.lowson.Util.RestaurantInputReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //Step1: Environment Initialization from file
        System.out.println("\n\n<<<<< Step1: Environment Initialization from file <<<<<");
        RestaurantInputReader reader = null;
        try {
            System.out.println("Trying to read from stdin ...");
            reader = new RestaurantInputReader(System.in);
            Environment.initAllRoles(reader);
        } catch (IOException e) {
            System.out.println("Failed reading file.");
            e.printStackTrace();
            System.exit(-1);
        }

        //Step2: Threads initialization
        System.out.println("\n\n<<<<< Step2: Threads initialization <<<<<");
        Environment.initThreads();

        /*
        Step3: Start Simulation.
         */
        System.out.println("\n\n<<<<<  Step3: Start Simulation. <<<<<");
        Environment.startAllThreads();

        /*
        Step4: Clock Thread will be in charge.
         */

    }

}
