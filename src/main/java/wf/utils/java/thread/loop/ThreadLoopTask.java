package wf.utils.java.thread.loop;


public class ThreadLoopTask implements LoopTask {

    private final Thread thread;
    private volatile boolean isRunning = false;
    private Runnable runnable;

    public ThreadLoopTask(Runnable runnable, long delay, long period){
        this.runnable = runnable;

        thread = new Thread(() -> {
            try {Thread.sleep(delay);} catch (InterruptedException e) {throw new RuntimeException(e);}

            while (isRunning) {
                if(this.runnable == null) break;
                this.runnable.run();
                try {Thread.sleep(period);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        });
        thread.setDaemon(true);
    }

    @Override
    public void stop() {
        isRunning = false;
    }

    @Override
    public void start() {
        if(isRunning) return;

        thread.start();
        isRunning = true;
    }

    public Thread getThread() {
        return thread;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public ThreadLoopTask setRunnable(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }
}
