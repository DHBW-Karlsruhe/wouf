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

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.Container;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public final class BHOptionPane extends JOptionPane {

	public BHOptionPane() {
		super();
	}

	public BHOptionPane(Object message, int messageType, int optionType) {
		super(message, messageType, optionType);
	}

	public static int showConfirmDialog(Component parentComponent,
			Object message, String title, int optionType) {
		BHOptionPane od = new BHOptionPane(message, QUESTION_MESSAGE,
				optionType);

		setListener(od);
		setFocusTraversalKeys(od);
		JDialog dialog = od.createDialog(parentComponent, title);
		dialog.setVisible(true);
		Object selectedValue = od.getValue();
		Object[] options = od.getOptions();
		if (options == null) {
			if (selectedValue instanceof Integer)
				return ((Integer) selectedValue).intValue();
			return CLOSED_OPTION;
		}

		return CLOSED_OPTION;

	}

	private static void setFocusTraversalKeys(Component component) {
		// Change the forward focus traversal keys for a component
		Set<AWTKeyStroke> forward = new HashSet<AWTKeyStroke>(
				component
						.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
		// set
		forward.add(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
		component.setFocusTraversalKeys(
				KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward);

		Set<AWTKeyStroke> backward = new HashSet<AWTKeyStroke>(
				component
						.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
		// set
		backward.add(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
		component.setFocusTraversalKeys(
				KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward);
	}

	public static void registerAction(JButton button) {
		button.registerKeyboardAction(button.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);

		button.registerKeyboardAction(button.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);

	}

	public static void setListener(Component cont) {
		if (cont instanceof Container) {
			for (Component comp : ((Container) cont).getComponents()) {
				if (comp instanceof JButton) {
					JButton button = (JButton) comp;
					registerAction(button);
				} else if (comp instanceof Container) {
					setListener(comp);
				}
			}
		}
	}

}
