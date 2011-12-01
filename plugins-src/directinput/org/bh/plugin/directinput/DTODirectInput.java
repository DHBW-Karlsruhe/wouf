/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber * : Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
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
package org.bh.plugin.directinput;

import org.apache.log4j.Logger;
import org.bh.calculation.ICalculationPreparer;
import org.bh.data.DTO;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;


/**
 * DTO for directly inputing
 *
 * Data Transfer Object which holds values for one period which
 * can directly be used for calculating the shareholder value. 
 *
 * @author Robert Vollmer
 * @author Michael Löckelt
 * @version 0.4, 16.12.2009
 *
 */

@SuppressWarnings("unchecked")
public class DTODirectInput extends DTO implements IPeriodicalValuesDTO, ICalculationPreparer {
	private static final long serialVersionUID = 8597865495976356944L;
	private static final String UNIQUE_ID = "directinput";
	private static final Logger log = Logger.getLogger(DTODirectInput.class);
	
	public enum Key {
		/**
		 * FreeCashFlow
		 */
		@Stochastic
		FCF,
		
		/**
		 * Liabilities
		 */
		@Stochastic
		LIABILITIES;
		
		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}	
	}
	
    /**
     * initialize key and method list
     */
	public DTODirectInput() {
		super(Key.class);
		log.debug("Object created!");
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}
	
	public static String getUniqueIdStatic() {
		return UNIQUE_ID;
	}
	
	@Override
	public Calculable getFCF(DTOPeriod period) {
		IPeriodicalValuesDTO dto = period.getPeriodicalValuesDTO("directinput");
		if (dto == null)
		    return null;
		return dto.getCalculable(Key.FCF);
	}

	@Override
	public Calculable getLiabilities(DTOPeriod period) {
		IPeriodicalValuesDTO dto = period.getPeriodicalValuesDTO("directinput");
		if (dto == null)
		    return null;
		return dto.getCalculable(Key.LIABILITIES);
	}
}
