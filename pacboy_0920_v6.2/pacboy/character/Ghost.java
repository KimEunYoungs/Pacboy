package pacboy.character;

import java.io.File;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Ghost extends JLabel {

//	static final String IMAGE_PATH = Paths.get(File.separator + "eclipseSTS", "workspace", "images").toString();
	private static final String IMAGE_PATH = Paths.get(File.separator + "pacco", "resource", "images").toString();

	private int x;
	private int y;

	private int[] initLocation;
	private ImageIcon ghostIcon;
	private boolean shouldStop = false;

	public Ghost(int[] initLocation) {
		setInitData(initLocation);
		setInitLayout();
		//debug
		debugger();
	}

	private void debugger() {
		System.out.println("ghost 초기좌표 (X,Y) : " + "(" + this.getX() + "," + this.getY() + ")");

	}

	public ImageIcon getGhostIcon() {
		return ghostIcon;
	}

	private void setInitData(int[] initLocation) {
		this.x = initLocation[1] * 40;
		this.y = initLocation[0] * 40 + 40; /* 좌표 + 이미지 한블럭 40size + 위쪽 상단패널40size */
		ghostIcon = new ImageIcon(IMAGE_PATH + File.separator + "ghost_red.png");
	}

	private void setInitLayout() {
		setSize(50, 50);
		setLocation(x, y);
		setIcon(ghostIcon);
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

	public boolean isShouldStop() {
		return shouldStop;
	}

	public void setShouldStop(boolean shouldStop) {
		this.shouldStop = shouldStop;
	}

}