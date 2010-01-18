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
//    //FTE Verfahren
//    private BHValueLabel FTEshareholderValue;
//    private BHDescriptionLabel FTEshareholderValueDESC;
//    private BHValueLabel FTEpresentValueTaxShield;
//    private BHDescriptionLabel FTEpresentValueTaxShieldDESC;
//    private BHValueLabel FTEflowEquity;
//    private BHDescriptionLabel FTEflowEquityDESC;
//    private BHValueLabel FTEflowEquityTaxShield;
//    private BHDescriptionLabel FTEflowEquityTaxShieldDESC;
//    private BHValueLabel FTEflowToEquity;
//    private BHDescriptionLabel FTEflowToEquityDESC;
//    private BHValueLabel FTEdebtAmortisation;
//    private BHDescriptionLabel FTEdebtAmortisationDESC;
//    private BHValueLabel FTEequityReturnRate;
//    private BHDescriptionLabel FTEequityReturnRateDESC;
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

        double border = 10;
        double size[][] = {{border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}, // Columns
            {border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, 
        	 border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, 
        	 border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, 
        	 border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, 
        	 border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED,
        	 border, TableLayout.PREFERRED, border}}; // Rows


        this.setLayout(new TableLayout(size));

//        //All Labels to FTE
//        FTEshareholderValue = new BHValueLabel("SHAREHOLDER_VALUE");
//        FTEshareholderValueDESC = new BHDescriptionLabel("SHAREHOLDER_VALUE");
//        FTEdebtAmortisation = new BHValueLabel("DEBT_AMORTISATION");
//        FTEdebtAmortisationDESC = new BHDescriptionLabel("DEBT_AMORTISATION");
//        FTEequityReturnRate = new BHValueLabel("EQUITY_RETURN_RATE_FTE");
//        FTEequityReturnRateDESC = new BHDescriptionLabel("EQUITY_RETURN_RATE_FTE");
//        FTEflowEquity = new BHValueLabel("FLOW_TO_EQUITY");
//        FTEflowEquityDESC = new BHDescriptionLabel("FLOW_TO_EQUITY");
//        FTEflowEquityTaxShield = new BHValueLabel("FLOW_TO_EQUITY_TAX_SHIELD");
//        FTEflowEquityTaxShieldDESC = new BHDescriptionLabel("FLOW_TO_EQUITY_TAX_SHIELD");
//        FTEflowToEquity = new BHValueLabel("LOW_TO_EQUITY_INTEREST");
//        FTEflowToEquityDESC = new BHDescriptionLabel("FLOW_TO_EQUITY_INTEREST");
//        FTEpresentValueTaxShield = new BHValueLabel("PRESENT_VALUE_TAX_SHIELD");
//        FTEpresentValueTaxShieldDESC = new BHDescriptionLabel("PRESENT_VALUE_TAX_SHIELD");

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
        
        this.add(infiniteFormula, "3,3");
        this.add(valueInfiniteFormula, "3,5");
        this.add(finiteFormula, "3,7");
        this.add(valueFiniteFormula, "3,9");
        
        if(!isAllSelected){
	        this.add(fteShareholderValue, "3,11");
	        this.add(fteCapitalStructure, "3,13");
        }
        this.add(fteFlowToEquity, "3,15");
        

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
