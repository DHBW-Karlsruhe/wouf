package org.bh.gui;

import java.awt.Component;

import org.bh.data.DTOScenario;
import org.bh.data.types.DistributionMap;

public interface IStochasticResultAnalyser {
	public Component setResult(DTOScenario scenario, DistributionMap result);
}
