/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import java.awt.Event;
import org.bh.gui.View;

/**
 *
 * @author Marco Hammel
 */
interface IController {

    String getUniqueId();//Zu welchem gehörst du??

    //getAnzeigename

    View getView() throws ControllerException;

    void setLanguage(String LANG);

    void handlePlattformEvent(Event e);



}
