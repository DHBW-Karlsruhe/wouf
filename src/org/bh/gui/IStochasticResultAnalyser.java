package org.bh.gui;

import javax.swing.JPanel;

import org.bh.data.DTOScenario;
import org.bh.data.types.DistributionMap;

public interface IStochasticResultAnalyser {
	public void setResult(DTOScenario scenario, DistributionMap result, JPanel panel);
}
