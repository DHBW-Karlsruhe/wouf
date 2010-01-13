/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.plugin.resultAnalysis;

import info.clearthought.layout.TableLayout;
import java.awt.Component;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
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
    //APV Verfahren
    private BHValueLabel APVshareholderValue;
    private BHDescriptionLabel APVshareholderValueDESC;
    private BHValueLabel APVpresentValue; //Label
    private BHDescriptionLabel APVpresentValueDESC;
    private BHValueLabel APVpresentValueTaxShield;
    private BHDescriptionLabel APVpresentValueTaxShieldDESC;
    // formulas
    private Component finiteFormula;
    private Component infiniteFormula;
    //export button
    private BHButton exportButton;

    public void initialize() {
        double border = 10;
        double size[][] = {{border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}, // Columns
            {border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}}; // Rows


        this.setLayout(new TableLayout(size));
        //All Labels to APV
        APVpresentValue = new BHValueLabel("PRESENT_VALUE_FCF");
        APVpresentValueDESC = new BHDescriptionLabel("PRESENT_VALUE_FCF");
        APVshareholderValue = new BHValueLabel("SHAREHOLDER_VALUE");
        APVshareholderValueDESC = new BHDescriptionLabel("SHAREHOLDER_VALUE");
        APVpresentValueTaxShield = new BHValueLabel("PRESENT_VALUE_TAX_SHIELD");
        APVpresentValueTaxShieldDESC = new BHDescriptionLabel("PRESENT_VALUE_TAX_SHIELD");

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
    }
}
