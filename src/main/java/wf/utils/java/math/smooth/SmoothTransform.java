package wf.utils.java.math.smooth;



public class SmoothTransform {

    private long start;
    private double from;
    private double to;
    private long duration;
    private SmoothTransformFunction function;
    private double value;


    public SmoothTransform(double from, double to, long duration, SmoothTransformFunction function) {
        this.from = from;
        this.value = from;
        this.to = to;
        this.duration = duration;
        this.function = function;
        this.start = System.currentTimeMillis();




    }

    public SmoothTransform(double from, double to) {
        this.from = from;
        this.value = from;
        this.to = to;
        this.duration = 3000;
        this.function = SmoothTransformFunctionType.LINEAR;
        this.start = System.currentTimeMillis();
    }

    public SmoothTransform(double from, double to, long duration) {
        this.from = from;
        this.value = from;
        this.to = to;
        this.duration = duration;
        this.function = SmoothTransformFunctionType.LINEAR;
        this.start = System.currentTimeMillis();
    }

    public SmoothTransform(double from, double to, SmoothTransformFunction function) {
        this.from = from;
        this.value = from;
        this.to = to;
        this.duration = 3000;
        this.function = function;
        this.start = System.currentTimeMillis();
    }


    public interface SmoothTransformFunction{
        public double get(double percent);
    }

    public double get(){
        update();
        return this.value;
    }

    public void reset(){
        this.value = from;
        this.start = System.currentTimeMillis();
    }

    public void update(){
        if(System.currentTimeMillis() - start >= duration) this.value = this.to;
        else this.value = this.from + (function.get(((double)System.currentTimeMillis() - start) / duration) * (this.to - this.from));
    }

    public void setTo(double to) {
        this.from = get();
        this.start = System.currentTimeMillis();
        this.to = to;
    }


    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }


    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public SmoothTransformFunction getFunction() {
        return function;
    }

    public void setFunction(SmoothTransformFunction function) {
        this.function = function;
    }

    public double getValue() {
        return value;
    }

    public boolean isEnd(){
        return value == to;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SmoothTransform{" +
                "start=" + start +
                ", from=" + from +
                ", to=" + to +
                ", duration=" + duration +
                ", function=" + function +
                ", value=" + value +
                '}';
    }
}
