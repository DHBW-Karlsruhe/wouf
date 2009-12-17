/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.platform;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.Observable;

/**
 * use of a observer pattern to realize event based communication between
 * platform and plugins
 * @author Marco Hammel
 */
public class PlatformEventObserverable extends Observable implements EventListener, ActionListener{
   

    private static PlatformEventObserverable instance;
    /**
     *
     */
    private PlatformEventObserverable(){

    }

    public static PlatformEventObserverable getInstance(){
        if(PlatformEventObserverable.instance == null){
            PlatformEventObserverable.instance = new PlatformEventObserverable();
        }
        return PlatformEventObserverable.instance;
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
