package org.bh.plugin.timeSeries;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.calculation.IStochasticProcess;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.plugin.wienerProcess.WienerProcess;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 * TODO plugin not finally implemented. 
 *
 *
 * @author Vito Masiello, Andreas Wussler
 * @version 1.0, 08.12.2010
 *
 */

public class TimeSeries implements IStochasticProcess {
	private static final Logger log = Logger.getLogger(TimeSeries.class);

	private static final String UNIQUE_ID = "timeSeries";
	private static final String GUI_KEY = "timeSeries";
	
	private DTOScenario scenario;
	private HashMap<String, Double> internalMap;
	private HashMap<String, Integer> map;

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	@Override
	/**
	 * if button calculate is pressed
	 */
	public JPanel calculateParameters() {
		internalMap = new HashMap<String, Double>();
		map = new HashMap<String, Integer>();
		TreeMap<DTOKeyPair, List<Calculable>> toBeDetermined = scenario.getPeriodStochasticKeysAndValues();
		JPanel result = new JPanel();
		//TODO ....
		result.setLayout(new BorderLayout());
		result.add(new JLabel("Zeitreihenanalyse: Cashflows:"+toBeDetermined.firstEntry().toString()));
		return result;
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
	
	/**
	 * Initaliasing: at first use
	 */
	@Override
	public TimeSeries createNewInstance(DTOScenario scenario) {
		TimeSeries instance = new TimeSeries(); //new TimeSeries instance
		instance.scenario = scenario; //give instance the reference to scenario
		return instance; //return new instance
	}

}
