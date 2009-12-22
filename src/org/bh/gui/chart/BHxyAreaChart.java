package org.bh.gui.chart;

import java.awt.Component;
import java.util.List;

import javax.swing.UIManager;

import org.bh.data.types.IValue;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.i18n.BHTranslator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.DefaultXYDataset;

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
public class BHxyAreaChart extends JFreeChart implements IBHComponent, IBHAddValue{
	BHTranslator translator = BHTranslator.getInstance();
	private String key;
	private JFreeChart chart;
	private DefaultXYDataset dataset;
	
	
	protected BHxyAreaChart(String title, String xAxis, String yAxis, Dataset dataset, String key, XYPlot plot) {
		super(plot);
		this.key = key;
		this.dataset = (DefaultXYDataset)dataset;
		
		chart = ChartFactory.createXYAreaChart(title, xAxis, yAxis, this.dataset, PlotOrientation.VERTICAL, true, true, false);
    	plot.setNoDataMessage(translator.translate("noDataAvailable"));
    	if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
    		chart.setBackgroundPaint(UIManager.getColor("desktop"));   
    	}
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
	public String getKey() {
		return key;
	}
	/**
	 * method to add a series into an empty dataset
	 */
	@Override
	public void addSeries(Comparable<String> seriesKey, double[][] data) {
		this.dataset.addSeries(seriesKey, data);
		fireChartChanged();
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
        public IValue getValue() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        @Override
        public  void setValue(IValue value){
            throw new UnsupportedOperationException("Not supported yet.");
        }

		@Override
		public void addSeries(Comparable<String> key, double[] values,
				int bins, double minimum, double maximum) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("This method has not been implemented");
		}

		@Override
		public void addValue(Number value, Comparable<String> columnKey) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("This method has not been implemented");
		}

		@Override
		public void addValue(Number value, int rowKey,
				Comparable<String> columnKey) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("This method has not been implemented");
		}

		@Override
		public void addValues(List<?> list) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("This method has not been implemented");
		}

		


}
