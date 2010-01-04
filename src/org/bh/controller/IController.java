package org.bh.controller;

import javax.swing.JPanel;

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
    
    //TODO datatyp result is not yet defined

    /**
	 * Defines the description of the plugin which will be displayed on the GUI.
	 *
	 * @return Translation key for the description.
    */
    String getGuiKey();

   
}
