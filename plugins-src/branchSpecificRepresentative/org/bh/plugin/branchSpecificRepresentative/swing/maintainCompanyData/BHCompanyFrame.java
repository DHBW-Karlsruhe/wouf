package org.bh.plugin.branchSpecificRepresentative.swing.maintainCompanyData;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JPanel;

import org.bh.data.DTOCompany;
import org.bh.gui.swing.comp.BHTextField;

import com.jgoodies.forms.layout.FormLayout;

/**
 * <short_description>
 *
 * <p>
 * <detailed_description>
 *
 * @author simon
 * @version 1.0, 17.01.2012
 *
 */
public class BHCompanyFrame extends JPanel
{
public BHCompanyFrame(DTOCompany comp){ 
	this.setLayout(new BorderLayout());
	BHTextField b = new BHTextField(comp.get(DTOCompany.Key.NAME));
	this.add(b);
}

}

