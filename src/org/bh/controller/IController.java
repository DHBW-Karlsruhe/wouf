package org.bh.controller;

import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import org.bh.data.IDTO;

/**
 *
 * @author Marco Hammel
 */
public interface IController {

    /**
     * @return the view of the component; if no view is defined the method returns null
     * @throws ControllerException
     */
    JPanel getViewPanel() throws ControllerException;
    
    /**
     * platform can define the dto access of the component by this method
     * @param model
     * @throws ControllerException
     */
    void setModel(IDTO<?> model) throws ControllerException;
    
    //TODO datatyp result is not yet defined
    
    /**
     * platform can hand over the result of an operation to a component by this method
     * @param result
     * @throws ControllerException 
     */
    void setResult(Map result) throws ControllerException;
    
    /**
     * deliver a List of keys representing values which are stochastical procedable
     * @return
     * @throws ControllerException 
     */
    List<String> getStochasticKeys() throws ControllerException;

    /**
	 * Defines the description of the plugin which will be displayed on the GUI.
	 *
	 * @return Translation key for the description.
    */
    String getGuiKey();
   
}
