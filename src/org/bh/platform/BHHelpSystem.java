package org.bh.platform;

import java.net.URL;

import javax.help.HelpSet;
import javax.help.JHelp;
import javax.swing.JPanel;
/**
 * 
 * method to create the helpSystem
 *
 * <p>
 * the Helpsystem is built.
 *
 * @author Lars.Zuckschwerdt
 * @version 1.0, 31.12.2009
 *
 */
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
