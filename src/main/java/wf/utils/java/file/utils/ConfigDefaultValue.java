package wf.utils.java.file.utils;

import java.io.Serializable;

public class ConfigDefaultValue implements Cloneable, Serializable {

    private String path;
    private Object value;

    public ConfigDefaultValue(String path, Object value) {
        this.path = path;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "ConfigDefaultValue{" +
                "path='" + path + '\'' +
                ", value=" + value +
                '}';
    }
}
