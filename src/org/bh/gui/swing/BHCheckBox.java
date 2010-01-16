package org.bh.gui.swing;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import org.apache.log4j.Logger;
import org.bh.data.types.IValue;
import org.bh.data.types.IntegerValue;
import org.bh.data.types.StringValue;
import org.bh.gui.CompValueChangeManager;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.ValidationRule;

/**
 * 
 * <p>
 * This class extends the Swing <code>JCheckBox</code> .
 * 
 * @author Kharitonov.Anton
 * @version 0.1, 2010/01/07
 * 
 * @author Norman
 * @version 0.2, 16.01.2010
 * 
 */

// TODO Hints setzen!!! Noch werden für Textfields keine Hints erzeugt
public class BHCheckBox extends JCheckBox implements IBHModelComponent {

	static final Logger log = Logger.getLogger(BHCheckBox.class);
	static final ITranslator translator = Services.getTranslator();
	
	/**
	 * unique key to identify Label.
	 */
	String key;
	String hint;

	boolean changeListenerEnabled = true;
	final CompValueChangeManager valueChangeManager = new CompValueChangeManager();

	/**
	 * Constructor to create new <code>BHCheckBox</code>. with key based text
	 * 
	 * @param key
	 *            unique key
	 */
	public BHCheckBox(final Object key) {
		super();
		this.key = key.toString();
		reloadText();
		addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (changeListenerEnabled) {
					valueChangeManager
							.fireCompValueChangeEvent(BHCheckBox.this);
					log.debug("ValueChangedEvent fired by BHCheckBox with key"
							+ key.toString());
				}
			}
		});
		setSelected(false);
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getHint() {
		return hint;
	}

	// /**
	// * set properties of instance.
	// */
	// private void setProperties() {
	// Services.addPlatformListener(this);
	// }

	public String getBHHint() {

		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public void platformEvent(PlatformEvent e) {
		if (e.getEventType() == Type.LOCALE_CHANGED) {
			reloadText();
		}
	}

	@Override
	public ValidationRule[] getValidationRules() {
		return new ValidationRule[0];
	}

	@Override
	public IValue getValue() {
		if (isSelected()) {
			return new IntegerValue(1);
		}
		return new IntegerValue(0);
	}

	@Override
	public CompValueChangeManager getValueChangeManager() {
		return valueChangeManager;
	}

	@Override
	public void setValidationRules(ValidationRule[] validationRules) {
		// noop
	}

	@Override
	public void setValue(IValue value) {
		if (value == null) {
			return;
		}
		if (value instanceof IntegerValue) {
			int valInt = ((IntegerValue) value).getValue();
			if (valInt >= 1) {
				setSelected(true);
			}
			if (valInt == 0) {
				setSelected(false);
			}
		}
		if (value instanceof StringValue) {
			String valStr = ((StringValue) value).getString();
			if (valStr.toLowerCase().equals("true")) {
				setSelected(true);
				return;
			}
			if (valStr.toLowerCase().equals("false")) {
				setSelected(false);
				return;
			}
			return;
		}
	}

	protected void reloadText() {
		hint = Services.getTranslator().translate(key, ITranslator.LONG);
		setToolTipText(hint);
		updateUI();
	}
	
	@Override
	public String toString() {
		return translator.translate(key);
	}
}