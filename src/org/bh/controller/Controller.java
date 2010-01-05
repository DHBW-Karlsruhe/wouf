package org.bh.controller;

import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;
import org.bh.data.DTOAccessException;
import org.bh.data.types.IValue;
import org.bh.gui.BHValidityEngine;
import org.bh.gui.View;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHStatusBar;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.IPlatformListener;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

/**
 *
 * @author Marco Hammel
 */
public abstract class Controller implements IController, ActionListener, IPlatformListener{

    protected static  Logger log = Logger.getLogger(Controller.class);
    
    /**
     * Reference to the active view of the plugin
     * Can be null
     */
    protected View view = null;
    
    /**
     * Reference to all model depending IBHcomponents on the UI
     */
    protected Map<String, IBHComponent> bhMappingComponents;
    
    /**
     * Reference to the Platform StatusBar. Must be set in every constructor
     */
    private static BHStatusBar bhStatusBar;
    
    public Controller(){
        this(null);
    }
    public Controller(View view){
        log.debug("Plugincontroller instance");
        this.view = view;
        Controller.bhStatusBar = Services.getBHstatusBar();
        if (view != null) {
            this.bhMappingComponents = this.view.getBHmodelComponents();
            this.AddControllerAsListener(this.view.getBHtextComponents());
        }
        Services.addPlatformListener(this);
    }
    
    /**
     * central exception handler method. Should be called in every catch statement
     * in each plugin.
     *
     * @see BHstatusBar
     * @param e
     */
    private void handleException(Exception e){
        log.error("Controller Exception ", e);
        //TODO how to show system erros to the user
        Services.getBHstatusBar().setHint(e.getMessage(), true);
    }
   
    public JPanel getViewPanel(){
        if(this.view != null) {
            return view.getViewPanel();
        }
        
        return null;
    }

    /**
     *
     * @param view
     */
    protected void setView(View view){
        this.view = view;
        if (view != null) {
        	this.bhMappingComponents = this.view.getBHmodelComponents();
                this.AddControllerAsListener(this.view.getBHtextComponents());
        }
    }

    /**
     * Method for Typconversion can be used with <code>Calculable.parseCalculable(String s)</code> 
     * method
     * @param value
     * @return
     */
    protected abstract IValue typeConverter(String value) throws ControllerException;

    /**
     * writes all dto values with a matching key in a IBHComponent to UI
     * @throws DTOAccessException
     */
    
    /**
     * get the ITranslator from the Platform
     * @see Servicess
     * @return
     */
    public static ITranslator getTranslator() {
        return Services.getTranslator();
    }
    /**
     * concret BHValidityEngine can use this method to set Validation Tool Tip
     * 
     * @param pane
     * @see BHStatusBar
     */
    public static void setBHstatusBarErrorHint(JScrollPane pane){
        Controller.bhStatusBar.setErrorHint(pane);
    }
     /**
      * concret BHValidityEngine can use this method to set Info Tool Tip
      *
      * @param hintLabel
      * @see JLabel
      * @see BHStatusBar
     */
    public static void setBHstatusBarHint(JLabel hintLabel){
        Controller.bhStatusBar.setHint(hintLabel);
    }
    /**
     * add the Controller for each BHButton on the UI as ActionListener
     *
     * @param comps
     * @see ActionListener
     * @see BHButton
     */
    private void AddControllerAsListener(Map<String, IBHComponent> comps){
        for(IBHComponent comp : comps.values()){
            if(comp instanceof BHButton){
                ((BHButton) comp).addActionListener(this);
            }
        }
    }

    protected BHValidityEngine getValidator(){
        return this.view.getValidator();
    }

}
