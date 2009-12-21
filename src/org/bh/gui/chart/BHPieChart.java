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
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

public class BHPieChart extends JFreeChart implements IBHComponent, IBHAddValue {
	 	BHTranslator translator = BHTranslator.getInstance();
	    
	    private String key;
	    private JFreeChart chart;
	    private DefaultPieDataset dataset;
	    
	    protected BHPieChart(String title, Plot plot, Dataset dataset, String key){
	    	super(plot);
	    	this.key = key;
	    	this.dataset = (DefaultPieDataset) dataset;
	    	
	    	chart = ChartFactory.createPieChart(title, this.dataset, true, true, false);
	    	plot.setNoDataMessage(translator.translate("noDataAvailable"));
	    	if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
	    		chart.setBackgroundPaint(UIManager.getColor("desktop"));   
	    	}
	    }
	    /**
	     * returns a unique Key
	     * @return
	     * 		String key
	     */
	    @Override
		public String getKey() {
			return this.key;
		}

		@Override
		public void addValue(Number value, Comparable<String> columnKey) {
			this.dataset.setValue(columnKey, value);
			fireChartChanged();
		}

		@Override
		public void addValues(List<?> list, Comparable<String> columnKey) {
			for(int i=0; i<list.size()+1; i++){
				this.dataset.setValue(columnKey, (Number)list.get(i));
				fireChartChanged();
			}
		}
		
		@Override
		public Component add(Component comp) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("This method has not been implemented");
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
		public void addValue(Number value, int rowKey,
				Comparable<String> columnKey) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("This method has not been implemented");
		}
		@Override
		public void addSeries(Comparable<String> key, double[] values,
				int bins, double minimum, double maximum) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("This method has not been implemented");
		}
		@Override
		public IValue getValue() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("This method has not been implemented");
		}
		@Override
		public void setValue(IValue value) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("This method has not been implemented");
		}

		


	    
}
