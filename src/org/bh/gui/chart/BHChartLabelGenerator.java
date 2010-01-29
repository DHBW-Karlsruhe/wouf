package org.bh.gui.chart;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;
/**
 * define a LabelGernerator for BHCharts
 * @see BHChart
 * @author Marco Hammel
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BHChartLabelGenerator extends AbstractCategoryItemLabelGenerator
									implements CategoryItemLabelGenerator{
	
	public BHChartLabelGenerator(){
		super("", NumberFormat.getInstance());
	}
	
	@Override
	public String generateLabel(CategoryDataset dataset, int row, int column) {
		String result = null;
		
		Number value = dataset.getValue(row, column);
		DecimalFormat number = new DecimalFormat(",##0.00");
		
		if (value != null){
			result = number.format(value);
		}
		return result;
	}
	
}
