package org.bh.gui.swing;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bh.platform.Services;

/**
 * <code>BHFileChooser</code> for opening and saving files.
 * 
 * @author Thiele.Klaus
 * @version 0.1, 2009/12/21
 * 
 */
public class BHFileChooser extends JFileChooser {
	private FileFilter filter;

	public BHFileChooser() {
		super();
		String description = Services.getTranslator().translate("Cfiletypes");
		String extension = Services.getTranslator().translate("fileExtension");
		filter = new FileNameExtensionFilter(description, extension);
		this.setFileFilter(filter);
	}
}
