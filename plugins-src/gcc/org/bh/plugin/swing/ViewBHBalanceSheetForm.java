/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.swing;

import org.bh.gui.View;
import org.bh.gui.ViewException;

/**
 *
 * @author Marco Hammel
 */
public class ViewBHBalanceSheetForm extends View{

    public static enum Key{
        GUIKEY,
        IIVGMAX,


    }

    public ViewBHBalanceSheetForm() throws ViewException{
        super(new BHBalanceSheetForm(), new ValidationBHBalanceSheetForm());
    }
}
