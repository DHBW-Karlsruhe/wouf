/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.resultAnalysis;

import java.util.Map;

import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.gui.View;
import org.bh.gui.ViewException;

/**
 *
 * @author Marco Hammel
 * @author Norman
 */
public class ViewBHResultPanel extends View{
    public ViewBHResultPanel(DTOScenario scenario, Map<String, Calculable[]> result, Map<String, Calculable> formulaValues) throws ViewException{
        super(new BHResultPanel(scenario, result, formulaValues));
    }
}
