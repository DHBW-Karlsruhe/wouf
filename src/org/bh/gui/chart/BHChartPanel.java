/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
 *
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
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
import org.bh.gui.swing.forms.border.BHBorderFactory;
import org.bh.gui.swing.misc.Icons;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.PlatformEvent.Type;
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
 * @update Yannick Rödl, 07.12.2011
 */
public class BHChartPanel extends JPanel implements IBHComponent, IPlatformListener, IBHAddValue,
		IBHAddGroupValue {
	private static final long serialVersionUID = -8018664370176080809L;

	private String key;
	private String inputHint;
	private JTextArea description;
	final ITranslator translator = Services.getTranslator();
	/**
	 * type defined reference to the current semantic repräsentation chart
	 */
	private Class<? extends IBHAddValue> chartClass;
	/**
	 * reference to the semantic representation of the chart
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
		description = new JTextArea(1, 20);
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
					reloadText();
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
		this.setBorder(BHBorderFactory.getInstacnce().createTitledBorder(
				BHBorderFactory.getInstacnce().createEtchedBorder(), this.key));
		
		//Add as a Plattform Listener, because we want to hear when locale is changed to reload the text.
		Services.addPlatformListener(this);
	}
	
	/**
	 * Handle PlatformEvents
	 */
	public void platformEvent(PlatformEvent e) {
		if(e.getEventType() == Type.LOCALE_CHANGED){
			this.reloadText();
		}
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
		description.setText(Services.getTranslator().translate(
				key + BHChart.DESC));
	}

	public void addSeries(Comparable<String> key, double[] values, int bins,
			double minimum, double maximum) {
		this.chartInstance.addSeries(key, values, bins, minimum, maximum);
	}

	public void addSeries(Comparable<String> seriesKey, double[][] data,
			Integer amountOfValues, Integer average) {
		this.chartInstance.addSeries(seriesKey, data, amountOfValues, average);
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
