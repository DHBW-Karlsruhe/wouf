package org.bh.platform;

import java.awt.Dimension;
import java.net.URL;
import java.util.Locale;

import javax.help.HelpSet;
import javax.help.JHelp;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

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
			Logger log = Logger.getLogger(BHHelpSystem.class);

			// Dieser Code muss aktiviert werden, damit die Software von Hudson
			// kompiliert läuft
			URL url = HelpSet.findHelpSet(cl, "jhelpset.hs");
			// ----------------------------------------------------------------

			// Dieser Code muss aktiviert werden, wenn die Software unabhängig
			// von Hudson kompiliert wird

			// URL wird hier geändert, damit die richtige Hilfedatei anhand der
			// Locale gewählt wird
			// Locale l = Services.getTranslator().getLocale();
			// int urlsize = url.toString().length();
			// String urlold = url.toString().substring(0, urlsize - 19);
			// TODO Pfadlänge checken und anpassen
			// log.info(urlold);
			// String urlnew = (urlold + l.toString() + ".jar!/jhelpset.hs");
			// log.info(urlnew);
			// URL urlnewurl = new URL(urlnew);
			// log.info(urlnewurl.toString());
			// -----------------------------------------------------------------

			log.info(url.toString());
			helpViewer = new JHelp(new HelpSet(cl, url));
			helpViewer.setPreferredSize(new Dimension(930, 650));
			helpViewer.setCurrentID(ID);

		} catch (Exception e) {
			System.err.println("API Help Set not found");
		}
		this.add(helpViewer);
		this.setVisible(true);

	}
}
