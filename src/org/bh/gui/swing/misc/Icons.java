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
