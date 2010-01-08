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
public class RandomDirectInputView extends View{

    public RandomDirectInputView() throws ViewException{
        super(new RandomDirectInputForm());
    }

}
