package org.bh.plugin.resultAnalysis;

import java.awt.Component;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewException;
import org.bh.platform.IDeterministicResultAnalyser;
/**
 * initialize plugin components and distribute values from the platform
 * @author Marco Hammel
 * @version 1.0
 */
public class DefaultResultAnalyser implements IDeterministicResultAnalyser {
    @Override
    public Component setResult(DTOScenario scenario, Map<String, Calculable[]> result) {
	try {
	    View view = new ViewBHResultPanel();
	    new BHResultController(view, result, scenario);

	    return view.getViewPanel();
	} catch (ViewException e) {
	    Logger.getLogger(DefaultResultAnalyser.class).error("Cannot create view", e);
	    return null;
	}
    }
}
