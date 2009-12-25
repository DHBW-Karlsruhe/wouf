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
		filter = new FileNameExtensionFilter(Services.getTranslator()
				.translate("Cfiletypes"), Services.getTranslator().translate(
				"fileExtension"));
		this.setFileFilter(filter);
	}
}