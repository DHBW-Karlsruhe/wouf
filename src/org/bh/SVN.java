package org.bh;

/**
 * This class is used to display the SVN revision with which the application was
 * built.
 * 
 * @author Robert
 * @version 1.0, 13.01.2010
 * 
 */
public class SVN {
	/**
	 * This string will be replaced with the current SVN revision by Hudson.
	 */
	private static final String REVISION = "{SVNREV}";

	public static int getRevision() {
		try {
			return Integer.valueOf(REVISION);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static boolean isRevisionSet() {
		return getRevision() != -1;
	}
}
