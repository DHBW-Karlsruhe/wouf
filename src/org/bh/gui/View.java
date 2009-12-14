/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui;

import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.bh.gui.swing.IBHComponent;

/**
 *
 * @author Marco Hammel
 */
public abstract class View implements KeyListener, PropertyChangeListener{

    BHValidityEngine validator;
    JPanel viewPanel;
    Map<String, IBHComponent> components;

    /**
     *
     * @param viewPanel
     * @param validator
     */
    protected View(JPanel viewPanel, BHValidityEngine validator){
        this.viewPanel = viewPanel;
        this.validator = validator;
    }

    public JPanel getPanel(){
        return viewPanel;
    }

    private void addPropertyChangeListener(JComponent comp){

    }

}
