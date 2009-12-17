package org.bh.gui.chart;

import java.awt.Component;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;

/**
 * class to  create BHXYAreaChart 
 *
 * <p>
 * class to create the <code>JFreeChart</code> BHxyAreaChart
 *
 * @author Lars
 * @version 0.1, 17.12.2009
 *
 */
public class BHxyAreaChart extends JFreeChart implements IBHComponent{
	private String key;
	private JFreeChart chart;
	
	
	protected BHxyAreaChart(String title, String xAxis, String yAxis, XYDataset dataset, String key, XYPlot plot) {
		super(plot);
		this.key = key;
		//BHTranslator translator = BHTranslator.getInstance();
		
		chart = ChartFactory.createXYAreaChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, true, true, false);
    	//plot.setNoDataMessage(translator.translate("noDataAvailable"));

	}
	
	/**
	 * method to get the <code>BHxyAreaChart</code>
	 * @return
	 * 		<code>JFreeChart</code> chart
	 */
	public JFreeChart getChart(){
		return chart;
	}
	
	/**
	 * returns unique ID
	 * 
	 */
	@Override
	public String getID() {
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
