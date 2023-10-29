package main.main50;

import wf.utils.java.algoritms.random.RandomStringGenerator;
import wf.utils.java.time.execution.ExecutionTimeNano;

public class Main34 {


    public static void main(String[] args) {

        int length = 500_000_000;

        try {Thread.sleep(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}

        ExecutionTimeNano executionTimeNano = new ExecutionTimeNano();

        //System.out.println(RandomStringGenerator.getRandomString(length));
        RandomStringGenerator.getRandomString(length);

        System.out.println(executionTimeNano.getSeconds());

        System.gc();
        try {Thread.sleep(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}
        executionTimeNano.update();

        //System.out.println(RandomStringGenerator.getRandomString2(length));


        System.out.println(executionTimeNano.getSeconds());
    }


}
