/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.plugin.resultAnalysis;

import info.clearthought.layout.TableLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.chart.BHChartPanel;
import org.bh.gui.swing.*;
import org.bh.platform.formula.FormulaException;
import org.bh.platform.formula.IFormula;
import org.bh.platform.formula.IFormulaFactory;

/**
 *
 * @author Marco Hammel
 */
public class BH_APV_ResultPanel extends JPanel {

    private static final Logger log = Logger.getLogger(BH_APV_ResultPanel.class);
    //APV Labels
    private BHValueLabel APVshareholderValue;
    private BHDescriptionLabel APVshareholderValueDESC;
    private BHValueLabel APVpresentValue; //Label
    private BHDescriptionLabel APVpresentValueDESC;
    private BHValueLabel APVpresentValueTaxShield;
    private BHDescriptionLabel APVpresentValueTaxShieldDESC;
    //APV Charts
    private BHChartPanel apvWFShareholderValues;
    private BHChartPanel apvBCCapitalStructure;
    // formulas
    private Component finiteFormula;
    private Component infiniteFormula;
    
    public BH_APV_ResultPanel(){
        this.initialize();
    }

    public void initialize() {
        double border = 10;
        double size[][] = {{border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}, // Columns
            {border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border,TableLayout.PREFERRED
        	, border,TableLayout.PREFERRED, border, border,TableLayout.PREFERRED, border}}; // Rows


        this.setLayout(new TableLayout(size));
        //this.setLayout(new FlowLayout());
        //All Labels to APV            
        APVpresentValue = new BHValueLabel("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF");
        APVpresentValueDESC = new BHDescriptionLabel("PRESENT_VALUE_FCF");
        APVshareholderValue = new BHValueLabel("SHAREHOLDER_VALUE");
        APVshareholderValueDESC = new BHDescriptionLabel("SHAREHOLDER_VALUE");
        APVpresentValueTaxShield = new BHValueLabel("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_TAX_SHIELD");
        APVpresentValueTaxShieldDESC = new BHDescriptionLabel("PRESENT_VALUE_TAX_SHIELD");

        //All APV Charts
        apvWFShareholderValues = BHChartFactory.getBarChart("Test", "x", "y", BHResultController.ChartKeys.APV_WF_SV);
        apvBCCapitalStructure = BHChartFactory.getStackedBarChart("Test2", "x", "y", BHResultController.ChartKeys.APV_BC_CS);

        //Formeldarstellung
        IFormulaFactory ff = IFormulaFactory.instance;
        IFormula f;

        try {
            f = ff.createFormula("APV_T", getClass().getResourceAsStream("APV_SHV_T.xml"), false);
            finiteFormula = f.getJMathComponent();

            f = ff.createFormula("APV_t1", getClass().getResourceAsStream("APV_SHV_t1.xml"), false);
            infiniteFormula = f.getJMathComponent();
        } catch (FormulaException e) {
            log.debug(e);
        }



        this.add(finiteFormula, "3,3");
        this.add(infiniteFormula, "5,3");

        this.add(APVpresentValueDESC, "1,5");
        this.add(APVpresentValue, "3,5");
        this.add(APVpresentValueTaxShieldDESC, "1,7");
        this.add(APVpresentValueTaxShield, "3,7");

        this.add(apvWFShareholderValues, "3,9");        
        this.add(apvBCCapitalStructure, "3,11");
        
    }
    public static void main(String[] args) {

	JFrame test = new JFrame("Test for ResultPanel");
	test.setContentPane(new BH_APV_ResultPanel());
	test.addWindowListener(new WindowAdapter() {

	    @Override
		public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});
	//test.pack();
	test.setVisible(true);

    }
}
