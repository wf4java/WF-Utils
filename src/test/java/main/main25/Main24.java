package main.main25;


import wf.utils.java.math.smooth.SmoothTransformFunctionType;
import wf.utils.java.math.smooth.controller.Item;
import wf.utils.java.math.smooth.controller.SmoothTransformEntityController;

import java.util.function.DoubleConsumer;

public class Main24 {


    public static void main(String[] args) {


        SmoothTransformEntityController<String> controller = new SmoothTransformEntityController<>(50);

        controller.setOrUpdateItem("test",new Item(0, 100, SmoothTransformFunctionType.EASE_BOTH,
                new DoubleConsumer() {
            @Override
            public void accept(double value) {
                System.out.println(value);
            }
        }));

        try {Thread.sleep(2500);} catch (InterruptedException e) {throw new RuntimeException(e);}

        controller.setOrUpdateItem("test",new Item(100, 150,10000,new DoubleConsumer() {
            @Override
            public void accept(double value) {
                System.out.println(value);
            }
        }));


        try {Thread.sleep(15000);} catch (InterruptedException e) {throw new RuntimeException(e);}

    }




}
