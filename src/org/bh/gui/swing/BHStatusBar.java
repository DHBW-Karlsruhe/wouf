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
package org.bh.gui.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.MouseInputAdapter;

import org.bh.gui.swing.misc.Icons;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 * BHStatusBar to display a status bar on the bottom of the screen
 * 
 * @author Tietze.Patrick, Maisel.Patrick
 * @version 0.1, 2009/12/05
 * @version 0.2, 2009/12/29
 * @version 0.3, 2010/12/15
 * 
 **/

@SuppressWarnings("serial")
public final class BHStatusBar extends JPanel {

	private static BHStatusBar instance = null;
	JLabel bh;
	JLabel hintLabel;
	JLabel errorHintLabel;
	CellConstraints cons;

	PopupFactory factory;
	public Popup popup;

	JScrollPane popupPane;
	boolean open;

	ITranslator translator = BHTranslator.getInstance();

	JOptionPane optionPane;

	private BHStatusBar() {
		// setLayout to the status bar
		String rowDef = "p";
		String colDef = "6px,fill:1px:grow,6px,min,6px,fill:1px:grow,6px";
		FormLayout layout = new FormLayout(colDef, rowDef);
		layout.setColumnGroups(new int[][] { { 2, 6 } });
		setLayout(layout);
		cons = new CellConstraints();

		hintLabel = new JLabel("");
		hintLabel.setIcon(Icons.INFO_ICON);
		removeHint();

		errorHintLabel = new JLabel(translator.translate("errorHint"));
		errorHintLabel.setIcon(Icons.ERROR_ICON);
		errorHintLabel.addMouseListener(new BHLabelListener());
		errorHintLabel
				.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		removeErrorHint();

		// create BH logo label
		bh = new JLabel(new ImageIcon(
				BHStatusBar.class.getResource("/org/bh/images/bh-logo-2.png")));

		// add components to panel
		add(hintLabel, new CellConstraints().xywh(2, 1, 1, 1));
		add(bh, cons.xywh(4, 1, 1, 1));
		add(errorHintLabel,
				new CellConstraints().xywh(6, 1, 1, 1, "right,center"));
	}



	public static BHStatusBar getInstance() {
		if (instance == null) {
			instance = new BHStatusBar();
		}
		return instance;
	}

	/**
	 * Method to set a hint text from a String to the hintLabel
	 * 
	 * @param hintText
	 * 
	 */
	public void setHint(String hintText) {
		setHint(hintText, false);
	}

	public void setHint(String hintText, boolean alert) {
		hintLabel.setText(hintText);
		if (alert) {
			hintLabel.setForeground(Color.RED);
		} else {
			hintLabel.setForeground(Color.BLACK);
		}
		hintLabel.setVisible(true);

	}

	/**
	 * Method to set error hint(s) from a JScrollPane to the errorHintLabel
	 * 
	 * @param pane
	 */

	public void setErrorHint(JScrollPane pane) {
		errorHintLabel.setVisible(true);
		// Fehlerhinweis wird jetzt übersetzt 15.12.2010
		errorHintLabel.setText(translator.translate("errorHint"));
		popupPane = pane;

		// creates the popup for the error information
		optionPane = new JOptionPane(pane, JOptionPane.PLAIN_MESSAGE);
	}

	public void removeHint() {
		hintLabel.setVisible(false);
	}

	public void removeErrorHint() {
		errorHintLabel.setVisible(false);
	}

	public void openToolTipPopup() {
		factory = PopupFactory.getSharedInstance();
		popup = factory.getPopup(this, popupPane, 500, 500);
		popup.show();
	}

	/**
	 * 
	 * This MouseListener provides the lErrorTip - Label the ability to show a
	 * popup with validation information
	 * 
	 * @author Tietze.Patrick
	 * @version 1.0, 2009/12/30
	 * @version 1.1, 2010/01/25
	 * 
	 */

	class BHLabelListener extends MouseInputAdapter {

		public BHLabelListener() {
			open = false;
		}

		public void mouseClicked(MouseEvent e) {
			optionPane.createDialog(null,
					translator.translate("errorsDialogTitle")).setVisible(true);
		}
	}
}
