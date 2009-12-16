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
import javax.swing.JPanel;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.i18n.ITranslator;

/**
 *
 * @author Marco Hammel
 */
public abstract class View implements  KeyListener, PropertyChangeListener{

    BHValidityEngine validator;
    JPanel viewPanel;
    Map<String, IBHComponent> bhcomponents = null;
    ITranslator translator;

    /**
     *
     * @param viewPanel
     * @param validator
     * @throws ViewException
     */
    protected View(JPanel viewPanel, BHValidityEngine validator) throws ViewException{
        this.viewPanel = viewPanel;
        this.validator = validator;
        this.bhcomponents = mapBHcomponents(viewPanel.getComponents());
    }

    /**
     *
     * @param translator
     */
    public void setTranslator(ITranslator translator){
        this.translator = translator;
    }

    /**
     * Map all components of IBHComponent in the instance of a panel
     * @param components
     * @return
     */
    private Map<String, IBHComponent> mapBHcomponents(Component[] components) throws ViewException{
        Map<String, IBHComponent> map = Collections.synchronizedMap(new HashMap<String, IBHComponent>());
        for(Component comp : components){
            if(comp instanceof JPanel){
                try{
                    map.putAll(mapBHcomponents(((JPanel)comp).getComponents()));
                }catch(Exception e){
                    throw new ViewException(e.getCause());
                }
                
            }
            if(comp instanceof IBHComponent){
                comp.addKeyListener(this);
                comp.addPropertyChangeListener(this);
                if(comp instanceof JPanel){
                    try{
                        map.putAll(mapBHcomponents(((JPanel)comp).getComponents()));
                    }catch(Exception e){
                        throw new ViewException(e.getCause());
                    }   
                }
                try{
                    map.put(((IBHComponent) comp).getKey(),(IBHComponent) comp);
                }catch(Exception e){
                    throw new ViewException(e.getCause());
                }
            }
        }
        return map;
    }
    public Map<String, IBHComponent>  getBHcomponents(){
        return bhcomponents;
    }
}
