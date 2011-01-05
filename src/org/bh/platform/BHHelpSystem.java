package org.bh.platform;

import java.awt.Dimension;
import java.net.URL;
import java.util.Locale;

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
@SuppressWarnings("serial")
public class BHHelpSystem extends JPanel {
	JHelp helpViewer = null;

	protected BHHelpSystem(String ID) {
		this.initialise(ID);
	}

	/**
	 * method to initialise the HelpFrame
	 * 
	 * @param ID
	 *            ID to get the choosen Help. mathe for MathHelp user for
	 *            Usermanual
	 */
	public void initialise(String ID) {

		try {
			ClassLoader cl = BHHelpSystem.class.getClassLoader();
			Locale l = Services.getTranslator().getLocale();
			URL url = HelpSet.findHelpSet(cl, "jhelpset.hs");
			System.out.println(url.toString());
			// URL wird hier geändert, damit die richtige Hilfedatei anhand der
			// Locale gewählt wird
			int urlsize = url.toString().length();
			String urlold = url.toString().substring(0, urlsize - 19);
			System.out.println(urlold);
			String urlnew = (urlold + l.toString() + ".jar!/jhelpset.hs");
			System.out.println(urlnew);
			URL urlnewurl = new URL(urlnew);
			System.out.println(urlnewurl.toString());
			// URL verändert

			helpViewer = new JHelp(new HelpSet(cl, urlnewurl));
			helpViewer.setPreferredSize(new Dimension(930, 650));
			helpViewer.setCurrentID(ID);

		} catch (Exception e) {
			System.err.println("API Help Set not found");
		}
		this.add(helpViewer);
		this.setVisible(true);

	}
}
