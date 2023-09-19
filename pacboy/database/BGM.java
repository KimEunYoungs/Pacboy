package pacboy.database;

import javax.sound.sampled.*;
import javax.swing.SwingUtilities;

//import com.java.class17.AudioPlayerEx;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BGM {
	private final Path AUDIO_PATH = Paths.get(File.separator + "eclipseSTS", "workspace", "audio");
	private String fileName;
	private boolean isLoop;
	private Clip clip = null;
	private FloatControl gainControl;

	public BGM(String fileName, boolean isLoop, float volume) {
		File audioFilePath = new File(AUDIO_PATH.toString(), fileName);
		try (AudioInputStream ais = AudioSystem
				.getAudioInputStream(new BufferedInputStream(new FileInputStream(audioFilePath)));) {

			clip = AudioSystem.getClip();
			clip.open(ais);
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume); // 사운드 크기
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		clip.setFramePosition(0);
		clip.start();
		if (isLoop == true)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

//	public void startOneTime() {
//		clip.setFramePosition(0);
//		clip.start();
//		try {
//			Thread.sleep(200);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		clip.stop();
//
//	}

	public void stop() {
		clip.stop();
	}

	public static void main(String[] args) {

	}
}