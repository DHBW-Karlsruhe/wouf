/*******************************************************************************
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
package org.bh.gui.swing.comp;

import javax.swing.JLabel;

import org.bh.data.types.IValue;
import org.bh.gui.CompValueChangeManager;
import org.bh.gui.IBHModelComponent;
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
