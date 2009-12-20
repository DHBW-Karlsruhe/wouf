/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui;

import java.util.Map;
import org.bh.gui.swing.IBHComponent;

/**
 *
 * @author Marco Hammel
 */
public abstract class BHValidityEngine {
    
    public BHValidityEngine(){

    }
    abstract boolean validate(IBHComponent comp);

    abstract void validateAll();
}
