package wf.utils.java.math.smooth.controller;

import java.util.ArrayList;
import java.util.List;

public class SmoothTransformController {


    private List<Item> items;

    private Thread thread;
    private int interval;



    public SmoothTransformController(int interval) {
        this(interval, new ArrayList<>());
    }

    public SmoothTransformController(int interval, List<Item> listTyped) {
        this.items = listTyped;
        this.interval = interval;
        this.thread = new Thread(() -> {
            while (true){

                items.forEach(Item::run);
                items.removeIf(Item::isEnd);

                try {Thread.sleep(interval);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        });
        this.thread.setDaemon(true);
        this.thread.start();
    }

    public void addItem(Item item){
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "SmoothTransformController{" +
                "items=" + items +
                ", thread=" + thread +
                ", interval=" + interval +
                '}';
    }
}
