package org.bh.data.types;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;

//TODO: Erwartungswert berechnen
public class DistributionMap implements Iterable<Entry<Double, Integer>> {

	private double tolerance;
	private TreeMap<Double, Integer> map;

	public DistributionMap(double tolerance) {
		this.tolerance = tolerance;
		map = new TreeMap<Double, Integer>(new Comparator<Double>() {
			public int compare(Double o1, Double o2) {
				if (o1 < o2)
					return -1;
				else if (o1 > o2)
					return 1;
				else
					return 0;
			}
		});
	}

	public void put(double value) {
		if (tolerance != 0)
			value = ((int) (value / tolerance)) * tolerance;

		if (map.containsKey(value)) {
			int number = map.get(value);
			map.put(value, ++number);
		} else {
			map.put(value, 1);
		}
	}

	public Iterator<Entry<Double, Integer>> iterator() {
		return map.entrySet().iterator();
	}
}
