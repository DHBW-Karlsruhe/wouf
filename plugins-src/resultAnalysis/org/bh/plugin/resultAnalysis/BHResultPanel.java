
package org.bh.plugin.resultAnalysis;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.gui.ViewException;
import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.swing.BHDescriptionLabel;
import org.bh.gui.swing.BHDescriptionTextArea;
import org.bh.gui.swing.BHValueLabel;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.bh.plugin.apv.APVCalculator;
import org.bh.plugin.fcf.FCFCalculator;
import org.bh.plugin.fte.FTECalculator;
import org.jfree.chart.ChartPanel;

import com.jgoodies.forms.layout.CellConstraints;
/**
 * 
 * Class to create one ResultPanel with two different Charts and Descriptions 
 *
 * <p>
 * This class creates a <code>JPanel</code> with the Results. There are two different
 * charts and the descriptions. 
 *
 * @author Lars.Zuckschwerdt
 * @version 1.0, 30.12.2009
 *
 */

public final class BHResultPanel extends JPanel{
	
	private JPanel panel;
	private ChartPanel lineChartLabel;
	private ChartPanel pieChartLabel;
	private ChartPanel barChartLabel;
	private BHDescriptionTextArea pieChartTextArea;
	
	//FTE Verfahren
	private BHValueLabel FTEshareholderValue;
	private BHDescriptionLabel FTEshareholderValueDESC;
	private BHValueLabel FTEpresentValueTaxShield;
	private BHDescriptionLabel FTEpresentValueTaxShieldDESC;
	private BHValueLabel FTEflowEquity;
	private BHDescriptionLabel FTEflowEquityDESC;
	private BHValueLabel FTEflowEquityTaxShield;
	private BHDescriptionLabel FTEflowEquityTaxShieldDESC;
	private BHValueLabel FTEflowToEquity;
	private BHDescriptionLabel FTEflowToEquityDESC;
	private BHValueLabel FTEdebtAmortisation;
	private BHDescriptionLabel FTEdebtAmortisationDESC;
	private BHValueLabel FTEequityReturnRate;
	private BHDescriptionLabel FTEequityReturnRateDESC;
	
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
	
	//APV Verfahren
	private BHValueLabel APVshareholderValue;
	private BHDescriptionLabel APVshareholderValueDESC;
	private BHValueLabel APVpresentValue; //Label
	private BHDescriptionLabel APVpresentValueDESC;
	private BHValueLabel APVpresentValueTaxShield;
	private BHDescriptionLabel APVpresentValueTaxShieldDESC;

	private CellConstraints cons;
	
	final ITranslator translator = Services.getTranslator();
	/**
	 * Constructor
	 * @throws ViewException 
	 */
	public BHResultPanel() throws ViewException{
		this.initialize();
	}
	
	/**
	 * Initialize method
	 * @throws ViewException 
	 */
	public void initialize() throws ViewException{
		GridLayout layout = new GridLayout(0,3);
		
		
		this.setLayout(layout);
		
       		/**
       		 * Creates the default LineChart and add it on a Label 
       		 */
       		lineChartLabel = new ChartPanel(BHChartFactory.getLineChart("TestChart", "XAxis", "YAxis",  "LineChart"));
       		lineChartLabel.setFont(UIManager.getFont("defaultFont"));
       		
       		/**
       		 * Creates the description and add it on a Label
       		 */
       		//lineChartTextArea = new BHDescriptionTextArea("RlineChartText", 5, 5);
       		
       		
       		/**
       		 * creates the default PieChart
       		 */
       		pieChartLabel = new ChartPanel(BHChartFactory.getPieChart("TestPieChart", "XAxis", "YAxis", "PieChart"));
       		pieChartLabel.setFont(UIManager.getFont("defaultFont"));
       		
       		
       		pieChartTextArea = new BHDescriptionTextArea("RpieChartText", 5, 5);
       		
       		/**
       		 * creates BarChart
       		 */
       		
       		//barChartLabel = new ChartPanel(BHChartFactory.getBarChart(IShareholderValueCalculator.SHAREHOLDER_VALUE, "XAxis", "YAxis", "FTEShareholderValue"));
       		barChartLabel.setFont(UIManager.getFont("defaultFont"));
       		
       		
       		/**
       		 * creates the Value- and DescriptionLabels
       		 */
       		//FIXME können nicht plugin Übergreifend auf Konstanten zugreifen! Muss über String gleichheit passieren!
       		// 				Ansprechpartner Sebastian
       		//All Labels to FTE
//       		FTEshareholderValue = new BHValueLabel(IShareholderValueCalculator.SHAREHOLDER_VALUE);
//       		FTEshareholderValueDESC = new BHDescriptionLabel(IShareholderValueCalculator.SHAREHOLDER_VALUE);
//       		FTEdebtAmortisation = new BHValueLabel(FTECalculator.Result.DEBT_AMORTISATION);
//       		FTEdebtAmortisationDESC = new BHDescriptionLabel(FTECalculator.Result.DEBT_AMORTISATION);
//       		FTEequityReturnRate = new BHValueLabel(FTECalculator.Result.EQUITY_RETURN_RATE_FTE);
//       		FTEequityReturnRateDESC = new BHDescriptionLabel(FTECalculator.Result.EQUITY_RETURN_RATE_FTE);
//       		FTEflowEquity = new BHValueLabel(FTECalculator.Result.FLOW_TO_EQUITY);
//       		FTEflowEquityDESC = new BHDescriptionLabel(FTECalculator.Result.FLOW_TO_EQUITY);
//       		FTEflowEquityTaxShield = new BHValueLabel(FTECalculator.Result.FLOW_TO_EQUITY_TAX_SHIELD);
//       		FTEflowEquityTaxShieldDESC = new BHDescriptionLabel(FTECalculator.Result.FLOW_TO_EQUITY_TAX_SHIELD);
//       		FTEflowToEquity = new BHValueLabel(FTECalculator.Result.FLOW_TO_EQUITY_INTEREST);
//       		FTEflowToEquityDESC = new BHDescriptionLabel(FTECalculator.Result.FLOW_TO_EQUITY_INTEREST);
//       		FTEpresentValueTaxShield = new BHValueLabel(FTECalculator.Result.PRESENT_VALUE_TAX_SHIELD);
//       		FTEpresentValueTaxShieldDESC = new BHDescriptionLabel(FTECalculator.Result.PRESENT_VALUE_TAX_SHIELD);
//       		
//       		//All Labels to FCF
//       		FCFshareholderValue = new BHValueLabel(IShareholderValueCalculator.SHAREHOLDER_VALUE);
//       		FCFshareholderValueDESC = new BHDescriptionLabel(IShareholderValueCalculator.SHAREHOLDER_VALUE);
//       		FCFpresentValue = new BHValueLabel(FCFCalculator.Result.PRESENT_VALUE_FCF);
//       		FCFpresentValueDESC = new BHDescriptionLabel(FCFCalculator.Result.PRESENT_VALUE_FCF);
//       		FCFdebtToEquityRatio = new BHValueLabel(FCFCalculator.Result.DEBT_TO_EQUITY_RATIO);
//       		FCFdebtToEquityRatioDESC = new BHDescriptionLabel(FCFCalculator.Result.DEBT_TO_EQUITY_RATIO);
//       		FCFequityReturnRate = new BHValueLabel(FCFCalculator.Result.EQUITY_RETURN_RATE_FCF);
//       		FCFequityReturnRateDESC = new BHDescriptionLabel(FCFCalculator.Result.EQUITY_RETURN_RATE_FCF);
//       		FCFwacc = new BHValueLabel(FCFCalculator.Result.WACC);
//       		FCFwaccDESC = new BHDescriptionLabel(FCFCalculator.Result.WACC);
//       		FCFwaccDebts = new BHValueLabel(FCFCalculator.Result.WACC_DEBTS);
//       		FCFwaccDebtsDESC = new BHDescriptionLabel(FCFCalculator.Result.WACC_DEBTS);
//       		FCFwaccEquity = new BHValueLabel(FCFCalculator.Result.WACC_EQUITY);
//       		FCFwaccEquityDESC = new BHDescriptionLabel(FCFCalculator.Result.WACC_EQUITY);
//       		
//       		//All Labels to APV
//       		APVpresentValue = new BHValueLabel(APVCalculator.Result.PRESENT_VALUE_FCF);
//       		APVpresentValueDESC = new BHDescriptionLabel(APVCalculator.Result.PRESENT_VALUE_FCF);
//       		APVshareholderValue = new BHValueLabel(IShareholderValueCalculator.SHAREHOLDER_VALUE);
//       		APVshareholderValueDESC = new BHDescriptionLabel(IShareholderValueCalculator.SHAREHOLDER_VALUE);
//       		APVpresentValueTaxShield = new BHValueLabel(APVCalculator.Result.PRESENT_VALUE_TAX_SHIELD);
//       		APVpresentValueTaxShieldDESC = new BHDescriptionLabel(APVCalculator.Result.PRESENT_VALUE_TAX_SHIELD);
       		
       		//Formeldarstellung
       		
       		
       		/**
       		 * add Content to ResultPanel
       		 */
       		this.add(FTEshareholderValueDESC);
       		this.add(FTEshareholderValue);
       		this.add(lineChartLabel);
       		
       		this.add(FTEdebtAmortisationDESC);
       		this.add(FTEdebtAmortisation);
       		this.add(pieChartLabel);
       		
       		this.add(FTEequityReturnRateDESC);
       		this.add(FTEequityReturnRate);
       		this.add(barChartLabel);
       		
       		//this.add(ResultFormulaParser.getAPVformula());
       		
       		this.add(FTEflowEquityDESC);
       		this.add(FTEflowEquity);
       		//this.add(barChartLabel);
       		
       		this.add(FTEflowEquityTaxShieldDESC);
       		this.add(FTEflowEquityTaxShield);
       		//this.add(lineChartLabel);
       		
       		this.add(FTEflowToEquityDESC);
       		this.add(FTEflowToEquity);
       		//this.add(lineChartLabel);
       		
       		this.add(FTEpresentValueTaxShieldDESC);
       		this.add(FTEpresentValueTaxShield);
       		//this.add(lineChartLabel);
       	
       		this.add(FCFpresentValueDESC);
       		this.add(FCFpresentValue);
       		//this.add(lineChartLabel);
       		
       		this.add(FCFdebtToEquityRatioDESC);
       		this.add(FCFdebtToEquityRatio);
       		//this.add(lineChartLabel);
       		
       		this.add(FCFequityReturnRateDESC);
       		this.add(FCFequityReturnRate);
       		//this.add(lineChartLabel);
       		
       		this.add(FCFshareholderValueDESC);
       		this.add(FCFshareholderValue);
       		//this.add(lineChartLabel);
       		
       		this.add(FCFwaccDESC);
       		this.add(FCFwacc);
       		//this.add(lineChartLabel);
       		
       		this.add(FCFwaccDebtsDESC);
       		this.add(FCFwaccDebts);
       		//this.add(lineChartLabel);
       		
       		this.add(FCFwaccEquityDESC);
       		this.add(FCFwaccEquity);
       		//this.add(lineChartLabel);
       		
       		this.add(APVpresentValueDESC);
       		this.add(APVpresentValue);
       		//this.add(lineChartLabel);
       		
       		this.add(APVpresentValueTaxShieldDESC);
       		this.add(APVpresentValueTaxShield);
       		//this.add(lineChartLabel);
       		
       		this.add(APVshareholderValueDESC);
       		this.add(APVshareholderValue);
       		//this.add(lineChartLabel);
       		
	}
	
	/**
     * Test main method.
	 * @throws ViewException 
     */
    public static void main(String args[]) throws ViewException {

	JFrame test = new JFrame("Test for ResultPanel");
	test.setContentPane(new BHResultPanel());
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