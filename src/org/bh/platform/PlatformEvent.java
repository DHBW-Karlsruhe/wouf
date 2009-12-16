/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.platform;

import java.awt.event.ActionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author Marco Hammel
 */
public class PlatformEvent extends ActionEvent{

    private static Logger log = Logger.getLogger(PlatformEvent.class);
    
    public PlatformEvent(Object source, int id, String command){
        super(source, id, command);
        log.debug("Platform Event " + command + " from " + source);
    }

    public PlatformEvent(Object source, int id, String command, int modifier){
        super(source, id, command, modifier);
        log.debug("Platform Event " + command + " from " + source);
    }
}
