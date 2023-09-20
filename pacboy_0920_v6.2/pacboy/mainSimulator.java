package pacboy;

import javax.swing.SwingUtilities;

import pacboy.frame.IntroFrame;

public class mainSimulator {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new IntroFrame());

	}

}
