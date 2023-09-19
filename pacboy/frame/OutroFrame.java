package pacboy.frame;

import javax.swing.*;

import pacboy.Coin;
import pacboy.database.BGM;
import pacboy.database.Maze;
import pacboy.database.Ranking;
import pacboy.frame.inter.UserInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutroFrame extends JFrame implements UserInterface, ActionListener {

	static final String IMAGE_PATH = Paths.get(File.separator + "eclipseSTS", "workspace", "images").toString();
	static boolean gameOver;
	static int score;
	private JButton exitButton;
	private JButton restartButton;
	String nickName;
	private BGM bgm;
	private Maze maze;

	private InGameFrame inGameFrame;

	// 랭킹 데이터를 저장할 HashMap
	static Map<String, Integer> rankingData = new HashMap<>();

	public OutroFrame(int score, boolean gameOver, String nickName) {
		this.score = score;
		this.gameOver = gameOver;
		this.nickName = nickName;

		setTitleAndSize();

		JPanel backgroundPanel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics gp) {
				super.paintComponent(gp);
				ImageIcon imageIcon = new ImageIcon(IMAGE_PATH + File.separator + "empty.png"); // 나중에 추가

				Image image = imageIcon.getImage();
				gp.drawImage(image, 0, 0, 400, 600, this);
			}
		};

		setComponent(backgroundPanel, score, nickName);

		setWindowLocation();

		setCloseOption();
		setResizableOption(false);
		setVisibleOption(true);

		if (gameOver) {
			bgm = new BGM("gameover.wav", true, -20.0f); // 아웃트로 노래 변경
			bgm.start();
		} else {
			bgm = new BGM("youwin.wav", true, -20.0f); // 아웃트로 노래 변경}
			bgm.start();
		}

	}

	@Override
	public void setTitleAndSize() {
		this.setTitle("Pacboy");
		this.setSize(400, 600);
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

	public void setComponent(JPanel panel, int score, String nickName) {
		// 승패에 따른 타이틀 레이블 변경
		JLabel outtroTitleLabel;
		if (gameOver) {
			outtroTitleLabel = new JLabel(new ImageIcon(IMAGE_PATH + File.separator + "gameover.png"));
		} else {
			outtroTitleLabel = new JLabel(new ImageIcon(IMAGE_PATH + File.separator + "youwin.png"));
		}
		outtroTitleLabel.setBounds(50, 100, 300, 50);
		outtroTitleLabel.setHorizontalAlignment(JLabel.CENTER);

		// 재시작 버튼
		restartButton = new JButton("Restart");
		restartButton.setFont(new Font("휴먼엑스포", Font.PLAIN, 20));
		restartButton.setBounds(30, 455, 150, 50);

		// 종료 버튼
		exitButton = new JButton("Exit");
		exitButton.setFont(new Font("휴먼엑스포", Font.PLAIN, 20));
		exitButton.setBounds(200, 455, 150, 50);

		restartButton.addActionListener(this);
		exitButton.addActionListener(this);

		// 랭킹 계산
		String rank = calculateRank(score);
		// 닉네임 라벨
		JLabel nickNameLabel = new JLabel("Player " + nickName);
		nickNameLabel.setFont(new Font("휴먼엑스포", Font.PLAIN, 20));
		nickNameLabel.setHorizontalAlignment(JLabel.CENTER);
		nickNameLabel.setBounds(20, 160, 350, 40);
		nickNameLabel.setForeground(Color.WHITE);
		// 등급 라벨
		JLabel rankLabel = new JLabel("Rank " + rank);
		rankLabel.setFont(new Font("휴먼엑스포", Font.PLAIN, 20));
		rankLabel.setHorizontalAlignment(JLabel.CENTER);
		rankLabel.setBounds(70, 190, 250, 50);
		rankLabel.setForeground(Color.WHITE);

		// 점수 라벨
		JLabel scoreLabel = new JLabel("Score " + score);
		scoreLabel.setFont(new Font("휴먼엑스포", Font.PLAIN, 20));
		scoreLabel.setBounds(70, 223, 250, 50);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setForeground(Color.WHITE);

		// 랭킹 표시 레이블
		JLabel ranklistLabel = new JLabel("Ranking");
		ranklistLabel.setFont(new Font("휴먼엑스포", Font.PLAIN, 30));
		ranklistLabel.setBounds(70, 270, 250, 50);
		ranklistLabel.setHorizontalAlignment(JLabel.CENTER);
		ranklistLabel.setForeground(new Color(243, 218, 49)); // 색상코드 사용

		// 랭킹 데이터를 가져와서 표시
		Ranking.addUserAndUpdateList(nickName, score);
		List<Ranking.User> ranking = Ranking.readRanking(); // ini파일을 읽어옴
		StringBuilder rankingText = new StringBuilder(); // StringBuilder 가변문자열을 처리하기 위한 클래스
		int rankNumber = 1;
		for (Ranking.User player : ranking) {
			rankingText.append(getRankString(rankNumber)).append(". ").append(player.name).append(" - ")
					.append(player.score).append("\n"); // .append 문자열끝에 문자열을 추가
			rankNumber++;
		}

		JTextArea rankTextArea = new JTextArea(rankingText.toString());
		rankTextArea.setFont(new Font("휴먼엑스포", Font.PLAIN, 18));
		rankTextArea.setBounds(93, 320, 250, 150);
		rankTextArea.setEditable(false);// 없을시 순위에 있는 리스트 수정이 가능해져버림
		rankTextArea.setOpaque(false);// 없을시 주변이 허옇게 바탕색이 생김
		rankTextArea.setForeground(Color.WHITE);

		// 패널에 컴포넌트 추가
		panel.add(nickNameLabel);
		panel.add(outtroTitleLabel);
		panel.add(rankLabel);
		panel.add(scoreLabel);
		panel.add(ranklistLabel);
		panel.add(rankTextArea); // 랭킹 표시 텍스트 영역 추가
		panel.add(restartButton);
		panel.add(exitButton);

//		// 나가기 버튼 클릭 시 동작 추가
//		exitButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent exit) {
//				System.exit(0);
//			}
//		});

		add(panel);
	}

	@Override
	public void dispose() {
		super.dispose();

	}

	@Override
	public void setWindowLocation() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		setLocation(dimension.width / 2 - 250, dimension.height / 2 - 400);

	}

	public void paint(Graphics g) {
		super.paint(g);
		Font TeamName = new Font("Arial Narrow", Font.BOLD, 15);
		g.setFont(TeamName);
		g.setColor(Color.white);
		g.drawString("Team. PacCo", 300, 580);
	}

	@Override
	public void setVisibleOption(boolean option) {
		this.setVisible(option);

	}

	@Override
	public void setResizableOption(boolean option) {

		this.setResizable(option);
	}

	@Override
	public void setCloseOption() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private String calculateRank(int score) {
		if (score < 20) {
			return "Bronze"; // 초보
		} else if (score >= 20 && score < 40) {
			return "Silver"; // 중수
		} else {
			return "Gold"; // 고수
		}
	}

	private String getRankString(int rank) {
		if (rank >= 11 && rank <= 13) {
			return rank + "th";
		}
		switch (rank % 10) {
		case 1:
			return rank + "st";
		case 2:
			return rank + "nd";
		case 3:
			return rank + "rd";
		default:
			return rank + "th";
		// 11과 13사이를 지정해주고 그 후 10을 나누게 하여 일의 자리를 나오게 한다.
		// 첫번째로 1등은 st, 2등은 nd, 3등은 rd, 그 후 나머지는 th로 표기되게 한다.
		}
	}

	public static void main(String[] args) {
		// 게임 오버 화면을 표시 (테스트용으로 점수 150 전달) 나중에 게임결과를 불러올때 사용
		SwingUtilities.invokeLater(() -> new OutroFrame(41, false, "안녕"));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Restart")) {
			ArrayList<int[]> wallMap = new ArrayList<int[]>();
			ArrayList<int[]> coinMap = new ArrayList<int[]>();
			maze.setCoinMap(coinMap);
			maze.setWallMap(wallMap);
			bgm.stop();
			this.dispose();
			new InGameFrame(nickName);
		} else {
			System.exit(0);
		}

	}
}