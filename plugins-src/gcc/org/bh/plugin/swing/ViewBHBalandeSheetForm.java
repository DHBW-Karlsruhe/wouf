/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.swing;

import org.bh.gui.ValidationMethods;
import org.bh.gui.View;
import org.bh.gui.ViewException;
import org.bh.plugin.swing.BHBalanceSheetForm;

/**
 *
 * @author Marco Hammel
 */
public class ViewBHBalandeSheetForm extends View{

    public ViewBHBalandeSheetForm() throws ViewException{
        super(new BHBalanceSheetForm(), new ValidationMethods());
    }

}
