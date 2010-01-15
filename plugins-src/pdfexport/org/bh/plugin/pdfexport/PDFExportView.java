package org.bh.plugin.pdfexport;

import javax.swing.JPanel;
import org.bh.gui.View;

import org.bh.gui.BHValidityEngine;
import org.bh.gui.ViewException;

public class PDFExportView extends View {
	public PDFExportView(JPanel viewPanel, BHValidityEngine validator)
			throws ViewException {
		super(viewPanel, validator);
	}
}
