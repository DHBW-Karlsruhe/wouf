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

import java.util.HashMap;
import java.util.Map;

import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.platform.Services;

/**
 * Shareholder value calculator which uses all other calculators and combines
 * their results.
 * 
 * @author Robert
 * @version 1.0, 16.01.2010
 * 
 */
public class AllDCFMethodsCalculator implements IShareholderValueCalculator {
	private static final String UNIQUE_ID = "all";
	private static final String GUI_KEY = "allDCF";

	@Override
	public Map<String, Calculable[]> calculate(DTOScenario scenario, boolean verboseLogging) {
		Map<String, Calculable[]> result = new HashMap<String, Calculable[]>();
		for (IShareholderValueCalculator calculator : Services.getDCFMethods()
				.values()) {
			if (calculator.getUniqueId().equals(UNIQUE_ID))
				continue;
			Map<String, Calculable[]> result2 = calculator.calculate(scenario, verboseLogging);
			if(result.containsKey("org.bh.calculation.IShareholderValueCalculator$Result.DEBT") && result.containsKey("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW")){
				Calculable[] debt = result.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT");
				Calculable[] fcf = result.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW");
				Calculable[] debt2 = result2.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT");
				Calculable[] fcf2 = result2.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW");
				result.putAll(result2);
				if(debt.length > debt2.length)
					result.put("org.bh.calculation.IShareholderValueCalculator$Result.DEBT", debt);
				if(fcf.length > fcf2.length)
					result.put("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW", fcf);
			}else
				result.putAll(result2);
		}
		return result;
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	@Override
	public boolean isApplicableForStochastic() {
		return false;
	}

}
