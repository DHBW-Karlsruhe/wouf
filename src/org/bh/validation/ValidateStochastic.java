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
package org.bh.validation;

import java.util.Iterator;
import java.util.Map;

/**
 * This class contains warning rules for stochastic processes.
 * Using random walk, there shouldn't be any calculation with all chance
 * values equal to 0.0 or all equal to 1.0. Using Wiener process, not all
 * standard deviation and slope fields should be equal to 0.0.
 * 
 * @author Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
public class ValidateStochastic {

	/**
	 * This method validates the chance-textfields of the RandomWalk.
	 * 
	 * @return false if all chance-textfields are equal to 0 or all equal to 1.
	 *         true if there is at least one value not being equal to 0 or 1.
	 */
	@SuppressWarnings("unchecked")
	public static boolean validateRandomWalk(Map<String, Double> internalMap) {
		boolean allZero = false;
		boolean allOne = false;
		Iterator iterator = internalMap.entrySet().iterator();
		int mapsize = internalMap.size();

		for (int i = 0; i < mapsize; i++) {

			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			
			// find chance textfields, which are build dynamicly in class "RandomWalk.java"
			if (key.contains("chance")) {
				double value = (Double) entry.getValue();
				if (value == 0 && allOne == false) {
					allZero = true;
				} else if (value == 1 && allZero == false) {
					allOne = true;
				} else { // (value != 0 && value != 1)
					allZero = allOne = false;
					break;
				}
			}
		}
		if (allZero == true || allOne == true) {
			return false;
		}
		return true;
	}

	/**
	 * This method validates the slope- and standard deviation-textfields
	 * of the Wiener process.
	 * 
	 * @return false if all textfields are equal to 0.
	 *         true if there is at least one value not being equal to 0.
	 */
	@SuppressWarnings("unchecked")
	public static boolean validateWienerProcess(Map<String, Double> internalMap) {
		boolean allZero = false;
		Iterator iterator = internalMap.entrySet().iterator();
		int mapsize = internalMap.size();

		for (int i = 0; i < mapsize; i++) {

			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();

			//System.out.println(key);
			
			// find slope and standarddeviation textfields,
			// which are build dynamicly in class "WienerProcess.java"
			if (key.contains("slope") || key.contains("standardDeviation")) {
				double value = (Double) entry.getValue();
				//System.out.println(value);
				if (value == 0) {
					allZero = true;
				} else { // (value != 0)
					allZero = false;
					break;
				}
			}
		}
		if (allZero == true) {
			return false;
		}
		return true;
	}
}
