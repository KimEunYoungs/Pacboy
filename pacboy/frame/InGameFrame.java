package pacboy.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;	
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import pacboy.Coin;
import pacboy.Ghost;
import pacboy.GhostMoveManager;
import pacboy.Pacboy;
import pacboy.PacboyMoveManager;
import pacboy.database.BGM;
import pacboy.database.Maze;
import pacboy.frame.inter.UserInterface;

public class InGameFrame extends JFrame implements UserInterface, ActionListener {
	/* 파일 경로 */
	static final String IMAGE_PATH = Paths.get(File.separator + "eclipseSTS", "workspace", "images").toString();
	private String nickName;
	private Timer timer;
	private int time = 20;
	private Maze maze = new Maze();
	private Ghost ghost;
	private Pacboy pacboy;
	private Coin coin;
	private JLabel timerLabel;
	private JLabel scoreLabel;

	private GhostMoveManager ghostMove;
	private PacboyMoveManager pacboyMove;
	private boolean gameOver;
	private BGM bgm;

	/* coin 객체 관리 */
	private static ArrayList<Coin> coinManager = new ArrayList<Coin>();

	public static ArrayList<Coin> getCoinManager() {
		return coinManager;
	}

	public void setCoinManager(ArrayList<Coin> coinManager) {
		this.coinManager = coinManager;
	}

	public JLabel getScoreLabel() {
		return scoreLabel;
	}

	public void setScoreLabel(JLabel scoreLabel) {
		this.scoreLabel = scoreLabel;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public BGM getBgm() {
		return bgm;
	}

	public void setBgm(BGM bgm) {
		this.bgm = bgm;
	}

	// 생성자
	public InGameFrame(String nickName) {
		this.nickName = nickName;
		// 창 타이틀 및 사이즈
		setTitleAndSize();

		// 배경화면
		JPanel MazeImgPanel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics graphics) {
				super.paintComponent(graphics);
				ImageIcon imageIcon = new ImageIcon(
						IMAGE_PATH + File.separator + "MazeLevel" + maze.getLevel() + ".png"); // 미로마다 다른걸 가져오도록하기

				Image image = imageIcon.getImage();
				graphics.drawImage(image, -5, +5, 400, 440, this);
			}
		};

		// 미로에 객체 추가하는 부분(유령, 팩보이, 코인, 점수, 타이머 등등)
		setComponent(MazeImgPanel);
		// 패널에 미로추가 (객체를 먼저 추가해야 미로가 안사라짐)
		add(MazeImgPanel);

		// 스레드 시작
		bgm = new BGM("ingame.wav", true, -30.0f);

		bgm.start();
		timer.start();
		
		new Thread(ghostMove).start();
		
		// PacboyMoveManager
//		new Thread(pacboyMove).start();

		// 창 위치
		setWindowLocation();
		setCloseOption();
		setResizableOption(false);
		setVisibleOption(true);

	}

	@Override
	public void setTitleAndSize() {
		maze.getMaze();
		this.setTitle("Pacboy");
		this.setSize(maze.getSizeOfMaze() * 41, maze.getSizeOfMaze() * 48);
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
		ghost = new Ghost(maze.getGhostInitLocation());
		pacboy = new Pacboy(maze.getPacboyInitLocation());

		pacboyMove = new PacboyMoveManager(pacboy, ghost, this);
		ghostMove = new GhostMoveManager(pacboy, ghost, this);

		// 시간 레이블 (객체로 변경예정)
		timerLabel = new JLabel("20");
		timerLabel.setForeground(Color.WHITE);
		timerLabel.setFont(new Font("Cooper Black", Font.PLAIN, 20));
		timerLabel.setBounds(100, 5, 50, 50);

		scoreLabel = new JLabel(" " + pacboy.getScore());
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(new Font("Cooper Black", Font.PLAIN, 20));
		scoreLabel.setBounds(300, 5, 50, 50);

		// 10초 후에 OutroFrame을 생성하고 현재 프레임을 숨깁니다.
		timer = new Timer(1000, this);
		timer.setRepeats(true);

		panel.add(timerLabel);
		panel.add(scoreLabel);
		add(panel);
		add(pacboy);
		add(ghost);
		
		for (int[] xy : maze.getCoinMap()) {
			coin = new Coin(xy[0], xy[1]);
			add(coin);
			coinManager.add(coin);
		}
	}

	@Override
	public void setWindowLocation() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		setLocation(dimension.width / 2 - 250, dimension.height / 2 - 400);

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

	@Override
	public void dispose() {
		super.dispose();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		time--;
		this.timerLabel.setText(String.valueOf(time));
		if (time < 0) {
			gameOver = true;
			timer.stop();
			bgm.stop();
			ghost.setShouldStop(true);
			remove(pacboy);
			this.dispose();
			coinManager = new ArrayList<Coin>();
			new OutroFrame(pacboy.getScore(), gameOver, nickName);
		}
	}

	public static void main(String[] args) {
		String nickname = "hello"; // 게임을 시작할 때 사용할 닉네임 설정하므로 필요없음
		SwingUtilities.invokeLater(() -> new InGameFrame(nickname));

	}

}