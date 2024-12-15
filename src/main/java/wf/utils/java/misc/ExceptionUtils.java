package wf.utils.java.misc;

import java.time.Duration;

public class ExceptionUtils {


    public static Runnable handle(Runnable runnable) {
        return () -> {
            try { runnable.run(); }
            catch (Throwable e) { e.printStackTrace(); }
        };
    }



    public static void retry(Runnable runnable, int count, Duration delay) {
        for (int i = 0; i < count; i++) {
            try {
                runnable.run();
                return;
            } catch (Exception e) {
                if (i == count - 1)
                    throw e;

                try { Thread.sleep(delay.toMillis()); }
                catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ie);
                }
            }
        }
    }


}
