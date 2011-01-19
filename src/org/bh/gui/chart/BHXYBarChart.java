/*******************************************************************************
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

import java.util.List;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * 
 * BHXYBarChart to create a XYBarChart
 * 
 * <p>
 * an default BHXYBarChart is created and modified.
 * 
 * @author Lars.Zuckschwerdt
 * @version 1.0, 27.01.2010
 * 
 */
public class BHXYBarChart extends BHChart implements IBHAddValue,
		IPlatformListener {
	private static final Logger log = Logger.getLogger(BHXYBarChart.class);
	private XYSeriesCollection dataset;

	public BHXYBarChart(final String key) {
		super(key);
		final ITranslator translator = Services.getTranslator();
		this.dataset = new XYSeriesCollection();
		chart = ChartFactory.createXYBarChart(null, translator.translate(key
				.concat(BHChart.DIMX)), false, translator.translate(key
				.concat(BHChart.DIMY)), dataset, PlotOrientation.VERTICAL,
				true, true, false);

		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("Chart.background"));
		}

		XYBarRenderer renderer = (XYBarRenderer) chart.getXYPlot()
				.getRenderer();
		renderer.setBarPainter(new StandardXYBarPainter());
		renderer.setShadowVisible(false);
		renderer.setDrawBarOutline(false);
		renderer.setToolTipGenerator(new XYToolTipGenerator() {

			@Override
			public String generateToolTip(XYDataset dataset, int series,
					int item) {
				Comparable<String> comp = dataset.getSeriesKey(series);
				if (comp
						.equals(translator
								.translate("org.bh.plugin.stochasticResultAnalysis.BHStochasticResultController$PanelKeys.AVERAGE"))) {
					return translator
							.translate("org.bh.plugin.stochasticResultAnalysis.BHStochasticResultController$PanelKeys.AVERAGE")
							+ ": "
							+ Services.numberToString(dataset.getXValue(series,
									item));
				} else if (comp
						.equals(translator
								.translate("org.bh.plugin.stochasticResultAnalysis.BHStochasticResultController$ChartKeys.RISK_AT_VALUE"))) {
					return translator
							.translate("org.bh.plugin.stochasticResultAnalysis.BHStochasticResultController$ChartKeys.RISK_AT_VALUE")
							+ ": "
							+ Services.numberToString(dataset.getXValue(series,
									item));
				} else {
					String x = Services.numberToString(dataset.getXValue(
							series, item));
					String y = Services.numberToString(dataset.getYValue(
							series, item));
					return translator.translate(key.concat(BHChart.DIMX)) + " "
							+ x + "; "
							+ translator.translate(key.concat(BHChart.DIMY))
							+ " " + y;

				}
			}
		});

		XYBarRenderer barRenderer = (XYBarRenderer) chart.getXYPlot()
				.getRenderer();

		barRenderer.setDrawBarOutline(false);

		reloadText();
		Services.addPlatformListener(this);
	}

	@Override
	public void addSeries(Comparable<String> key, double[] values, int bins,
			double minimum, double maximum) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addSeries(Comparable<String> seriesKey, double[][] data,
			Integer amountOfValues, Integer average) {
		XYSeries series = new XYSeries(translator.translate(seriesKey));
		for (int i = 0; i < data.length; i++) {
			series.add(data[i][0], data[i][1]);
		}
		dataset.addSeries(series);
		dataset.setIntervalWidth(0.0);

		final XYPlot plot = chart.getXYPlot();
		final NumberAxis axis2 = new NumberAxis(
				translator
						.translate("org.bh.plugin.stochasticResultAnalysis.BHStochasticResultController$ChartKeys.DISTRIBUTION_CHART.Y2"));
		double upper = (average / amountOfValues.doubleValue() * 100) * 1.047;
		double lower = 0.0;
		axis2.setRange(lower, upper);

		plot.setRangeAxis(1, axis2);
		plot.mapDatasetToRangeAxis(1, 1);

		final NumberAxis axis = (NumberAxis) plot.getDomainAxis();
		axis.setAutoRange(true);

		chart.fireChartChanged();
	}

	@Override
	public void addValue(Number value, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addValue(Number value, Comparable rowKey,
			Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addValues(List<?> list) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void platformEvent(PlatformEvent e) {
	}
	
	@Override
	public void addSeries(Comparable<String> seriesKey, double[][] data) {
		XYSeries series = new XYSeries(seriesKey);
		for (int i = 0; i < data.length; i++) {
			series.add(data[i][0], data[i][1]);
		}
		dataset.addSeries(series);
		dataset.setIntervalWidth(0.0);
		chart.fireChartChanged();
	}

	@Override
	public void removeSeries(int number) {
		try {
			dataset.removeSeries(number);
			chart.fireChartChanged();
		} catch (IllegalArgumentException e) {
			log.info(number + " not in Series List");
		}

	}
}
