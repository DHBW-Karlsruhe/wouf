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
package org.bh.gui.swing.tree;

import javax.swing.JPopupMenu;

import org.bh.gui.swing.BHMenuItem;
import org.bh.platform.PlatformKey;


/**
 * 
 * PopupMenu für right-clicked tree nodes
 *
 * <p>
 * This popup provides some additional functions or a rather fast way to existing functions 
 * connected to the right-clicked node
 *
 * @author Zuckschwerdt.Lars
 * @author Schmalzhaf.Alexander
 * @version 1.0, 17.01.2010
 *
 */
@SuppressWarnings("serial")
public class BHTreePopup extends JPopupMenu{
	
	public enum Type{
		PROJECT,
		SCENARIO,
		PERIOD;
	}
	
	public BHTreePopup(Type type){
		
		
		this.add(new BHMenuItem(PlatformKey.POPUPADD));
		this.add(new BHMenuItem(PlatformKey.POPUPDUPLICATE));
		if(type == Type.PROJECT)
			this.add(new BHMenuItem(PlatformKey.POPUPEXPORT));
		this.addSeparator();
		this.add(new BHMenuItem(PlatformKey.POPUPREMOVE));
		
	}
	
	
	

}
