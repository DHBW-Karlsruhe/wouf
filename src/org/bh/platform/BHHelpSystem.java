/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
 *
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
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
 * @author Patrick Maisel
 * @version 1.0, 31.12.2009
 * @version 1.1, 13.01.2011 Mehrsprachigkeit implementiert
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

			// Standardpfad für Hilfe-Datei wird gewählt
			URL url = HelpSet.findHelpSet(cl, "de/jhelpset.hs");

			// URL wird hier geändert, damit die richtige Hilfedatei anhand der
			// Locale gewählt wird. Wenn zu einer Locale keine Hilfedatei
			// existiert, wird die Sprache der Hilfe auf englisch gesetzt.
			Locale l = Services.getTranslator().getLocale();
			if (!(l.toString().equals("de")) && !(l.toString().equals("en"))) {
				log.info("No Help file for this language. Help will be displayed in english.");
				int urlsize = url.toString().length();
				String urlold = url.toString().substring(0, urlsize - 20);
				log.info("Hilfedatei gefunden: " + url);
				// log.info(urlold);
				String urlnew = (urlold + ".jar!/" + "en" + "/jhelpset.hs");
				//log.info(urlnew);
				URL urlnewurl = new URL(urlnew);
				log.info("Benutzte Hilfedatei: " + urlnewurl.toString());
				helpViewer = new JHelp(new HelpSet(cl, urlnewurl));
				helpViewer.setPreferredSize(new Dimension(930, 650));
				helpViewer.setCurrentID(ID);
			} else {
				int urlsize = url.toString().length();
				String urlold = url.toString().substring(0, urlsize - 20);
				log.info("Hilfedatei gefunden: " + url);
				// log.info(urlold);
				String urlnew = (urlold + ".jar!/" + l + "/jhelpset.hs");
				//log.info(urlnew);
				URL urlnewurl = new URL(urlnew);
				log.info("Benutzte Hilfedatei: " + urlnewurl.toString());
				helpViewer = new JHelp(new HelpSet(cl, urlnewurl));
				helpViewer.setPreferredSize(new Dimension(930, 650));
				helpViewer.setCurrentID(ID);
			}

			// -----------------------------------------------------------------

			// log.info(urlnewurl.toString());

		} catch (Exception e) {
			System.err.println("API Help Set not found");
		}
		this.add(helpViewer);
		this.setVisible(true);

	}
}
