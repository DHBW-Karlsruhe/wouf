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
package org.bh.gui.swing;

import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.bh.gui.swing.comp.BHButton;
import org.bh.platform.PlatformKey;

/**
 * 
 * BHToolButton to display buttons on tool bar 
 *
 * <p>
 * This class extends the Swing <code>JButton</code> to display buttons with special icons
 * on screen.
 *
 * @author Tietze.Patrick
 * @author Schmalzhaf.Alexander
 * 
 * 
 * @version 0.1, 2009/12/16
 *
 */

public final class BHToolButton extends BHButton{
	private static final long serialVersionUID = 5224665479917197926L;
	private static final Logger log = Logger.getLogger(BHToolButton.class);
    public String buttonName;
    
    /**
     * Standard constructor for <code>BHToolButton</code>.
     * 
     * @param key
     * @param eventKey
     * @param imageName
     */
    public BHToolButton(PlatformKey key, int eventKey, String imageName){
    	super(key);
    	
    	//Look for the image.
    	String imgLocation = "/org/bh/images/buttons/" + imageName + ".png";
		 
        URL imageURL = BHToolBar.class.getResource(imgLocation);
        
        if (imageURL != null) {                      
            //image found
        	this.setText("");
            setIcon(new ImageIcon(imageURL, ""));
        } else {                                     
        	log.error("Resource not found: " + imgLocation);
        } 
    }

	public void setText(String text) {
		// do not allow setting a text for this button
	}
}
