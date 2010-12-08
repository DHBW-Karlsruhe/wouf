package org.bh.plugin.timeSeries;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JFrame;
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
 * @author Vito Masiello, Andreas Wußler
 * @version 1.0, 08.12.2010
 *
 */

public class TimeSeries implements IStochasticProcess {
	private static final Logger log = Logger.getLogger(TimeSeries.class);

	private static final String UNIQUE_ID = "timeSeries";
	private static final String GUI_KEY = "timeSeries";
	
	private DTOScenario scenario;
	private JPanel panel;
	private HashMap<String, Double> internalMap;
	private HashMap<String, Integer> map;

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	@Override
	public JPanel calculateParameters() {
		if(false){//TODO calculate Parameters
			internalMap = new HashMap<String, Double>();
			map = new HashMap<String, Integer>();
			TreeMap<DTOKeyPair, List<Calculable>> toBeDetermined = scenario.getPeriodStochasticKeysAndValues();
			
			JPanel result = new JPanel();

			String rowDef = "4px,p,4px,p,4px,p,4px,p,4px";
			String colDef = "4px,right:pref,4px,60px:grow,8px:grow,right:pref,4px,max(35px;pref):grow,4px:grow";
			FormLayout layout = new FormLayout(colDef, rowDef);
			result.setLayout(layout);
			layout.setColumnGroups(new int[][] { { 4, 8 } });
			CellConstraints cons = new CellConstraints();
		}
		// Print Not yet implemented error
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
	public TimeSeries createNewInstance(DTOScenario scenario) {
		TimeSeries instance = new TimeSeries(); //new TimeSeries instance
		instance.scenario = scenario; //give instance the reference to scenario
		return instance; //return new instance
	}

}
