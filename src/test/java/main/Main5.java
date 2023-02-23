package main;


import wf.utils.java.thread.queue.Queue;

public class Main5 {

    public static Queue queue = new Queue(true);


    public static void main(String[] args) {
        queue.start();


        queue.addQueue(new Runnable() {
            @Override
            public void run() {
                System.out.println("a1, Priority: 0");
                try {Thread.sleep(2000);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        });

        queue.addQueue(new Runnable() {
            @Override
            public void run() {
                System.out.println("a2, Priority: 0");
            }
        }, 0);

        queue.addQueue(new Runnable() {
            @Override
            public void run() {
                System.out.println("a3, Priority: 1");
            }
        }, 1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }







}
