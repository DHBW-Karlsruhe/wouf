package org.bh.plugin.gcc.branchspecific;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

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
		JTextField main = new JTextField(branch.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY).toString());
		JTextField mid = new JTextField(branch.get(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY).toString());
		JTextField sub = new JTextField(branch.get(DTOBranch.Key.BRANCH_KEY_SUB_CATEGORY).toString());
		
		JSplitPane paneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, main, sub);
		
		this.add(paneH, BorderLayout.CENTER);
	}
	

}
