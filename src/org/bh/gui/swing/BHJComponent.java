/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui.swing;

import javax.swing.JComponent;
import org.bh.data.types.IValue;

/**
 *
 * @author Marco Hammel
 */
public class BHJComponent extends JComponent implements IBHComponent{

    String key;
    int[] validateRules;

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

    public IValue getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
