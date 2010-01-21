/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.resultAnalysis;

import org.bh.gui.View;
import org.bh.gui.ViewException;

/**
 * 
 * @author Marco Hammel
 * @author Norman
 */
public class ViewBHResultPanel extends View {
    public ViewBHResultPanel() throws ViewException {
	super(new BHResultPanel());
    }
}
