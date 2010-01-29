/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui.chart;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import org.bh.gui.IBHComponent;
import org.bh.gui.swing.misc.Icons;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import com.jgoodies.forms.layout.FormLayout;

/**
 * define and deliver central semantic information and access to a chart
 * instance type BHChart
 * 
 * @see JFreeChart
 * @see BHChart
 * @author Marco Hammel
 * @version 1.0
 */
public class BHChartPanel extends JPanel implements IBHComponent, IBHAddValue,
		IBHAddGroupValue {
	private static final long serialVersionUID = -8018664370176080809L;

	private String key;
	private String inputHint;
	final ITranslator translator = Services.getTranslator();
	/**
	 * type defined reference to the current semantic repräsentation chart
	 */
	private Class<? extends IBHAddValue> chartClass;
	/**
	 * reference tio the semantic repräsentation of the chart
	 */
	private IBHAddValue chartInstance;
	/**
	 * refernce to the current JFreeChart instance which is painted in the
	 * chartpanel
	 */
	private JFreeChart chart;

	public BHChartPanel(Object key, JFreeChart chart,
			Class<? extends IBHAddValue> chartClass, IBHAddValue chartInstance) {
		this.key = key.toString();
		this.chartClass = chartClass;
		this.chartInstance = chartInstance;
		this.setLayout(new FormLayout("pref,pref,4px", "p"));
		this.add(new ChartPanel(chart), "1,1");
		JTextArea description = new JTextArea(1, 20);
		description.setText(Services.getTranslator().translate(
				key + BHChart.DESC));
		description.setEditable(false);
		description.setVisible(false);
		description.setAutoscrolls(true);
		description.setBorder(BorderFactory.createLoweredBevelBorder());
		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		JPanel panel = new JPanel(new FormLayout("pref", "p,p"));

		JToggleButton info = new JToggleButton();
		info.setIcon(Icons.INFO_ICON);
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton button = (JToggleButton) e.getSource();
				JTextArea ta = new JTextArea();
				for (Component comp : button.getParent().getComponents()) {
					if (comp instanceof JTextArea)
						ta = (JTextArea) comp;
				}
				if (button.isSelected()) {
					ta.setVisible(true);
				} else {
					ta.setVisible(false);
				}
			}
		});
		panel.add(info, "1,1,left,top");
		panel.add(description, "1,2");

		this.add(panel, "2,1,left,top");
		this.chart = chart;
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(), translator
				.translate(this.key)));
	}

	/**
	 * get the semantic object for the chart by changing dataset at runtime
	 * 
	 * @return
	 */
	public Class<? extends IBHAddValue> getChartClass() {
		return this.chartClass;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public IBHAddValue getChartInstance() {
		return this.chartInstance;
	}

	public String getHint() {
		return this.inputHint;
	}

	public String getKey() {
		return this.key;
	}

	protected void reloadText() {
		// inputHint = Services.getTranslator().translate(key,
		// ITranslator.LONG);
		// setToolTipText(inputHint);
	}

	public void addSeries(Comparable<String> key, double[] values, int bins,
			double minimum, double maximum) {
		this.chartInstance.addSeries(key, values, bins, minimum, maximum);
	}

	public void addSeries(Comparable<String> seriesKey, double[][] data,
			Integer amountOfValues, Integer average) {
		this.chartInstance.addSeries(key, data, amountOfValues, average);
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

	public void setDefaultGroupSettings(String[] categories) {
		((IBHAddGroupValue) this.chartInstance)
				.setDefaultGroupSettings(categories);
	}

	public void addValue(Number value, Comparable row,
			Comparable<String> columnKey, int catIdx) {
		((IBHAddGroupValue) this.chartInstance).addValue(value, row, columnKey,
				catIdx);
	}

	@Override
	public void addSeries(Comparable<String> seriesKey, double[][] data) {
	    this.chartInstance.addSeries(seriesKey, data);
	}

	@Override
	public void removeSeries(int number) {
	    this.chartInstance.removeSeries(number);
	}

}
