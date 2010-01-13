package org.bh.gui.swing;

import javax.swing.JLabel;

import org.bh.data.types.IValue;
import org.bh.gui.CompValueChangeManager;
import org.bh.validation.ValidationRule;

/**
 * Label which displays the value belonging to a DTO key.
 * 
 * @author Robert Vollmer
 * @version 1.0, 2010/01/07
 * 
 */
public class BHValueLabel extends JLabel implements IBHModelComponent {
	private static final long serialVersionUID = 2258191535683187945L;
	private String key;
	private IValue value;
	private final CompValueChangeManager valueChangeManager = new CompValueChangeManager();

	/**
	 * Constructor to create new <code>BHLabel</code>.
	 * 
	 * @param key
	 *            Translation key
	 */
	public BHValueLabel(Object key) {
		this.key = key.toString();
	}

	/**
	 * Returns the unique ID of the <code>BHLabel</code>.
	 * 
	 * @return id unique identifier.
	 */
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getHint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public ValidationRule[] getValidationRules() {
		return new ValidationRule[0];
	}

	@Override
	public IValue getValue() {
		return value;
	}

	@Override
	public CompValueChangeManager getValueChangeManager() {
		return valueChangeManager;
	}

	@Override
	public void setValidationRules(ValidationRule[] validationRules) {
		// nothing to do
	}

	@Override
	public void setValue(IValue value) {
		this.value = value;
		if (value != null)
			setText(value.toString());
		else
			setText("");
	}
}
