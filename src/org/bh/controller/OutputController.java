/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import java.util.Map;

import javax.swing.text.JTextComponent;

import org.bh.data.DTOAccessException;
import org.bh.data.types.Calculable;
import org.bh.gui.View;
import org.bh.gui.chart.IBHAddValue;
import org.bh.gui.swing.BHDescriptionLabel;
import org.bh.gui.swing.BHTextField;

/**
 *
 * @author Marco Hammel
 */
public class OutputController extends Controller implements IOutputController{

    protected Map<String, Calculable[]> result;

    public OutputController(View view, Map<String, Calculable[]> result){
        super(view);
        this.result = result;
    }

    public OutputController(View view){
        super(view);
    }

    public OutputController(Map<String, Calculable[]> result){
        this(null, result);
    }

    protected void loadAllToView()throws ControllerException{
        log.debug("Plugin load from dto in view");
        for(String key : this.bhMappingComponents.keySet()){
            if(this.result.containsKey(key)){
                if(this.bhMappingComponents.get(key) instanceof BHTextField || this.bhMappingComponents.get(key) instanceof BHDescriptionLabel){
                    String value = this.result.get(key)[0].toString();
                    ((JTextComponent) this.bhMappingComponents.get(key)).setText(value);
                }else if(this.bhMappingComponents.get(key) instanceof IBHAddValue){

                }
            }
        }
    }
    /**
     *
     * @param key
     * @throws DTOAccessException
     * @throws ControllerException
     */
    protected void loadToView(String key) throws ControllerException{
        log.debug("Plugin load from dto in view");
        //this.bhMappinglcomponents.get(key).setValue(this.model.get(key));
    }

	@Override
	public void setResult(Map<String, Calculable[]> result)
			throws ControllerException {
		this.result = result;
	}

}
