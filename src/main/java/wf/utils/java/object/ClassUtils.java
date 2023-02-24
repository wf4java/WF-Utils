package wf.utils.java.object;

public class ClassUtils {

    public static Object getValue(Object o, String s){
        try {return o.getClass().getDeclaredField(s).get(new Object());
        } catch (NoSuchFieldException | IllegalAccessException e) {throw new RuntimeException(e);}
    }

}
