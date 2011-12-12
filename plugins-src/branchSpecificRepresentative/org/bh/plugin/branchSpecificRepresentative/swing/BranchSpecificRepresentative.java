package org.bh.plugin.branchSpecificRepresentative.swing;

import java.util.TreeMap;

import javax.swing.JPanel;

import org.bh.calculation.ITimeSeriesProcess;
import org.bh.data.DTOScenario;
import org.bh.gui.swing.comp.BHButton;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHProgressBar;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

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

	private enum Elements{
		BRANCH_SPECIFIC_REPRESENTATIVE_GOODNESS,
		POPUP_GOODNESS_BRANCH_SPECIFIC_REPRESENTATIVE;
		
		@Override
	    public String toString() {
	        return getClass().getName() + "." + super.toString();
	    }
	}
	
	private DTOScenario scenario;
	
	@Override
	public JPanel calculateParameters() {
		JPanel result = new JPanel();
		
		//create layout
		String rowDef = "4px,p";
		String colDef = "4px,right:pref,4px,60px:grow,8px:grow,right:pref,4px,max(35px;pref):grow,4px:grow";
		FormLayout layout = new FormLayout(colDef, rowDef);
		result.setLayout(layout);
		layout.setColumnGroups(new int[][] { { 4, 8 } });
		CellConstraints cons = new CellConstraints();
		
		//get translator
		ITranslator translator = BHTranslator.getInstance();
		
		//create goodness field
		BHTextField tfBranchSpecGoodness = new BHTextField(Elements.BRANCH_SPECIFIC_REPRESENTATIVE_GOODNESS);
		tfBranchSpecGoodness.setEditable(false);
		tfBranchSpecGoodness.setToolTipText(translator.translate(Elements.BRANCH_SPECIFIC_REPRESENTATIVE_GOODNESS, ITranslator.LONG));
		tfBranchSpecGoodness.setText("10 %");
		
		BHDescriptionLabel dlGoodnessDescription = new BHDescriptionLabel(
				Elements.BRANCH_SPECIFIC_REPRESENTATIVE_GOODNESS); 
		dlGoodnessDescription.setToolTipText(translator.translate(Elements.BRANCH_SPECIFIC_REPRESENTATIVE_GOODNESS, ITranslator.LONG));
		
		BHButton bShowDeviationAnalysis = new BHButton(Elements.POPUP_GOODNESS_BRANCH_SPECIFIC_REPRESENTATIVE);
		bShowDeviationAnalysis.setToolTipText(translator.translate(Elements.POPUP_GOODNESS_BRANCH_SPECIFIC_REPRESENTATIVE, ITranslator.LONG));
		bShowDeviationAnalysis.addActionListener(new DeviationAnalysisListener());
		
		// add components to panel
		result.add( dlGoodnessDescription, cons.xywh(2, 2, 1, 1));
		result.add( tfBranchSpecGoodness,
				cons.xywh(4, 2, 1, 1));
		result.add( bShowDeviationAnalysis,
				cons.xywh(6, 2, 1, 1));
		
		return result;
	}

	@Override
	public String getGuiKey() {
		return ITimeSeriesProcess.Key.BRANCH_SPECIFIC_REPRESENTATIVE.toString();
	}

	@Override
	public String getUniqueId() {
		return ITimeSeriesProcess.Key.BRANCH_SPECIFIC_REPRESENTATIVE.toString();
	}

	@Override
	public ITimeSeriesProcess createNewInstance(DTOScenario scenario) {
		BranchSpecificRepresentative instance = new BranchSpecificRepresentative();
		instance.scenario = scenario;
		return instance;
	}

	@Override
	public void updateParameters() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public TreeMap<Integer, Double> calculate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public TreeMap<Integer, Double>[] calculateCompare(int p) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void setProgressB(BHProgressBar bhComponent) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void setInterrupted() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

}
