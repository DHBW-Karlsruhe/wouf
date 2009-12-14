/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui.swing;

import org.bh.gui.swing.IBHComponent;
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
    Map<String, JComponent> bhComponents;

    @Override
    public Component add(Component comp){
        if(comp instanceof IBHComponent){
            bhComponents.put(key,(JComponent) comp);
        }
         return super.add(comp);
    }

    public String getKey() {
        return key;
    }

    public int[] getValidateRules() {
        return validateRules;
    }

}
