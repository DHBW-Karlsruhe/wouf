package org.bh.plugin.stochasticResultAnalysis;




import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.bh.controller.Controller;
import org.bh.data.DTOScenario;
import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.chart.BHChartPanel;
import org.bh.gui.swing.comp.BHCheckBox;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHSlider;
import org.bh.gui.swing.comp.BHTable;
import org.bh.gui.swing.comp.BHValueLabel;
import org.bh.gui.swing.forms.border.BHBorderFactory;
import org.bh.platform.i18n.ITranslator;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import com.jgoodies.forms.layout.FormLayout;

// @update 23.12.2010 Timo Klein

@SuppressWarnings("serial")
public class StochasticPanel extends JPanel{
	
	final ITranslator translator = Controller.getTranslator();
	GridBagConstraints d;
	JLabel space;

	 @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(StochasticPanel.class);
	    //Chart
	    private BHChartPanel distributionChart;
	    private BHChartPanel cashflowChart;
	    private BHChartPanel cashflowChartCompare;
	    private BHTable cashTable;
	    
	    private JScrollPane tablePane;
	    private JPanel compare_p;
	    
	    public StochasticPanel(){
	        this.initialize();
	    }

	    public void initialize() {
	        this.setLayout(new GridBagLayout());
			d = new GridBagConstraints();

	        distributionChart = BHChartFactory.getXYBarChart(BHStochasticResultController.ChartKeys.DISTRIBUTION_CHART);
	        cashflowChart = BHChartFactory.getLineChart(BHStochasticResultController.ChartKeys.CASHFLOW_CHART);
	        cashflowChartCompare = BHChartFactory.getLineChart(BHStochasticResultController.ChartKeys.CASHFLOW_CHART_COMPARE);
	        XYPlot plot = cashflowChart.getChart().getXYPlot();
	        IntervalMarker target = new IntervalMarker(0.0, 500.0);
			target.setLabel("Prognostiziert");
	        target.setLabelAnchor(RectangleAnchor.BOTTOM);
	        target.setLabelTextAnchor(TextAnchor.BOTTOM_CENTER);
	        target.setLabelFont(new Font("SansSerif", Font.ITALIC, 20));
			target.setPaint(new Color(222, 222, 255, 128));
			plot.addDomainMarker(target, Layer.BACKGROUND);
	 

	        BHDescriptionLabel sd = new BHDescriptionLabel("standardDeviation");
	        BHDescriptionLabel ew = new BHDescriptionLabel((BHStochasticResultController.PanelKeys.AVERAGE));
	        BHValueLabel sdValue = new BHValueLabel((BHStochasticResultController.ChartKeys.STANDARD_DEVIATION));
	        BHValueLabel ewValue = new BHValueLabel((BHStochasticResultController.ChartKeys.AVERAGE).toString());
	        
	        BHDescriptionLabel riskAt = new BHDescriptionLabel(BHStochasticResultController.PanelKeys.VALUE);
	        
	        BHSlider slider = new BHSlider(BHStochasticResultController.ChartKeys.RISK_AT_VALUE, 0, 100, 95);
	        BHSlider slider_cashflow = new BHSlider(BHStochasticResultController.ChartKeys.CASHFLOW_COMPARE_SLIDER);
	        slider_cashflow.setMinimum(2);
	        //BHTextField riskAtField = new BHTextField(BHStochasticResultController.ChartKeys.RISK_AT_VALUE,"95", true);
	        
	        BHDescriptionLabel min = new BHDescriptionLabel("min");
	        BHDescriptionLabel max = new BHDescriptionLabel("max");
	        BHDescriptionLabel choose_p = new BHDescriptionLabel(BHStochasticResultController.PanelKeys.COMPARE_P);
	        BHValueLabel minValue = new BHValueLabel(BHStochasticResultController.ChartKeys.RISK_AT_VALUE_MIN);
	        BHValueLabel maxValue = new BHValueLabel(BHStochasticResultController.ChartKeys.RISK_AT_VALUE_MAX);
	        
	        
	        JPanel rav = new JPanel();
	        rav.setLayout(new FormLayout ("4px:grow,right:pref,10px,pref,4px,pref,4px:grow","4px,p,4px,p,4px,p,4px,p,4px,p,4px"));
	        rav.add(riskAt, "2,2");
	        //riskAtField.setPreferredSize(new Dimension(50,riskAtField.getPreferredSize().height));
                slider.setPreferredSize(new Dimension(200,slider.getPreferredSize().height));
//	        ValidationRule[] rules = {VRIsDouble.INSTANCE, VRIsBetween.BETWEEN0AND100};//Validation for value at risk
//                riskAtField.setValidationRules(rules);
	        rav.add(slider, "4,2");
	        rav.add(new JLabel("%"), "6,2");
	        rav.add(min, "2,4");
	        rav.add(minValue, "4,4");
	        rav.add(new BHDescriptionLabel("currency"), "6,4"); //AWussler replaced: 3.12.2010: Now its translatable
	        rav.add(max, "2,6");
	        rav.add(maxValue, "4,6");
	        rav.add(new BHDescriptionLabel("currency"), "6,6"); //AWussler replaced: 3.12.2010: Now its translatable
	        rav.setBorder(BHBorderFactory.getInstacnce().createTitledBorder(BHBorderFactory.getInstacnce()
					.createEtchedBorder(EtchedBorder.LOWERED),
					BHStochasticResultController.ChartKeys.RISK_AT_VALUE, TitledBorder.LEFT,
					TitledBorder.DEFAULT_JUSTIFICATION));

	        compare_p = new JPanel();
	        compare_p.setLayout(new GridBagLayout());
	        compare_p.setBorder(BHBorderFactory.getInstacnce().createTitledBorder(BHBorderFactory.getInstacnce()
					.createEtchedBorder(EtchedBorder.LOWERED),
					BHStochasticResultController.ChartKeys.RISK_AT_VALUE, TitledBorder.LEFT,
					TitledBorder.DEFAULT_JUSTIFICATION));
	        
	        d.gridx = 0;
			d.gridy = 0;
	        compare_p.add(choose_p); 
	        d.gridx = 0;
			d.gridy = 1;
	        compare_p.add(slider_cashflow);
	        
	        
	        d.fill = GridBagConstraints.HORIZONTAL;
			d.gridx = 0;
			d.gridy = 0;
			d.insets = new Insets(30,10,0,0);
	        this.add(distributionChart, d); 
	       // this.add(distributionChart, "1,3,5,3");
	        
	        
	        
	        /**
	         * AWussler added: 3.12.2010
	         * put sd and ew (standardabweichung, erwartungswert) in a panel box:
	         */
	        JPanel p_sd_ew = new JPanel();
	        p_sd_ew.setLayout(new GridLayout(2, 3)); //Gridlayout: 2 rows, 3 columns
	        //add first row:
	        p_sd_ew.add(sd);
	        p_sd_ew.add(sdValue);
	        p_sd_ew.add(new BHDescriptionLabel("currency"));
	        //add second row:
	        p_sd_ew.add(ew);
	        p_sd_ew.add(ewValue);
	        p_sd_ew.add(new BHDescriptionLabel("currency"));
	        p_sd_ew.setBorder(BHBorderFactory.getInstacnce().createEtchedBorder(EtchedBorder.LOWERED));//set untitled Border
	        //end: AWussler added: 3.12.2010
	        
	        /* AWussler removed and replaced: 3.12.2010: instead of adding each Label add the new panel containing all labels:
	        this.add(sd, "1,5");
	        this.add(sdValue, "3,5");
	        this.add(new JLabel("GE"), "5,5");
	        
	        this.add(ew, "1,7");
	        this.add(ewValue, "3,7");
	        this.add(new JLabel("GE"), "5,7");*/
	        d.fill = GridBagConstraints.HORIZONTAL;
			d.gridx = 0;
			d.gridy = 1;
			d.insets = new Insets(10,10,0,0);
	        this.add(p_sd_ew, d);
	       // this.add(p_sd_ew, "1,5,3,7");//first and second number: coordinate (x,y) of upper left corner, first and second number: coordinate (x,y) of under right corner
	        //end: AWussler removed and replaced: 3.12.2010
	        d.fill = GridBagConstraints.HORIZONTAL;
			d.gridx = 0;
			d.gridy = 2;
			d.insets = new Insets(10,10,0,0);
	        this.add(rav,d);
	        
	        space = new JLabel();
	        d.fill = GridBagConstraints.HORIZONTAL;
			d.gridx = 1;
			d.gridy = 0;
			d.weightx = 1.0;
			d.insets = new Insets(0,0,0,0);
			this.add(space,d);
	        //this.add(p_sd_ew, "1,5,3,7");
			
			
			
			
	        
	    }
	    
	    public void addTimeSeries(){
	    	
	    	cashTable = new BHTable(BHStochasticResultController.PanelKeys.CASHFLOW_TABLE){
		        public boolean isCellEditable(int x, int y) {
		            return false;
		        }
		    };
		    
	        tablePane = new JScrollPane(cashTable);
	        JScrollBar scroll = new JScrollBar();
	        tablePane.setVerticalScrollBar(scroll);
	        
	    	d.fill = GridBagConstraints.HORIZONTAL;
			d.gridx = 0;
			d.gridy = 3;
			d.insets = new Insets(30,10,0,0);
			d.weightx = 0;
		    this.add(cashflowChart, d);
		    
		    d.fill = GridBagConstraints.HORIZONTAL;
			d.gridx = 0;
			d.gridy = 4;
			d.insets = new Insets(30,10,30,0);
			this.add(tablePane,d);
		    
		    d.fill = GridBagConstraints.HORIZONTAL;
			d.gridx = 0;
			 System.out.println("Das sollte nicht erscheinen");
			d.gridy = 5;
			d.insets = new Insets(30,10,0,0);
		    this.add(cashflowChartCompare, d);
		    
		    d.fill = GridBagConstraints.HORIZONTAL;
			d.gridx = 0;
			d.gridy = 6;
			d.insets = new Insets(30,10,0,0);
		    this.add(compare_p, d);
		    
		   
	    }
	}
