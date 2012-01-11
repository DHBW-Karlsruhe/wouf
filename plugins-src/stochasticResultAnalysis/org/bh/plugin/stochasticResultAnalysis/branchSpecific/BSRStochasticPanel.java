/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber * : Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.plugin.stochasticResultAnalysis.branchSpecific;




import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Dictionary;
import java.util.Hashtable;

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
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import com.jgoodies.forms.layout.FormLayout;

// @update 23.12.2010 Timo Klein

@SuppressWarnings("serial")
public class BSRStochasticPanel extends JPanel{
	
	final ITranslator translator = Controller.getTranslator();
	GridBagConstraints d;
	JLabel space;

	 @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(BSRStochasticPanel.class);
	    //Chart
	    private BHChartPanel distributionChart;
	    private BHChartPanel cashflowChart;
	    private BHChartPanel cashflowChartCompare;
	    private BHTable cashTable;
	    
	    private JScrollPane tablePane;
	    private JPanel compare_p;
	    
	    public BSRStochasticPanel(){
	        this.initialize();
	    }

	    public void initialize() {
	        this.setLayout(new GridBagLayout());
			d = new GridBagConstraints();

	        distributionChart = BHChartFactory.getXYBarChart(BHBSRStochasticResultController.ChartKeys.DISTRIBUTION_CHART);
	        cashflowChart = BHChartFactory.getLineChart(BHBSRStochasticResultController.ChartKeys.CASHFLOW_CHART);
	        cashflowChartCompare = BHChartFactory.getLineChart(BHBSRStochasticResultController.ChartKeys.CASHFLOW_CHART_COMPARE);
	        XYPlot plot = cashflowChart.getChart().getXYPlot();
	        IntervalMarker target = new IntervalMarker(0.0, 500.0);
			target.setLabel(Services.getTranslator().translate(BHBSRStochasticResultController.ChartKeys.CASHFLOW_FORECAST));
	        target.setLabelAnchor(RectangleAnchor.BOTTOM);
	        target.setLabelTextAnchor(TextAnchor.BOTTOM_CENTER);
	        target.setLabelFont(new Font("SansSerif", Font.ITALIC, 20));
			target.setPaint(new Color(222, 222, 255, 128));
			plot.addDomainMarker(target, Layer.BACKGROUND);
			
			
			
			XYPlot plot2 = cashflowChartCompare.getChart().getXYPlot();
			plot2.getRenderer().setSeriesPaint(0, Color.red);
			plot2.getRenderer().setSeriesPaint(1, Color.blue);
			
			
	 

	        BHDescriptionLabel sd = new BHDescriptionLabel("standardDeviation");
	        BHDescriptionLabel ew = new BHDescriptionLabel((BHBSRStochasticResultController.PanelKeys.AVERAGE));
	        BHValueLabel sdValue = new BHValueLabel((BHBSRStochasticResultController.ChartKeys.STANDARD_DEVIATION));
	        BHValueLabel ewValue = new BHValueLabel((BHBSRStochasticResultController.ChartKeys.AVERAGE).toString());
	        
	        BHDescriptionLabel riskAt = new BHDescriptionLabel(BHBSRStochasticResultController.PanelKeys.VALUE);
	        
	        BHSlider slider = new BHSlider(BHBSRStochasticResultController.ChartKeys.RISK_AT_VALUE, 0, 100, 95);
//	        BHSlider sliderRatio = new BHSlider(BHBSRStochasticResultController.ChartKeys.BSR_RATIO, 0, 100, 95); //new BN
	        BHSlider slider_cashflow = new BHSlider(BHBSRStochasticResultController.ChartKeys.CASHFLOW_COMPARE_SLIDER);
	        slider_cashflow.setMinimum(3);
	        slider_cashflow.setValue(3);
	        slider_cashflow.setMajorTickSpacing(2);
	        slider_cashflow.setMinorTickSpacing(1);
	        slider_cashflow.setPaintTicks( true );
	        slider_cashflow.setPaintLabels(true);
	        //BHTextField riskAtField = new BHTextField(BHStochasticResultController.ChartKeys.RISK_AT_VALUE,"95", true);
	        
//	        cashflowChart.add (sliderRatio, BorderLayout.SOUTH);
	        
	        BHDescriptionLabel min = new BHDescriptionLabel("min");
	        BHDescriptionLabel max = new BHDescriptionLabel("max");
	        BHDescriptionLabel choose_p = new BHDescriptionLabel(BHBSRStochasticResultController.PanelKeys.COMPARE_P);
	        BHValueLabel minValue = new BHValueLabel(BHBSRStochasticResultController.ChartKeys.RISK_AT_VALUE_MIN);
	        BHValueLabel maxValue = new BHValueLabel(BHBSRStochasticResultController.ChartKeys.RISK_AT_VALUE_MAX);
	        
	        
	        JPanel rav = new JPanel();
	        rav.setLayout(new FormLayout ("4px:grow,right:pref,10px,pref,4px,pref,4px:grow","4px,p,4px,p,4px,p,4px,p,4px,p,4px"));
	        rav.add(riskAt, "2,2");
	        //riskAtField.setPreferredSize(new Dimension(50,riskAtField.getPreferredSize().height));
                slider.setPreferredSize(new Dimension(200,slider.getPreferredSize().height));
//                sliderRatio.setPreferredSize(new Dimension(200,sliderRatio.getPreferredSize().height));
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
					BHBSRStochasticResultController.ChartKeys.RISK_AT_VALUE, TitledBorder.LEFT,
					TitledBorder.DEFAULT_JUSTIFICATION));

	        compare_p = new JPanel();
	        compare_p.setLayout(new GridBagLayout());
	        compare_p.setBorder(BHBorderFactory.getInstacnce().createTitledBorder(BHBorderFactory.getInstacnce()
					.createEtchedBorder(EtchedBorder.LOWERED),
					BHBSRStochasticResultController.ChartKeys.COMPARE_P_HEAD, TitledBorder.LEFT,
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
	    	
	    	cashTable = new BHTable(BHBSRStochasticResultController.PanelKeys.CASHFLOW_TABLE){
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
			d.insets = new Insets(30,10,0,0);
			tablePane.setPreferredSize(new Dimension(456,250));
			this.add(tablePane,d);
		    
		    d.fill = GridBagConstraints.HORIZONTAL;
			d.gridx = 0;
			d.gridy = 5;
			d.insets = new Insets(30,10,0,0);
		    this.add(cashflowChartCompare, d);
		    
		    d.fill = GridBagConstraints.HORIZONTAL;
			d.gridx = 0;
			d.gridy = 6;
			d.insets = new Insets(30,10,30,0);
		    this.add(compare_p, d);
		    
		   
	    }
	}
