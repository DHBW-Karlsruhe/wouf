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
package org.bh.gui.chart;

import java.util.Iterator;
import java.util.List;

import javax.swing.UIManager;

import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.ui.TextAnchor;

/**
 * 
 * BHstackedBarChart to create the LineChart
 * 
 * <p>
 * stackedBarChart is created and modified
 * 
 * @author Lars
 * @version 0.1, 16.12.2009
 * 
 */
public class BHstackedBarChart extends BHChart implements IBHAddGroupValue,
		IPlatformListener {

	private DefaultCategoryDataset dataset;
	private GroupedStackedBarRenderer groupRenderer;
	private KeyToGroupMap groupMap;
	private String[] categories;
	private CategoryPlot plot;
	private SubCategoryAxis domainAxis;

	protected BHstackedBarChart(final Dataset dataset, final String key,
			boolean legend, boolean tooltips) {
		super(key);
		this.dataset = (DefaultCategoryDataset) dataset;

		chart = ChartFactory.createStackedBarChart(null,
				translator.translate(key.concat(BHChart.DIMX)), translator
						.translate(key.concat(BHChart.DIMY)), this.dataset,
				PlotOrientation.VERTICAL, legend, tooltips, false);

		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("Chart.background"));
		}
		CategoryItemRenderer renderer = chart.getCategoryPlot().getRenderer();
		renderer.setBaseItemLabelGenerator(new BHChartLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);

		// Labels im 45 Grad Winkel mit maximal 5 Zeilen anzeigen
		final CategoryAxis domainAxis = chart.getCategoryPlot().getDomainAxis();
		domainAxis.setMaximumCategoryLabelLines(5);
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		final ItemLabelPosition p = new ItemLabelPosition(
				ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER,
				-Math.PI / 2.0);
		renderer.setBasePositiveItemLabelPosition(p);

		this.plot = chart.getCategoryPlot();
		this.groupRenderer = new GroupedStackedBarRenderer();

		StackedBarRenderer barRenderer = (StackedBarRenderer) chart
				.getCategoryPlot().getRenderer();
		barRenderer.setDrawBarOutline(false);
		barRenderer.setMaximumBarWidth(0.1);
		barRenderer.setBarPainter(new StandardBarPainter());

		barRenderer.setShadowVisible(false);
		groupRenderer.setShadowVisible(false);
		groupRenderer.setBarPainter(new StandardBarPainter());

		reloadText();
		Services.addPlatformListener(this);
	}

	@Override
	public final void addValue(Number value, Comparable row,
			Comparable<String> columnKey) {
		this.dataset.addValue(value, row, columnKey);
	}

	@Override
	public final void addValue(Number value, Comparable row,
			Comparable<String> columnKey, int catIdx) {
		this.addValue(value, row, columnKey);
		groupMap.mapKeyToGroup(row, categories[catIdx]);
		this.groupRenderer.setSeriesToGroupMap(groupMap);
	}

	@Override
	public void setDefaultGroupSettings(String[] categories) {
		this.categories = categories;
		this.groupMap = new KeyToGroupMap(categories[0]);
		// String cats = "";
		// for(String s : categories){
		// cats = cats + " / " + translator.translate(s);
		// }
		this.domainAxis = new SubCategoryAxis("");
		this.domainAxis.setCategoryMargin(0.05);
		for (String s : categories) {
			this.domainAxis.addSubCategory(translator.translate(s));
		}
		// this.domainAxis.
		this.plot.setDomainAxis(domainAxis);
		this.groupRenderer.setItemMargin(0.0);
		this.plot.setRenderer(groupRenderer);
	}

	@Override
	public void addValues(List<?> list) {
		Iterator<?> it = list.iterator();

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j <= i; j++) {
				while (it.hasNext()) {
					this.dataset.addValue((Number) list.get(i), i,
							(String) list.get(j));
					chart.fireChartChanged();
				}
			}
		}
	}

	@Override
	public final void addValue(Number value, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public final void addSeries(Comparable<String> key, double[] values,
			int bins, final double minimum, final double maximum) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addSeries(Comparable<String> seriesKey, double[][] data,
			Integer amountOfValues, Integer average) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	// @Override
	public void addSeries(Comparable<String> seriesKey, double[][] data) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	/**
	 * Handle PlatformEvents
	 */
	@Override
	public void platformEvent(PlatformEvent e) {
		if (e.getEventType() == Type.LOCALE_CHANGED) {
			this.reloadText();
		}
	}

	@Override
	public void removeSeries(int number) {
	    // TODO Auto-generated method stub
	    throw new UnsupportedOperationException("This method has not been implemented");
	}
}
