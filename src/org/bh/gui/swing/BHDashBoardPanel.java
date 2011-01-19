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
package org.bh.gui.swing;

//import info.clearthought.layout.TableLayout;
//import info.clearthought.layout.TableLayoutConstants;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.chart.BHChartPanel;
import org.bh.platform.DashBoardController;

/**
 * In this class a (panel for the) dashboard is created, to compare
 * results of scenarios against each other.
 * 
 * @author Norman Weisenburger, Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
@SuppressWarnings("serial")
public final class BHDashBoardPanel extends JPanel {

	private BHChartPanel stackedBarChart;

	
	public BHDashBoardPanel() {
		initialize();
	}

	public void initialize() {
		setLayout(new BorderLayout());


		JPanel chartArea = new JPanel();

		stackedBarChart = BHChartFactory
				.getStackedBarChart(DashBoardController.ChartKeys.DB_SBC_SV, true, true);
		chartArea.add(stackedBarChart);
		
		add(chartArea, BorderLayout.CENTER);
	}
}