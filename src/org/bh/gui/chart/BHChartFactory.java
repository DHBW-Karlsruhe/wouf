package org.bh.gui.chart;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.CategoryDataset;



public class BHChartFactory{
    
    public static JFreeChart getLineChart(String title, String XAxis, String YAxis, CategoryDataset dataset, Plot plot, int ID){
	
	BHLineChart chart = new BHLineChart(title, XAxis, YAxis, dataset, plot, ID);
	return chart.getChart();
	
    }
}