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
				} else {
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
			//which are build dynamicly in class "WienerProcess.java"
			if (key.contains("slope") || key.contains("standardDeviation")) {
				double value = (Double) entry.getValue();
				System.out.println(value);
				if (value == 0) {
					allZero = true;
				} else {
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
