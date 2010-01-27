/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.controller;

import java.util.Arrays;
import java.util.Map;

import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.gui.IBHComponent;
import org.bh.gui.IBHModelComponent;
import org.bh.gui.chart.IBHAddValue;
import org.bh.gui.view.View;
import org.bh.platform.ScenarioController;

/**
 * every plugin controller with a output exercise should be a subclass of this class
 * @author Marco Hammel
 * @version 1.0
 */
public class OutputController extends Controller implements IOutputController {

    /**
     * deterministic result managed by the mvc component
     */
    protected Map<String, Calculable[]> result;
    /**
     * stochastical result managed by the mvc component
     * @see DistributionMap
     */
    protected DistributionMap stochasticResult;
    /**
     * scenario which triggerd plugin
     * @see ScenarioController
     */
    protected DTOScenario scenario;

    /**
     * controller for deterministic results
     * @param view
     * @param result
     * @param scenario
     */
    public OutputController(View view, Map<String, Calculable[]> result, DTOScenario scenario) {
        super(view);
        setResult(result, scenario);
    }

    /**
     * controller for stochastical results
     * @param view
     * @param result
     * @param scenario
     */
    public OutputController(View view, DistributionMap result, DTOScenario scenario) {
        super(view);
        setResult(result, scenario);
    }

    public OutputController(View view) {
        super(view);
    }

    @Override
    public void setResult(Map<String, Calculable[]> result, DTOScenario scenario)
            throws ControllerException {
        this.result = result;
        this.scenario = scenario;

        log.debug("Loading results to view");
        for (Map.Entry<String, IBHComponent> entry : view.getBHComponents().entrySet()) {
            String key = entry.getKey();
            IBHComponent comp = entry.getValue();
            Calculable[] values = result.get(key);
            if (values == null) {
                continue;
            }

            if (comp instanceof IBHModelComponent) {
                ((IBHModelComponent) comp).setValue(values[0]);
            } else if (comp instanceof IBHAddValue) {
                ((IBHAddValue) comp).addValues(Arrays.asList(values));
            }
        }
    }

    @Override
    public void setResult(DistributionMap result, DTOScenario scenario) throws ControllerException {
        this.scenario = scenario;
        this.stochasticResult = result;
    }
}
