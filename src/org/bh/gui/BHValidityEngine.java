/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui;

import com.jgoodies.validation.ValidationResult;
import java.util.Map;

import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;

/**
 *
 * @author Marco Hammel
 */
public abstract class BHValidityEngine {

    private boolean isValid = false;
    private Map<String, IBHComponent> toValidate;

    public BHValidityEngine(){

    }
    public BHValidityEngine(Map<String, IBHComponent> toValidate){
        this.toValidate = toValidate;
    }
    /**
     * return wheater the last validationAll has an error or warning
     * @param validation
     */
    private void setValidStatus(ValidationResult validation){
        if (validation.hasErrors() || validation.hasWarnings()){
            isValid = true;
        }else{
            isValid = false;
        }
    }
    protected boolean isValid(){
        return isValid;
    }

    abstract ValidationResult validate(IBHComponent comp);

    abstract ValidationResult validateAll(Map<String, BHTextField> toValidate);
}
