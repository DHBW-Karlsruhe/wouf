package org.bh.gui.chart;

import java.awt.Component;
import java.util.List;

import javax.swing.UIManager;

import org.bh.data.types.IValue;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.i18n.BHTranslator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.Dataset;
import org.jfree.data.statistics.HistogramDataset;

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
public class BHHistogramChart extends JFreeChart implements IBHComponent, IBHAddValue{
	BHTranslator translator = BHTranslator.getInstance();
    
	private String key;
	private JFreeChart chart;
	private HistogramDataset dataset;
	
	protected BHHistogramChart(String title, String xAxis, String yAxis, Dataset dataset, String key, Plot plot) {
		super(plot);
		this.key = key;
		this.dataset = (HistogramDataset)dataset;
		
		chart = ChartFactory.createHistogram(title, xAxis, yAxis, this.dataset, PlotOrientation.VERTICAL, true, true, false);
		chart.getXYPlot().setForegroundAlpha(0.75f);
		plot.setNoDataMessage(translator.translate("noDataAvailable"));
		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
    		chart.setBackgroundPaint(UIManager.getColor("desktop"));   
    	}
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
	/**
	 * method to add a series into an empty HistogramDataset
	 */
	@Override
	public void addSeries(Comparable<String> key, double[] values, int bins,
			double minimum, double maximum) {
		this.dataset.addSeries(key, values, bins, minimum, maximum);
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

	@Override
	public void addValue(Number value, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void addValue(Number value, int rowKey, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void addValues(List<?> list) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}	

    public IValue getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public  void setValue(IValue value){
        throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public void addSeries(Comparable<String> seriesKey, double[][] data) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
}
