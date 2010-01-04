/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.directinput;

import java.awt.event.ActionEvent;
import java.util.Map;
import org.bh.controller.Controller;
import org.bh.controller.ControllerException;
import org.bh.data.types.Calculable;
import org.bh.data.types.IValue;
import org.bh.platform.PlatformEvent;

/**
 *
 * @author Marco Hammel
 */
public class ControllerBHPeriodInputForm extends Controller{

    @Override
    protected IValue typeConverter(String value) throws ControllerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setResult(Map<String, Calculable[]> result) throws ControllerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getGuiKey() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void platformEvent(PlatformEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
