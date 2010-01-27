/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui.view;

/**
 *
 * @author Marco Hammel
 */
public class ViewException extends Exception{

    public ViewException(){
        super();
    }

    public ViewException(String arg0){
        super(arg0);
    }

    public ViewException(Throwable cause){
        super(cause);
    }
}
