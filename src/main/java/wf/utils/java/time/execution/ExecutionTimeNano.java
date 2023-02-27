package wf.utils.java.time.execution;

import wf.utils.java.data_utils.NumberUtils;

public class ExecutionTimeNano {

    private long startTime;

    public ExecutionTimeNano() {
        this.startTime = System.nanoTime();
    }

    public long getNano(){
        return System.nanoTime() - startTime;
    }

    public double getMillis(){
        return getNano() / 1000000d;
    }

    public double getSeconds(){
        return getNano() / 1000000000d;
    }

    public String getSecondsFormatted(){
        return NumberUtils.formatNumber(getSeconds(), 1);
    }

    public String getSecondsFormatted(int count){
        return NumberUtils.formatNumber(getSeconds(), count);
    }

    public void update(){
        this.startTime = System.nanoTime();
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "ExecutionTimeNano{" +
                "startTime=" + startTime +
                '}';
    }
}
