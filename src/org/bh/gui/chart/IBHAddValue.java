package org.bh.gui.chart;

import java.util.List;

public interface IBHAddValue {
	/**
	 * method to add a list of values into an empty dataset
	 * 
	 * @param list
	 *            List<?> list of values
	 */
	void addValues(List<?> list);

	/**
	 * method to add a single value into an empty dataset
	 * 
	 * @param value
	 *            Number value to add
	 * @param columnKey
	 *            Comparable<String> columnKey to identify the column
	 */
	void addValue(Number value, Comparable<String> columnKey);

	/**
	 * method to add a single value into an empty dataset
	 * 
	 * @param value
	 *            Number value to add
	 * @param rowKey
	 *            int rowKey to identify the row
	 * @param columnKey
	 *            Comparable<String> columnKey to identify the column
	 */
	void addValue(Number value, Comparable rowKey, Comparable<String> columnKey);

	/**
	 * method to add a series into an empty dataset
	 * 
	 * @param key
	 *            String key to identify the series
	 * @param values
	 *            double[] values to add
	 * @param bins
	 *            int bins
	 * @param minimum
	 *            double minimum of bins
	 * @param maximum
	 *            double maximum of bins
	 */
	void addSeries(Comparable<String> key, double[] values, int bins,
			double minimum, double maximum);

	/**
	 * method to add a series into an empty dataset
	 * 
	 * @param seriesKey
	 *            String key to identify the series
	 * @param data
	 *            double[][] Array with values to add
	 */
	void addSeries(Comparable<String> seriesKey, double[][] data);

}
