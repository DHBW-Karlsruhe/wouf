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
import org.bh.platform.formula.IFormulaFactory;

/**
 *
 * @author Marco Hammel
 */
public class BH_FCF_ResultPanel extends JPanel {

    private static final Logger log = Logger.getLogger(BH_FCF_ResultPanel.class);
    //FCF Verfahren
    private BHValueLabel FCFshareholderValue;
    private BHDescriptionLabel FCFshareholderValueDESC;
    private BHValueLabel FCFpresentValue; //Label
    private BHDescriptionLabel FCFpresentValueDESC;
    private BHValueLabel FCFequityReturnRate;
    private BHDescriptionLabel FCFequityReturnRateDESC;
    private BHValueLabel FCFdebtToEquityRatio;
    private BHDescriptionLabel FCFdebtToEquityRatioDESC;
    private BHValueLabel FCFwaccEquity;
    private BHDescriptionLabel FCFwaccEquityDESC;
    private BHValueLabel FCFwaccDebts;
    private BHDescriptionLabel FCFwaccDebtsDESC;
    private BHValueLabel FCFwacc;
    private BHDescriptionLabel FCFwaccDESC;
    // formulas
    private Component finiteFormula;
    private Component infiniteFormula;

    public void initialize() {

        double border = 10;
        double size[][] = {{border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}, // Columns
            {border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}}; // Rows


        this.setLayout(new TableLayout(size));
        //All Labels to FCF
        FCFshareholderValue = new BHValueLabel("SHAREHOLDER_VALUE");
        FCFshareholderValueDESC = new BHDescriptionLabel("SHAREHOLDER_VALUE");
        FCFpresentValue = new BHValueLabel("PRESENT_VALUE_FCF");
        FCFpresentValueDESC = new BHDescriptionLabel("Result.PRESENT_VALUE_FCF");
        FCFdebtToEquityRatio = new BHValueLabel("DEBT_TO_EQUITY_RATIO");
        FCFdebtToEquityRatioDESC = new BHDescriptionLabel("Result.DEBT_TO_EQUITY_RATIO");
        FCFequityReturnRate = new BHValueLabel("EQUITY_RETURN_RATE_FCF");
        FCFequityReturnRateDESC = new BHDescriptionLabel("EQUITY_RETURN_RATE_FCF");
        FCFwacc = new BHValueLabel("WACC");
        FCFwaccDESC = new BHDescriptionLabel("WACC");
        FCFwaccDebts = new BHValueLabel("WACC_DEBTS");
        FCFwaccDebtsDESC = new BHDescriptionLabel("WACC_DEBTS");
        FCFwaccEquity = new BHValueLabel("WACC_EQUITY");
        FCFwaccEquityDESC = new BHDescriptionLabel("WACC_EQUITY");

        //Formeldarstellung
        IFormulaFactory ff = IFormulaFactory.instance;
        IFormula f;

        try {
            f = ff.createFormula("FCF_T", getClass().getResourceAsStream("FCF_SHV_T.xml"), false);
            finiteFormula = f.getJMathComponent();

            f = ff.createFormula("FCF_t1", getClass().getResourceAsStream("FCF_SHV_t1.xml"), false);
            infiniteFormula = f.getJMathComponent();
        } catch (FormulaException e) {
            log.debug(e);
        }

        this.add(finiteFormula, "3,3");
        this.add(infiniteFormula, "5,3");

    }
}
