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

    String getPluginId();

    View getView();

    void setLanguage(String LANG);

    void setPlattformEvent(Event e);

}
