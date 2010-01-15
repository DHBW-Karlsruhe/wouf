/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui.chart;

import java.util.List;

import org.bh.gui.swing.IBHComponent;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Marco Hammel
 */
public class BHChartPanel extends ChartPanel implements IBHComponent, IBHAddValue{
    private static final long serialVersionUID = -8018664370176080809L;

    private String key;
    private String inputHint;
    private Class<? extends IBHAddValue> chartClass;
    private IBHAddValue chartInstance;

    public BHChartPanel(Object key, JFreeChart chart, Class<? extends IBHAddValue> chartClass, IBHAddValue chartInstance){
        super(chart);
        this.key = key.toString();
        this.chartClass = chartClass;
        this.chartInstance = chartInstance;
    }

    /**
     * get the semantic object for the chart by changing dataset at runtime
     * @return
     */
    public Class<? extends IBHAddValue> getChartClass(){
        return this.chartClass;
    }

    public IBHAddValue getChartInstance(){
        return this.chartInstance;
    }

    public String getHint() {
        return this.inputHint;
    }

    public String getKey() {
        return this.key;
    }

    protected void reloadText() {
//	inputHint = Services.getTranslator().translate(key, ITranslator.LONG);
//	setToolTipText(inputHint);
    }

    public void addSeries(Comparable<String> key, double[] values, int bins, double minimum, double maximum) {
        this.chartInstance.addSeries(key, values, bins, minimum, maximum);
    }

    public void addSeries(Comparable<String> seriesKey, double[][] data) {
        this.chartInstance.addSeries(key, data);
    }

    public void addValue(Number value, Comparable<String> columnKey) {
        this.chartInstance.addValue(value, columnKey);
    }

    public void addValues(List<?> list) {
        this.chartInstance.addValues(list);
    }

	@Override
	public void addValue(Number value, Comparable rowKey,
			Comparable<String> columnKey) {
		 this.chartInstance.addValue(value, rowKey, columnKey);
	}




}
