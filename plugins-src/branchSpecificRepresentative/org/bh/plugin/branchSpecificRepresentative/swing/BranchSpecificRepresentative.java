package org.bh.plugin.branchSpecificRepresentative.swing;

import java.util.TreeMap;

import javax.swing.JPanel;

import org.bh.calculation.ITimeSeriesProcess;
import org.bh.data.DTOScenario;
import org.bh.gui.swing.comp.BHButton;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHProgressBar;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.ScenarioController;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class should provide GUI data for showing the popup and goodness.
 * 
 * <p>
 * This class should provide GUI data for showing a popup and providing
 * data how good the calculated values are.
 * 
 * @author Yannick Rödl
 * @version 0.1, 12.12.2011
 * 
 */
public class BranchSpecificRepresentative implements ITimeSeriesProcess {

	private static final String BRANCH_SPECIFIC_REPRESENTATIVE_GOODNESS = "goodnessBranchSpecificRepresentative";
	private static final String POPUP_GOODNESS_BRANCH_SPECIFIC_REPRESENTATIVE = "popup_goodness";

	private static final String UNIQUE_ID = "branchSpecificRepresentative";
	private static final String GUI_KEY = "branchSpecificRepresentative";
	
	private DTOScenario scenario;
	
	@Override
	public JPanel calculateParameters() {
		JPanel result = new JPanel();
		
		String rowDef = "4px,p";
		String colDef = "4px,right:pref,4px,60px:grow,8px:grow,right:pref,4px,max(35px;pref):grow,4px:grow";
		FormLayout layout = new FormLayout(colDef, rowDef);
		result.setLayout(layout);
		layout.setColumnGroups(new int[][] { { 4, 8 } });
		CellConstraints cons = new CellConstraints();
		
		result.add(new BHDescriptionLabel(
				BRANCH_SPECIFIC_REPRESENTATIVE_GOODNESS), cons.xywh(2, 2, 1, 1));
		result.add(new BHTextField(BRANCH_SPECIFIC_REPRESENTATIVE_GOODNESS),
				cons.xywh(4, 2, 1, 1));
		result.add(new BHButton(POPUP_GOODNESS_BRANCH_SPECIFIC_REPRESENTATIVE),
				cons.xywh(6, 2, 1, 1));
		
		return result;
	}

	/* Specified by interface/super class. */
	@Override
	public String getGuiKey() {
		return BranchSpecificRepresentative.GUI_KEY;
	}

	/* Specified by interface/super class. */
	@Override
	public String getUniqueId() {
		return BranchSpecificRepresentative.UNIQUE_ID;
	}

	/* Specified by interface/super class. */
	@Override
	public ITimeSeriesProcess createNewInstance(DTOScenario scenario) {
		BranchSpecificRepresentative instance = new BranchSpecificRepresentative();
		instance.scenario = scenario;
		return instance;
	}

	/* Specified by interface/super class. */
	@Override
	public void updateParameters() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	/* Specified by interface/super class. */
	@Override
	public TreeMap<Integer, Double> calculate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	/* Specified by interface/super class. */
	@Override
	public TreeMap<Integer, Double>[] calculateCompare(int p) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	/* Specified by interface/super class. */
	@Override
	public void setProgressB(BHProgressBar bhComponent) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	/* Specified by interface/super class. */
	@Override
	public void setInterrupted() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

}
