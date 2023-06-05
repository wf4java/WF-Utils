package wf.utils.java.math.smooth.controller;

import wf.utils.java.math.smooth.SmoothTransform;

import java.util.HashMap;
import java.util.Map;

public class SmoothTransformEntity<T> {

    private Map<T, SmoothTransform> items;


    public SmoothTransformEntity() {
        this(new HashMap<>());
    }

    public SmoothTransformEntity(Map<T, SmoothTransform> mapTyped) {
        this.items = mapTyped;
    }

    public void setOrUpdateItem(T t, SmoothTransform transform){
        SmoothTransform currentTransform = items.get(t);
        if(currentTransform == null) items.put(t, transform);
        else {
            currentTransform.setTo(transform.getTo());
            currentTransform.setDuration(transform.getDuration());
        }
    }

    public void addOrUpdateItem(T t, SmoothTransform transform){
        SmoothTransform currentTransform = items.get(t);
        if(currentTransform == null) items.put(t, transform);
        else {
            currentTransform.setTo(currentTransform.getTo() + transform.getTo());
            currentTransform.setDuration(transform.getDuration());
        }
    }

    public double get(T t, boolean autoDelete){
        SmoothTransform transform = items.get(t);
        if(transform == null) return 0d;

        if(autoDelete && items.get(t).isEnd()) items.remove(t);
        return transform.get();
    }

    public double get(T t){
        SmoothTransform transform = items.get(t);
        if(transform == null) return 0d;

        if(items.get(t).isEnd()) items.remove(t);
        return transform.get();
    }



    public void setItem(T t, Item item){
        items.put(t, item);
    }


    public Map<T, SmoothTransform> getItems() {
        return items;
    }

    public void setItems(Map<T, SmoothTransform> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SmoothTransformEntity{" +
                "items=" + items +
                '}';
    }
}
