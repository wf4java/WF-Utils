package wf.utils.java.data.set;


import wf.utils.java.data.map.ArrayMap;

import java.io.Serializable;
import java.util.*;

public class ArraySet<E> extends AbstractSet<E> implements Set<E>, Serializable {
    private final transient ArrayMap<E,Object> map;
    private static final Object PRESENT = new Object();


    public ArraySet() {
        map = new ArrayMap<>();
    }

    public ArraySet(int initialCapacity) {
        map = new ArrayMap<>(initialCapacity);
    }

    public ArraySet(List<E> entries) {
        this(entries.size());
        addAll(entries);
    }


    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }


    public int size() {
        return map.size();
    }


    public boolean isEmpty() {
        return map.isEmpty();
    }


    public boolean contains(Object o) {
        return map.containsKey(o);
    }


    public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }


    public boolean remove(Object o) {
        return map.remove(o)==PRESENT;
    }


    public void clear() {
        map.clear();
    }


}