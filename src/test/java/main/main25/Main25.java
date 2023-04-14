package main.main25;

import wf.plugin.utils.main.Main;
import wf.utils.java.math.smooth.controller.Item;
import wf.utils.java.thread.queue.QueueKey;

import java.util.HashMap;

public class Main25 {


    public static void main(String[] args) {

        HashMap<String, String> map = new HashMap<>();

        map.put("1", "a");
        map.put("2", "a");
        map.put("3", "a");
        map.put("4", "a");
        map.put("5", "a");

        map.entrySet().removeIf((e) -> e.getKey().equals("5"));

        System.out.println(map);


    }


}
