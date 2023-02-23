package wf.utils.java.time.execution;

import wf.utils.java.convert.NumberUtils;

public class ExecutionTime {

    private long startTime;

    public ExecutionTime() {
        this.startTime = System.currentTimeMillis();
    }

    public long getMillis(){
        return System.currentTimeMillis() - startTime;
    }

    public double getSeconds(){
        return getMillis() / 1000d;
    }

    public String getSecondsFormatted(){
        return NumberUtils.formatNumber(getSeconds(), 1);
    }

    public String getSecondsFormatted(int count){
        return NumberUtils.formatNumber(getSeconds(), count);
    }

    public void update(){
        this.startTime = System.currentTimeMillis();
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "ExecutionTime{" +
                "startTime=" + startTime +
                '}';
    }
}
