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
import org.apache.log4j.Logger;
import org.bh.controller.Controller;
import org.bh.gui.swing.BHStatusBar;
import org.bh.gui.swing.IBHComponent;

/**
 *
 * @author Marco Hammel
 */
public abstract class BHValidityEngine {

    private static  Logger log = Logger.getLogger(BHValidityEngine.class);

    /**
     * flag for the validation of a complete UI instance
     */
    private boolean isValid = false;
    /**
     * keep the result from validateAll
     */
    private ValidationResult validationResultAll;

    /**
     * set boolean <code>isValid</code> wheater the last validationAll has an error or warning
     * 
     * @param validation    as Result of a Validation
     * @see ValidationResult
     */
    private void setValidityStatus(ValidationResult validation) {
        if (validation.hasErrors() || validation.hasWarnings()) {
            log.debug("validation has errors or warnings");
            this.isValid = false;
        } else {
            this.isValid = true;
        }
    }

    /**
     * deliver the flag wheather a UI is valid or not
     *
     * @return  boolean true is valid false --> isn´t proofed yet, is not valid
     */
    public boolean isValid() {
        if(validationResultAll != null){
            setValidityStatus(validationResultAll);
            return this.isValid;
        }else{
            return false;
        }
        
    }
    /**
     * creates the JScroll Pane with validation messages
     *
     * @param validationResult
     * @return  JScrollPane
     * @see JScrollPane
     */
    private JScrollPane createValidationResultList(ValidationResult validationResult) {
        log.debug("JScrollPane is build");
        ValidationResultModel validationResultModel = new DefaultValidationResultModel();
        validationResultModel.setResult(validationResult);

        JScrollPane resultList = (JScrollPane) ValidationResultViewFactory.createReportList(validationResultModel);
        return resultList;
    }
    /**
     * set the Input hint to the BHStatusBar
     *
     * @param comp instances of IBHComponent
     * @see BHStatusBar
     */
    protected static void setInputHintLabel(IBHComponent comp) {
        log.debug("Input ToolTip is setted to Status Bar");
        Controller.setBHstatusBarToolTip((JLabel) ValidationComponentUtils.getInputHint((JComponent) comp));
    }
    /**
     * run a validation and deliver the Result to the BHStatusBar
     *
     * @param toValidate IBHComponents which have to be validated as a Map
     * @throws ViewException 
     * @see IBHComponent
     * @see BHStatusBar
     */
    public void publishValidationAll(Map<String, IBHComponent> toValidate) throws ViewException{
        log.debug("Trigger validation process for All Components");
        validationResultAll = validateAll(toValidate);
        setValidityStatus(validationResultAll);
        Controller.setBHstatusBarValidationToolTip(createValidationResultList(validationResultAll));
    }
    /**
     * set the messages of the validation of a single component to the BHStatusBar
     *
     * @param comp
     * @throws ViewException
     * @see BHStatusBar
     */
    protected void publishValidationComp(IBHComponent comp) throws ViewException{
        log.debug("Trigger validation for a single component");
        ValidationResult valRes = validate(comp);
        Controller.setBHstatusBarValidationToolTip(createValidationResultList(valRes));
    }
    /**
     * have to register the model related components and set the ValidationComponentUtils entries
     *
     * @param toValidate a Map of IBHComponents
     * @see ValidationComponentUtils
     */
    abstract void registerComponents(Map<String, IBHComponent> toValidate) throws ViewException;
   
    /**
     * Shell proof the constant based rules of a single component
     *
     * @param comp single IBHComponent
     * @return the result of the Validation as ValidationResult
     * @see ValidationResult
     */
    abstract ValidationResult validate(IBHComponent comp) throws ViewException;

    /**
     * Shell proof the single components and can also proof related conditions between
     * components
     *
     * @param toValidate
     * @return the result of the Validation as ValidationResult
     * @see ValidationResult
     */
    abstract ValidationResult validateAll(Map<String, IBHComponent> toValidate) throws ViewException;
}
