package org.bh.plugin.gcc.swing;

import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;

public class GCCCombinedForm extends JPanel {
	public GCCCombinedForm(JPanel balanceSheetForm, JPanel plsForm) {
		setLayout(new FormLayout("4px,p,4px", "p,p"));
		add(balanceSheetForm, "2,1");
		add(plsForm, "2,2");
	}
}
