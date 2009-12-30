package org.bh.gui.swing;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.bh.controller.Controller;
import org.bh.gui.ValidationMethods;
import org.bh.gui.View;
import org.bh.gui.ViewException;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.jgoodies.validation.view.ValidationResultViewFactory;

public class ViewTest extends View {

	private static JPanel bhScenHF;
	private ValidationMethods valMeth;
	private ValidationResult validationResultAll;
	private JScrollPane pane;
	
	public ViewTest(JPanel bhScenHF, ValidationMethods valMeth) throws ViewException {
		super(bhScenHF, valMeth);

	}
	
    public static void setInputHintLabel(IBHComponent comp) {
        JLabel infoLabel = ((JLabel) ValidationComponentUtils.getInputHint((JComponent) comp));
        System.out.println(infoLabel.toString());
    }

    public void publishValidationAll(Map<String, IBHComponent> toValidate) throws ViewException{
        validationResultAll = valMeth.validateAll(toValidate);
        valMeth.setValidityStatus(validationResultAll);
        pane = valMeth.createValidationResultList(validationResultAll);
        int counter = pane.countComponents();
        for(int i = 0; i < counter; i++) {
        	pane.getNextFocusableComponent().toString();
        }
    }

    protected void publishValidationComp(IBHComponent comp) throws ViewException{
        ValidationResult valRes = valMeth.validate(comp);
        pane = valMeth.createValidationResultList(valRes);
        int counter = pane.countComponents();
        for(int i = 0; i < counter; i++) {
        	pane.getNextFocusableComponent().toString();
        }
    }
    
	
    
//    public static void main(String args[]) {
//
//		JFrame test = new JFrame("Test for ViewHeadData1");
//		test.setContentPane(new BHScenarioHeadForm());
//		test.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});
//		test.pack();
//		test.show();
//	}
}
