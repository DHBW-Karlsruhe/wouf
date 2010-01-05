/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.directinput;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.bh.controller.Controller;
import org.bh.controller.ControllerException;
import org.bh.controller.IPeriodController;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOPeriod;
import org.bh.data.types.Calculable;
import org.bh.data.types.IValue;
import org.bh.platform.PlatformEvent;

/**
 *
 * @author Marco Hammel
 */
public class ControllerBHPeriodInputForm extends Controller implements IPeriodController{

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

	@Override
	public void editDTO(DTOPeriod dto, JPanel panel) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public int getGuiPriority() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public List<DTOKeyPair> getStochasticKeys() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void stopEditing() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}



}
