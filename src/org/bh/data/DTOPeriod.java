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
package org.bh.data;

import java.util.ServiceLoader;

import javax.help.TryMap;

import org.apache.log4j.Logger;
import org.bh.calculation.ICalculationPreparer;
import org.bh.data.types.Calculable;
import org.bh.platform.PluginManager;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementCostOfSales;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementTotalCost;

/**
 * Period DTO
 * 
 * <p>
 * This DTO contains period data.
 * 
 * @author Michael Löckelt
 * @version 0.2, 16.12.2009
 * 
 */

public class DTOPeriod extends DTO<IPeriodicalValuesDTO> {
	private static final long serialVersionUID = 1576283051584502782L;
	private static final Logger log = Logger.getLogger(DTOPeriod.class);
	
	public enum Key {
		/**
		 * identify the position of this period
		 * for example a year or a quarter
		 */
		NAME,
		
		/**
		 * total liabilities
		 */
		@Method LIABILITIES,
		
		/**
		 * FreeCashFlow
		 */
		@Method FCF;
		
		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}	
	}
	
	DTOPeriod previous = null;
	DTOPeriod next = null;
	DTOScenario scenario = null;
	
    /**
     * initialize key and method list
     */
	public DTOPeriod() {
		super(Key.class);
		log.debug("Object created!");
	}
	
	/**
	 * Get or calculate the liabilities for this period.
	 * @return
	 */
	public Calculable getLiabilities() {
		Calculable result = null;
		ServiceLoader<ICalculationPreparer> preparers = PluginManager.getInstance().getServices(ICalculationPreparer.class);
		for (ICalculationPreparer preparer : preparers) {
			result = preparer.getLiabilities(this);
			if (result != null)
				break;
		}
		if (result == null) {
			throw new DTOAccessException("Cannot calculate liabilities");
		}
		return result;
	}
	
	/**
	 * Get or calculate the FCF for this period.
	 * @return
	 */
	public Calculable getFCF() {
		Calculable result = null;
		ServiceLoader<ICalculationPreparer> preparers = PluginManager.getInstance().getServices(ICalculationPreparer.class);

		for (ICalculationPreparer preparer : preparers) {
			
			result = preparer.getFCF(this);
			
			if (result != null) {
				break;
			}

		}
		if (result == null) {
			throw new DTOAccessException("Cannot calculate FCF");
		}
		return result;
	}

	/**
	 * Get the DTO for the previous period.
	 * @return DTO for the previous period.
	 */
	public DTOPeriod getPrevious() {
		return previous;
	}

	/**
	 * Get the DTO for the following period.
	 * @return DTO for the following period.
	 */
	public DTOPeriod getNext() {
		return next;
	}

	/**
	 * Returns the first matching DTO with periodical values.
	 * @param uniqueId Type of the DTO.
	 * @return The DTO or null if none could be found.
	 */
	public IPeriodicalValuesDTO getPeriodicalValuesDTO(String uniqueId) {
		for (IPeriodicalValuesDTO child : children) {
			if (child.getUniqueId().equals(uniqueId))
				return child;
		}
		return null;
	}
	
	/**
	 * Get taxes for scenario.
	 * @return Taxes for scenario.
	 */
	public Calculable getTax() {
		return scenario.getTax();
	}
	
	public DTOScenario getScenario() {
		return scenario;
	}	
}
