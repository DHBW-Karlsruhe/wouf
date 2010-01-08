package org.bh.plugin.resultAnalysis;

import java.util.Map;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.controller.OutputController;
import org.bh.data.types.Calculable;
import org.bh.gui.IDeterministicResultAnalyser;
import org.bh.gui.View;
import org.bh.gui.ViewException;

public class DefaultResultAnalyser implements IDeterministicResultAnalyser {
	@Override
	public void setResult(Map<String, Calculable[]> result, JPanel panel) {
		try {
			View view = new ViewBHResultPanel();
			panel.add(view.getViewPanel());
			new OutputController(view, result);
		} catch (ViewException e) {
			Logger.getLogger(DefaultResultAnalyser.class).error("Cannot create view", e);
		}
	}
}
