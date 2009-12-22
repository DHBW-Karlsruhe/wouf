package org.bh.gui.chart;

import java.awt.Component;
import java.util.Iterator;
import java.util.List;

import javax.swing.UIManager;

import org.bh.data.types.IValue;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.i18n.BHTranslator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
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
public class BHLineChart extends JFreeChart implements IBHComponent, IBHAddValue{
    BHTranslator translator = BHTranslator.getInstance();
    
    private String key;
    private JFreeChart chart;
    private DefaultCategoryDataset dataset;

    protected BHLineChart(String title, String XAxis, String YAxis, Dataset dataset, Plot plot, String key){
    	super(plot);
    	this.key = key;
    	this.dataset = (DefaultCategoryDataset) dataset;
    	
    	chart = ChartFactory.createLineChart(title, XAxis, YAxis, this.dataset, PlotOrientation.VERTICAL, true, true, false); 
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
	public void addValue(Number value, int row, Comparable<String> columnKey) {
		
		this.dataset.addValue(value, dataset.getRowKey(row), columnKey);
		fireChartChanged();
	}

	@Override
	public void addValues(List<?> list) {
		Iterator<?> it = list.iterator();
		
		for(int i=0; i<list.size();i++){
			for(int j=0; j<=i;j++){
				while(it.hasNext()){
					this.dataset.addValue((Number)list.get(i), i, (String)list.get(j));
					fireChartChanged();
				}
			}
		}
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

	@Override
	public void addValue(Number value, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void addSeries(Comparable<String> key, double[] values, int bins,
			double minimum, double maximum) {
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
