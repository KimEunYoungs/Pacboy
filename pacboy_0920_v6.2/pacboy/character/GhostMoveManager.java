package pacboy.character;

import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.Random;

import pacboy.database.BGM;
import pacboy.database.Maze;
import pacboy.frame.InGameFrame;
import pacboy.frame.OutroFrame;

public class GhostMoveManager extends JFrame implements Runnable {

	private Pacboy pacboy;
	private Ghost ghost;
	private InGameFrame mazeframe;
	private int x;
	private int y;
	private ArrayList<int[]> wallMap = new ArrayList<int[]>();
	private BGM dieBgm = new BGM("die.wav", false, -20.00f);

	public GhostMoveManager(Pacboy pacboy, Ghost ghost, InGameFrame mazeframe) {
		this.pacboy = pacboy;
		this.ghost = ghost;
		this.mazeframe = mazeframe;
		this.x = ghost.getX();
		this.y = ghost.getY();
	}

	public Ghost getGhost() {
		return ghost;
	}

	public void setGhost(Ghost ghost) {
		this.ghost = ghost;
	}

	// 유령 랜덤 이동
	private void moveGhostRandomly() {

		Random random = new Random();
		int newX = ghost.getX();
		int newY = ghost.getY();
		int ranNum = random.nextInt(4) + 1;
		int moveAmount = 40;
		wallMap = Maze.getWallMap();
		switch (ranNum) {
		// LEFT
		case 1:
			if (newX >= 40) {
				newX -= moveAmount;
				if (checkWall(newX, newY))
					break;
				ghost.setX(newX);
			}
			break;
		// RRIGHT
		case 2:
			if (newX < 360) {
				newX += moveAmount;
				if (checkWall(newX, newY))
					break;
				ghost.setX(newX);
			}
			break;
		// UP
		case 3:
			if (newY >= 80) {
				newY -= moveAmount;
				if (checkWall(newX, newY))
					break;
				ghost.setY(newY);
			}
			break;
		// DOWN
		case 4:
			if (newY < 400) {
				newY += moveAmount;
				if (checkWall(newX, newY))
					break;
				ghost.setY(newY);
			}
			break;
		}
		if (pacboy.getX() == ghost.getX() && pacboy.getY() == ghost.getY()) {
			System.out.println("충돌! (ghost->pacboy)");
			dieBgm.start();
			ghost.setShouldStop(true);
			mazeframe.remove(pacboy);
			mazeframe.getTimer().stop();
			mazeframe.getBgm().stop();
			mazeframe.setCoinManager(new ArrayList<Coin>());
			mazeframe.dispose();
			new OutroFrame(pacboy.getScore(), true, mazeframe.getNickName());
		}

		System.out.println("Ghost 위치 (X,Y) : " + "(" + ghost.getX() + ", " + ghost.getY() + ")");
		ghost.setLocation(newX, newY);

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

	@Override
	public void run() {
		while (!ghost.isShouldStop()) {
			this.moveGhostRandomly();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}