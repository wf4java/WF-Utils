package wf.utils.java.file.utils.types;

import java.util.Random;

public class IntegerRandom {

    private int min;
    private int max;
    private Random random;

    public IntegerRandom(int min, int max) {
        this.min = min;
        this.max = max;
        random = new Random();
    }

    public IntegerRandom(String range) {
        this(Integer.parseInt(range.split(":")[0]), Integer.parseInt(range.split(":")[1]));
    }

    public int get(){
        return random.nextInt(max - min) + min;
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

    public String getStringSerializable(){
        return (min + ":" + max);
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
