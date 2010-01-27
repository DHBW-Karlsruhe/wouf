/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.resultAnalysis;

import org.bh.gui.view.View;
import org.bh.gui.view.ViewException;

/**
 * View Component of the result plugin
 * @author Marco Hammel
 * @author Norman
 * @version 1.0
 */
public class ViewBHResultPanel extends View {
    public ViewBHResultPanel() throws ViewException {
	super(new BHResultPanel());
    }
}
