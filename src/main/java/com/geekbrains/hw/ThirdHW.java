package com.geekbrains.hw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdHW {
    public static void main(String[] args) {
        /*String test = "123";
        String test2 = "456";

        if(test.equals(test2)) {
            System.out.println("Равны");
        } else {
            System.out.println("Не равны");
        }*/


        List<String> stringList = List.of("123", "456", "123", "678", "678", "678");

        HashMap<String, Integer> counter = new HashMap<>();
        for(String string: stringList) {
            if(counter.containsKey(string)) {
                counter.put(string, counter.get(string) + 1);
            } else {
                counter.put(string, 1);
            }
        }

        for(Map.Entry<String, Integer> object: counter.entrySet()) {
            System.out.println(object.getKey() + " " + object.getValue());
            System.out.println();
        }
    }
}
