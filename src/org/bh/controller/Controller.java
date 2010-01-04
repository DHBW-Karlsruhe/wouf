package org.bh.controller;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import org.apache.log4j.Logger;
import org.bh.data.DTOAccessException;
import org.bh.data.IDTO;
import org.bh.data.types.Calculable;
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

    /**
     * have to be used in case of a UI based and model driven mvc plugin
     * and register the plugin at the platform
     *
     * @param view a instance of a View subclass
     * @param model a instance of a dto 
     */
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
     * in each plugin.
     *
     * @see BHstatusBar
     * @param e
     */
    private void handleException(Exception e){
        log.error("Controller Exception ", e);
        //TODO how to show system erros to the user
//        Controller.bhStatusBar.setToolTip(e.getMessage());
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
        	this.bhModelcomponents = this.view.getBHmodelComponents();
                this.AddControllerAsListener(this.view.getBHtextComponents());
        }
    }

    /**
     * writes all data to its dto reference
     * @throws DTOAccessException
     */
    protected void saveAllToModel() throws DTOAccessException{
        log.debug("Plugin save to dto");
        this.model.setSandBoxMode(Boolean.TRUE);
        for(String key : this.bhModelcomponents.keySet()){
            this.model.put(key, this.typeConverter(this.bhModelcomponents.get(key).getValue()));
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
     * save specific component to model
     * @param comp
     * @throws DTOAccessException
     */
    protected void safeToModel(IBHComponent comp)throws DTOAccessException{
        log.debug("Plugin save to dto");
        this.model.setSandBoxMode(Boolean.TRUE);
        //TODO define typeconverter
        //this.model.put(comp.getKey(), comp.getValue());
    }
    /**
     * writes all dto values with a mathcing key in a IBHComponent to UI
     * @throws DTOAccessException
     */
    protected void loadAllToView()throws DTOAccessException{
        log.debug("Plugin load from dto in view");
        for(String key : this.bhModelcomponents.keySet()){
            //this.bhModelcomponents.get(key).setValue(this.model.get(key));
        }
    }
    /**
     *
     * @param key
     * @throws DTOAccessException
     * @throws ControllerException
     */
    protected void loadToView(String key) throws DTOAccessException, ControllerException{
        log.debug("Plugin load from dto in view");
        //this.bhModelcomponents.get(key).setValue(this.model.get(key));
    }

    public void setModel(IDTO<?> model) {
        this.model = model;
    }

    public IDTO<?> getModel() {
        return model;
    }


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
    public static void setBHstatusBarValidationHint(JScrollPane pane){
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
    
    public List<String> getStochasticKeys() throws ControllerException{
        if(model != null) {
            return this.model.getStochasticKeys();
        }
        throw new ControllerException("No referende to a valid model");
    }

    protected BHValidityEngine getValidator(){
        return this.view.getValidator();
    }

}
