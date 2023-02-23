package wf.utils.java.thread.mutex;

import java.util.concurrent.atomic.AtomicBoolean;

public class Mutex {
    private final AtomicBoolean lock;
    private final Object mutex;

    public Mutex() {
        this.lock = new AtomicBoolean(false);
        this.mutex = new Object();
    }

    public Mutex(boolean lock) {
        this.lock = new AtomicBoolean(lock);
        this.mutex = new Object();
    }

//  while(!thread.isInterrupted()){
//      mutex.step();
//      ...
//   }

    public void step() {
        if (lock.get()) synchronized(mutex) {
            try {
                mutex.wait();
            } catch (InterruptedException ex) {}
        }
    }

    public void lock() {
        lock.set(true);
    }

    public void unlock() {
        lock.set(false);

        synchronized(mutex) {
            mutex.notify();
        }
    }
}



