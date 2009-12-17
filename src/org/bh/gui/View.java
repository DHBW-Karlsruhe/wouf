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

    private BHValidityEngine validator;
    private JPanel viewPanel;
    private Map<String, IBHComponent> bhcomponents = null;
    private ITranslator translator;

    /**
     *
     * @param viewPanel
     * @param validator
     * @param translator 
     * @throws ViewException
     */
    protected View(JPanel viewPanel, BHValidityEngine validator, ITranslator translator) throws ViewException{
        this.viewPanel = viewPanel;
        this.validator = validator;
        this.translator = translator;
        this.bhcomponents = mapBHcomponents(viewPanel.getComponents());
    }

    /**
     * add View class as Key and PropertyChangeListener of all BHComponent
     * @param comp
     */
    private void addViewListener(Component comp){
        comp.addKeyListener(this);
        comp.addPropertyChangeListener(this);
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
                addViewListener(comp);
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

    /**
     *
     * @return
     */
    public JPanel getViewPanel(){
        return this.viewPanel;
    }

    protected void setViewPanel(JPanel panel){
        this.viewPanel = panel;
    }
}
