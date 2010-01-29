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
