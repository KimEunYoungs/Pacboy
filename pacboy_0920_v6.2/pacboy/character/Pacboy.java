package pacboy.character;

import java.io.File;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Pacboy extends JLabel {
	
//	static final String IMAGE_PATH = Paths.get(File.separator + "eclipseSTS", "workspace", "images").toString();
	static final String IMAGE_PATH = Paths.get(File.separator + "pacco", "resource", "images").toString();

	private int x;
	private int y;
	private int score = 0;

	private int[] initLocation;
	private ImageIcon pacboyIcon;

	public Pacboy(int[] initLocation) {
		setInitData(initLocation);
		setInitLayout();
		// debug
		debugger();
	}

	private void debugger() {
		System.out.println("pacboy 초기좌표 (X,Y) : " + "(" + this.getX() + "," + this.getY() + ")");

	}

	public ImageIcon getPacboyIcon() {
		return pacboyIcon;
	}

	private void setInitData(int[] initLocation) {
		this.x = initLocation[1] * 40;
		this.y = initLocation[0] * 40 + 40;/* 좌표 + 이미지 한블럭 40size + 위쪽 상단패널 20size */
		this.score = score;
		pacboyIcon = new ImageIcon(IMAGE_PATH + File.separator + "pacboy_init.png");
	}

	private void setInitLayout() {
		setIcon(pacboyIcon);
		setLocation(x, y);
		setSize(50, 50);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}