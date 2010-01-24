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

			System.out.println(key);
			
			// find slope and standarddeviation textfields,
			// which are build dynamicly in class "WienerProcess.java"
			if (key.contains("slope") || key.contains("standardDeviation")) {
				double value = (Double) entry.getValue();
				System.out.println(value);
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
