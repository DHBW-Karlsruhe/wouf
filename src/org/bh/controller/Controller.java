/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// no build
package org.bh.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Observer;
import org.apache.log4j.Logger;
import org.bh.data.IDTO;
import org.bh.data.DTOAccessException;
import org.bh.gui.View;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

/**
 *
 * @author Marco Hammel
 */
public abstract class Controller implements IController, ActionListener, Observer{

    private static  Logger log = Logger.getLogger(Controller.class);
    private View view;
    private Map<String, IBHComponent> bhcomponents;
    private IDTO model;


    public Controller(){

    }
    public Controller(View view){
        log.debug("PLugincontroller instance");
        this.view = view;
    }
    public Controller(View view, IDTO model){
        this.model = model;
        this.view = view;
    }

    private void handleException(Exception e){
        log.error("Controller Exception ");
    }

    public View getView(){
        return view;
    }

    public void setView(View view){
        this.view = view;
    }

    private boolean safeToModel() throws DTOAccessException{
        log.debug("Plugin save to dto");
        model.setSandBoxMode(Boolean.TRUE);

        return true;
    }

    private boolean loadToView(){
        return true;
    }

    public void handlePlattformEvent(ActionEvent e) {
        actionPerformed(e);
    }

    public ITranslator getTranslator() {
        return Services.getTranslator();
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
