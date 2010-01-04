/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import java.util.Map;
import org.bh.data.types.Calculable;
import org.bh.gui.View;

/**
 *
 * @author Marco Hammel
 */
public abstract class OutputController extends Controller implements IOutputController{

    protected Map<String, Calculable[]> result;

    public OutputController(View view, Map<String, Calculable[]> result){
        super(view);
        this.result = result;
    }

    public OutputController(View view){
        super(view);
    }

    public OutputController(Map<String, Calculable[]> result){
        this(null, result);
    }
}
