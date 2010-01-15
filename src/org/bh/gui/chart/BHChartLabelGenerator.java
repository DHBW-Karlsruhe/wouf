package org.bh.gui.chart;

import java.text.NumberFormat;

import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;

public class BHChartLabelGenerator extends AbstractCategoryItemLabelGenerator
									implements CategoryItemLabelGenerator{
	
	public BHChartLabelGenerator(){
		super("", NumberFormat.getInstance());
	}

	@Override
	public String generateLabel(CategoryDataset dataset, int row, int column) {
		String result = null;
		Number value = dataset.getValue(row, column);
		if (value != null){
			result = value.toString();
		}
		return result;
	}
}
