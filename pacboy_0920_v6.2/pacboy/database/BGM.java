package pacboy.database;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class BGM {
	private static final Path AUDIO_PATH = Paths.get(File.separator + "pacco", "resource", "audio");
	private String fileName;
	private boolean isLoop;
	private Clip clip = null;
	private FloatControl gainControl;

	public BGM(String fileName, boolean isLoop, float volume) {
		File audioFilePath = new File(AUDIO_PATH.toString(), fileName);
		try (AudioInputStream ais = AudioSystem
				.getAudioInputStream(new BufferedInputStream(new FileInputStream(audioFilePath)));) {
			this.fileName = fileName;
			this.isLoop = isLoop;
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

	public void stop() {
		clip.stop();
	}
}