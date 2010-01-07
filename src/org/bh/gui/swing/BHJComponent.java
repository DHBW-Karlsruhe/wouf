/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui.swing;

import javax.swing.JComponent;

import org.bh.data.types.IValue;
// TODO Remove this class?
/**
 *
 * @author Marco Hammel
 */
public class BHJComponent extends JComponent implements IBHComponent{

    private String key;
    private int[] validateRules;
    private String inputHint;

    public void setValidateRules(int[] validateRules) {
        this.validateRules = validateRules;
    }

    public String getKey() {
        return key;
    }

    public int[] getValidateRules() {
        return validateRules;
    }

    public boolean isTypeValid() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public  void setValue(IValue value){
        throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public String getBHHint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
}
