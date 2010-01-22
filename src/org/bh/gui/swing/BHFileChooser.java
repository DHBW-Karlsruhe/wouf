package org.bh.gui.swing;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bh.platform.Services;

/**
 * <code>BHFileChooser</code> for opening and saving files.
 * 
 * @author Thiele.Klaus
 * @version 1.0, 2010/01/22
 * 
 */
public class BHFileChooser extends JFileChooser {
	
	/**
	 * FileFilter instance.
	 */
	private FileFilter filter;

	/**
	 * Constructor.
	 */
	public BHFileChooser() {
		super();
		String description = Services.getTranslator().translate("Cfiletypes");
		String extension = Services.getTranslator().translate("fileExtension");
		filter = new FileNameExtensionFilter(description, extension);
		this.setFileFilter(filter);
	}
}
