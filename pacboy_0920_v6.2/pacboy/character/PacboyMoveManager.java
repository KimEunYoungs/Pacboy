package pacboy.character;

import javax.swing.ImageIcon;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import java.nio.file.Paths;
import java.util.ArrayList;

import pacboy.database.BGM;
import pacboy.database.Maze;
import pacboy.frame.InGameFrame;
import pacboy.frame.OutroFrame;

public class PacboyMoveManager implements KeyListener {

	private static final String IMAGE_PATH = Paths.get(File.separator + "pacco", "resource", "images").toString();
	private ImageIcon pacboyMouseOpenRight = new ImageIcon(
			Paths.get(IMAGE_PATH + File.separator + "pacboy_right.png").toString());
	private ImageIcon pacboyMouseOpenLeft = new ImageIcon(
			Paths.get(IMAGE_PATH + File.separator + "pacboy_left.png").toString());
	private ImageIcon pacboyMouseOpenUp = new ImageIcon(
			Paths.get(IMAGE_PATH + File.separator + "pacboy_up.png").toString());
	private ImageIcon pacboyMouseOpenDown = new ImageIcon(
			Paths.get(IMAGE_PATH + File.separator + "pacboy_down.png").toString());
	private ImageIcon pacboyMouseClose = new ImageIcon(
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
		coinBgm = new BGM("eatingcoin.wav", false, -20.00f);
		dieBgm = new BGM("die.wav", false, -20.00f);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		movePacboyIcon(keyCode);
	}

	@Override
	public void keyReleased(KeyEvent e) {
//      changeDirection();
		this.pacboy.setIcon(pacboyMouseClose);
	}

	private void movePacboyIcon(int keyCode) {
		int newX = this.pacboy.getX();
		int newY = this.pacboy.getY();
		int moveAmount = 40;
		wallMap = Maze.getWallMap();
		coinManager = InGameFrame.getCoinManager();

		switch (keyCode) {
		// LEFT
		case KeyEvent.VK_LEFT:
			this.pacboy.setIcon(pacboyMouseOpenLeft);
			if (newX >= 40) {
				newX -= moveAmount;
				if (checkWall(newX, newY))
					break;
				pacboy.setX(newX);
			}
			break;
		// RIGHT
		case KeyEvent.VK_RIGHT:
			this.pacboy.setIcon(pacboyMouseOpenRight);
			if (newX < 360) {
				newX += moveAmount;
				if (checkWall(newX, newY))
					break;
				pacboy.setX(newX);
			}
			break;
		// UP
		case KeyEvent.VK_UP:
			this.pacboy.setIcon(pacboyMouseOpenUp);
			if (newY >= 80) {
				newY -= moveAmount;
				if (checkWall(newX, newY))
					break;
				pacboy.setY(newY);
			}
			break;
		// DOWN
		case KeyEvent.VK_DOWN:
			this.pacboy.setIcon(pacboyMouseOpenDown);
			if (newY < 400) {
				newY += moveAmount;
				if (checkWall(newX, newY))
					break;
				pacboy.setY(newY);
			}
			break;
		}
		int coinIndex = checkCoin(pacboy.getX(), pacboy.getY());

		if (pacboy.getX() == ghost.getX() && pacboy.getY() == ghost.getY()) {// 충돌 확인
			System.out.println("충돌! (pacboy->ghost)");
			dieBgm.start();
			ghost.setShouldStop(true);
			mazeframe.remove(pacboy);
			mazeframe.getTimer().stop();
			mazeframe.getBgm().stop();
			mazeframe.setCoinManager(new ArrayList<Coin>());
			mazeframe.dispose();
			new OutroFrame(pacboy.getScore(), true, mazeframe.getNickName());
		} else if (coinIndex > -1) {
			coinBgm.start();
			mazeframe.remove(coinManager.get(coinIndex));
			coinManager.remove(coinIndex);
			// 미로에 있는 코인을 다 획득한 경우 : 성공
			if (coinManager.size() == 0) {
				mazeframe.getTimer().stop();
				mazeframe.getBgm().stop();
				ghost.setShouldStop(true);
				mazeframe.remove(pacboy);
				mazeframe.setCoinManager(new ArrayList<Coin>());
				mazeframe.dispose();
				coinManager = new ArrayList<Coin>();
				new OutroFrame(pacboy.getScore(), false, mazeframe.getNickName());
			}
			// 콘솔창에 점수 출력
			int currentScore = pacboy.getScore();
			pacboy.setScore(currentScore + 1);
			System.out.println("현재 점수 : " + pacboy.getScore());
		}

		System.out.println("pacman 위치 (X,Y) : " + "(" + pacboy.getX() + ", " + pacboy.getY() + ")");
		this.pacboy.setLocation(newX, newY);
	}

	// 벽 감지 메서드
	private boolean checkWall(int x, int y) {
		for (int[] wallXY : wallMap) {
			if (x == wallXY[0] * 40 && y == (wallXY[1] * 40) + 40) {
				return true;
			}
		}
		return false;
	}

	// 코인 감지 메서드
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