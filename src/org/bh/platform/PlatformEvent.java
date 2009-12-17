/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.platform;

import java.util.EventObject;
import org.apache.log4j.Logger;

/**
 *
 * @author Marco Hammel
 */
public class PlatformEvent extends EventObject{

    private static Logger log = Logger.getLogger(PlatformEvent.class);
    private Key eventKey;

    public static enum Key{
        /**
         * plugin should call saveAll method
         */
        SAVEALL,
        /**
         * plugin should put the dto copy back to ui
         */
        GETCOPY

    }
    
    public PlatformEvent(Object source, Key key){
        super(source);
        eventKey = key;
        log.debug("Platform Event from " + source);
    }

    public String getEventKey(){
        return this.eventKey.toString();
    }
    

}
