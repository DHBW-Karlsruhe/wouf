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

import javax.swing.JTextArea;

import org.bh.gui.IBHComponent;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.bh.platform.i18n.ITranslator;

/**
 * BHTextArea to display simple text fields at screen.
 * 
 * <p>
 * This class extends the Swing <code>JTextArea</code> to display simple text
 * fields in Business Horizon.
 * 
 * @author Lars
 * @author Robert
 * @version 0.2, 2010/01/07
 * 
 */
public class BHDescriptionTextArea extends JTextArea implements IBHComponent, IPlatformListener {
	private static final long serialVersionUID = -3524100441367520993L;
	/**
	 * unique key to identify Label.
	 */
	private String key;
	private String hint;
	
	public BHDescriptionTextArea(Object key, int row, int column) {
		super(row, column);
		this.key = key.toString();
		this.setEditable(false);
		this.setWrapStyleWord(true);
		this.setLineWrap(true);
		this.setOpaque(false);
		
		reloadText();
		Services.addPlatformListener(this);
	}
	
	public BHDescriptionTextArea(Object key) {
		this(key, 0, 0);
	}

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
	
	protected void reloadText() {
		this.setText(Services.getTranslator().translate(key));
		hint = Services.getTranslator().translate(key, ITranslator.LONG);
		setToolTipText(hint);
	}
}
