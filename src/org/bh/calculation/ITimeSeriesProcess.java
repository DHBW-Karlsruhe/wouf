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
package org.bh.calculation;

import java.util.TreeMap;

import javax.swing.JPanel;

import org.bh.data.DTOScenario;
import org.bh.gui.swing.comp.BHProgressBar;
import org.bh.platform.IDisplayablePlugin;

/**
 * This interface is implemented by plugins which can execute timeSeries
 * processes.
 * 
 * @author Andreas Wußler, Timo Klein
 * @version 1.0, 22.12.2010
 * @update 23.12.2010 Timo Klein
 */
public interface ITimeSeriesProcess extends IStochasticProcess{
	
	public enum Key{
		TIME_SERIES,
		BRANCH_SPECIFIC_REPRESENTATIVE;
		
		@Override
        public String toString() {
            return getClass().getName() + "." + super.toString();
        }
	};
	
	/**
	 * Creates a new instance of the same class for a specific scenario.
	 * 
	 * @param The scenario used in this instance of the time series process.
	 * @return A new instance of the same class.
	 */
	ITimeSeriesProcess createNewInstance(DTOScenario scenario);

	void setProgressB(BHProgressBar bhComponent);

	void setInterrupted();

}
