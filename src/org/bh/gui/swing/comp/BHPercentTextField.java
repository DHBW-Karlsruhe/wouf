/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui.swing.comp;

import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.IValue;

/**
 *
 * @author Marco Hammel
 */
public final class BHPercentTextField extends BHTextField{
	private static final long serialVersionUID = 1607333535610045051L;

	public BHPercentTextField(Object key, String value){
        super(key, value, true);
    }

    public BHPercentTextField(Object key){
        super(key, true);
    }

    @Override
    public IValue getValue(){
    	Calculable result = (Calculable) super.getValue();
    	if (result != null)
    		result = result.div(new DoubleValue(100));
        return result;
    }

    @Override
    public void setValue(IValue value){
        if (value != null)
        	value = ((Calculable) value).mul(new DoubleValue(100));
        super.setValue(value);
    }
}
