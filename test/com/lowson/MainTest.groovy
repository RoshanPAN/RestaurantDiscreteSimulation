package com.lowson

/**
 * Created by lenovo1 on 2017/4/16.
 */
class MainTest extends GroovyTestCase {
    void test(){
        // evaluate invalid input
        FileInputStream fis = new FileInputStream(new File("./resource/data1.txt"));
        System.setIn(fis);
        Main.main()
    }
}
