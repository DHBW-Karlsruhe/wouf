/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.plugin.resultAnalysis;

import info.clearthought.layout.TableLayout;

import java.awt.Component;
import java.util.Map;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.data.types.Calculable;
import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.chart.BHChartPanel;
import org.bh.platform.formula.FormulaException;
import org.bh.platform.formula.IFormula;
import org.bh.platform.formula.IFormulaFactory;

/**
 *
 * @author Marco Hammel
 */
public class BH_FTE_ResultPanel extends JPanel {

    private static final Logger log = Logger.getLogger(BH_FTE_ResultPanel.class);

    // formulas
    private Map<String, Calculable> formulaValues;
    private Component finiteFormula;
    private Component infiniteFormula;
    private Component valueFiniteFormula;
    private Component valueInfiniteFormula;
    //charts
    private BHChartPanel fteShareholderValue;
    private BHChartPanel fteCapitalStructure;
    private BHChartPanel fteFlowToEquity;
    
    public BH_FTE_ResultPanel(boolean isAllSelected, Map<String, Calculable> formulaValues){
        this.formulaValues = formulaValues;
    	initialize(isAllSelected);
    }
    
    public void initialize(boolean isAllSelected) {

        double border = 30;
        
        if(!isAllSelected){
	        double size[][] = {{border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}, // Columns
	            {border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, 
	        	 border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED}}; // Rows
        	this.setLayout(new TableLayout(size));
        }else{
        	double size[][] = {{border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}, // Columns
    	            {border, TableLayout.PREFERRED, border, TableLayout.PREFERRED}}; // Rows
            	this.setLayout(new TableLayout(size));
        }

        //Formeldarstellung
        IFormulaFactory ff = IFormulaFactory.instance;
        IFormula f;
        
        try {
            f = ff.createFormula("FTE_T", getClass().getResourceAsStream("FTE_SHV_T.xml"), false);
            infiniteFormula = f.getJMathComponent();

            f = ff.createFormula("FTE_T", getClass().getResourceAsStream("FTE_SHV_T.xml"), false);
            valueInfiniteFormula = f.getJMathComponentForInputValues(formulaValues);
            
            f = ff.createFormula("FTE_t1", getClass().getResourceAsStream("FTE_SHV_t1.xml"), false);
            finiteFormula = f.getJMathComponent();
            
            f = ff.createFormula("FTE_t1", getClass().getResourceAsStream("FTE_SHV_t1.xml"), false);
            valueFiniteFormula = f.getJMathComponentForInputValues(formulaValues);
            
        } catch (FormulaException e) {
            log.debug(e);
        }
        //All FTE Charts
        fteShareholderValue = BHChartFactory.getBarChart( BHResultController.ChartKeys.FTE_BC_SV.toString(), false,false);
        fteCapitalStructure = BHChartFactory.getStackedBarChart( BHResultController.ChartKeys.FTE_BC_CS.toString(), true, false);
        fteFlowToEquity = BHChartFactory.getBarChart( BHResultController.ChartKeys.FTE_BC_FTE.toString(), false, false);
        
        if(!isAllSelected){
	        this.add(infiniteFormula, "3,3");
	        this.add(valueInfiniteFormula, "3,5");
	        this.add(finiteFormula, "3,7");
	        this.add(valueFiniteFormula, "3,9");
	        this.add(fteShareholderValue, "3,11");
	        //this.add(fteCapitalStructure, "3,13");
	        this.add(fteFlowToEquity, "3,13");
        }else{
        	this.add(fteFlowToEquity, "3,1");
        }

    }
//    public static void main(String[] args) {
//
//    	JFrame test = new JFrame("Test for ResultPanel");
//    	test.setContentPane(new BH_APV_ResultPanel());
//    	test.addWindowListener(new WindowAdapter() {
//
//    	    @Override
//    		public void windowClosing(WindowEvent e) {
//    		System.exit(0);
//    	    }
//    	});
//    	//test.pack();
//    	test.setVisible(true);
//
//    }
}
