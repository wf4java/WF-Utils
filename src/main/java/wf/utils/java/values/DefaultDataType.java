package wf.utils.java.values;


import java.util.ArrayList;

public enum DefaultDataType {

    BOOLEAN(Boolean.class,false),
    FLOAT(Float.class,false),
    DOUBLE(Double.class,false),
    INTEGER(Integer.class,false),
    STRING(String.class,false),
    CHARACTER(Character.class,false),
    LONG(Long.class,false),
    BYTE(Byte.class,false),
    SHORT(Short.class,false),
    BOOLEAN_ARRAY(Long[].class,true),
    FLOAT_ARRAY(Float[].class,true),
    DOUBLE_ARRAY(Double[].class,true),
    INTEGER_ARRAY(Integer[].class,true),
    STRING_ARRAY(String[].class,true),
    CHARACTER_ARRAY(Character[].class,true),
    LONG_ARRAY(Long[].class,true),
    BYTE_ARRAY(Byte[].class,true),
    SHORT_ARRAY(Short[].class,true);

    private final Class typeClass;
    private final boolean itsArray;

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

    public static boolean isDefaultType(Object object){
        for(DefaultDataType type : values())
            if(type.typeClass.equals(object.getClass())) return true;

        return false;
    }

    public static boolean isDefaultArray(Object object){
        for(DefaultDataType type : arrayTypes)
            if(type.typeClass.equals(object.getClass())) return true;
        return false;
    }

    public static boolean isArray(Object object){
        return Object[].class.isAssignableFrom(object.getClass());
    }

}
