package wf.utils.java.data.list;

import java.util.ArrayList;

public class BoundedArrayList<T> extends ArrayList<T> {

    private int bound;

    public BoundedArrayList(int bound) {
        super(bound);
        this.bound = bound;
    }



    public boolean add(T t){
        if(size() == bound) remove(0);
        else if(size() > bound) removeRange(0, size() - bound + 1);
        super.add(t);
        return true;
    }

}
