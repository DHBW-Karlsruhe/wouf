/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.bh.data.IDTO;
import org.bh.gui.View;
import org.bh.platform.i18n.ITranslator;

/**
 *
 * @author Marco Hammel
 */
public interface IController {

    /**
     * return the view of the component if no view is defined the method return null
     * @return
     * @throws ControllerException
     */
    JPanel getView();
    /**
     * platform can define the dto access of the component by this method
     * @param model
     */
    void setModel(IDTO model);
    //TODO datatyp result is not yet defined
    /**
     * platform can overhand the result(s) of an operation to a component by this method
     * @param result
     */
    void setResult(Map result);

    List<String> getStochasticKeys();
   
}
