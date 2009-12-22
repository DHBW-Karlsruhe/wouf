package org.bh.gui.swing;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import org.bh.platform.PlatformEvent;
import org.bh.platform.IPlatformListener;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;

/**
 * SplashScreen on startup of Business Horizon Application, to block the
 * mainframe while loading.
 * 
 * @author Patrick Heinz
 * @author Robert Vollmer
 * @version 0.1, 2009/12/20
 * 
 */

public class BHSplashScreen extends JWindow implements Runnable,
		IPlatformListener {
	private static final long serialVersionUID = -3834743030431316878L;
	private static final int MIN_DISPLAY_TIME = 3000;
	private boolean isLoaded = false;

	public BHSplashScreen() {
		Services.addPlatformListener(this);
	}

	public void run() {
		setLayout(new BorderLayout());

		// build GUI components
		Icon splashScreenIcon = new ImageIcon(BHSplashScreen.class
				.getResource("/org/bh/images/SplashScreen.jpg"));
		JLabel iconLabel = new JLabel("", splashScreenIcon, JLabel.CENTER);
		iconLabel.setSize(477, 229);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		// progressBar.setBackground(new Color(0,0,50));

		add(iconLabel, BorderLayout.CENTER);
		add(progressBar, BorderLayout.SOUTH);

		setSize(477, 240);
		// center the splash screen on the screen
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setVisible(true);

		try {
			Thread.sleep(MIN_DISPLAY_TIME);
			synchronized (this) {
				while (!isLoaded) {
					wait();
				}
			}
		} catch (InterruptedException e) {
			// should not happen
		}

		dispose();
	}

	@Override
	public void platformEvent(PlatformEvent e) {
		if (e.getEventType() == Type.PLATFORM_LOADING_COMPLETED) {
			isLoaded = true;
			synchronized (this) {
				notify();
			}
		}
	}
}