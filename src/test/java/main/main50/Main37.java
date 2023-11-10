package main.main50;

import wf.utils.java.object.ObjectToString;
import wf.utils.java.object.ReflectionUtils;
import wf.utils.java.thread.loop.ThreadLoopTask;

import java.util.ArrayList;
import java.util.HashMap;


public class Main37 {


    public static void main(String[] args) {

        
        ArrayList<String> list = new ArrayList<>();
        list.add("1");


        System.out.println(ObjectToString.toString(new Thread(""), true));



//        ArrayList<String> list = new ArrayList<>();
//
//        ReflectionUtils.setValue(list, "size", 500);
//
//        System.out.println(list.size());
//
//        System.out.println(ReflectionUtils.getValue(list, "size"));


    }




}
