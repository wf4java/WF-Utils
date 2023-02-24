package wf.utils.java.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;

public class SoundUtils {


    public static void playSound(InputStream audio){
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(audio);
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {e.printStackTrace();}
            }
        }).start();
    }


}
