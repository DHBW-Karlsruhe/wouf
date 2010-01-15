/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.plugin.resultAnalysis;

import info.clearthought.layout.TableLayout;

import java.awt.Component;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
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
public class BH_APV_ResultPanel extends JPanel {

    private static final Logger log = Logger.getLogger(BH_APV_ResultPanel.class);
    //APV Charts
    private BHChartPanel apvWFShareholderValues;
    private BHChartPanel apvBCCapitalStructure;
    // formulas
    private Component finiteFormula;
    private Component infiniteFormula;
    private Component valueFormula;
    
    enum Keys{
    	
    }
    
    public BH_APV_ResultPanel(){
        this.initialize();
    }

    public void initialize() {
        double border = 10;
        double size[][] = {{border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}, // Columns
            {border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border,TableLayout.PREFERRED, 
        	border,TableLayout.PREFERRED}}; // Rows


        this.setLayout(new TableLayout(size));

        //All APV Charts
        apvWFShareholderValues = BHChartFactory.getWaterfallChart("Test", "Periode", "GE", BHResultController.ChartKeys.APV_WF_SV);
        apvBCCapitalStructure = BHChartFactory.getStackedBarChart("Test2", "Periode", "GE", BHResultController.ChartKeys.APV_BC_CS);

        //Formeldarstellung
        IFormulaFactory ff = IFormulaFactory.instance;
        IFormula f;
        
        //create HashMap for Formelparser
//        Map<String,Calculable> formelMap;
//        formelMap.put("UWAPV, T");
//        formelMap.put("FCFT");
//        formelMap.put("ruEK");
//        formelMap.put("srFK");
//        formelMap.put("FKT");
//        formelMap.put("rFK");
//        formelMap.put("FKT2");
//      
        try {
            f = ff.createFormula("APV_T", getClass().getResourceAsStream("APV_SHV_T.xml"), false);
            
            finiteFormula = f.getJMathComponent();

            f = ff.createFormula("APV_t1", getClass().getResourceAsStream("APV_SHV_t1.xml"), false);
            infiniteFormula = f.getJMathComponent();
            
//            f = ff.createFormula("APV_T", getClass().getResourceAsStream("APV_SHV_T.xml"), false);
//            valueFormula = f.getJMathComponentForInputValues(formelMap);
//            
        } catch (FormulaException e) {
            log.debug(e);
        }


        
        this.add(finiteFormula, "3,3");
        this.add(infiniteFormula, "3,5");
//        this.add(valueFormula, "3,7");

        this.add(apvWFShareholderValues, "3,9");        
        this.add(apvBCCapitalStructure, "3,11");
        
    }
}
