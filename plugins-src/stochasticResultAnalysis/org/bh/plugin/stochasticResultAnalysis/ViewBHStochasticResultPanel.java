package org.bh.plugin.stochasticResultAnalysis;

import org.bh.data.DTOScenario;
import org.bh.data.types.DistributionMap;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewException;
import org.bh.validation.ValidationMethods;

public class ViewBHStochasticResultPanel extends View{
    public ViewBHStochasticResultPanel(DTOScenario scenario, DistributionMap result) throws ViewException{
        super(new BHStochasticResultPanel(scenario, result), new ValidationMethods());
    }
}
