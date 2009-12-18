package org.bh.gui.chart;

import java.awt.Component;

import javax.swing.UIManager;

import org.bh.gui.swing.IBHComponent;
import org.bh.platform.i18n.BHTranslator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
/**
 * 
 * BHLineChart to create the LineChart 
 *
 * <p>
 * LineChart is created and modified
 *
 * @author Lars
 * @version 0.1, 16.12.2009
 *
 */
public class BHLineChart extends JFreeChart implements IBHComponent{
    BHTranslator translator = BHTranslator.getInstance();
    
    private String key;
    private JFreeChart chart;

    protected BHLineChart(String title, String XAxis, String YAxis, CategoryDataset dataset, Plot plot, String key){
    	super(plot);
    	this.key = key;
    	chart = ChartFactory.createLineChart(title, XAxis, YAxis, dataset, PlotOrientation.VERTICAL, true, true, false); 
    	plot.setNoDataMessage(translator.translate("noDataAvailable"));
    	if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
    		chart.setBackgroundPaint(UIManager.getColor("desktop"));   
    	}
    }
    
    /**
     * returns the created Chart of the <code>BHLineChart</code>
     * 
     * @return JFreeChart LineChart
     */
    
    public JFreeChart getChart(){
    	return chart;
    }

   
	@Override
	public Component add(Component comp) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	/**
	 * Returns the unique ID of the <code>BHLineChart</code>.
	 * 
	 * @return id unique identifier.
	 */
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public int[] getValidateRules() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public boolean isTypeValid() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void setValidateRules(int[] validateRules) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
 
}
