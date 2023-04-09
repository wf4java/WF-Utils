package wf.utils.java.math.smooth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmoothTransformEntityController<T> {

    private Map<T, Item> items;

    private Thread thread;
    private int interval;



    public SmoothTransformEntityController(int interval) {
        this(interval, new HashMap<>());
    }

    public SmoothTransformEntityController(int interval, Map<T, Item> mapTyped) {
        this.items = mapTyped;
        this.interval = interval;
        this.thread = new Thread(() -> {

            while (true){
                this.items.forEach((k, v) -> v.run());
                this.items.entrySet().removeIf((e) -> e.getValue().isEnd());

                try {Thread.sleep(this.interval);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        });
        this.thread.setDaemon(true);
        this.thread.start();
    }

    public void setOrUpdateItem(T t, Item item){
        Item currentItem = items.get(t);
        if(currentItem == null) items.put(t, item);
        else {
            currentItem.setTo(item.getTo());
            currentItem.setRunnable(item.getRunnable());
            currentItem.setDuration(item.getDuration());
        }
    }

    public void setItem(T t, Item item){
        items.put(t, item);
    }





    public Map<T, Item> getItems() {
        return items;
    }

    public void setItems(Map<T, Item> items) {
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
