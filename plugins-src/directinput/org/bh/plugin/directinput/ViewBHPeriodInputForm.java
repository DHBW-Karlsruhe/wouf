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
public class ViewBHPeriodInputForm extends View{

    public ViewBHPeriodInputForm(String year) throws ViewException{
        super(new DirectInputForm(year));
    }

}
