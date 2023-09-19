package pacboy;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import pacboy.database.Maze;
import pacboy.frame.InGameFrame;
import pacboy.frame.OutroFrame;

public class GhostMoveManager extends JFrame implements Runnable {

	private InGameFrame mazeframe;
	private Pacboy pacboy;
	private Ghost ghost;
	private int x;
	private int y;
	private ArrayList<int[]> wallMap = new ArrayList<int[]>();

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

	// 랜덤 좌표 설정
	private void moveGhostRandomly() {

		Random random = new Random();
		int newX = ghost.getX();
		int newY = ghost.getY();
		int ranNum = random.nextInt(4) + 1;
		int moveAmount = 40;
		wallMap = Maze.getWallMap();
		switch (ranNum) {
		// L
		case 1:
			if (newX >= 40) {
				newX -= moveAmount;
				if (checkWall(newX, newY))
					break;
				ghost.setX(newX);
			}
			break;
		// R
		case 2:
			if (newX < 360) {
				newX += moveAmount;
				if (checkWall(newX, newY))
					break;
				ghost.setX(newX);
			}
			break;
		// U
		case 3:
			if (newY >= 80) {
				newY -= moveAmount;
				if (checkWall(newX, newY))
					break;
				ghost.setY(newY);
			}
			break;
		// D
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
			System.out.println("충돌! (pacboy->ghost)");
			ghost.setShouldStop(true);
			pacboy.setShouldStop(true);
			mazeframe.getTimer().stop();
			mazeframe.getBgm().stop();
			mazeframe.setCoinManager(new ArrayList<Coin>());
			mazeframe.dispose();
			new OutroFrame(pacboy.getScore(), true, mazeframe.getNickName());
		}

		System.out.println("Ghost 위치 (X,Y) : " + "(" + ghost.getX() + ", " + ghost.getY() + ")");
		ghost.setLocation(newX, newY);

	}

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