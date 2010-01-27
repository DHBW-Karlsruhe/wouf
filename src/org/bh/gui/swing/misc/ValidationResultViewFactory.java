/*
 * Copyright (c) 2003-2009 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


/**
 * This class is the overwritten version of JGoodies ValidationResultViewFactory.
 * Standard icons replaced by NimbusIcons.
 * 
 * @author Patrick Heinz
 * @version 0.1, 21.01.10 
 */


package org.bh.gui.swing.misc;

import java.awt.Color;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.bh.gui.swing.BHStatusBar;

import com.jgoodies.validation.Severity;
import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.view.ValidationResultListAdapter;

/**
 * A factory class that vends views that present the state and contents of
 * {@link ValidationResult}s. The validation views are composed from user
 * interface components like text areas, lists, labels, etc. Most factory
 * methods require a {@link ValidationResultModel} that notifies the view about
 * changes in an underlying ValidationResult.
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.14 $
 * 
 * @see ValidationResult
 * @see ValidationMessage
 */
public final class ValidationResultViewFactory {

	private static final Color DEFAULT_REPORT_BACKGROUND = new Color(255, 255,
			210);

	private ValidationResultViewFactory() {
		// Override default constructor; prevents instantiation.
	}

	// Creating Validation Views **********************************************

	/**
	 * Creates and returns a list that presents validation messages. The list
	 * content is bound to the given {@link ValidationResultModel} using a
	 * {@link ValidationResultListAdapter}.
	 * 
	 * @param model
	 *            the model that provides the observable validation result
	 * @return a <code>JList</code> that shows validation messages
	 */
	public static JComponent createReportList(ValidationResultModel model) {
		return createReportList(model, DEFAULT_REPORT_BACKGROUND);
	}

	/**
	 * Creates and returns a list wrapped in a scroll pane that presents
	 * validation messages. The list content is bound to the given
	 * {@link ValidationResultModel} using a {@link ValidationResultListAdapter}
	 * .
	 * 
	 * @param model
	 *            the model that provides the observable validation result
	 * @param backgroundColor
	 *            the color used to paint the area's background
	 * @return a <code>JList</code> that shows validation messages
	 */
	@SuppressWarnings("synthetic-access")
	public static JComponent createReportList(ValidationResultModel model,
			Color backgroundColor) {
		JList list = new JList();
		list.setFocusable(false);
		list.setBackground(backgroundColor);
		list.setCellRenderer(new BasicValidationMessageCellRenderer());
		list.setModel(new ValidationResultListAdapter(model));
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setVisible(model.hasMessages());

		model.addPropertyChangeListener(
				ValidationResultModel.PROPERTYNAME_MESSAGES,
				new MessageStateChangeHandler(scrollPane));

		return scrollPane;
	}

	// Accessing Useful Icons *************************************************

	/**
	 * Returns a default error icon useful to indicate validation errors.
	 * 
	 * @return a default error icon
	 */
	public static ImageIcon getErrorIcon() {
		return (new ImageIcon(BHStatusBar.class
				.getResource("/org/bh/images/tree/error_red.png")));
	}

	/**
	 * Returns a default warnings icon useful to indicate validation warnings.
	 * 
	 * @return a default warning icon
	 */
	public static ImageIcon getWarningIcon() {
		return (new ImageIcon(BHStatusBar.class
				.getResource("/org/bh/images/tree/warning.png")));
	}

	/**
	 * Returns a large error icon useful to indicate validation errors.
	 * 
	 * @return a large error icon
	 */
	public static ImageIcon getLargeErrorIcon() {
		return (new ImageIcon(BHStatusBar.class
				.getResource("/org/bh/images/tree/error_red_large.png")));
	}

	/**
	 * Returns a large warnings icon useful to indicate validation warnings.
	 * 
	 * @return a large warning icon
	 */
	public static ImageIcon getLargeWarningIcon() {
		return (new ImageIcon(BHStatusBar.class
				.getResource("/org/bh/images/tree/warning_large.png")));
	}

	/**
	 * Returns a large information icon useful to indicate input hints.
	 * 
	 * @return a large information icon
	 */
	public static ImageIcon getLargeInfoIcon() {
		return (new ImageIcon(BHStatusBar.class
				.getResource("/org/bh/images/tree/info_large.png")));
	}

	/**
	 * Returns a default information icon useful to indicate input hints.
	 * 
	 * @return a default information icon
	 */
	public static ImageIcon getInfoIcon() {
		return (new ImageIcon(BHStatusBar.class
				.getResource("/org/bh/images/tree/info.png")));
	}

	/**
	 * Returns a question icon useful for decisions.
	 * 
	 * @return a question icon
	 */
	public static ImageIcon getQuestionIconNimbus() {
		return (new ImageIcon(BHStatusBar.class
				.getResource("/org/bh/images/tree/question.png")));
	}

	/**
	 * Returns the warning icon for warnings, the error icon for errors and
	 * {@code null} otherwise.
	 * 
	 * @param severity
	 *            the severity used to lookup the icon
	 * @return the warning icon for warnings, error icon for errors, {@code
	 *         null} otherwise
	 * @see #getWarningIcon()
	 * @see #getErrorIcon()
	 * @see #getSmallIcon(Severity)
	 */
	public static Icon getIcon(Severity severity) {
		if (severity == Severity.ERROR) {
			return getErrorIcon();
		} else if (severity == Severity.WARNING) {
			return getWarningIcon();
		} else {
			return null;
		}
	}

	/**
	 * Returns the small warning icon for warnings, the small error icon for
	 * errors and {@code null} otherwise.
	 * 
	 * @param severity
	 *            the severity used to lookup the icon
	 * @return the small warning icon for warnings, the small error icon for
	 *         errors, {@code null} otherwise
	 * @see #getSmallWarningIcon()
	 * @see #getSmallErrorIcon()
	 * @see #getIcon(Severity)
	 */
//	public static Icon getSmallIcon(Severity severity) {
//		if (severity == Severity.ERROR) {
//			return getSmallErrorIcon();
//		} else if (severity == Severity.WARNING) {
//			return getSmallWarningIcon();
//		} else {
//			return null;
//		}
//	}

	// ValidationResultModel Listeners ****************************************

	/**
	 * Sets the component visible if the validation result has messages.
	 */
	public static final class MessageStateChangeHandler implements
			PropertyChangeListener {

		private final Component component;

		/**
		 * Constructs a MessageStateHanlder that updates the visibility of the
		 * given Component.
		 * 
		 * @param component
		 *            the component to show and hide
		 */
		public MessageStateChangeHandler(Component component) {
			this.component = component;
		}

		/**
		 * The ValidationResult's 'messages' property has changed. Hides or
		 * shows the component if the ValidationResult is OK or has messages
		 * resp.
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(
					ValidationResultModel.PROPERTYNAME_MESSAGES)) {
				boolean hasMessages = ((Boolean) evt.getNewValue())
						.booleanValue();
				component.setVisible(hasMessages);
			}
		}
	}

	// Renderer ***************************************************************

	/**
	 * A <code>ListCellRenderer</code> implementation which renders labels for
	 * instances of <code>ValidationMessage</code>.
	 */
	private static final class BasicValidationMessageCellRenderer extends
			DefaultListCellRenderer {

		/**
		 * In addition to the superclass behavior, this method assumes that the
		 * value is a ValidationMessage. It sets the renderer's icon to the one
		 * associated with the ValidationMessage's severity and sets the
		 * renderer's text to the message's formatted text.
		 */
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, false, // Ignore
					// the
					// selection
					// state
					false); // Ignore the cell focus state
			ValidationMessage message = (ValidationMessage) value;
			this.setIcon(ValidationResultViewFactory
					.getIcon(message.severity()));
			this.setText(message.formattedText());
			return this;
		}
	}
}
