/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import java.awt.event.ActionListener;
import java.util.Map;
import org.bh.gui.View;
import org.bh.platform.IDTO;
import org.bh.platform.Value;

/**
 *
 * @author Marco Hammel
 */
public abstract class Controller implements IController, ActionListener{

    private View view;
    private Map<String, Value> modelEnities;


    protected Controller(){
        this.view = this.createView();

//        try{
//            this.modelEnities = IDTO.getAllKeys();
//        }catch(DTOException dtoE){
//            this.throwExceptionToView(dtoE);
//        }
        
    }

    abstract void throwExceptionToView(Exception e);

    public View getView(){
        return view;
    }

    abstract View createView();


}
