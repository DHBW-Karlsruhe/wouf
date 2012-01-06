package org.bh.plugin.branchSpecificRepresentative.swing.maintainCompanyData;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.bh.gui.swing.BHMenuItem;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.PlatformKey;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

/**
 * <short_description>
 *
 * <p>
 * <detailed_description>
 *
 * @author DEETNEBE
 * @version 1.0, 05.01.2012
 *
 */
public final class MaintainCompanyBar extends JMenuBar implements IPlatformListener{

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	ITranslator translator = Services.getTranslator();
	
	JMenu menuBSRData;
	BHMenuItem exportBSRData, importBSRData;
	
	public MaintainCompanyBar(){
		
		menuBSRData = new JMenu(translator.translate("MBSRData"));
//		menuBSRData.setMnemonic(UNDEFINED_CONDITION);
		add(menuBSRData);
		
		exportBSRData = new BHMenuItem(PlatformKey.EXPORT_COMPANY_DATA);
		menuBSRData.add(exportBSRData);
//		exportBSRData.addActionListener(this);
		
		importBSRData = new BHMenuItem(PlatformKey.IMPORT_COMPANY_DATA);
		menuBSRData.add(importBSRData);
//		importCompanyData.addActionListener(this);
		
	}

	
	/* Specified by interface/super class. */
	@Override
	public void platformEvent(PlatformEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

}
