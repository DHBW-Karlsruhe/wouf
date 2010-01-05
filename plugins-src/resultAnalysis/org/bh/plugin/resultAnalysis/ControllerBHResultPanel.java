/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.resultAnalysis;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.bh.controller.ControllerException;
import org.bh.controller.OutputController;
import org.bh.data.types.Calculable;
import org.bh.gui.ViewException;
import org.bh.platform.PlatformEvent;

/**
 *
 * @author Marco Hammel
 */
public class ControllerBHResultPanel extends OutputController{

    public ControllerBHResultPanel() throws ViewException{
        super(new ViewBHResultPanel());
    }

    public ControllerBHResultPanel(Map<String, Calculable[]> result) throws ViewException{
        super(new ViewBHResultPanel(), result);
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

    public void setResult(Map<String, Calculable[]> result) throws ControllerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   

}
