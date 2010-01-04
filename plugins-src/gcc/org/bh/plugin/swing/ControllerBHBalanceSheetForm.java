/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.swing;

import java.awt.event.ActionEvent;
import java.util.Map;
import org.bh.controller.Controller;
import org.bh.controller.ControllerException;
import org.bh.data.types.Calculable;
import org.bh.data.types.IValue;
import org.bh.gui.ViewException;
import org.bh.platform.PlatformEvent;

/**
 *
 * @author Marco Hammel
 */
public final class ControllerBHBalanceSheetForm extends Controller{

    public ControllerBHBalanceSheetForm() throws ViewException{
        super(new ViewBHBalanceSheetForm());
    }

    public void setResult(Map result) throws ControllerException {
        throw new ControllerException("Is not a result plugin");
    }

    public String getGuiKey() {
        return "BHBalanceSheet";
    }

    public void actionPerformed(ActionEvent e) {
        
    }

    public void platformEvent(PlatformEvent e) {
        switch(e.getEventType()){
            case SAVEALL:
                this.saveAllToModel();
                break;
            case DATA_CHANGED:
                break;
        }
    }

    @Override
    protected IValue typeConverter(String value) throws ControllerException {
        //TODO define conversion Rules for the fields
        return Calculable.parseCalculable(value);
    }

}
