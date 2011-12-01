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
package org.bh.gui.swing.comp;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import org.apache.log4j.Logger;
import org.bh.data.types.IValue;
import org.bh.data.types.IntegerValue;
import org.bh.data.types.StringValue;
import org.bh.gui.CompValueChangeManager;
import org.bh.gui.IBHModelComponent;
import org.bh.platform.IPlatformListener;
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

@SuppressWarnings("serial")
public class BHCheckBox extends JCheckBox implements IBHModelComponent,
		IPlatformListener {

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
		reloadText(); //unnötiger doppelter Aufruf entfernt, da es Probleme in
		// mit der Anzeige in den Einstellungen gab, was den Text der
		// chbanimation gab
		Services.addPlatformListener(this);
		addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (changeListenerEnabled) {
					valueChangeManager
							.fireCompValueChangeEvent(BHCheckBox.this);
				}
			}
		});
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getHint() {
		return hint;
	}

	@Override
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
		return new IntegerValue(isSelected() ? 1 : 0);
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
			setSelected(false);
			return;
		} else if (value instanceof IntegerValue) {
			int valInt = ((IntegerValue) value).getValue();
			changeListenerEnabled = false;
			setSelected(valInt >= 1);
			changeListenerEnabled = true;
		} else if (value instanceof StringValue) {
			String valStr = ((StringValue) value).getString();
			changeListenerEnabled = false;
			setSelected(valStr.toLowerCase().equals("true"));
			changeListenerEnabled = false;
		}
	}

	protected void reloadText() {
		hint = Services.getTranslator().translate(key, ITranslator.LONG);
		setToolTipText(hint);
		setText(translator.translate(key));
	}

	@Override
	public String toString() {
		return translator.translate(key);
	}
}