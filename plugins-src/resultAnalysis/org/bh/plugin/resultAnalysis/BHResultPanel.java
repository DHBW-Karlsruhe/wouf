package org.bh.plugin.resultAnalysis;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.gui.ViewException;
import org.bh.gui.chart.BHChartPanel;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHDataExchangeDialog;
import org.bh.gui.swing.BHDescriptionTextArea;
import org.bh.platform.IImportExport;
import org.bh.platform.IPrint;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import com.jgoodies.forms.layout.CellConstraints;

/**
 * 
 * Class to create one ResultPanel with two different Charts and Descriptions
 * 
 * <p>
 * This class creates a <code>JPanel</code> with the Results. There are two
 * different charts and the descriptions.
 * 
 * @author Lars.Zuckschwerdt
 * @version 1.0, 30.12.2009
 * 
 * @author Norman
 * @version 1.1, 10.01.2010
 * 
 * @author Marco Hammel
 * @version 1.2 11.01.2010
 */
public final class BHResultPanel extends JPanel {

	static final Logger log = Logger.getLogger(BHResultPanel.class);
	private final BHResultPanel me = this;
	private JPanel panel;
	private ChartPanel lineChartLabel;
	private ChartPanel pieChartLabel;
	private ChartPanel barChartLabel;
	private BHDescriptionTextArea pieChartTextArea;
	// TODO check Marco, Lars: Really necessary for every procedure?
	private CellConstraints cons;
	// formulas
	private Component finiteFormula;
	private Component infiniteFormula;
	// export button
	private BHButton exportButton;
	// probably not necessary in a later version
	JPanel procedurePanel = null;
	// print Button
	private BHButton printButton;
	final DTOScenario scenario;
	final Map<String, Calculable[]> result;
        private final Map<String, Calculable> formulaValues;
	final ITranslator translator = Services.getTranslator();

	/**
	 * Constructor
	 * 
	 * @throws ViewException
	 */
	public BHResultPanel(DTOScenario scenario, Map<String, Calculable[]> result, Map<String, Calculable> formulaValues) {
		this.scenario = scenario;
		this.result = result;
                this.formulaValues = formulaValues;
		initialize();
	}

	/**
	 * Initialize method
	 * 
	 * @throws ViewException
	 */
	public void initialize() {

		// double border = 10;
		// double size[][] = {{border, TableLayout.PREFERRED, border,
		// TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}, //
		// Columns
		// {border, TableLayout.PREFERRED, border, TableLayout.PREFERRED,
		// border, TableLayout.PREFERRED, border, TableLayout.PREFERRED,
		// border}}; // Rows
		//
		//
		// this.setLayout(new TableLayout(size));
		this.setLayout(new BorderLayout());

		// //this.setMaximumSize(BHMainFrame.chartsPanel.getMaximumSize());
		// /*
		// * Creates the default LineChart and add it on a Label
		// */
		// lineChartLabel = BHChartFactory.getLineChart("TestChart", "XAxis",
		// "YAxis", "LineChart");
		// lineChartLabel.setFont(UIManager.getFont("defaultFont"));
		//
		// /*
		// * Creates the description and add it on a Label
		// */
		// //lineChartTextArea = new BHDescriptionTextArea("RlineChartText", 5,
		// 5);
		//
		//
		// /*
		// * creates the default PieChart
		// */
		// pieChartLabel = BHChartFactory.getPieChart("TestPieChart", "XAxis",
		// "YAxis", "PieChart");
		// pieChartLabel.setFont(UIManager.getFont("defaultFont"));
		//
		//
		// pieChartTextArea = new BHDescriptionTextArea("RpieChartText", 5, 5);

		/*
		 * creates BarChart
		 */

		// barChartLabel = new
		// ChartPanel(BHChartFactory.getBarChart(IShareholderValueCalculator.SHAREHOLDER_VALUE,
		// "XAxis", "YAxis", "FTEShareholderValue"));
		// barChartLabel.setFont(UIManager.getFont("defaultFont"));

		/*
		 * creates the Value- and DescriptionLabels
		 */

		// exportButton
		exportButton = new BHButton("EXPORTSCENARIO");
		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				BHDataExchangeDialog dialog = new BHDataExchangeDialog(null,
						true);
				dialog.setAction(IImportExport.EXP_SCENARIO_RES);
				dialog.setModel(scenario);
				dialog.setResults(result);
				
				try {
					List<Image> icons = new ArrayList<Image>();
					icons.add(ImageIO.read(getClass().getResourceAsStream("/org/bh/images/BH-Logo-16px.png")));
					icons.add(ImageIO.read(getClass().getResourceAsStream("/org/bh/images/BH-Logo-32px.png")));
					icons.add(ImageIO.read(getClass().getResourceAsStream("/org/bh/images/BH-Logo-48px.png")));
					dialog.setIconImages(icons);
				} catch (Exception eI) {
					log.error("Failed to load IconImage", eI);
				}
				
				List<JFreeChart> charts = new ArrayList<JFreeChart>();
				for(Component c : procedurePanel.getComponents()) {
					if(c instanceof BHChartPanel) {
						BHChartPanel cp = (BHChartPanel) c;
						charts.add(cp.getChart());
					}
				}
				dialog.setCharts(charts);
				
				dialog.setVisible(true);

			}
		});

		// printButton
		printButton = new BHButton("PRINTSCENARIO");
		printButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String, IPrint> pPlugs = Services.getPrintPlugins(IPrint.PRINT_SCENARIO_RES);
				
				List<JFreeChart> charts = new ArrayList<JFreeChart>();
				for(Component c : procedurePanel.getComponents()) {
					if(c instanceof BHChartPanel) {
						BHChartPanel cp = (BHChartPanel) c;
						charts.add(cp.getChart());
					}
				}
				((IPrint) pPlugs.values().toArray()[0]).printScenarioResults(scenario, result, charts);
				//File temp;
				
//				int requiredMethods = IImportExport.BATCH_EXPORT
//						+ IImportExport.EXP_SCENARIO_RES;
//				Map<String, IImportExport> expPlugs = Services
//						.getImportExportPlugins(requiredMethods);
//				IImportExport exp = expPlugs.get("pdf");
//				if (exp == null) {
//					if (expPlugs.values().size() == 0) {
//						PlatformUserDialog.getInstance().showErrorDialog(
//								translator.translate("PRINTNOTPOSSIBLE"), " ");
//					} else {
//						exp = (IImportExport) expPlugs.values().toArray()[0];
//					}
//				} else {
//					try {
//						temp = File.createTempFile("bh_scneario_print", "." + exp.getFileExtension());
//						exp.exportScenarioResults(scenario, result, temp
//								.getAbsolutePath());
//						Desktop.getDesktop().print(temp);
//						temp.deleteOnExit();
//					} catch (IOException e1) {
//						log.debug(e1);
//					}
//				}
			}
		});

		/*
		 * add Content to ResultPanel
		 */
		// this.add(FTEshareholderValueDESC, "1,1");
		// this.add(FTEshareholderValue, "2,1");
		// this.add(lineChartLabel, "3,1");
		//       		
		// this.add(FTEdebtAmortisationDESC, "1,2");
		// this.add(FTEdebtAmortisation, "2,2");
		// this.add(pieChartLabel, "3,2");
		//       		
		// this.add(FTEequityReturnRateDESC, BorderLayout.WEST);
		// this.add(FTEequityReturnRate, BorderLayout.CENTER);
		// //this.add(barChartLabel, BorderLayout.EAST);
		//       		
		// //this.add(ResultFormulaParser.getAPVformula());
		//       		
		// this.add(FTEflowEquityDESC, BorderLayout.WEST);
		// this.add(FTEflowEquity, BorderLayout.CENTER);
		// //this.add(barChartLabel, BorderLayout.EAST);
		//       		
		// this.add(FTEflowEquityTaxShieldDESC, BorderLayout.WEST);
		// this.add(FTEflowEquityTaxShield, BorderLayout.CENTER);
		// //this.add(lineChartLabel, BorderLayout.EAST);
		//       		
		// this.add(FTEflowToEquityDESC, BorderLayout.WEST);
		// this.add(FTEflowToEquity, BorderLayout.CENTER);
		// //this.add(lineChartLabel, BorderLayout.EAST);
		//       		
		// this.add(FTEpresentValueTaxShieldDESC, BorderLayout.WEST);
		// this.add(FTEpresentValueTaxShield, BorderLayout.CENTER);
		// //this.add(lineChartLabel, BorderLayout.EAST);
		//       	
		// this.add(FCFpresentValueDESC, BorderLayout.WEST);
		// this.add(FCFpresentValue, BorderLayout.CENTER);
		// //this.add(lineChartLabel, BorderLayout.EAST);
		//       		
		// this.add(FCFdebtToEquityRatioDESC, BorderLayout.WEST);
		// this.add(FCFdebtToEquityRatio, BorderLayout.CENTER);
		// //this.add(lineChartLabel, BorderLayout.EAST);
		//       		
		// this.add(FCFequityReturnRateDESC, BorderLayout.WEST);
		// this.add(FCFequityReturnRate, BorderLayout.CENTER);
		// //this.add(lineChartLabel, BorderLayout.EAST);
		//       		
		// this.add(FCFshareholderValueDESC, BorderLayout.WEST);
		// this.add(FCFshareholderValue, BorderLayout.CENTER);
		// //this.add(lineChartLabel, BorderLayout.EAST);
		//       		
		// this.add(FCFwaccDESC, BorderLayout.WEST);
		// this.add(FCFwacc, BorderLayout.CENTER);
		// //this.add(lineChartLabel, BorderLayout.EAST);
		//       		
		// this.add(FCFwaccDebtsDESC, BorderLayout.WEST);
		// this.add(FCFwaccDebts, BorderLayout.CENTER);
		// //this.add(lineChartLabel, BorderLayout.EAST);
		//       		
		// this.add(FCFwaccEquityDESC, BorderLayout.WEST);
		// this.add(FCFwaccEquity, BorderLayout.CENTER);
		// //this.add(lineChartLabel, BorderLayout.EAST);
		//       		

		if (scenario.getDCFMethod().getUniqueId().equals("fcf")) {
			procedurePanel = new BH_FCF_ResultPanel(false, formulaValues);
		} else if (scenario.getDCFMethod().getUniqueId().equals("apv")) {
			procedurePanel = new BH_APV_ResultPanel(false, formulaValues);
		} else if (scenario.getDCFMethod().getUniqueId().equals("fte")) {
			procedurePanel = new BH_FTE_ResultPanel(false, formulaValues);
		}else if (scenario.getDCFMethod().getUniqueId().equals("all")){
			procedurePanel = new JPanel();
			procedurePanel.setLayout(new BorderLayout());
			procedurePanel.add(new BH_APV_ResultPanel(true,formulaValues), BorderLayout.NORTH);
			procedurePanel.add(new BH_FCF_ResultPanel(true, formulaValues), BorderLayout.CENTER);
			procedurePanel.add(new BH_FTE_ResultPanel(true, formulaValues), BorderLayout.SOUTH);
	    }
		this.add(procedurePanel, BorderLayout.CENTER);
		JPanel buttons = new JPanel();
		
		buttons.add(exportButton, BorderLayout.WEST);
		buttons.add(printButton, BorderLayout.EAST);
		add(buttons, BorderLayout.SOUTH);

	}
	// /**
	// * Test main method.
	// * @throws ViewException
	// */
	// public static void main(String args[]) throws ViewException {
	//
	// JFrame test = new JFrame("Test for ResultPanel");
	// test.setContentPane(new BHResultPanel());
	// test.addWindowListener(new WindowAdapter() {
	//	    
	// @Override
	// public void windowClosing(WindowEvent e) {
	// System.exit(0);
	// }
	// });
	// //test.pack();
	// test.setVisible(true);
	// }
}
