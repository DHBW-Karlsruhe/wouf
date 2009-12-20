package org.bh.gui.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.MouseInputListener;

/**
 * SplashScreen on startup of Business Horizon Application,
 * to block the mainframe while loading.
 * 
 * @author Heinz.Patrick
 * @version 0.1, 2009/12/20
 * 
 */

public class BHSplashScreen extends JWindow implements Runnable, MouseInputListener {

	private Container c;
	private JLabel iconLabel;
	private Icon splashScreenIcon;
	private JProgressBar progressBar;

	public BHSplashScreen() {
		c = getContentPane();
		c.setLayout(new BorderLayout());
		
		// build GUI components
		splashScreenIcon = new ImageIcon("images/SplashScreen.jpg");
		iconLabel = new JLabel("", splashScreenIcon, JLabel.CENTER);
		iconLabel.setSize(477, 229);

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		// progressBar.setBackground(new Color(0,0,50));

		c.add(iconLabel, BorderLayout.CENTER);
		c.add(progressBar, BorderLayout.SOUTH);
		addMouseListener(this);
		
		this.setSize(477, 240);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			// Show BHSplashScreen at least for 2 seconds
			if (i <= 2) {
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
			else {				
				try {
					// TODO Call method to check if all data is loaded
					Thread.sleep(1000);
//					JWindow setVisible(false), when all data is loaded
//					this.setVisible(false); <-- sets progress bar invisible, not window...
				}
				catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * MouseListener interface methods to make BHSplashScreen disappear when clicked on it
	 */
	public void mouseClicked(MouseEvent e) {
		this.setVisible(false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}