package wf.utils.java.values;

import org.bukkit.persistence.PersistentDataAdapterContext;

import java.util.ArrayList;

public enum DefaultDataType {

    BOOLEAN(Boolean.class,false),
    FLOAT(Float.class,false),
    DOUBLE(Double.class,false),
    INTEGER(Integer.class,false),
    STRING(String.class,false),
    CHARACTER(Character.class,false),
    LONG(Long.class,false),
    BOOLEAN_ARRAY(Long[].class,true),
    FLOAT_ARRAY(Float[].class,true),
    DOUBLE_ARRAY(Double[].class,true),
    INTEGER_ARRAY(Integer[].class,true),
    STRING_ARRAY(String[].class,true),
    CHARACTER_ARRAY(Character[].class,true),
    LONG_ARRAY(Long[].class,true);

    private Class typeClass;
    private boolean itsArray;

    private static final ArrayList<DefaultDataType> arrayTypes = new ArrayList<>(7);


    static {
        for(DefaultDataType type : values()){
            if(!type.itsArray) continue;
            arrayTypes.add(type);
        }
    }

    DefaultDataType(Class typeClass, boolean itsArray){
        this.typeClass = typeClass;
        this.itsArray = itsArray;
    }

    public Class getTypeClass(){
        return typeClass;
    }

    public boolean isItsArray() {
        return itsArray;
    }

    public static boolean itsDefaultType(Object object){
        for(DefaultDataType type : values()){
            if(type.typeClass == object.getClass()) return true;
        }
        return false;
    }

    public static boolean itsDefaultArray(Object object){
        return arrayTypes.contains(object);
    }

}
