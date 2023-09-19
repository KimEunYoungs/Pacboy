package pacboy.frame;

import javax.swing.*;

import pacboy.database.BGM;
import pacboy.frame.inter.UserInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;

public class IntroFrame extends JFrame implements UserInterface, ActionListener {
	// 이미지 경로 설정
	static final String IMAGE_PATH = Paths.get(File.separator + "eclipseSTS", "workspace", "images").toString();
	String nickName;
	private BGM bgm;
	private JButton exitButton;
	private JButton startButton;
	private JTextField nicknameField;

	public IntroFrame() {
		setTitleAndSize();
		// 배경 패널 생성
		JPanel backgroundPanel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics gp) {
				super.paintComponent(gp);
				// 배경 이미지 설정
				ImageIcon imageIcon = new ImageIcon(IMAGE_PATH + File.separator + "empty.png"); // 나중에 추가
				Image image = imageIcon.getImage();
				gp.drawImage(image, 0, 0, 400, 600, this);
			}
		};

		setComponent(backgroundPanel);
		setWindowLocation();
		setCloseOption();
		setResizableOption(false);
		setVisibleOption(true);

		bgm = new BGM("intro.wav", true, -20.0f);
		bgm.start();

	}

	@Override
	public void setTitleAndSize() {
		// 타이틀 설정
		this.setTitle("Pacboy");
		// 화면 크기 설정
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
		// 타이틀 이미지 레이블
		JLabel introTitleLabel = new JLabel(new ImageIcon(IMAGE_PATH + File.separator + "title.png"));
		introTitleLabel.setBounds(50, 100, 300, 150);
		introTitleLabel.setHorizontalAlignment(JLabel.CENTER);

		// 닉네임 레이블
		JLabel nicknameLabel = new JLabel("Nickname"); // "Nickname" 라벨 추가
		nicknameLabel.setFont(new Font("휴먼엑스포", Font.PLAIN, 15));
		nicknameLabel.setBounds(37, 395, 500, 40);
		nicknameLabel.setForeground(Color.WHITE); // 폰트 색상을 흰색으로 설정

		// 닉네임 입력 필드
		nicknameField = new JTextField();
		nicknameField.setFont(new Font("휴먼엑스포", Font.PLAIN, 15));
		nicknameField.setBounds(200, 400, 150, 30); // 닉네임 창 크기 변경

		// 시작 버튼
		startButton = new JButton("Start");
		startButton.setFont(new Font("휴먼엑스포", Font.PLAIN, 20));
		startButton.setBounds(30, 450, 150, 50);

		// 종료 버튼
		exitButton = new JButton("Exit");
		exitButton.setFont(new Font("휴먼엑스포", Font.PLAIN, 20));
		exitButton.setBounds(200, 450, 150, 50);

		startButton.addActionListener(this);
		exitButton.addActionListener(this);

		// 패널에 컴포넌트 추가
		panel.add(introTitleLabel);
		panel.add(nicknameLabel);
		panel.add(nicknameField);
		panel.add(startButton);
		panel.add(exitButton);

		add(panel);
	}

	// 팀 이름 그리기
	public void paint(Graphics g) {
		super.paint(g);
		Font TeamName = new Font("Arial Narrow", Font.BOLD, 15);
		g.setFont(TeamName);
		g.setColor(Color.white);
		g.drawString("Team. PacCo", 300, 580);
	}

	@Override
	public void setWindowLocation() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		setLocation(dimension.width / 2 - 250, dimension.height / 2 - 400);

	}

	// 프레임의 보이기/감추기 설정
	@Override
	public void setVisibleOption(boolean option) {
		this.setVisible(option);

	}

	// 창 크기 변경 설정
	@Override
	public void setResizableOption(boolean option) {

		this.setResizable(option);
	}

	// 프레임의 닫기 동작 설정
	@Override
	public void setCloseOption() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public static void main(String[] args) {
		// 스윙 애플리케이션 시작
		SwingUtilities.invokeLater(() -> new IntroFrame());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Start")) {
			nickName = nicknameField.getText();
			if (nickName.isEmpty()) {
				// 닉네임이 비어있을 때 알림 메시지 표시
				JOptionPane.showMessageDialog(null, "Please enter your nickname!", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				// 닉네임을 입력한 후 인게임 화면으로 이동
				new InGameFrame(nickName); // 닉네임을 생성자에 전달
				bgm.stop();
				this.dispose();
			}

		} else {
			System.exit(0);
		}

	}
}