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
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.apache.log4j.Logger;
import org.bh.data.IDTO;
import org.bh.gui.chart.IBHAddValue;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHLabel;
import org.bh.gui.swing.BHStatusBar;
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;

/**
 *
 * @author Marco Hammel
 */
public abstract class View implements KeyListener, PropertyChangeListener, MouseListener {

    private static  Logger log = Logger.getLogger(View.class);

    /**
     * refernce to the instance of the UI validator
     * @see BHValidityEngine
     */
    private BHValidityEngine validator = null;
    /**
     * refernce to a subclass of JPanel keeping UI Components
     */
    private JPanel viewPanel;
    /**
     * representign chart BH components
     * @see IBHAddValue
     */
    private Map<String, IBHAddValue> bhChartComponents = Collections.synchronizedMap(new HashMap<String, IBHAddValue>());
    /**
     * representing all components related to the model
     * @see IDTO
     */
    private Map<String, IBHComponent> bhModelComponents;
    /**
     * representing all BH labels and Buttons
     * @see BHButton
     * @see BHLabel
     */
    private Map<String, IBHComponent> bhTextComponents = Collections.synchronizedMap(new HashMap<String, IBHComponent>());
    private static Map<String, IBHComponent> validationRegister = null;

    /**
     * Should be used in case of a UI which have to be validated
     *
     * @param viewPanel instances of a subclass of JPanel
     * @param validator instances of a subclass of IBHValidator
     * @throws ViewException    in case of null or mapping issues
     * @see JPanel
     * @see BHValidityEngine
     */
    public View(JPanel viewPanel, BHValidityEngine validator) throws ViewException {
        this.setViewPanel(viewPanel);
        this.setValidator(validator);
    }
    /**
     * Only should be uesed for UI´s without validation
     *
     * @param viewPanel instances of a subclass of JPanel
     * @throws ViewException    in case of null or mapping issues
     * @see JPanel
     */
    public View(JPanel viewPanel) throws ViewException {
        this.setViewPanel(viewPanel);
    }

    /**
     * add View class as Key and PropertyChange to all BHTextFields an MouseListener to all IBHComponents
     *
     * @see IBHComponent
     * @see BHTextField
     * @see KeyListener
     * @see PropertyChangeListener
     * @see MouseListener
     * @param comp
     */
    private void addViewListener(Component comp) {
        if (comp instanceof IBHComponent){
            comp.addMouseListener(this);
        }
        if (comp instanceof BHTextField) {
            comp.addKeyListener(this);
            comp.addPropertyChangeListener(this);
        }

    }

    /**
     * Map all components of IBHComponent in the model, text or chart map and set
     * KeyListener and PropertyChangeListener <code>addViewListener</code>
     *
     * @see KeyListener
     * @see PropertyChangeListener
     * @param components
     * @return Map of model related components
     */
    private Map<String, IBHComponent> mapBHcomponents(Component[] components) throws ViewException {
        log.debug("UI Components are getting organized and registered in a View instance");
        Map<String, IBHComponent> map = Collections.synchronizedMap(new HashMap<String, IBHComponent>());
        for (Component comp : components) {
            //TODO Have to be a motherclass which represents all possible containers
            if ((comp instanceof JPanel || comp instanceof JScrollPane) && !(comp instanceof IBHComponent)) {
                try {
                    map.putAll(mapBHcomponents(((JPanel) comp).getComponents()));
                } catch (Exception e) {
                    log.error("Mapping Error in the View", e.getCause());
                    throw new ViewException(e.getCause());
                }
            }
            if (comp instanceof IBHComponent) {
                addViewListener(comp);
                if (comp instanceof JPanel) {
                    try {
                        map.putAll(mapBHcomponents(((JPanel) comp).getComponents()));
                    } catch (Exception e) {
                        log.error("Mapping Error in the View", e.getCause());
                        throw new ViewException(e.getCause());
                    }
                }
                if (comp instanceof BHLabel || comp instanceof BHButton) {
                    try {
                        this.bhTextComponents.put(((IBHComponent) comp).getKey(), (IBHComponent) comp);
                    } catch (Exception e) {
                        log.error("Mapping Error in the View", e.getCause());
                        throw new ViewException(e.getCause());
                    }
                } else if (comp instanceof IBHAddValue) {
                    this.bhChartComponents.put(((IBHComponent) comp).getKey(), (IBHAddValue) comp);
                } else {
                    try {
                        map.put(((IBHComponent) comp).getKey(), (IBHComponent) comp);
                    } catch (Exception e) {
                        log.error("Mapping Error in the View", e.getCause());
                        throw new ViewException(e.getCause());
                    }
                }
            }
        }
        return map;
    }

    /**
     * deliver the Labels an Buttones with translation key
     *
     * @return  Map of text related components
     */
    public Map<String, IBHComponent> getBHtextComponents() {
        return this.bhTextComponents;
    }

    /**
     * deliver the components which can bind their values to matching dto
     * 
     * @return  Map of model related componentes
     */
    public Map<String, IBHComponent> getBHmodelComponents() {
        return this.bhModelComponents;
    }

    /**
     * deliver the BH charts
     * @return Map of BHcharts
     */
    protected Map<String, IBHAddValue> getBHchartComponents() {
        return bhChartComponents;
    }

    /**
     * deliver the instance of the Plugin Panel for the Platform
     *
     * @return instance of a subclass of JPanel
     * @see JPanel
     */
    public JPanel getViewPanel() {
        return this.viewPanel;
    }

    /**
     * Controller can set a new view
     * All maps will automatically be refactored
     * 
     * @param panel instance of a JPanel subclass
     * @throws ViewException    in case of null reference
     * @see JPanel
     */
    public void setViewPanel(JPanel panel) throws ViewException {
        log.debug("a new panel is setted");
        if (panel == null) {
            log.error("null reference for view is setted");
            throw new ViewException("null refernce panel is setted");
        }
        this.viewPanel = panel;
        this.bhModelComponents = mapBHcomponents(panel.getComponents());
        View.validationRegister = this.bhModelComponents;
    }

    /**
     * deliver the <code>BHValidityEngine</code> instance
     * @return instance of a subclass of BHValidityEngine
     * @see BHValidityEngine
     */
    public BHValidityEngine getValidator() {
        return validator;
    }

    /**
     * set a new validator instance to the view
     * <code>registerComponents</code> will be called
     * @param validator
     * @throws ViewException    in case of null refernce
     * @see View
     */
    public void setValidator(BHValidityEngine validator) throws ViewException {
        log.debug("a new validator is setted");
        if (validator == null) {
            log.error("null reference for validator is setted");
            throw new ViewException("null reference validator");
        }
        this.validator = validator;
        this.validator.registerComponents(View.validationRegister);
    }

    /**
     * call the <code>publishValidationComp</code> method of a event on a BHTextField occurs
     * 
     * @param e representes the source of the event
     * @see BHTextField
     */
    private void handleValidateEvent(Object e) {
        if (e instanceof BHTextField && validator != null) {
            try {
                validator.publishValidationComp((BHTextField) e);
            } catch (ViewException ex) {
                log.error("validation thorws errors", ex);
            }
        }
    }

    /**
     * set the InputHint on the Platform in case of a IBHComponent
     *
     * @param e representes the source
     * @see BHStatusBar
     */
    private void handleInputInfoEvent(Object e){
        if (e instanceof IBHComponent){
            BHValidityEngine.setInputHintLabel((IBHComponent) e);
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
