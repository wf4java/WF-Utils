package main;

import wf.utils.java.time.execution.ExecutionTime;
import wf.utils.java.time.execution.ExecutionTimeNano;

public class Main7 {


    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(1000);

        ExecutionTimeNano time = new ExecutionTimeNano();

        Thread.sleep(4000);

        System.out.println(time.getSeconds());
        System.out.println(time.getSecondsFormatted());
        System.out.println(time.getSecondsFormatted(4));

        Thread.sleep(4000);

        System.out.println(time.getSeconds());
        System.out.println(time.getSecondsFormatted());
        System.out.println(time.getSecondsFormatted(4));

        time.update();

        Thread.sleep(5000);

        System.out.println(time.getSeconds());
        System.out.println(time.getSecondsFormatted());
        System.out.println(time.getSecondsFormatted(4));

    }


}
