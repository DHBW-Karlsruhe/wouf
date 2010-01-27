package org.bh.controller;

import javax.swing.JPanel;

import org.bh.gui.view.View;

/**
 *
 * @author Marco Hammel
 */
public interface IController {

    /**
     * deliver the active JPanel of the component if no view is defined the method returns null
     * @return setted JPanel;
     * @throws ControllerException
     */
    JPanel getViewPanel() throws ControllerException;
    /**
     * active View instance of the component
     * @return View
     */
    View getView();
}
