package org.bh.plugin.gcc.swing;

import javax.swing.JPanel;

import org.bh.gui.swing.BHButton;

import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("serial")
public class GCCCombinedForm extends JPanel {
	private JPanel pbuttons;
	public GCCCombinedForm(JPanel balanceSheetForm, JPanel plsForm) {
		setLayout(new FormLayout("4px,p,4px,p,4px", "p,p"));
		add(balanceSheetForm, "2,1,2,1,left,default");
		add(plsForm, "2,2,1,1,left,default");
		add(this.getPbuttos(),"4,2,1,1,right,default");
	}
	
	public JPanel getPbuttos(){
		if (pbuttons == null){
			pbuttons = new JPanel();
			pbuttons.setLayout(new FormLayout("4px,p,4px,p,4px", "p"));
			pbuttons.add(new BHButton("Bbsexport"),"2,1,1,1,right,bottom");
			pbuttons.add(new BHButton("Bbsimport"),"4,1,1,1,right,bottom");
		}
		return pbuttons;
	}
}
