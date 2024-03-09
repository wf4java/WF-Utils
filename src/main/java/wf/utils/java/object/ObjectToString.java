package wf.utils.java.object;


import wf.utils.java.console.ConsoleColor;
import wf.utils.java.values.DefaultDataType;


import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Set;

public class ObjectToString {

    public static String toString(Object object) {
        return toString(object, false);
    }

    public static String toString(Object object, boolean colored){
        if(!colored) return toString(object, null, 0, false, null);
        else return ConsoleColor.translateAlternateColorCodes(toString(object, null, 0, true, null), '&');
    }

    private static String toString(Object object, String fieldName, int indentationLevel, boolean colored, Set<Object> objectSet) {
        StringBuilder builder = new StringBuilder();
        Field[] fields = getAllFields(object.getClass());
        if(objectSet == null){
            objectSet = Collections.newSetFromMap(new IdentityHashMap<>());
            objectSet.add(object);
        }

        if(fieldName == null) builder.append(colored ? "&4" : "").append(object.getClass().getSimpleName());
        else builder.append(getTypeOfClassOwn(object.getClass(), colored));

        if(fieldName != null) builder.append(fieldName);

        builder.append(" ").append(getOpenBracesSymbol(colored));
        if(fields.length != 0) builder.append("\n");

        for (Field field : fields) {
            builder.append(getIndentation(indentationLevel + 1));
            builder.append(fieldToString(object, field, indentationLevel + 1, colored, objectSet));
        }

        builder.append(fields.length == 0 ? " " : getIndentation(indentationLevel));
        builder.append(getCloseBracesSymbol(colored));
        if(fieldName != null) builder.append("\n");

        return builder.toString();
    }


    private static String fieldToString(Object object, Field field, int indentationLevel, boolean colored, Set<Object> objectSet) {
        field.setAccessible(true);
        if (!field.isAccessible()) return "";
        field.setAccessible(true);

        Object result;
        try {result = field.get(object);}
        catch (IllegalAccessException e) {throw new RuntimeException(e);}

        if (result == null)
            return getFieldTypeOwn(field, colored) + getFieldName(field, colored) + getEqualsSymbol(colored) + getNullSymbol(colored) + getCommaSymbol(colored) +" \n";

        if (DefaultDataType.isDefaultArray(result))
            return getFieldType(field, colored) + getFieldName(field, colored) + getEqualsSymbol(colored) + (colored ? "&a" : "") + defaultArrayToString((Object[]) result, colored) + getCommaSymbol(colored) +" \n";

        if(DefaultDataType.isArray(result))
            return getFieldTypeOwn(field, colored) + getFieldName(field, colored) + getEqualsSymbol(colored) + (colored ? "&a" : "") + objectArrayToString((Object[]) result, indentationLevel, colored, objectSet);

        if(DefaultDataType.isDefaultType(result))
            return getFieldType(field, colored) + getFieldName(field, colored) + getEqualsSymbol(colored) + (colored ? "&a" : "") + String.valueOf(result) + getCommaSymbol(colored) + " \n";



        if(objectSet.contains(result)) return getFieldTypeOwn(field, colored) + getFieldName(field, colored) + getEqualsSymbol(colored) + (colored? "&a" : "") + "*..." + getCommaSymbol(colored) + " \n";
        objectSet.add(result);

        return toString(result, getFieldName(field, colored), indentationLevel, colored, objectSet);
    }


    private static String objectArrayToString(Object[] array, int indentationLevel, boolean colored, Set<Object> objectSet) {
        StringBuilder builder = new StringBuilder();
        builder.append(getOpenSquareBracketSymbol(colored));


        for(int i = 0; i < array.length; i++) {
            Object object = array[i];


            if(object == null){
                builder.append("\n");
                builder.append(getIndentation(indentationLevel + 1)).append(getNullSymbol(colored));
                if(i != (array.length - 1)) builder.append(getCommaSymbol(colored));
                if(i == (array.length - 1)) builder.append("\n");
            }
            else {


                if (DefaultDataType.isDefaultArray(object)){
                    builder.append("\n");
                    builder.append(getIndentation(indentationLevel + 1)).append(colored ? "&a" : "").append(defaultArrayToString((Object[]) object, colored));
                    if(i != (array.length - 1)) builder.append(getCommaSymbol(colored));
                    if(i == (array.length - 1)) builder.append("\n");
                }

                else if(DefaultDataType.isArray(object)) {
                    builder.append("\n");
                    builder.append(colored ? "&a" : "").append(objectArrayToString((Object[]) object, indentationLevel, colored, objectSet));
                    if(i != (array.length - 1)) builder.append(getCommaSymbol(colored));
                    if(i == (array.length - 1)) builder.append("\n");
                }

                else if(DefaultDataType.isDefaultType(object)) {
                    builder.append(colored ? "&a" : "").append(String.valueOf(object));
                    if(i != (array.length - 1)) builder.append(getCommaSymbol(colored)).append(" ");
                }

                else{
                    builder.append("\n");

                    if(objectSet.contains(object)) builder.append(getIndentation(indentationLevel + 1)).append(colored ? "&a" : "").append("*...");
                    else{
                        objectSet.add(object);
                        builder.append(getIndentation(indentationLevel + 1)).append(toString(object, null, indentationLevel + 1, colored, objectSet));
                    }



                    if(i != (array.length - 1)) builder.append(getCommaSymbol(colored));
                    if(i == (array.length - 1)) builder.append("\n");
                }
            }
        }

        if(builder.charAt(builder.length() - 1) == '\n') builder.append(getIndentation(indentationLevel)).append(getCloseSquareBracketSymbol(colored));
        else builder.append(getCloseSquareBracketSymbol(colored));

        builder.append("\n");

        return builder.toString();
    }


    private static String defaultArrayToString(Object[] array, boolean colored) {
        StringBuilder builder = new StringBuilder();

        builder.append(getOpenSquareBracketSymbol(colored));

        for(int i = 0; i < array.length; i++) {
            Object object = array[i];

            if(object == null) builder.append(getNullSymbol(colored));
            else builder.append(colored ? "&a" : "").append(String.valueOf(object));

            if(i != (array.length - 1)) builder.append(colored ? "&b," : ",");
        }

        builder.append(getCloseSquareBracketSymbol(colored));


        return builder.toString();
    }


    private static String getIndentation(int indentationLevel) {
        if(indentationLevel == 0) return "";

        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < indentationLevel; i++)
            indentation.append("    ");

        return indentation.toString();
    }


    private static String getFieldName(Field field, boolean colored){
        return (colored ? "&b" : "") + field.getName();
    }


    private static String getFieldType(Field field, boolean colored){
        return getTypeOfClass(field.getType(), colored);
    }

    private static String getFieldTypeOwn(Field field, boolean colored){
        return getTypeOfClassOwn(field.getType(), colored);
    }


    private static String getTypeOfClass(Class<?> clazz, boolean colored) {
        return  (getOpenBracketSymbol(colored) + (colored ? "&7" : "") + clazz.getSimpleName() + getCloseBracketSymbol(colored) + " ");
    }

    private static String getTypeOfClassOwn(Class<?> clazz, boolean colored) {
        return ((colored ? "&4(" : "(") + (colored ? "&4" : "") + clazz.getSimpleName() + (colored ? "&4)" : ")") + " ");
    }


    private static String getEqualsSymbol(boolean colored) {
        return (colored ? "&9" : "") + " = ";
    }

    private static String getOpenBracketSymbol(boolean colored) {
        return (colored? "&7" : "") + "(";
    }

    private static String getCloseBracketSymbol(boolean colored) {
        return (colored? "&7" : "") + ")";
    }

    private static String getOpenBracesSymbol(boolean colored) {
        return (colored ? "&5" : "") + "{";
    }

    private static String getCloseBracesSymbol(boolean colored) {
        return (colored? "&5" : "") + "}";
    }

    private static String getOpenSquareBracketSymbol(boolean colored) {
        return (colored? "&8" : "") + "[";
    }

    private static String getCloseSquareBracketSymbol(boolean colored) {
        return (colored? "&8" : "") + "]";
    }

    private static String getCommaSymbol(boolean colored) {
        return (colored? "&6" : "") + ",";
    }

    private static String getNullSymbol(boolean colored) {
        return (colored? "&a" : "") + "null";
    }


    private static Field[] getAllFields(Class<?> clazz) {
        Set<Field> fields = new HashSet<>();
        while (clazz != null) {
            Collections.addAll(fields, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        fields.forEach((f) -> f.setAccessible(true));
        return fields.toArray(new Field[0]);
    }


}