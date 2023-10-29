package main.main50;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main32 {


    public static void main(String[] args) {


        HashMap<String, Integer> hashMap = new HashMap<>(2);



        hashMap.put("page", 1);
        hashMap.put("Aa", 1);
        hashMap.put("BB", 1);
        hashMap.put("has_next_page", 2);
        hashMap.put("has_prev_page", 3);


        boolean hasCollisions = false;
        Set<Map.Entry<String, Integer>> entries = hashMap.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            int hash = entry.getKey().hashCode();
            if (hashMap.containsKey(entry.getKey()) &&
                    hashMap.get(entry.getKey()) != entry.getValue()) {
                hasCollisions = true;
                break;
            }
        }


        if (hasCollisions) {
            System.out.println("HashMap содержит коллизии");
        } else {
            System.out.println("HashMap не содержит коллизий");
        }

    }




}
