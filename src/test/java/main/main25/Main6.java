package main.main25;

import wf.utils.java.thread.mutex.MutexWhileThread;

public class Main6 {

    public static MutexWhileThread thread;

    public static void main(String[] args) {
        thread = new MutexWhileThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("WORKED");
            }
        });
        thread.start();
    }


}
