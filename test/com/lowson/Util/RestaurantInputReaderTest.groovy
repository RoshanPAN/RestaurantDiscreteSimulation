package com.lowson.Util

/**
 * Created by lenovo1 on 2017/4/15.
 */
class RestaurantInputReaderTest extends GroovyTestCase {
    void testInputReader(){
        RestaurantInputReader reader = new RestaurantInputReader("./resource/data1.txt");
        printf  reader.readLineAsNum().toString()

    }
}
