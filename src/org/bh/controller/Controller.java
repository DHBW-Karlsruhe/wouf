package org.bh.controller;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.data.DTOAccessException;
import org.bh.data.IDTO;
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

    private static  Logger log = Logger.getLogger(Controller.class);
    
    /**
     * Reference to the active view of the plugin
     * Can be null
     */
    private View view = null;
    
    /**
     * Reference to all model depending IBHcomponents on the UI
     */
    private Map<String, IBHComponent> bhModelcomponents;
    
    /**
     * Referenz to the model
     * Can be null
     */
    private IDTO<?> model = null;
    
    /**
     * Reference to the Platform StatusBar. Must be set in every constructor
     */
    private static BHStatusBar bhStatusBar;

    public Controller(View view, IDTO<?> model){
        log.debug("Plugincontroller instance");
        this.model = model;
        this.view = view;
        Controller.bhStatusBar = Services.getBHstatusBar();
        if (view != null) {
            this.bhModelcomponents = this.view.getBHmodelComponents();
            this.AddControllerAsListener(this.view.getBHtextComponents());
        }
        Services.addPlatformListener(this);
    }
    
    public Controller(){
    	this(null, null);
    }
    public Controller(View view){
        this(view, null);
    }
    public Controller(IDTO<?> model){
    	this(null, model);
    }
    
    
    /**
     * central exception handler method. Should be called in every catch statement
     * in each plugin. The Standard writes a message to the <code>BHstatusBar</code>
     * of the Platform
     * @see BHstatusBar
     * @param e
     */
    private void handleException(Exception e){
        log.error("Controller Exception ", e);
        Controller.bhStatusBar.setToolTip(e.getMessage());
    }
   
    /**
     * @see IController
     * @return
     */
    public JPanel getView(){
        if(this.view != null) {
            return view.getViewPanel();
        }
        
        return null;
    }

    public void setView(View view){
        this.view = view;
        if (view != null) {
        	this.bhModelcomponents = this.view.getBHmodelComponents();
                this.AddControllerAsListener(this.view.getBHtextComponents());
        }
    }

    /**
     * writes all data to its dto reference
     * @return
     * @throws DTOAccessException
     */
    private void saveAllToModel() throws DTOAccessException{
        log.debug("Plugin save to dto");
        this.model.setSandBoxMode(Boolean.TRUE);
        for(String key : this.bhModelcomponents.keySet()){
            this.model.put(key, this.bhModelcomponents.get(key).getValue());
        }
    }
    /**
     * save specific component to model
     * @param comp
     * @throws DTOAccessException
     */
    private void safeToModel(IBHComponent comp)throws DTOAccessException{
        log.debug("Plugin save to dto");
        this.model.setSandBoxMode(Boolean.TRUE);
        this.model.put(comp.getKey(), comp.getValue());
    }
    /**
     * writes all dto values with a mathcing key in a IBHComponent to UI
     * @return
     * @throws DTOAccessException
     */
    private void loadAllToView()throws DTOAccessException{
        log.debug("Plugin load from dto in view");
        for(String key : this.bhModelcomponents.keySet()){
            this.bhModelcomponents.get(key).setValue(this.model.get(key));
        }
    }
    private void loadToView(String key) throws DTOAccessException, ControllerException{
        log.debug("Plugin load from dto in view");
        this.bhModelcomponents.get(key).setValue(this.model.get(key));
    }

    public void setModel(IDTO<?> model) {
        this.model = model;
    }

    /**
     * get the ITranslator from the Platform
     * @see Servicess
     * @return
     */
    public ITranslator getTranslator() {
        return Services.getTranslator();
    }
    /**
     * concret BHValidityEngine can use this method to set Validation Tool Tip
     * @BHStatusBar
     * @param label
     */
    public static void setBHstatusBarValidationToolTip(JLabel label){
        Controller.bhStatusBar.setValidationToolTip(label);
    }
     /**
     * concret BHValidityEngine can use this method to set Tool Tip
     * @BHStatusBar
      * @param tooltip
     */
    public static void setBHstatusBarToolTip(String tooltip){
        Controller.bhStatusBar.setToolTip(tooltip);
    }
    /**
     *
     * @param comps
     */
    private void AddControllerAsListener(Map<String, IBHComponent> comps){
        for(IBHComponent comp : comps.values()){
            if(comp instanceof BHButton){
                ((BHButton) comp).addActionListener(this);
            }
        }
    }

    public List<String> getStochasticKeys() {
        if(model != null) {
            return this.model.getStochasticKeys();
        }
        return null;
    }




}
