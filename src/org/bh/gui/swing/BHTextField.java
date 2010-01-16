package org.bh.gui.swing;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import org.apache.log4j.Logger;
import org.bh.data.types.Calculable;
import org.bh.data.types.IValue;
import org.bh.data.types.StringValue;
import org.bh.gui.CompValueChangeManager;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.ValidationRule;

/**
 * BHTextField to display simple input fields at screen.
 * 
 * <p>
 * This class extends the Swing <code>JTextField</code> to display simple input
 * fields in Business Horizon.
 * 
 * @author Thiele.Klaus
 * @author Marco Hammel
 * @version 0.1, 2009/12/13
 * 
 */

public class BHTextField extends JTextField implements IBHModelComponent, IPlatformListener {
	private static final long serialVersionUID = -5249789865255724932L;

	/**
	 * unique key to identify Label.
	 */
	private String key;
	private ValidationRule[] validationRules = new ValidationRule[0];
	private String hint;
	boolean changeListenerEnabled = true;
	final CompValueChangeManager valueChangeManager = new CompValueChangeManager();
	private static final Logger log = Logger.getLogger(BHTextField.class);
	private boolean returnCalculable;

	/**
	 * Constructor to create new <code>BHTextField</code>.
	 * 
	 * @param key
	 *            unique key
	 * @param value
	 *            default value
	 * @param returnCalculable
	 *            whether to return a {@link Calculable} or {@link StringValue}
	 */
	public BHTextField(Object key, String value, boolean returnCalculable) {
		super(value);
		this.key = key.toString();
		if (this.key.isEmpty())
			log.debug("Empty key", new IllegalArgumentException());
		((AbstractDocument) getDocument())
				.setDocumentFilter(new ChangeListener());
		this.returnCalculable = returnCalculable;

		reloadText();
		Services.addPlatformListener(this);
	}

	/**
	 * Constructor to create new <code>BHTextField</code> which returns
	 * Calculables.
	 * 
	 * @param key
	 *            unique key
	 * @param value
	 *            default value
	 */
	public BHTextField(Object key, String value) {
		this(key, value, true);
	}

	/**
	 * Constructor to create new <code>BHTextField</code>.
	 * 
	 * @param key
	 *            unique key
	 * @param returnCalculable
	 *            whether to return a {@link Calculable} or {@link StringValue}
	 */
	public BHTextField(Object key, boolean returnCalculable) {
		this(key, "", returnCalculable);
	}

	/**
	 * Constructor to create new <code>BHTextField</code> which returns
	 * Calculables.
	 * 
	 * @param key
	 *            unique key
	 */
	public BHTextField(Object key) {
		this(key, "", true);
	}

	@Override
	public String getKey() {
		return key.toString();
	}

	@Override
	public ValidationRule[] getValidationRules() {
		return validationRules;
	}

	@Override
	public void setValidationRules(ValidationRule[] validationRules) {
		this.validationRules = validationRules;
	}

	@Override
	public IValue getValue() {
		if (returnCalculable)
			return Calculable.parseCalculable(this.getText());
		return new StringValue(this.getText());
	}

	@Override
	public String getHint() {
		return hint;
	}

	@Override
	public void setValue(IValue value) {
		if (value != null)
			this.setText(value.toString());
		else
			this.setText("");
	}

	@Override
	public CompValueChangeManager getValueChangeManager() {
		return valueChangeManager;
	}

	@Override
	public void setText(String t) {
		changeListenerEnabled = false;
		super.setText(t);
		changeListenerEnabled = true;
	}
	
	/**
	 * Handle PlatformEvents
	 */
	@Override
	public void platformEvent(PlatformEvent e) {
		if (e.getEventType() == Type.LOCALE_CHANGED) {
			reloadText();
		}
	}

	/**
	 * Reloads text if necessary.
	 */
	protected void reloadText() {
		hint = Services.getTranslator().translate(key, ITranslator.LONG);
		setToolTipText(hint);
	}

	protected class ChangeListener extends DocumentFilter {
		boolean enabled = true;

		@Override
		public void insertString(FilterBypass fb, int offset, String string,
				AttributeSet attr) throws BadLocationException {
			super.insertString(fb, offset, string, attr);
			fireChangeEvent();
		}

		@Override
		public void remove(FilterBypass fb, int offset, int length)
				throws BadLocationException {
			super.remove(fb, offset, length);
			fireChangeEvent();
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length,
				String text, AttributeSet attrs) throws BadLocationException {
			super.replace(fb, offset, length, text, attrs);
			fireChangeEvent();
		}

		private void fireChangeEvent() {
			if (changeListenerEnabled)
				valueChangeManager.fireCompValueChangeEvent(BHTextField.this);
		}
	}
}
