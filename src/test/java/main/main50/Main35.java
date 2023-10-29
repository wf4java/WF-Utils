package main.main50;

import wf.utils.java.algoritms.random.RandomStringGenerator;
import wf.utils.java.time.execution.ExecutionTimeNano;

import java.util.HashSet;
import java.util.Set;

public class Main35 {


    public static void main(String[] args) {

        int each = 10_000_000;
        int collision = 0;

        ExecutionTimeNano executionTimeNano = new ExecutionTimeNano();

        Set<String> set = new HashSet<>(each);

        for(int i = 0; i < each; i++){
            String s = RandomStringGenerator.getRandomString(16);
            if(set.contains(s)) collision++;
            else set.add(s);
        }

        System.out.println(executionTimeNano.getSeconds() + " seconds");
        System.out.println("Collisions: " + collision);

    }


}
