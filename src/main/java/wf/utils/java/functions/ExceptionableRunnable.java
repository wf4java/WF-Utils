package wf.utils.java.functions;

@FunctionalInterface
public interface ExceptionableRunnable {

    public abstract void run() throws Exception;

    public static Runnable of(ExceptionableRunnable er) {
        return new Runnable() {
            @Override
            public void run() {
                try { er.run(); }
                catch (Exception e) { throw new RuntimeException(e); }
            }
        };
    }

}