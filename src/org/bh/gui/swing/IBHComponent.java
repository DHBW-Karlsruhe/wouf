/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui.swing;

import java.awt.Component;
import org.bh.data.types.IValue;

/**
 * Every Swing element which shall use a defined DTO key must implement this interface
 * @author Marco Hammel
 */
public interface IBHComponent {

    /**
     * Constant can be use to check objects
     */
    Boolean ISBHCOMPONENT = true;

    /**
     * No check for the DTO constants USe the DTO Enums to be conform
     * @return value of a DTO key
     */
    public String getKey();
    /**
     * returns the modelspecific value of a UI Component
     * @return
     */
    public IValue getValue();
    /**
     * Number of rules and the rules itself are platform indipendent But
     * shall be consitent in every plugin by using one Validity Engine per plugin
     * @return amount of rules defined in a subclass of BHValidity engine
     * @see BHValidityEngine
     */
    public int[] getValidateRules();
    /**
     * can set the Rules for the validation by runtime;
     * @param validateRules
     */
    public void setValidateRules(int[] validateRules);
    /**
     * you have to override the add method and implement a put into the map
     * for
     * @param comp
     * @return
     */
    public Component add(Component comp);

    /**
     * component information about its validity status
     * @return
     */
    public boolean isTypeValid();
}
