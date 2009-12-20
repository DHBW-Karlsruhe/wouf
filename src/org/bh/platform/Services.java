/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.platform;

import javax.swing.event.EventListenerList;

import org.bh.gui.swing.BHStatusBar;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

/**
 *
 * @author Marco Hammel
 * @author Robert Vollmer
 */
public class Services {

    private static EventListenerList platformListeners = new EventListenerList();
    private static ITranslator translator = BHTranslator.getInstance();
    private static BHStatusBar bhStatusBar = BHStatusBar.getInstance("");

    public static ITranslator getTranslator(){
        return translator;
    }

    public static void addPlatformListener(PlatformListener l){
    	platformListeners.add(PlatformListener.class, l);
    }
    
    public static void removePlatformListener(PlatformListener l){
    	platformListeners.remove(PlatformListener.class, l);
    }
    
    public static void firePlatformEvent(PlatformEvent event) {
    	for (PlatformListener l : platformListeners.getListeners(PlatformListener.class))
    		l.platformEvent(event);
    }

    public static BHStatusBar getBHstatusBar(){
    	return bhStatusBar;
    }
}
