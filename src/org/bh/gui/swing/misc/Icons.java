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
/**
 * This class contains icons which are used to show the validation status etc.
 * 
 * @author Robert Vollmer
 * @version 1.0, 29.01.10 
 */

package org.bh.gui.swing.misc;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.jgoodies.validation.Severity;

public final class Icons {
    public static final ImageIcon INFO_ICON = new ImageIcon(Icons.class.getResource("/org/bh/images/tree/info.png"));
    public static final ImageIcon WARNING_ICON = new ImageIcon(Icons.class.getResource("/org/bh/images/tree/warning.png"));
    public static final ImageIcon ERROR_ICON = new ImageIcon(Icons.class.getResource("/org/bh/images/tree/error.png"));
    public static final ImageIcon QUESTION_ICON = new ImageIcon(Icons.class.getResource("/org/bh/images/tree/question.png"));

    /**
     * Returns an icon for the specified severity.
     * 
     * @param severity
     * @return The icon, or null if not supported.
     */
    public static Icon getIcon(Severity severity) {
	if (severity == Severity.ERROR) {
	    return ERROR_ICON;
	} else if (severity == Severity.WARNING) {
	    return WARNING_ICON;
	} else {
	    return null;
	}
    }
}
