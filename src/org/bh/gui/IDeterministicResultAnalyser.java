package org.bh.gui;

import java.util.Map;

import javax.swing.JPanel;

import org.bh.data.types.Calculable;

public interface IDeterministicResultAnalyser {
	/**
	 * When called, the plugin should analyse the results (e.g. display them on
	 * the GUI).
	 * 
	 * @param result
	 *            The result to analyse.
	 * @param panel
	 *            Panel which can be used to display a GUI.
	 */
	public void setResult(Map<String, Calculable[]> result, JPanel panel);
}
