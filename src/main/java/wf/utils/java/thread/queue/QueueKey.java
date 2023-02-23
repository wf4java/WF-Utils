package wf.utils.java.thread.queue;

public class QueueKey {

    private Runnable runnable;
    private int priority;
    private String name;
    private long createTime = System.nanoTime();

    public QueueKey(Runnable runnable) {
        this(runnable,0);
    }

    public QueueKey(Runnable runnable, String name) {
        this(runnable,0, name);
    }

    public QueueKey(Runnable runnable, int priority) {
        this.runnable = runnable;
        this.priority = priority;
    }

    public QueueKey(Runnable runnable, int priority, String name) {
        this.runnable = runnable;
        this.priority = priority;
        this.name = name;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public long getCreateTime() {
        return createTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "QueueKey{" +
                "runnable=" + runnable +
                ", priority=" + priority +
                ", name='" + name + '\'' +
                '}';
    }
}
