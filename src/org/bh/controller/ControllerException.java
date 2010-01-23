/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

/**
 * exception class for plugin exceptions
 * @author Marco Hammel
 * @version 1.0
 */
public class ControllerException extends RuntimeException{

    public ControllerException(){
        super();
    }
    public ControllerException(String arg0){
        super(arg0);
    }
    /**
     * 
     * @param cause
     */
    public ControllerException(Throwable cause){
        super(cause);
    }
}
