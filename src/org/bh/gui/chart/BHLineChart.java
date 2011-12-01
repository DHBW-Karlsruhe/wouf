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

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.Series;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;

/**
 * 
 * BHLineChart to create the LineChart
 * 
 * <p>
 * LineChart is created and modified
 * 
 * @author Lars
 * @version 0.1, 16.12.2009
 * @update 23.12.2010 Timo Klein
 * 
 */
public class BHLineChart extends BHChart implements IBHAddValue, IPlatformListener {
	public static enum ChartKeys {
		CASHFLOW_IS, CASHFLOW_FORECAST, CASHFLOW_CHART, CASHFLOW_FORECAST2;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}

	}
	
	private XYSeriesCollection dataset;
	private static final Logger log = Logger.getLogger(BHLineChart.class);

	protected BHLineChart(Dataset dataset, final String key) {
		super(key);
		dataset = new XYSeriesCollection();
		this.dataset = (XYSeriesCollection) dataset;

		chart = ChartFactory.createXYLineChart(null
                        , translator.translate(key.concat(BHChart.DIMX))
                        , translator.translate(key.concat(BHChart.DIMY))
                        , this.dataset
                        , PlotOrientation.VERTICAL, true, true, false);
		
		
		final XYPlot plot = chart.getXYPlot();
		final NumberAxis rangeAxis = (NumberAxis) plot.getDomainAxis();
		rangeAxis.setTickUnit(new NumberTickUnit(1.0));
		rangeAxis.setAutoRangeStickyZero(false);
        plot.getRenderer().setSeriesPaint(0, Color.RED);
         
        NumberAxis numberaxis = (NumberAxis)plot.getRangeAxis(); 
        numberaxis.setAutoRangeIncludesZero(false);
        numberaxis.setAutoRange(true);
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); 
       
		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("Chart.background"));
		}
		
		reloadText();
		Services.addPlatformListener(this);
	}

	@Override
	public final void addValue(Number value, Comparable row, Comparable<String> columnKey) {  //auskommentiert bei Timo Klein
//		if(this.dataset.getColumnKeys().indexOf(columnKey) == -1){
//			this.dataset.addValue(value, row, columnKey);
//		}else{
//			this.dataset.addValue(value, row, columnKey+"'");
//		}
//		chart.fireChartChanged();
	}

	@Override
	public void addValues(List<?> list) { //auskommentiert bei Timo Klein
//		Iterator<?> it = list.iterator();
//
//		for (int i = 0; i < list.size(); i++) {
//			for (int j = 0; j <= i; j++) {
//				while (it.hasNext()) {
//					this.dataset.addValue((Number) list.get(i), i,
//							(String) list.get(j));
//					chart.fireChartChanged();
//				}
//			}
//		}
	}

	@Override
	public final void addValue(Number value, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public final void addSeries(Comparable<String> key, double[] values, int bins,
			final double minimum, final double maximum) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	//Timo Klein 23.12
	public void addSeries(Comparable<String> seriesKey, double[][] data, Integer amountOfValues, Integer average) {
//		XYSeries series = new XYSeries(translator.translate(seriesKey));
//		for (int i = 0; i < data.length; i++) {
//			//series.add(data[i][0], data[i][1]);
//			series.add(3.0,2.0);
//			series.add(4.0, 1.0);
//		}
//		final XYSeriesCollection dataset = new XYSeriesCollection();
//		dataset.addSeries(series);
//		//dataset.setIntervalWidth(0.0);
//
//		final XYPlot plot = chart.getXYPlot();
//		final NumberAxis axis2 = new NumberAxis(
//				translator
//						.translate("org.bh.plugin.stochasticResultAnalysis.BHStochasticResultController$ChartKeys.DISTRIBUTION_CHART.Y2"));
//		double upper = (average / amountOfValues.doubleValue() * 100) * 1.047;
//		double lower = 0.0;
//		axis2.setRange(lower, upper);
//
//		plot.setRangeAxis(1, axis2);
//		plot.mapDatasetToRangeAxis(1, 1);
//
//		final NumberAxis axis = (NumberAxis) plot.getDomainAxis();
//		axis.setAutoRange(true);
//
//		chart.fireChartChanged();
	}

	
	/**
	 * Handle PlatformEvents
	 */
	@Override
	public void platformEvent(PlatformEvent e) {
		if(e.getEventType() == Type.LOCALE_CHANGED){
			this.reloadText();
		}
	}

	@Override
	//timo Klein
	public void addSeries(Comparable<String> seriesKey, double[][] data) {	
		XYSeries series = new XYSeries(seriesKey);
		for (int i = 0; i < data.length; i++) {
			series.add(data[i][0], data[i][1]);
		}
		dataset.addSeries(series);
		chart.fireChartChanged();
	}

	@Override
	//Timo Klein
	public void removeSeries(int number) {
		try {
			dataset.removeSeries(number);
			chart.fireChartChanged();
		} catch (IllegalArgumentException e) {
			log.info(number + " not in Series List");
		}
	}
	
	public void reloadText(){
		/**
		 * Da für die Charts leider keine Keys verwendet wurden, 
		 * sondern gleich die Strings übergeben wurden, kann hier
		 * nicht auf sie zugegriffen werden.
		 * Daher die statische Programmierung
		 */
		for(int i =0;i<dataset.getSeriesCount();i++){
			String key = (String) dataset.getSeriesKey(i);
			XYSeries series = dataset.getSeries(i);
			if(key.equals("Ist-Cashflow")|key.equals("Is-Cashflow")){
				series.setKey(Services.getTranslator().translate(
						ChartKeys.CASHFLOW_IS.toString()));
			}
			if(key.equals("expected-Cashflow")|key.equals("Prognose-Cashflow")){
				series.setKey(Services.getTranslator().translate(
						ChartKeys.CASHFLOW_FORECAST.toString()));
			}
			
		}
		
		XYPlot plot = chart.getXYPlot();
//		plot.getDomainMarkers(layer)
		ValueAxis axe = plot.getRangeAxis();
		String range = axe.getLabel();
		if(range.equals("Cashflow in GE")|range.equals("Cashflow in MU")){
			axe.setLabel(Services.getTranslator().translate(
					ChartKeys.CASHFLOW_CHART.toString()+".Y"));
		}
		
		Collection target = plot.getDomainMarkers(Layer.BACKGROUND);
		if(target!=null){
			Iterator<Marker> it = target.iterator();
			while(it.hasNext()){
				Marker m = it.next();
				String interval = m.getLabel();
				if(interval.equals("Prognose")|interval.equals("forecast")){
					m.setLabel(Services.getTranslator().translate(
							ChartKeys.CASHFLOW_FORECAST2.toString()));
				}
			}
		}

	}


	
}
