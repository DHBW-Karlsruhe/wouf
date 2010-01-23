/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import org.bh.data.IDTO;

/**
 * interface for all plugin controllers with a writer excecise.
 * @author Marco Hammel
 * @version 1.0
 */
public interface IInputController extends IController{

     /**
     * platform can define the dto access of the component by this method
     * @param model
     * @throws ControllerException
     */
    void setModel(IDTO<?> model) throws ControllerException;
}
