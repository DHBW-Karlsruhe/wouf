/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.platform;

import java.util.Observer;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

/**
 *
 * @author Marco Hammel
 */
public class Services {

    private static PlatformEventObserverable observerable = PlatformEventObserverable.getInstance();
    private static ITranslator translator = BHTranslator.getInstance();

    public static ITranslator getTranslator(){
        return translator;
    }

    public static void addObserver(Observer o){
        observerable.addObserver(o);
    }

}
