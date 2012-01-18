package org.bh.plugin.branchSpecificRepresentative.swing.maintainCompanyData;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.bh.data.DTOBranch;
import org.bh.data.DTOCompany;
import org.bh.gui.swing.comp.BHTextField;

/**
 * <short_description>
 *
 * <p>
 * <detailed_description>
 *
 * @author simon
 * @version 1.0, 18.01.2012
 *
 */
public class BHBranchFrame extends JPanel {

public BHBranchFrame(DTOBranch branch){ 
		this.setLayout(new BorderLayout());
		BHTextField main = new BHTextField(branch.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY));
		BHTextField mid = new BHTextField(branch.get(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY));
		BHTextField sub = new BHTextField(branch.get(DTOBranch.Key.BRANCH_KEY_SUB_CATEGORY));
		
		JSplitPane paneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, main, mid);
		
		this.add(paneH);
	}
	

}
