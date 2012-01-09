package org.bh.plugin.branchSpecificRepresentative.swing.maintainCompanyData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;

import org.apache.log4j.Logger;
import org.bh.gui.IBHComponent;
import org.bh.gui.swing.BHMenuBar;
import org.bh.gui.swing.BHMenuItem;
import org.bh.gui.swing.BHPopupFrame;
import org.bh.platform.PlatformKey;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

/**
 * Frame to maintain company data to calculate branch specific representative.
 * 
 * <p>
 * This class should be the base class for maintaining all the company data,
 * which is necessary to calculate the branch specific representative. This is
 * supposed to be the entry point to change and maintain company data.
 * 
 * @author Yannick Rödl
 * @version 1.0, 27.12.2011
 * 
 */
public class MaintainCompanyDataFrame extends BHPopupFrame implements
		ActionListener {

	public enum GUI_KEYS {
		TITLE;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}

	public enum MenuBar {
		MENU_EXTRAS;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}

	/**
	 * Generated <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -6127674860072304710L;

//	wurde in "Extras" eingepflegt
//	private MaintainCompanyBar menuBar;

	public MaintainCompanyDataFrame() {
		super();
		
//		wurde in "Extras" eingepflegt
//		menuBar = new MaintainCompanyBar();
//		this.setJMenuBar(menuBar);
	}

	@Override
	public void setAdditionalMenuEntriesInMainFrame(BHMenuBar menuBar) {
		ITranslator translator = BHTranslator.getInstance();

		JMenu extras = new JMenu(translator.translate(MaintainCompanyDataFrame.MenuBar.MENU_EXTRAS.toString()));
		extras.setMnemonic(translator.translate(MaintainCompanyDataFrame.MenuBar.MENU_EXTRAS.toString(),ITranslator.MNEMONIC).charAt(0));

		// All JMenuItems for BSR Company Data
		
		BHMenuItem maintainCompanyData = new BHMenuItem(PlatformKey.MAINTAIN_COMPANY_DATA, 0);
		maintainCompanyData.addActionListener(this);

		BHMenuItem exportCompanyData = new BHMenuItem(PlatformKey.EXPORT_COMPANY_DATA, 0);
		exportCompanyData.addActionListener(this);

		BHMenuItem importCompanyData = new BHMenuItem(
				PlatformKey.IMPORT_COMPANY_DATA, 0);
		importCompanyData.addActionListener(this);

		extras.add(maintainCompanyData);
		extras.addSeparator();
		extras.add(exportCompanyData);
		extras.add(importCompanyData);

		menuBar.add(extras);
	}

	public String getUniqueId() {
		return BHPopupFrame.ID.MAINTAIN_COMPANIES.toString();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		IBHComponent comp = (IBHComponent) arg0.getSource();
		if (comp.getKey().equals("MmaintainCompData")) {
			Logger.getLogger(MaintainCompanyDataFrame.class).info(
					"Popup should be loaded now.");
			this.setVisible(true);

		} else if (comp.getKey().equals("MexportCompanyData")) {
			BSRManualPersistance.saveBranches();

		} else if (comp.getKey().equals("MimportCompanyData")) {
			BSRManualPersistance.loadBranches();

		} else {
			System.out.println("Something definitly went wrong");
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		// TODO We have to do something with the data here.
	}

	/* Specified by interface/super class. */
	@Override
	public String getTitleKey() {
		return MaintainCompanyDataFrame.GUI_KEYS.TITLE.toString();
	}
}
