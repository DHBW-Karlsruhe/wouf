package org.bh.gui.swing;

import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.bh.platform.i18n.BHTranslator;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class BHOptionDialog extends JFrame{
	
	CellConstraints cons;
	
	BHLabel language;
	JComboBox combo;
	BHButton changeLanguage;
	
	
	public BHOptionDialog(){
		this.setProperties();
		
		//setLayout to the status bar
		String rowDef = "p";
		String colDef = "0:grow(0.05),0:grow(0.3),0:grow(0.3),0:grow(0.3),0:grow(0.05)";
		setLayout(new FormLayout(colDef, rowDef));
		cons = new CellConstraints();
		
		//create select language components
		language = new BHLabel("", BHTranslator.getInstance().translate("MoptionsLanguage"));
				
		//String[] languages = { "Deutsch", "Englisch" };
		combo = new JComboBox(BHTranslator.getInstance().getAvaiableLocales());
		
		changeLanguage = new BHButton("");
		changeLanguage.setText(BHTranslator.getInstance().translate("Bapply"));
		
		//add components
		add(language, cons.xywh(2, 1, 1, 1,"right,center"));
		add(combo, cons.xywh(3, 1, 1, 1));
		add(changeLanguage,cons.xywh(4, 1, 1, 1));
		
	}
	
	private void setProperties(){
		this.setTitle(BHTranslator.getInstance().translate("MoptionsDialog"));
		this.setSize(400,200);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
}
