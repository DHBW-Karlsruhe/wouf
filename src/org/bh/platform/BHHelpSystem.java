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
	
	protected BHHelpSystem(String ID){
		this.initialise(ID);
	}
	public void initialise(String ID){
		
			try {
		      	ClassLoader cl = BHHelpSystem.class.getClassLoader();
		      	URL url = HelpSet.findHelpSet(cl, "jhelpset.hs");
		      	//helpViewer = new JHelp(new HelpSet(cl, url));
		      	} catch (Exception e) {
				System.err.println("API Help Set not found");
			}
		this.add(helpViewer);
		this.setVisible(true);

	}
}
