package wf.utils.java.file.yamlconfiguration.utils.types;

import wf.utils.java.file.yamlconfiguration.utils.StringSerializable;

public class IntegerInRange implements StringSerializable<IntegerInRange> {

    private int min;
    private int max;

    public IntegerInRange() {}

    public IntegerInRange(int min, int max) {
        if(min > max){ min = min + max - (max = min); }
        this.min = min;
        this.max = max;
    }

    public IntegerInRange(String s) {
        this(s.split("\\.\\.")[0].isEmpty() ? Integer.MIN_VALUE : Integer.parseInt(s.split("\\.\\.")[0]),
                s.split("\\.\\.")[1].isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(s.split("\\.\\.")[1]));
    }

    public boolean inRange(int n){
        return (min <= n && n <= max);
    }

    @Override
    public IntegerInRange getSerializableObject(String s) {
        return new IntegerInRange(s);
    }

    @Override
    public String getSerializableString() {
        return ((min == Integer.MIN_VALUE ? "" : min) + ".." + (max == Integer.MAX_VALUE ? "" : max));
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "IntegerInRange{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
