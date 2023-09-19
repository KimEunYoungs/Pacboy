package pacboy.frame;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;

import java.awt.GridLayout;

import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pacboy.database.Maze;
import pacboy.frame.inter.UserInterface;

public class MazeSimulator extends JFrame implements UserInterface {
	static final String IMAGE_PATH = Paths.get(File.separator + "eclipseSTS", "workspace", "images").toString();

	private Maze maze = new Maze();

	public MazeSimulator() {
		setTitleAndSize();

		/* 미로 패널 */
		JPanel mazePanel = setMazePanel();
		System.out.println(maze.getSizeOfMaze() * 40);
		setLayoutManager(new GridLayout(maze.getSizeOfMaze() + 1, maze.getSizeOfMaze()), mazePanel);
		setMazeComponent(mazePanel);
		setWindowLocation();
		setCloseOption();
		setResizableOption(false);
		setVisibleOption(true);

	}

	@Override
	public void setTitleAndSize() {
		this.setTitle("Pacboy");
		maze.getMaze();
		this.setSize(maze.getSizeOfMaze() * 41, maze.getSizeOfMaze() * 48);
	}

	public JPanel setMazePanel() {
		maze.getMaze();
		JPanel mazePanel = new JPanel();
		return mazePanel;
	}

	public void setMazeComponent(JPanel mazePanel) {

		/* 시간 및 점수 부분 */
		mazePanel.setBackground(Color.BLACK);

		// 타이머와 점수 레이블 설정
		JLabel timerName = new JLabel("Time");
		JLabel scoreName = new JLabel("Score");

		/* 폰트 설정 */
		timerName.setForeground(Color.WHITE);
		timerName.setFont(new Font("Cooper Black", Font.PLAIN, 15));
		scoreName.setForeground(Color.WHITE);
		scoreName.setFont(new Font("Cooper Black", Font.PLAIN, 13));

		/* 그리드 10칸을 맞추는 작업 10칸이 아닐때 수정이 필요함 */
		mazePanel.add(new JLabel());
		mazePanel.add(timerName);
		mazePanel.add(new JLabel());
		mazePanel.add(new JLabel());
		mazePanel.add(new JLabel());

		mazePanel.add(new JLabel());
		mazePanel.add(scoreName);
		mazePanel.add(new JLabel());
		mazePanel.add(new JLabel());
		mazePanel.add(new JLabel());

		int mazeSize = maze.getSizeOfMaze();// 미로사이즈
		JLabel Block = null;
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				if (maze.getMazeValue(i, j) == -1) {
					Block = new JLabel(new ImageIcon(IMAGE_PATH + File.separator + "brick.png"));
				} else if (maze.getMazeValue(i, j) == 1) {
					// 코인이지만 배경화면에는 존재 X
					Block = new JLabel(new ImageIcon(IMAGE_PATH + File.separator + "empty.png"));
				} else if (maze.getMazeValue(i, j) == 0) {
					Block = new JLabel(new ImageIcon(IMAGE_PATH + File.separator + "empty.png"));
				} else {
					System.out.println("에러발생");
				}
				mazePanel.add(Block);

			}

		}
		/* 위치 테스트 */
		JLabel label = new JLabel("!");
		label.setSize(100, 20);
		label.setLocation(30, 30);
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

// 마우스 클릭했을때 현재 클릭한 장소의 레이블 좌표로 옮기기
			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				label.setLocation(x, y);
				System.out.println("x : " + e.getX() + "y : " + e.getY());
				Color color = label.getBackground();

// background 색칠을 위해서는 불투명 설정을 해줘야 한다.
				label.setOpaque(true);
				label.setBackground(Color.BLUE);
				label.setForeground(Color.WHITE);

			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		add(label);

		add(mazePanel);
		ActionListener mazeActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("유령과 팩보이가 행동하는 메서드 들이 들어갈 공간!");
			}

		};

	}

	@Override
	public JPanel setPanel() {
		return new JPanel();
	}

	@Override
	public void setLayoutManager(LayoutManager layoutManager, JPanel panel) {
		panel.setLayout(layoutManager);
	}

	@Override
	public void setComponent(JPanel panel) {
	}

	@Override
	public void setWindowLocation() {
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension d = tool.getScreenSize();
		setLocation(d.width / 2 - 250, d.height / 2 - 400);// 창 위치

	}

	@Override
	public void setCloseOption() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void setResizableOption(boolean option) {
		this.setResizable(option);
	}

	@Override
	public void setVisibleOption(boolean option) {

		this.setVisible(option);
	}

	public static void main(String[] args) {
		MazeSimulator inGameMazeSnap = new MazeSimulator();

	}

}
