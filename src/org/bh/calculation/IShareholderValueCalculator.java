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

import java.util.Map;

import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.platform.IDisplayablePlugin;

/**
 * This interface is implemented by classes which provide calculation methods
 * for a company's shareholder value.
 * 
 * @author Robert Vollmer
 * @version 1.2, 18.12.2009
 * 
 */
public interface IShareholderValueCalculator extends IDisplayablePlugin {
	/**
	 * Key for {@link #calculate(DTOScenario)} which identifies the shareholder
	 * value.
	 */
	public enum Result{
		SHAREHOLDER_VALUE,
		DEBT,
		FREE_CASH_FLOW,
		DEBT_RETURN_RATE,
		EQUITY_RETURN_RATE,
		TAXES;
		
		@Override
        public String toString() {
            return getClass().getName() + "." + super.toString();
        }
	}
	
	/**
	 * This method calculates the shareholder value and possibly other values.
	 * 
	 * <p>
	 * The returned map contains at least an entry with the key
	 * {@link #SHAREHOLDER_VALUE}, so the shareholder value can always be
	 * retrieved using
	 * <code>result.get(IShareholderValueCalculator.Result.SHAREHOLDER_VALUE.toString())[0]</code>.
	 * 
	 * @param scenario
	 *            The DTO of the scenario.
	 * @return A map with the results. Values which do not belong to a specific
	 *         value are stored in an array with one element.
	 */
	Map<String, Calculable[]> calculate(DTOScenario scenario, boolean verboseLogging);

	/**
	 * Defines a unique string which identifies this calculation method.
	 * 
	 * @return The unique ID.
	 */
	String getUniqueId();
	
	/**
	 * 
	 * @return true if applicable for stochastic Processes
	 * 		false if not!
	 */
	boolean isApplicableForStochastic();
}
