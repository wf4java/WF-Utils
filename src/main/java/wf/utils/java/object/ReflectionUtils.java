package wf.utils.java.object;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static Object getValue(Object o, String s){
        try {
            Field field = o.getClass().getDeclaredField(s);
            field.setAccessible(true);
            return field.get(o);
        } catch (NoSuchFieldException | IllegalAccessException e) {throw new RuntimeException(e);}
    }

    public static <T> T getValue(Object o, String s, Class<T> c){
        try {
            Field field = o.getClass().getDeclaredField(s);
            field.setAccessible(true);
            return c.cast(field.get(o));
        } catch (NoSuchFieldException | IllegalAccessException e) {throw new RuntimeException(e);}
    }

    public static void setValue(Object o, String s, Object value){
        try {
            Field field = o.getClass().getDeclaredField(s);
            field.setAccessible(true);
            field.set(o, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {throw new RuntimeException(e);}
    }




}
