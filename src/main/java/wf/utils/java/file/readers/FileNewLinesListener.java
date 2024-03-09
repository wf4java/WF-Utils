package wf.utils.java.file.readers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class FileNewLinesListener {

    private Thread thread;



    public FileNewLinesListener(String path, Consumer<String> run) {
        this(path, run, "utf-8", 25);
    }

    public FileNewLinesListener(String path, Consumer<String> run, String charSetName) {
        this(path, run, charSetName, 25);
    }


    public FileNewLinesListener(String path, Consumer<String> run, String charSetName, long repeatTime) {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), charSetName));
                    reader.skip(Long.MAX_VALUE);

                    String line = reader.readLine();

                    while (true) {
                        if (line == null) Thread.sleep(repeatTime);
                        else run.accept(line);
                        line = reader.readLine();
                    }
                }catch (IOException | InterruptedException e) { e.printStackTrace(); }
            }
        });
        thread.start();
    }

    public void stop(){
        thread.interrupt();
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
