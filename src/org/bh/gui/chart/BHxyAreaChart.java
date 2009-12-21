package org.bh.gui.chart;

import java.awt.Component;

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
public class BHxyAreaChart extends JFreeChart implements IBHComponent{
	BHTranslator translator = BHTranslator.getInstance();
	private String key;
	private JFreeChart chart;
	
	
	protected BHxyAreaChart(String title, String xAxis, String yAxis, Dataset dataset, String key, XYPlot plot) {
		super(plot);
		this.key = key;
		
		chart = ChartFactory.createXYAreaChart(title, xAxis, yAxis, (DefaultXYDataset)dataset, PlotOrientation.VERTICAL, true, true, false);
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


}
