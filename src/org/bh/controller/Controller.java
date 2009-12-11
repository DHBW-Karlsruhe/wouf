/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import java.awt.event.ActionListener;
import org.bh.data.IDTO;
import org.bh.data.DTOAccessException;
import org.bh.gui.View;

/**
 *
 * @author Marco Hammel
 */
public abstract class Controller implements IController, ActionListener{

    private View view;
	private IDTO model;


    protected Controller(){
        this.view = this.bindView();
        try{
            this.model = this.bindModel();
        }catch(DTOAccessException e){
            this.handleDTOException(e);
        }
   
    }

    abstract void handleDTOException(Exception e);

    public View getView(){
        return view;
    }

    abstract View bindView();

    abstract IDTO bindModel() throws DTOAccessException;


}
