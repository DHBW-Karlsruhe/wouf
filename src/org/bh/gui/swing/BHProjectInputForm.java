package org.bh.gui.swing;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bh.data.DTOProject;
import org.bh.gui.ValidationMethods;
import org.bh.gui.swing.BHLabel;
import org.bh.gui.swing.BHTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form with the project data
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 0.1, 01.01.2010
 * 
 */

public class BHProjectInputForm extends JPanel {

    private BHLabel lproject;
    private BHLabel lprojectname;
    private BHTextField tfprojectname;
    
    public BHProjectInputForm (){
	
	String rowDef = "4px:grow,p,4px,p,4px:grow";
	String colDef = "4px:grow,right:pref,4px,min(150px;pref):grow,4px:grow";

	FormLayout layout = new FormLayout(colDef, rowDef);
	this.setLayout(layout);

	CellConstraints cons = new CellConstraints();
	
	this.add(this.getLproject (), cons.xywh(4, 2, 1, 1, "center, center"));
	this.add(this.getLprojectname (), cons.xywh(2, 4, 1, 1));
	this.add(this.getTfprojectname (), cons.xywh(4, 4, 1, 1));
    }

    public BHLabel getLproject() {
        if (lproject == null){
            lproject = new BHLabel ("","Projekt");
        }
	return lproject;
    }

    public BHLabel getLprojectname() {
	if (lprojectname == null){
            lprojectname = new BHLabel (DTOProject.Key.NAME.toString(),"Name");
            lprojectname.setVisible(false);
        }
	return lprojectname;
    }

    public BHTextField getTfprojectname() {
	if (tfprojectname == null){
            tfprojectname = new BHTextField (DTOProject.Key.NAME.toString());
			// TODO add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfprojectname.setValidateRules(rules);
        }
	return tfprojectname;
    }
    
    /**
     * Test main method.
     */
    public static void main(String args[]) {

	JFrame test = new JFrame("Test for ViewPeriodData1");
	test.setContentPane(new BHProjectInputForm ());
	test.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});
	test.pack();
	test.show();
    }
    
}
