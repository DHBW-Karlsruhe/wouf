/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.resultAnalysis;

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
public class ControllerBHResultPanel extends Controller{

    public ControllerBHResultPanel() throws ViewException{
        super(new ViewBHResultPanel());
    }

    @Override
    protected IValue typeConverter(String value) throws ControllerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setResult(Map<String, Calculable[]> result) throws ControllerException {
        //TODO Mapping to Gui
    }

    public String getGuiKey() {
        return "BHResultPanel";
    }

    public void actionPerformed(ActionEvent e) {

    }

    public void platformEvent(PlatformEvent e) {

        switch(e.getEventType()){
            case SAVEALL:
                //TODO Safe Results?
                break;
            case DATA_CHANGED:

                break;

        }
    }

}
