/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// no build
package org.bh.controller;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import org.bh.data.IDTO;
import org.bh.data.DTOAccessException;
import org.bh.gui.View;
import org.bh.gui.swing.IBHComponent;

/**
 *
 * @author Marco Hammel
 */
public abstract class Controller implements IController, ActionListener{

    private View view = null;
    private Map<String, IBHComponent> bhcomponents;
    private List<IDTO> model;

    abstract void handleDTOException(Exception e);

    public View getView(){
        return view;
    }

    private void bindData(View view){
       
    }
    abstract View bindView();

    abstract IDTO bindModel() throws DTOAccessException;


}
