/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui;

import java.util.Map;
import javax.swing.JPanel;
import org.bh.data.Value;

/**
 *
 * @author Marco Hammel
 */
public abstract class View {

    JPanel viewPanel;
    Map<String, Value> viewWrapper;

    public View(){
        
    }

    protected JPanel getPanel(){
        return viewPanel;
    }

}
