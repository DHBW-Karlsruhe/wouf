/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gcc.org.bh.plugin.ggc.swing;

import org.bh.gui.ValidationMethods;
import org.bh.gui.View;
import org.bh.gui.ViewException;
import org.bh.plugin.ggc.swing.BHBalanceSheetForm;

/**
 *
 * @author Marco Hammel
 */
public class ViewBHBalandeSheetForm extends View{

    public ViewBHBalandeSheetForm() throws ViewException{
        super(new BHBalanceSheetForm(), new ValidationMethods());
    }

}
