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
@SuppressWarnings("serial")
public class BH_FCF_ResultPanel extends JPanel {

    private static final Logger log = Logger.getLogger(BH_FCF_ResultPanel.class);
    // formulas
    private Map<String, Calculable> formulaValues;
    private Component finiteFormula;
    private Component infiniteFormula;
    private Component valueFiniteFormula;
    private Component valueInfiniteFormula;
    
    public BH_FCF_ResultPanel(boolean isAllSelected, Map<String, Calculable> formulaValues){
        this.formulaValues = formulaValues;
        this.initialize(isAllSelected);
    }

    public void initialize(boolean isAllSelected) {

        double border = 30;

        double size[][] = {{border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}, // Columns
            {border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border,
        	TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}}; // Rows


        this.setLayout(new TableLayout(size));

        //Formeldarstellung
        IFormulaFactory ff = IFormulaFactory.instance;
        IFormula f;

        try {
            f = ff.createFormula("FCF_T", getClass().getResourceAsStream("FCF_SHV_T.xml"), false);
            infiniteFormula = f.getJMathComponent();

            f = ff.createFormula("FCF_T", getClass().getResourceAsStream("FCF_SHV_T.xml"), false);
            valueInfiniteFormula = f.getJMathComponentForInputValues(formulaValues);
            
            f = ff.createFormula("FCF_t1", getClass().getResourceAsStream("FCF_SHV_t1.xml"), false);
            finiteFormula = f.getJMathComponent();
            
            f = ff.createFormula("FCF_t1", getClass().getResourceAsStream("FCF_SHV_t1.xml"), false);
            valueFiniteFormula = f.getJMathComponentForInputValues(formulaValues);
            
        } catch (FormulaException e) {
            log.debug(e);
        }
        
        //All charts
        //TODO Schön Darstellen!!!!
        BHChartPanel fcf_shareholderValue = BHChartFactory.getWaterfallChart( BHResultController.ChartKeys.FCF_WF_SV, false, false);
       // BHChartPanel fcf_capitalStructure = BHChartFactory.getStackedBarChart( BHResultController.ChartKeys.FCF_BC_CS, true, false);
        BHChartPanel fcf_fcf = BHChartFactory.getBarChart( BHResultController.ChartKeys.FCF_BC_FCF, false, false);
        BHChartPanel fcf_returnRate = BHChartFactory.getBarChart( BHResultController.ChartKeys.FCF_BC_RR, true, false);        
        
        if(!isAllSelected){
	        this.add(infiniteFormula, "3,3");
	        this.add(valueInfiniteFormula, "3,5");
	        this.add(finiteFormula, "3,7");
	        this.add(valueFiniteFormula, "3,9");
        }   
        this.add(fcf_shareholderValue, "3,11");
//        if(!isAllSelected)
//        	this.add(fcf_capitalStructure, "3,13");
        this.add(fcf_fcf, "3,15");
        this.add(fcf_returnRate, "3,17");

    }
}
