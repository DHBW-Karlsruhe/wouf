/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui;

import java.awt.Component;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import org.bh.gui.swing.BHLabel;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.i18n.ITranslator;

/**
 *
 * @author Marco Hammel
 */
public abstract class View implements  KeyListener, PropertyChangeListener{

    private BHValidityEngine validator;
    private JPanel viewPanel;
    private Map<String, IBHComponent> bhModelComponents;
    private Map<String, IBHComponent> bhTextComponents = Collections.synchronizedMap(new HashMap<String, IBHComponent>());
    private ITranslator translator;

    /**
     *
     * @param viewPanel
     * @param validator
     * @param translator 
     * @throws ViewException
     */
    public View(JPanel viewPanel, BHValidityEngine validator, ITranslator translator) throws ViewException{
        this.viewPanel = viewPanel;
        this.validator = validator;
        this.translator = translator;
        this.bhModelComponents = mapBHcomponents(viewPanel.getComponents());
    }

    /**
     *
     * @param viewPanel
     */
    public View(JPanel viewPanel){
        this.viewPanel = viewPanel;
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
     * Map all components of IBHComponent in the model or text map and set
     * KeyListener and PropertyChangeListener <code>addViewListener</code>
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
                if(comp instanceof BHLabel){
                    try{
                        this.bhTextComponents.put(((IBHComponent)comp).getKey(), (IBHComponent) comp);
                    }catch(Exception e){
                        throw new ViewException(e.getCause());
                    }
                }else{
                    try{
                        map.put(((IBHComponent) comp).getKey(),(IBHComponent) comp);
                    }catch(Exception e){
                        throw new ViewException(e.getCause());
                    }
                }
            }
        }
        return map;
    }
    /**
     *
     * @return
     */
    protected ITranslator getTranslator(){
        return this.translator;
    }

    protected void setTranslator(ITranslator translator) {
        this.translator = translator;
    }
    /**
     * deliver the Labels with translation key
     * @return
     */
    public Map<String, IBHComponent> getBHtextComponents(){
        return this.bhTextComponents;
    }
    /**
     * deliver the components which can bind their values to matching dto
     * @return
     */
    public Map<String, IBHComponent>  getBHmodelComponents(){
        return this.bhModelComponents;
    }
    /**
     * deliver the instance of the Plugin Panel
     * @return
     */
    public JPanel getViewPanel(){
        return this.viewPanel;
    }
    /**
     * Controller can set a new view
     * All maps will automaticaly be refactored
     * @param panel
     * @throws ViewException
     */
    protected void setViewPanel(JPanel panel) throws ViewException{
        this.viewPanel = panel;
        this.bhTextComponents = Collections.synchronizedMap(new HashMap<String, IBHComponent>());
        this.bhModelComponents = mapBHcomponents(viewPanel.getComponents());
    }
    /**
     *
     * @return
     */
    public BHValidityEngine getValidator() {
        return validator;
    }

    /**
     *
     * @param validator
     */
    protected void setValidator(BHValidityEngine validator) {
        this.validator = validator;
    }


    private void handleValidateEvent(Object e){
        validator.validate((IBHComponent) e);

    }
    public void keyPressed(KeyEvent e) {
        if(e.getSource() instanceof IBHComponent) {
            this.handleValidateEvent(e.getSource());
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getSource() instanceof IBHComponent) {
            this.handleValidateEvent(e.getSource());
        }
    }

    public void keyTyped(KeyEvent e) {
        if(e.getSource() instanceof IBHComponent) {
            this.handleValidateEvent(e.getSource());
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getSource() instanceof IBHComponent) {
            this.handleValidateEvent(evt.getSource());
        }
    }


}
