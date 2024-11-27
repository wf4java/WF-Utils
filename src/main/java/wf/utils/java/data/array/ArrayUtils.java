package wf.utils.java.data.array;

public class ArrayUtils {


    public <T> T getLast(T[] array) {
        if (array == null || array.length == 0)
            return null;
        return array[array.length - 1];
    }
    
    
}
