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
    JPanel getView();
    
    /**
     * platform can define the dto access of the component by this method
     * @param model
     */
    void setModel(IDTO<?> model);
    
    //TODO datatyp result is not yet defined
    
    /**
     * platform can hand over the result of an operation to a component by this method
     * @param result
     */
    void setResult(Map result);
    
    /**
     * deliver a List of keys representing values which are stochastical procedable
     * Can return null if no model is connected
     * @return
     */
    List<String> getStochasticKeys();
   
}
