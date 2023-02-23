package wf.utils.java.thread.mutex;

public class MutexWhileThread extends Thread{

    private final Mutex mutex;
    private Runnable runnable;

    public MutexWhileThread(Runnable runnable) {
        this(runnable,false);
    }
    public MutexWhileThread(Runnable runnable, boolean block) {
        super();
        this.runnable = runnable;
        mutex = new Mutex(block);
    }

    public void run(){
        while (!isInterrupted()) {
            mutex.step();
            runnable.run();
        }
    }

    public void lock(){
        mutex.lock();
    }

    public void unlock(){
        mutex.unlock();
    }

    public Mutex getMutex() {
        return mutex;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public String toString() {
        return "MutexWhileThread{" +
                "mutex=" + mutex +
                ", runnable=" + runnable +
                '}';
    }
}
