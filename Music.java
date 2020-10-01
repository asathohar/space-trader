import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;


/**
 * defines container class for music-playing method
 */
public class Music {

    /**
     * opens audio file, plays music
     * @param filepath the location of the audio file
     */
    public static void playMusic(String filepath) {
        try {
            AudioInputStream music = AudioSystem.getAudioInputStream(new File(filepath));
            Clip clip = AudioSystem.getClip();
            clip.open(music);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
