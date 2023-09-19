package pacboy.frame.inter;

import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public interface UserInterface {
	
	public void setTitleAndSize();

	public JPanel setPanel();
	
	public void setLayoutManager(LayoutManager layoutManager, JPanel panel);

	public void setComponent(JPanel panel);
	
	public void setWindowLocation();
	
	public void setCloseOption();

	public void setResizableOption(boolean option);
	
	public void setVisibleOption(boolean option);

}