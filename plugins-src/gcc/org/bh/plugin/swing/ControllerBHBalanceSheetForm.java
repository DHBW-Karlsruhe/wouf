/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.swing;

import java.awt.event.ActionEvent;
import java.util.Map;
import org.bh.controller.Controller;
import org.bh.controller.ControllerException;
import org.bh.controller.InputController;
import org.bh.data.DTOPeriod;
import org.bh.data.IDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.IValue;
import org.bh.gui.ViewException;
import org.bh.platform.PlatformEvent;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet;

/**
 *
 * @author Marco Hammel
 */
public final class ControllerBHBalanceSheetForm extends InputController{

    public ControllerBHBalanceSheetForm() throws ViewException{
        super(new ViewBHBalanceSheetForm());
    }

    public ControllerBHBalanceSheetForm(IDTO<?> model) throws ViewException{
        super(new ViewBHBalanceSheetForm(), model);
    }

    public String getGuiKey() {
        return "BHBalanceSheet";
    }

    public void actionPerformed(ActionEvent e) {
        
    }

    public void platformEvent(PlatformEvent e) {
        switch(e.getEventType()){
            case SAVEALL:
                IDTO<?> model = this.getModel();
                if(model != null){
                    if(model instanceof DTOPeriod){
                        DTOGCCBalanceSheet bs = new DTOGCCBalanceSheet();
                        ((DTOPeriod)this.getModel()).addChild(bs);
                        this.setModel(bs);
                        this.saveAllToModel();
                    }else if(model instanceof DTOGCCBalanceSheet){
                        this.saveAllToModel();
                    }
                }
                
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
