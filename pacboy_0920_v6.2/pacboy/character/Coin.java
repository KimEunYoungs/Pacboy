package pacboy.character;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Coin extends JLabel {
	
//	final String IMAGE_PATH = Paths.get(File.separator + "eclipseSTS", "workspace", "images").toString();
	private final static String IMAGE_PATH = Paths.get(File.separator + "pacco", "resource", "images").toString();

	private int x;
	private int y;
	private ArrayList<int[]> coinMap = new ArrayList<int[]>();

	private ImageIcon coinIcon;

	public Coin(int x, int y) {
		setInitData(x, y);
		setInitLayout();
	}

	private void setInitData(int x, int y) {
		this.x = x * 40;
		this.y = y * 40 + 40;
		coinIcon = new ImageIcon(IMAGE_PATH + File.separator + "coin.png");
	}

	private void setInitLayout() {
		setSize(50, 50);
		setLocation(x, y);
		setIcon(coinIcon);
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
}
