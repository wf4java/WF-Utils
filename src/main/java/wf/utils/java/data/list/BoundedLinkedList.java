package wf.utils.java.data.list;

import java.util.ArrayList;
import java.util.LinkedList;

public class BoundedLinkedList<T> extends LinkedList<T> {

    private int bound;

    public BoundedLinkedList(int bound) {
        this.bound = bound;
    }



    public boolean add(T t){
        if(size() == bound) remove(0);
        else if(size() > bound) removeRange(0, size() - bound + 1);
        super.add(t);
        return true;
    }

}