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

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(path)),"windows-1251"));
                    reader.skip(Long.MAX_VALUE);

                    String line = reader.readLine();

                    while (true) {
                        if (line == null) Thread.sleep(25);
                        else run.accept(line);
                        line = reader.readLine();
                    }
                }catch (IOException | InterruptedException e) { e.printStackTrace(); }
            }
        });
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
