/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.platform;

import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

/**
 *
 * @author Marco Hammel
 */
public class Services {

    public static ITranslator getTranslator(){
        return BHTranslator.getInstance();
    }

}
