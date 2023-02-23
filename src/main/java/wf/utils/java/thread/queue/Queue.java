package wf.utils.java.thread.queue;

import wf.utils.java.thread.mutex.MutexWhileThread;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Queue {

    private PriorityQueue<QueueKey> queue = new PriorityQueue<>(new Comparator<QueueKey>() {
        public int compare(QueueKey o1, QueueKey o2) {
            int compare = Integer.compare(o2.getPriority(), o1.getPriority());
            if(compare != 0) return compare;
            return Long.compare(o1.getCreateTime(), o2.getCreateTime());
        }
    });

    private MutexWhileThread thread;


    public Queue() {
        thread = new MutexWhileThread(this::executeQueue);
    }

    public Queue(boolean daemon) {
        thread = new MutexWhileThread(this::executeQueue);
        thread.setDaemon(daemon);
    }


    public Queue start(){
        thread.start();
        return this;
    }

    private void executeQueue(){
        if(queue.size() == 0) {
            thread.lock();
            return;
        }
        queue.poll().getRunnable().run();
    }

    public void addQueue(QueueKey key){
        queue.add(key);
        update();
    }

    public void addQueue(Runnable runnable){
        queue.add(new QueueKey(runnable));
        update();
    }

    public void addQueue(Runnable runnable, String name){
        queue.add(new QueueKey(runnable, name));
        update();
    }

    public void addQueue(Runnable runnable, int priority){
        queue.add(new QueueKey(runnable, priority));
        update();
    }

    public void addQueue(Runnable runnable, int priority, String name){
        queue.add(new QueueKey(runnable, priority, name));
        update();
    }

    public boolean remove(QueueKey key){
        return queue.remove(key);
    }

    public void clear(){
        queue.clear();
    }

    public boolean remove(String name){
        return queue.removeIf((key) -> key.getName().equals(name));
    }

    public void update(){
        if(thread == null || thread.getState() == Thread.State.NEW ) return;
        thread.unlock();
    }

    public MutexWhileThread getThread() {
        return thread;
    }

}
