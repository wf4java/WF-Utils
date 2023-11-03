package main.main50;

import wf.utils.java.object.ObjectToString;
import wf.utils.java.object.ReflectionUtils;
import wf.utils.java.thread.loop.ThreadLoopTask;
import wf.utils.java.time.cooldown.CoolDown;
import wf.utils.java.time.execution.ExecutionTimeNano;


public class Main37 {


    public static void main(String[] args) {


        ExecutionTimeNano executionTimeNano = new ExecutionTimeNano();

        executionTimeNano.update();


        System.out.println(ObjectToString.convert(executionTimeNano, true));;





        ThreadLoopTask task = new ThreadLoopTask(() -> {}, 100,100);

        ReflectionUtils.setValue(task, "thread", new Thread("Thread-test"));

        System.out.println(ReflectionUtils.getValue(task, "thread", Thread.class).getName());


    }


}
