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
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
/**
 * 
 * BHPieChart to create a default PieChart
 *
 * <p>
 * a BHPieChart is created but currently unused in Business Horizon!
 *
 * @author Lars.Zuckschwerdt
 * @version 1.0, 27.01.2010
 *
 */
public class BHPieChart extends BHChart implements IBHAddValue, IPlatformListener {

	private DefaultPieDataset dataset;

	protected BHPieChart( final Dataset dataset, String key) {
		super(key);
		this.dataset = (DefaultPieDataset) dataset;

		chart = ChartFactory.createPieChart(null, this.dataset, true, true,
				false);
		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("Chart.background"));
		}
		
		reloadText();
		Services.addPlatformListener(this);
	}
	
	@Override
	public final void addValue(Number value, Comparable<String> columnKey) {
		this.dataset.setValue(columnKey, value);
		chart.fireChartChanged();
	}

	@Override
	public void addValues(List<?> list) {
		Iterator<?> it = list.iterator();

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j <= i; j++) {
				while (it.hasNext()) {
					this.dataset.setValue((String) list.get(j), (Number) list
							.get(i));
					chart.fireChartChanged();
				}
			}
		}
		// for(int i=0; i<list.size()+1; i++){
		// this.dataset.setValue(columnKey, (Number)list.get(i));
		// fireChartChanged();
		// }
	}

	@Override
	public void addValue(Number value, Comparable rowKey, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addSeries(Comparable<String> key, double[] values, int bins,
			double minimum, double maximum) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addSeries(Comparable<String> seriesKey, double[][] data, Integer amountOfValues, Integer average) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void platformEvent(PlatformEvent e) {
		if(e.getEventType()== Type.LOCALE_CHANGED){
			this.reloadText();
		}
	}

	@Override
	public void addSeries(Comparable<String> seriesKey, double[][] data) {
	    // TODO Auto-generated method stub
	    throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void removeSeries(int number) {
	    // TODO Auto-generated method stub
	    throw new UnsupportedOperationException("This method has not been implemented");
	}
	
}
