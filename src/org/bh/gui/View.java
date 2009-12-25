/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.gui;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import org.bh.gui.chart.IBHAddValue;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHLabel;
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;

/**
 *
 * @author Marco Hammel
 */
public abstract class View implements KeyListener, PropertyChangeListener, MouseListener {

    private BHValidityEngine validator = null;
    private JPanel viewPanel;
    private Map<String, IBHAddValue> bhChartComponents = Collections.synchronizedMap(new HashMap<String, IBHAddValue>());
    private Map<String, IBHComponent> bhModelComponents;
    private Map<String, IBHComponent> bhTextComponents = Collections.synchronizedMap(new HashMap<String, IBHComponent>());
    private static Map<String, IBHComponent> validationRegister = null;
    ;

    public View(JPanel viewPanel, BHValidityEngine validator) throws ViewException {
        this.setViewPanel(viewPanel);
        this.setValidator(validator);
    }

    public View(JPanel viewPanel) throws ViewException {
        this.setViewPanel(viewPanel);
    }

    /**
     * add View class as Key and PropertyChangeListener of all BHTextField
     * @param comp
     */
    private void addViewListener(Component comp) {
        if (comp instanceof BHTextField) {
            comp.addKeyListener(this);
            comp.addPropertyChangeListener(this);
            comp.addMouseListener(this);
        }

    }

    /**
     * Map all components of IBHComponent in the model, text or chart map and set
     * KeyListener and PropertyChangeListener <code>addViewListener</code>
     * @param components
     * @return
     */
    private Map<String, IBHComponent> mapBHcomponents(Component[] components) throws ViewException {
        Map<String, IBHComponent> map = Collections.synchronizedMap(new HashMap<String, IBHComponent>());
        for (Component comp : components) {
            //TODO Have to be a motherclass which represents all possible containers
            if (comp instanceof JPanel && !(comp instanceof IBHComponent)) {
                try {
                    map.putAll(mapBHcomponents(((JPanel) comp).getComponents()));
                } catch (Exception e) {
                    throw new ViewException(e.getCause());
                }
            }
            if (comp instanceof IBHComponent) {
                addViewListener(comp);
                if (comp instanceof JPanel) {
                    try {
                        map.putAll(mapBHcomponents(((JPanel) comp).getComponents()));
                    } catch (Exception e) {
                        throw new ViewException(e.getCause());
                    }
                }
                if (comp instanceof BHLabel || comp instanceof BHButton) {
                    try {
                        this.bhTextComponents.put(((IBHComponent) comp).getKey(), (IBHComponent) comp);
                    } catch (Exception e) {
                        throw new ViewException(e.getCause());
                    }
                } else if (comp instanceof IBHAddValue) {
                    this.bhChartComponents.put(((IBHComponent) comp).getKey(), (IBHAddValue) comp);
                } else {
                    try {
                        map.put(((IBHComponent) comp).getKey(), (IBHComponent) comp);
                    } catch (Exception e) {
                        throw new ViewException(e.getCause());
                    }
                }
            }
        }
        return map;
    }

    /**
     * deliver the Labels with translation key
     * @return
     */
    public Map<String, IBHComponent> getBHtextComponents() {
        return this.bhTextComponents;
    }

    /**
     * deliver the components which can bind their values to matching dto
     * @return
     */
    public Map<String, IBHComponent> getBHmodelComponents() {
        return this.bhModelComponents;
    }

    /**
     * deliver the
     * @return
     */
    protected Map<String, IBHAddValue> getBHchartComponents() {
        return bhChartComponents;
    }

    /**
     * deliver the instance of the Plugin Panel
     * @return
     */
    public JPanel getViewPanel() {
        return this.viewPanel;
    }

    /**
     * Controller can set a new view
     * All maps will automatically be refactored
     * @param panel
     * @throws ViewException
     */
    public void setViewPanel(JPanel panel) throws ViewException {
        if (panel == null) {
            throw new ViewException("null panel is setted");
        }
        this.viewPanel = panel;
        this.bhModelComponents = mapBHcomponents(panel.getComponents());
        View.validationRegister = this.bhModelComponents;
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
    public void setValidator(BHValidityEngine validator) throws ViewException {
        if (validator == null) {
            throw new ViewException("null reference validator");
        }
        this.validator = validator;
        this.validator.registerComponents(View.validationRegister);
    }

    /**
     * 
     * @param e
     */
    private void handleValidateEvent(Object e) {
        if (e instanceof BHTextField && validator != null) {
            validator.publishValidationComp((BHTextField) e);
        }
    }

    /**
     *
     * @param e
     */
    private void handleInputInfoEvent(Object e){
        if (e instanceof IBHComponent){
            BHValidityEngine.setInpuHintLabel((IBHComponent) e);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getSource() instanceof IBHComponent) {
            this.handleValidateEvent(e.getSource());
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getSource() instanceof IBHComponent) {
            this.handleValidateEvent(e.getSource());
        }
    }

    public void keyTyped(KeyEvent e) {
        if (e.getSource() instanceof IBHComponent) {
            this.handleValidateEvent(e.getSource());
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof IBHComponent) {
            if (!"permanentFocusOwner".equals(evt.getPropertyName())){
                return;
            }else{
                Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                this.handleInputInfoEvent(focusOwner);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof IBHComponent) {
            this.handleInputInfoEvent(e);
        }
    }

    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof IBHComponent) {
            this.handleInputInfoEvent(e);
        }
    }

    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof IBHComponent) {
            this.handleInputInfoEvent(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof IBHComponent) {
            this.handleInputInfoEvent(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof IBHComponent) {
            this.handleInputInfoEvent(e);
        }
    }
}
