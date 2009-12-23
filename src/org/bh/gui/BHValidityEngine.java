/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.jgoodies.validation.view.ValidationResultViewFactory;
import java.util.Map;

import javax.swing.JLabel;
import org.bh.controller.Controller;
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;

/**
 *
 * @author Marco Hammel
 */
public abstract class BHValidityEngine{

    private static boolean isValid = false;
    private static ValidationResultModel validationModel;
    private static ValidationResult validationResult;

    /**
     * return wheater the last validationAll has an error or warning
     * @param validation
     */
    private static void setValidityStatus(ValidationResult validation){
        if (validation.hasErrors() || validation.hasWarnings()){
            isValid = false;
        }else{
            isValid = true;
        }
    }
    public static boolean isValid(){
        return isValid;
    }
    private static void setValidityReportLabel(ValidationResultModel validationModel){
         Controller.setBHstatusBarValidationToolTip(ValidationResultViewFactory.createReportIconAndTextLabel(validationModel));
    }
    /**
     * Shell proof the constant based rules of a single component
     * @param comp
     * @return
     */
    abstract ValidationResult validate(IBHComponent comp);
    /**
     * Shell proof the single components and can also proof related conditions between
     * components
     * @param toValidate
     * @return
     */
    abstract ValidationResult validateAll(Map<String, IBHComponent> toValidate);
}
