package wf.utils.java.file.readers;

import java.util.function.Consumer;

public class FileNewLinesListener {

    private Thread thread;


    public FileNewLinesListener(String path, Consumer<String> run) {

//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"windows-1251"));
//                    reader.skip(Long.MAX_VALUE);
//
//                    String line = reader.readLine();
//
//                    while (true) {
//                        if (line == null) Thread.sleep(25);
//                        else run.accept(line);
//                        line = reader.readLine();
//                    }
//                }catch (IOException | InterruptedException e) { e.printStackTrace(); }
//            }
//        });
        run.accept("[Client thread/INFO][CHAT]Задание: Расшифруй слово [тресниивеУт]");
        //thread.start();
    }

    public void stop(){
        thread.stop();
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public String toString() {
        return "FileNewLinesReader{" +
                "thread=" + thread +
                '}';
    }
}
