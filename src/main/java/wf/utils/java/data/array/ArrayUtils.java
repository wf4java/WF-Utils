package wf.utils.java.data.array;

public class ArrayUtils {


    public static <T> T getLast(T[] array) {
        if (array == null || array.length == 0)
            return null;
        return array[array.length - 1];
    }

    public static <T> T getFirst(T[] array) {
        if (array == null || array.length == 0)
            return null;
        return array[0];
    }
    
    
    
    
}
