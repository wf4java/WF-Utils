package wf.utils.java.math.smooth.controller;

import wf.utils.java.math.smooth.SmoothTransform;


import java.util.function.DoubleConsumer;

public class Item extends SmoothTransform {

    private DoubleConsumer runnable;

    public Item(double from, double to, long duration, SmoothTransformFunction function, DoubleConsumer runnable) {
        super(from, to, duration, function);
        this.runnable = runnable;
    }

    public Item(double from, double to, DoubleConsumer runnable) {
        super(from, to);
        this.runnable = runnable;
    }

    public Item(double from, double to, long duration, DoubleConsumer runnable) {
        super(from, to, duration);
        this.runnable = runnable;
    }

    public Item(double from, double to, SmoothTransformFunction function, DoubleConsumer runnable) {
        super(from, to, function);
        this.runnable = runnable;
    }


    public DoubleConsumer getRunnable() {
        return runnable;
    }

    public void run(){
        runnable.accept(super.get());
    }

    public void setRunnable(DoubleConsumer runnable) {
        this.runnable = runnable;
    }

    @Override
    public String toString() {
        return "Item{" +
                 super.toString() +
                "runnable=" + runnable +
                '}';
    }
}
