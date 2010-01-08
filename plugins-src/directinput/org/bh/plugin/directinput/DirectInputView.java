/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.directinput;

import org.bh.gui.View;
import org.bh.gui.ViewException;

/**
 *
 * @author Marco Hammel
 */
public class DirectInputView extends View{

    public DirectInputView() throws ViewException{
        super(new DirectInputForm());
    }

}
