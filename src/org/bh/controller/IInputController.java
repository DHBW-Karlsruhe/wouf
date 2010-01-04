/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import org.bh.data.IDTO;

/**
 *
 * @author Marco Hammel
 */
public interface IInputController extends IController{

     /**
     * platform can define the dto access of the component by this method
     * @param model
     * @throws ControllerException
     */
    void setModel(IDTO<?> model) throws ControllerException;
}
