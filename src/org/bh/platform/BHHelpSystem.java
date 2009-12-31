package org.bh.platform;

import java.net.URL;

import javax.help.HelpSet;
import javax.help.JHelp;
import javax.swing.JPanel;

public class BHHelpSystem extends JPanel{
	JHelp helpViewer = null;
	
	protected BHHelpSystem(){
		this.initialise();
	}
	public void initialise(){
		
			try {
		      	ClassLoader cl = BHHelpSystem.class.getClassLoader();
		      	URL url = HelpSet.findHelpSet(cl, "jhelpset.hs");
		      	helpViewer = new JHelp(new HelpSet(cl, url));
		      	//helpViewer.setCurrentID("Simple.Introduction");
		      	System.out.println("Hier");
			} catch (Exception e) {
				System.err.println("API Help Set not found");
			}
		this.add(helpViewer);
		this.setVisible(true);

	}
//	public static void main(String[] args0){
//		JFrame frame = new JFrame();
//		frame.setTitle("Hilfe zu (hier Dein Programmname)");
//		frame.setSize(800,600);
//		frame.getContentPane().add(new BHHelpSystem());
//		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		frame.setVisible(true);
//	}
}
