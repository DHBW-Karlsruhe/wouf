package org.bh.controller;

import javax.swing.JPanel;

import org.bh.gui.View;

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
    View getView();
}
