package org.bh.plugin.timeSeries;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.calculation.IStochasticProcess;
import org.bh.data.DTOScenario;
import org.bh.data.types.DistributionMap;
import org.bh.plugin.wienerProcess.WienerProcess;

/**
 * 
 * TODO plugin not finally implemented. 
 *
 *
 * @author Vito Masiello
 * @version 1.0, 08.12.2010
 *
 */

public class TimeSeries implements IStochasticProcess {
	private static final Logger log = Logger.getLogger(TimeSeries.class);

	private static final String UNIQUE_ID = "timeSeries";
	private static final String GUI_KEY = "timeSeries";

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	@Override
	public JPanel calculateParameters() {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException("This method has not been implemented");
		String message = "\"Noch nicht vollständig implementiert calculateParameters()\"";
		JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
		return null;
	}

	@Override
	public void updateParameters() {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException("This method has not been implemented");
		String message = "\"Noch nicht vollständig implementiert updateParameters()\"";
		JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public DistributionMap calculate() {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException("This method has not been implemented");
		String message = "\"Noch nicht vollständig implementiert calculate()\"";
		JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
		return null;
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}

	@Override
	public IStochasticProcess createNewInstance(DTOScenario scenario) {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException("This method has not been implemented");
		String message = "\"Time Series noch nicht vollständig implementiert createNewInstance()\"";
		JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
		return null;
	}

}
