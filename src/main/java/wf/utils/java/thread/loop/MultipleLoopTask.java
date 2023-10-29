package wf.utils.java.thread.loop;

public interface MultipleLoopTask extends LoopTask {

    public void addRunnable(String name, Runnable runnable);

    public boolean removeRunnable(String name);

}
