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

import javax.swing.JSlider;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.bh.gui.IBHComponent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

public class BHSlider extends JSlider implements IBHComponent {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1447473844655945376L;
	private String key;
	private String hint;
	private static final Logger log = Logger.getLogger(BHSlider.class);

	public BHSlider(Object key, int min, int max, int value) {
		super(SwingConstants.HORIZONTAL, min, max, value);
		this.key = key.toString();
		if (this.key.isEmpty())
			log.debug("Empty key", new IllegalArgumentException());
		this.setMajorTickSpacing(10);
		this.setMinorTickSpacing(2);
		this.setPaintTicks(true);
		this.setPaintLabels(true);
	}
	
	public BHSlider(Object key){
		super(SwingConstants.HORIZONTAL);
		this.key = key.toString();
		this.setMajorTickSpacing(10);
		this.setMinorTickSpacing(2);
		this.setPaintTicks(true);
		this.setPaintLabels(true);
	}

	@Override
	public String getHint() {
		return hint;
	}

	@Override
	public String getKey() {
		return key.toString();
	}

	protected void reloadText() {
		hint = Services.getTranslator().translate(key, ITranslator.LONG);
		setToolTipText(hint);
	}

}