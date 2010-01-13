/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import java.util.Map;

import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;

/**
 *
 * @author Marco Hammel
 */
public interface IOutputController extends IController{

    /**
     * platform can hand over the result of an operation to a component by this method
     * @param result
     * @param scenario TODO
     * @throws ControllerException
     */
    void setResult(Map<String, Calculable[]> result, DTOScenario scenario) throws ControllerException;
}
