/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui;

import org.bh.gui.swing.IBHComponent;

/**
 *
 * @author Marco Hammel
 */
public abstract class BHValidityEngine {

    int[] validityRules;

    abstract boolean validate(IBHComponent comp);


}
