/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui.swing;

import org.bh.data.types.Calculable;
import org.bh.data.types.IValue;

/**
 *
 * @author Marco Hammel
 */
public class BHPercentTextField extends BHTextField{
    private static final long serialVersionUID = -5249789865255724932L;

    private static final int FACTOR = 100;

    public BHPercentTextField(Object key, String value, boolean returnCalculable){
        super(key, value, returnCalculable);
    }

    public BHPercentTextField(Object key, String value){
        super(key, value);
    }

    public BHPercentTextField(Object key, boolean returnCalculable){
        super(key, returnCalculable);
    }

    public BHPercentTextField(Object key){
        super(key);
    }

    @Override
    public IValue getValue(){
        return Calculable.parseCalculable(
                Double.valueOf(Double.parseDouble(
                    this.getText()) / BHPercentTextField.FACTOR
                ).toString()
               );
    }

    @Override
    public void setValue(IValue value){
        if(value != null){
            super.setValue(
                Calculable.parseCalculable(
                    (Double.toString(
                        ((Calculable)value).parse().
                            doubleValue() * BHPercentTextField.FACTOR
                        )
                    )
                )
              );
        }else{
            this.setText("");
        }
    }
}
