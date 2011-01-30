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

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter;

import org.bh.gui.IBHAction;
import org.bh.gui.IBHComponent;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.PlatformKey;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.bh.platform.i18n.ITranslator;

/**
 * BHButton to display buttons at screen.
 * 
 * <p>
 * This class extends the Swing <code>JButton</code> to display simple buttons
 * in Business Horizon.
 * 
 * @author Thiele.Klaus
 * @author Schmalzhaf.Alexander
 * @author Robert
 * 
 * @version 0.1, 2009/12/13
 * @version 0.1.1 2009/12/26
 * 
 */
public class BHButton extends JButton implements IBHComponent, IBHAction, IPlatformListener {
	private static final long serialVersionUID = -8018664370176080809L;
	private static List<IBHAction> platformItems = new ArrayList<IBHAction>();
	private static ITranslator translator = Services.getTranslator();

	private String key;
	private PlatformKey platformKey;
	String hint;

	/**
	 * Secondary constructor for platform buttons (are added to
	 * platformButtons-list and uses ENUM Platform Key instead of String for
	 * key)
	 * 
	 * @param key
	 */
	public BHButton(PlatformKey platformKey) {
		this(platformKey.toString());
		this.platformKey = platformKey;
		platformItems.add(this);
	}

	/**
	 * Standard constructor to create buttons NOT for platform
	 * 
	 * @param key
	 */
	public BHButton(String key) {
		super();
		this.key = key;
		this.addMouseListener(new BHToolTipListener());

		reloadText();
		Services.addPlatformListener(this);
	}

	/**
	 * Standard constructor to create buttons NOT for platform
	 * 
	 * @param key
	 */
	public BHButton(Object key) {
		this(key.toString());
	}

	/**
	 * return the key for value mapping
	 * 
	 * @return
	 */
	@Override
	public String getKey() {
		return key;
	}
	
	public void changeText(Object key) {
		this.key = key.toString();
		reloadText();
	}

	@Override
	public PlatformKey getPlatformKey() {
		return this.platformKey;
	}

	public static List<IBHAction> getPlatformItems() {
		return platformItems;
	}

	@Override
	public boolean isPlatformItem() {
		return (this.platformKey != null);
	}

	@Override
	public String getHint() {
		return hint;
	}
	

	@Override
	public void platformEvent(PlatformEvent e) {
		if (e.getEventType() == Type.LOCALE_CHANGED) {
			this.reloadText();
		}
	}

	protected void reloadText() {
		this.setText(translator.translate(key));
		hint = translator.translate(key, ITranslator.LONG);
		System.out.println("hint ist " + hint);
		setToolTipText(hint);
	}

	/**
	 * 
	 * This MouseListener provides Buttons with the ability to show their texts
	 * in StatusBar
	 * 
	 * @author Alexander Schmalzhaf
	 * @version 1.0, 29.12.2009
	 * 
	 */
	class BHToolTipListener extends MouseInputAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			if (!hint.isEmpty())
				Services.getBHstatusBar().setHint(hint);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (!hint.isEmpty())
				Services.getBHstatusBar().removeHint();
		}
	}
}
