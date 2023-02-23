package wf.utils.java.values;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.jetbrains.annotations.NotNull;

public enum DefaultDataType {

    BOOLEAN(Boolean.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    INTEGER(Integer.class),
    STRING(String.class),
    CHARACTER(Character.class),
    LONG(Long.class),
    BOOLEAN_ARRAY(Long[].class),
    FLOAT_ARRAY(Float[].class),
    DOUBLE_ARRAY(Double[].class),
    INTEGER_ARRAY(Integer[].class),
    STRING_ARRAY(String[].class),
    CHARACTER_ARRAY(Character[].class),
    LONG_ARRAY(Long[].class);

    private Class typeClass;

    DefaultDataType(Class typeClass){
        this.typeClass = typeClass;
    }

    public Class getTypeClass(){
        return typeClass;
    }

}
