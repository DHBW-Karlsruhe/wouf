/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

/**
 *
 * @author Marco Hammel
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
