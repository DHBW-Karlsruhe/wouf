package org.bh.gui.chart;

import java.awt.Component;

import org.bh.gui.swing.IBHComponent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.*;

/**
 * BHHistogramChart to create a Histogramchart 
 *
 * <p>
 * Histogramchart is created and modified
 *
 * @author Lars
 * @version 0.1, 17.12.2009
 *
 */
public class BHHistogramChart extends JFreeChart implements IBHComponent{
	private String key;
	private JFreeChart chart;

	
	protected BHHistogramChart(String title, String xAxis, String yAxis, HistogramDataset dataset, String key, Plot plot) {
		super(plot);
		//BHTranslator translator = BHTranslator.getInstance();
		
		this.key = key;
		
		chart = ChartFactory.createHistogram(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, true, true, false);
		//plot.setNoDataMessage(translator.translate("noDataAvailable"));
		chart.getXYPlot().setForegroundAlpha(0.75f);
        
	}
	
	/**
	 * method to get the <code>JFreeChart</code> BHHistogramChart
	 * 
	 * @return
	 * 		<code>JFreeChart</code> chart
	 */
	public JFreeChart getChart(){
		return chart;
	}
	
	/**
	 * returns key of BHHistogramChart
	 */
	@Override
	public String getKey() {
		return key;
	}

	/* Specified by interface/super class. */
	@Override
	public Component add(Component comp) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	/* Specified by interface/super class. */
	@Override
	public int[] getValidateRules() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	/* Specified by interface/super class. */
	@Override
	public boolean isTypeValid() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	/* Specified by interface/super class. */
	@Override
	public void setValidateRules(int[] validateRules) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
}