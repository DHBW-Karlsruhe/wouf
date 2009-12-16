/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui.swing;

import java.awt.Component;
import java.util.Map;
import javax.swing.JComponent;

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

}
