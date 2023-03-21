package wf.utils.java.file.yamlconfiguration.utils.types;

import wf.utils.java.file.yamlconfiguration.utils.StringSerializable;

import java.util.Random;

public class IntegerRandom implements StringSerializable<IntegerRandom> {

    private int min;
    private int max;
    private Random random;



    public IntegerRandom() {}

    public IntegerRandom(int min, int max) {
        if(min > max){ min = min + max - (max = min); }
        this.min = min;
        this.max = max;
        random = new Random();
    }

    public IntegerRandom(String s) {
        this(s.split(":")[0].isEmpty() ? Integer.MIN_VALUE : Integer.parseInt(s.split(":")[0]),
                s.split(":")[1].isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(s.split(":")[1]));
    }

    public int get(){
        return (int) ((random.nextDouble() * (max - min + 1)) + min);
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

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }


    @Override
    public IntegerRandom getSerializableObject(String s) {
        return new IntegerRandom(s);
    }

    @Override
    public String getSerializableString() {
        return ((min == Integer.MIN_VALUE ? "" : min) + ".." + (max == Integer.MAX_VALUE ? "" : max));
    }


    @Override
    public String toString() {
        return "IntegerRandom{" +
                "min=" + min +
                ", max=" + max +
                ", random=" + random +
                '}';
    }


}
