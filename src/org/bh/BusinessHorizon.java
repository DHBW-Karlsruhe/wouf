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
package org.bh;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.bh.platform.PlatformController;
import org.bh.platform.PluginManager;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

/**
 * 
 * This is the entry class for Business Horizon.
 * 
 * The main method of this class will be called when Business Horizon starts.
 * 
 * @author Robert Vollmer
 * @version 0.2, 20.12.2009
 * 
 * 
 */

public class BusinessHorizon {
	public static final boolean DEBUG = true;
	private static final Logger log = Logger.getLogger(BusinessHorizon.class);

	/**
	 * @param args
	 *            Commandline arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (DEBUG)
			Services.setupLogger();
		
		log.info("Business Horizon is starting...");
		
		Thread
		.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				log.error("Uncaught exception", e);
			}
		});

		if (SVN.isRevisionSet())
			log.info("SVN Revision is " + SVN.getRevision());
		
		// Check if JRE is Java 6 Update 10, else quit.
		if (!Services.jreFulfillsRequirements()) {	
			String message = Services.getTranslator().translate("PjreRequirement", ITranslator.LONG);
			String title = Services.getTranslator().translate("PjreRequirement");
			log.error(message);
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		System.setSecurityManager(null);
		
		PluginManager.init();
		
		
		// set menu name
		if(System.getProperty("os.name").startsWith("Mac OS X"))
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Business Horizon");

		// set Look&Feel
		Services.setNimbusLookAndFeel();
		
		// Invoke start of BHMainFrame
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PlatformController.getInstance();
			}
		});
	}
}
