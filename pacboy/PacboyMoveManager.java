package pacboy;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;
import pacboy.database.BGM;
import pacboy.database.Maze;
import pacboy.frame.InGameFrame;
import pacboy.frame.OutroFrame;

public class PacboyMoveManager implements KeyListener {

	static final String IMAGE_PATH = Paths.get(File.separator + "eclipseSTS", "workspace", "images").toString();
	private ImageIcon pacManMouseOpenRight = new ImageIcon(
			Paths.get(IMAGE_PATH + File.separator + "pacboy_right.png").toString());
	private ImageIcon pacManMouseOpenLeft = new ImageIcon(
			Paths.get(IMAGE_PATH + File.separator + "pacboy_left.png").toString());
	private ImageIcon pacManMouseOpenUp = new ImageIcon(
			Paths.get(IMAGE_PATH + File.separator + "pacboy_up.png").toString());
	private ImageIcon pacManMouseOpenDown = new ImageIcon(
			Paths.get(IMAGE_PATH + File.separator + "pacboy_down.png").toString());
	private ImageIcon pacManMouseClose = new ImageIcon(
			Paths.get(IMAGE_PATH + File.separator + "pacboy_close.png").toString());

	private InGameFrame mazeframe;
	private Pacboy pacboy;
	private Ghost ghost;
	private int x;
	private int y;
	private ArrayList<int[]> wallMap = new ArrayList<int[]>();
	private ArrayList<Coin> coinManager = new ArrayList<Coin>();
	private BGM coinBgm;
	private BGM dieBgm;
//	private boolean shouldStop = false;


	public Pacboy getPacboy() {
		return pacboy;
	}

	public void setPacboy(Pacboy pacboy) {
		this.pacboy = pacboy;
	}

	public PacboyMoveManager(Pacboy pacman, Ghost ghost, InGameFrame inGameFrameTest) {
		this.pacboy = pacman;
		this.ghost = ghost;
		this.mazeframe = inGameFrameTest;
		this.x = pacman.getX();
		this.y = pacman.getY();
		this.pacboy.setFocusable(true);
		this.pacboy.addKeyListener(this);
		coinBgm = new BGM("eatingcoin.wav", false, -0.30f);
		dieBgm = new BGM("die.wav", false, -0.30f);
	}

//	@Override
//	public void dispose() {
//		super.dispose();
//		System.out.println("팩보이 객체 죽음");
//	}
//
//	@Override
//	public void run() {
//		this.pacboy.setFocusable(true);
//		this.pacboy.addKeyListener(this);
//	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		moveIcon(keyCode);
	}

	@Override
	public void keyReleased(KeyEvent e) {
//      changeDirection();
		this.pacboy.setIcon(pacManMouseClose);
	}

	private void moveIcon(int keyCode) {
		int newX = this.pacboy.getX();
		int newY = this.pacboy.getY();
		int moveAmount = 40;
		wallMap = Maze.getWallMap();
//		coinMap = Maze.getCoinMap();
		coinManager = InGameFrame.getCoinManager();

		switch (keyCode) {
		// L
		case KeyEvent.VK_LEFT:
			this.pacboy.setIcon(pacManMouseOpenLeft);
			if (newX >= 40) {
				newX -= moveAmount;
				if (checkWall(newX, newY))
					break;
				pacboy.setX(newX);
			}
			break;
		// R
		case KeyEvent.VK_RIGHT:
			this.pacboy.setIcon(pacManMouseOpenRight);
			if (newX < 360) {
				newX += moveAmount;
				if (checkWall(newX, newY))
					break;
				pacboy.setX(newX);
			}
			break;
		// U
		case KeyEvent.VK_UP:
			this.pacboy.setIcon(pacManMouseOpenUp);
			if (newY >= 80) {
				newY -= moveAmount;
				if (checkWall(newX, newY))
					break;
				pacboy.setY(newY);
			}
			break;
		// D
		case KeyEvent.VK_DOWN:
			this.pacboy.setIcon(pacManMouseOpenDown);
			if (newY < 400) {
				newY += moveAmount;
				if (checkWall(newX, newY))
					break;
				pacboy.setY(newY);
			}
			break;
		}
		if (pacboy.getX() == ghost.getX() && pacboy.getY() == ghost.getY()) {
			System.out.println("충돌! (pacboy->ghost)");
			dieBgm.start();
			ghost.setShouldStop(true);
			mazeframe.remove(pacboy);
			//pacboy.setShouldStop(true); //??
			mazeframe.getTimer().stop();
			mazeframe.getBgm().stop();
			mazeframe.setCoinManager(new ArrayList<Coin>());
			mazeframe.dispose();
			new OutroFrame(pacboy.getScore(), true, mazeframe.getNickName());

//			mazeframe.dispose();
		}
		int coinIndex = checkCoin(pacboy.getX(), pacboy.getY());
		if (coinIndex > -1) {
			coinBgm.start();
			// bgm과 같이 하면 화면에서 코인이 안사라지는 에러 발생
			mazeframe.remove(coinManager.get(coinIndex));
			coinManager.remove(coinIndex);

			if (coinManager.size() == 0) {

				mazeframe.getTimer().stop();
				mazeframe.getBgm().stop();
				ghost.setShouldStop(true);
				mazeframe.remove(pacboy);
				mazeframe.setCoinManager(new ArrayList<Coin>());
				//pacboy.setShouldStop(true); //??
				mazeframe.dispose();
				coinManager = new ArrayList<Coin>();
				new OutroFrame(pacboy.getScore(), false, mazeframe.getNickName());
			}

			int currentScore = pacboy.getScore();
			pacboy.setScore(currentScore + 1);

			/* paint */
			// pacboy.setScoreText(pacboy.getScore());

			System.out.println("현재 점수 : " + pacboy.getScore());
		}

		System.out.println("pacman 위치 (X,Y) : " + "(" + pacboy.getX() + ", " + pacboy.getY() + ")");
		this.pacboy.setLocation(newX, newY);
	}

	private boolean checkWall(int x, int y) {
		for (int[] wallXY : wallMap) {
			if (x == wallXY[0] * 40 && y == (wallXY[1] * 40) + 40) {
				return true;
			}
		}
		return false;
	}

	private int checkCoin(int x, int y) {
		for (Coin coin : coinManager) {
			if (coin.getX() == x && coin.getY() == y) {
				mazeframe.getScoreLabel().setText(" " + (pacboy.getScore() + 1));
				return coinManager.indexOf(coin);
			}
		}
		return -1;
	}
}