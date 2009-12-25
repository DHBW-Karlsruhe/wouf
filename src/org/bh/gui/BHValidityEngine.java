/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.gui;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.jgoodies.validation.view.ValidationResultViewFactory;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import org.bh.controller.Controller;
import org.bh.gui.swing.IBHComponent;

/**
 *
 * @author Marco Hammel
 */
public abstract class BHValidityEngine {

    private boolean isValid = false;
    private ValidationResult validationResultAll;

    /**
     * set boolean <code>isValid</code> wheater the last validationAll has an error or warning
     * @param validation
     */
    private void setValidityStatus(ValidationResult validation) {
        if (validation.hasErrors() || validation.hasWarnings()) {
            this.isValid = false;
        } else {
            this.isValid = true;
        }
    }

    public boolean isValid() {
        if(validationResultAll != null){
            setValidityStatus(validationResultAll);
            return this.isValid;
        }else{
            return false;
        }
        
    }

    private JScrollPane createValidationResultList(ValidationResult validationResult) {
        ValidationResultModel validationResultModel = new DefaultValidationResultModel();
        validationResultModel.setResult(validationResult);

        JScrollPane resultList = (JScrollPane) ValidationResultViewFactory.createReportList(validationResultModel);
        return resultList;
    }

    protected static void setInpuHintLabel(IBHComponent comp) {
        Controller.setBHstatusBarToolTip((JLabel) ValidationComponentUtils.getInputHint((JComponent) comp));
    }

    public void publishValidationAll(Map<String, IBHComponent> toValidate){
        validationResultAll = validateAll(toValidate);
        setValidityStatus(validationResultAll);
        Controller.setBHstatusBarValidationToolTip(createValidationResultList(validationResultAll));
    }
    protected void publishValidationComp(IBHComponent comp){
        ValidationResult valRes = validate(comp);
        Controller.setBHstatusBarValidationToolTip(createValidationResultList(valRes));
    }
    abstract void registerComponents(Map<String, IBHComponent> toValidate);
   
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
