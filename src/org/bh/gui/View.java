/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui;

import java.awt.Component;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.bh.gui.swing.IBHComponent;

/**
 *
 * @author Marco Hammel
 */
public abstract class View implements  KeyListener, PropertyChangeListener{

    BHValidityEngine validator;
    JPanel viewPanel;
    Map<String, IBHComponent> bhcomponents = null;

    /**
     *
     * @param viewPanel
     * @param validator
     */
    protected View(JPanel viewPanel, BHValidityEngine validator){
        this.viewPanel = viewPanel;
        this.validator = validator;
        this.bhcomponents = mapBHcomponents(viewPanel.getComponents());
    }

    private Map<String, IBHComponent> mapBHcomponents(Component[] components){
        Map<String, IBHComponent> map = Collections.synchronizedMap(new HashMap<String, IBHComponent>());
        for(Component comp : components){
            if(comp instanceof JPanel){
                map.putAll(mapBHcomponents(((JPanel)comp).getComponents()));
            }
            if(comp instanceof IBHComponent){
                if(comp instanceof JPanel){
                    map.putAll(mapBHcomponents(((JPanel)comp).getComponents()));
                }
                map.put(((IBHComponent) comp).getKey(),(IBHComponent) comp);
            }
        }
        return map;
    }
    public Map<String, IBHComponent>  getBHcomponents(){
        return bhcomponents;
    }
}
