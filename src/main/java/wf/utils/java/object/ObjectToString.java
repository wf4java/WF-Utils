package wf.utils.java.object;

import wf.utils.java.values.DefaultDataType;

import java.lang.reflect.Field;
import java.util.Arrays;

//NOT WORKING
//NOT WORKING
//NOT WORKING
public class ObjectToString {

    public static String convert(Object object){
        StringBuilder builder = new StringBuilder(object.getClass().getSimpleName() + "{\n");

        for(Field field : object.getClass().getDeclaredFields()){
            builder.append("    " + filedToString(object, field));
        }

        builder.append("}");
        return builder.toString();
    }

    public static String filedToString(Object object, Field field){
        field.setAccessible(true);
        if(!field.isAccessible()) return "";
        field.setAccessible(true);
        Object result;
        try {result = field.get(object);} catch (IllegalAccessException e) {throw new RuntimeException(e);}
        if(result == null){
            return field.getName() + "=null";
        }else if(DefaultDataType.itsDefaultType(result)){
            if(DefaultDataType.itsDefaultArray(result)){
                return field.getName() + "=" + Arrays.toString((Object[]) result) + ", \n";
            }else{
                return field.getName() + "=" + result.toString() + ", \n";
            }
        }else{
            return convert(result);
        }
    }




}
