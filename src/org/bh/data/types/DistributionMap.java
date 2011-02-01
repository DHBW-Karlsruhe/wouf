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
package org.bh.data.types;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.bh.calculation.ITimeSeriesProcess;

/**
 * 
 * This class provides the functionalities for a mathematical distribution.
 * 
 * @author Sebastian
 * @version 0.2, 06.01.2010
 * 
 * @author Norman
 * @version 0.3, 13.01.2010 implemented interfaces Map, Iterable to harmonize
 *          interfaces for export (both deterministic and stochastic results
 *          implement the map interface)
 * 
 */
public class DistributionMap implements Map<Double, Integer>,
		Iterable<Entry<Double, Integer>> {
	
	private static final Logger log = Logger.getLogger(DistributionMap.class);
	
	private final int MAXIMUM_AMOUNT_OF_DIFFERENT_VALUES = 50; 
	private double tolerance;
	private TreeMap<Double, Integer> map;
	
	private int amountOfValues;
	private double sumOfValues;
	private int amountOfDifferentValues;
	//only for displaying purpose
	private int maxAmountOfValuesInCluster;
	private TreeMap<Integer, Double> TimeSeriesMap;
	private TreeMap<Integer, Double>[] TimeSeriesMapCompare;
	private ITimeSeriesProcess TSprocess;
	private boolean TimeSeries;

	/**
	 * The constructor for the distribution map.
	 * 
	 * @param tolerance
	 *            Values which are added to the map can be clustered so that
	 *            they fit into a specific range. The parameter tolerance
	 *            describes the width of the cluster. E.g. tolerance = 5 =>
	 *            values from 0 to 4 are added into the same cluster.
	 */
	public DistributionMap(double tolerance) {
		amountOfDifferentValues = 0;
		this.amountOfValues = 0;
		sumOfValues = 0;
		this.tolerance = tolerance;
		map = new TreeMap<Double, Integer>();
	}

	/**
	 * This method adds the value to the map. If the map contains the value
	 * already the occurrence will be increased so that the map describes how
	 * often one value has been put into the map.
	 * 
	 * @param value
	 *            The value that should be added to the Map.
	 */
	public synchronized void put(double value) {
		if (tolerance == 0) {
			if (map.containsKey(value)) {
				int number = map.get(value);
				map.put(value, ++number);
			} else {
				map.put(value, 1);
			}
		} else {
			double bucket = (Math.round(value / tolerance)) * tolerance;
			if (map.containsKey(bucket)) {
				int number = map.get(bucket);
				map.put(bucket, ++number);
			} else {
				amountOfDifferentValues++;
				map.put(bucket, 1);
			}
		}
		amountOfValues++;
		sumOfValues += value;
	}

	/**
	 * This method returns the iterator for the map for displaying purpose.
	 * 
	 * @return the Iterator as an Entry with Key = value and Value = Occurrence
	 *         of the value.
	 */
	@Override
	public Iterator<Entry<Double, Integer>> iterator() {
		return map.entrySet().iterator();
	}

	/**
	 * Returns the average of the distribution. Implemented as SUM_OF_ALL_VALUES
	 * / OVERALL_NUMBER_OF_VALUES.
	 * 
	 * @return the average.
	 */
	public double getAverage() {
		return sumOfValues / amountOfValues;
	}

	/**
	 * Returns the standard deviation of the distribution.</br>
	 * StandardDeviation = &radic;((&sum;(Value² * Occurrence) /
	 * OVERALL_NUMBER_OF_VALUES) - AVERAGE²)
	 * 
	 * @return the standard deviation.
	 */
	public double getStandardDeviation() {
		double sum = 0;
		for (Entry<Double, Integer> e : map.entrySet()) {
			sum += Math.pow(e.getKey(), 2) * e.getValue();
		}
		return Math.sqrt((sum / amountOfValues) - Math.pow(getAverage(), 2));
	}

	/**
	 * This method does a value at risk analysis on the distribution.
	 * 
	 * @param confidenceCoefficient
	 *            This parameter describes the confidence of the user with which
	 *            he wants to know the range of the values.
	 * @return The range of values which still fulfill his confidence.</br> E.g.
	 *         confidence = 95% => the "real" value will be in between the
	 *         returned borders with a likeliness of 95%
	 */
	public IntervalValue valueAtRisk(double confidenceCoefficient) {
		double min = 0;
		double max = 0;
		double n = amountOfValues * ((1 - confidenceCoefficient / 100) / 2);

		int sum = 0;
		for (Entry<Double, Integer> e : map.entrySet()) {
			sum += e.getValue();
			if (sum >= n) {
				min = e.getKey();
				break;
			}
		}
		sum = 0;
		for (Entry<Double, Integer> e : map.descendingMap().entrySet()) {
			sum += e.getValue();
			if (sum >= n) {
				max = e.getKey();
				break;
			}
		}
		return new IntervalValue(min, max);
	}

	// public double calculateQuality(double standardDeviation, double mean)
	// throws MathException{
	// System.out.println(standardDeviation);
	// double qualityIndicator = 0;
	// NormalDistributionImpl ndi = new NormalDistributionImpl(mean,
	// standardDeviation);
	// //NormalDistributionImpl ndi = new NormalDistributionImpl(mean, 30);
	// //ndi.cumulativeProbability(0.01);
	// for(Entry<Double, Integer> e : map.entrySet()){
	// qualityIndicator += Math.pow(ndi.cumulativeProbability(e.getKey()) -
	// (e.getValue() / amountOfValues), 2);
	// }
	// return qualityIndicator / amountOfValues;
	// }
	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set<Entry<Double, Integer>> entrySet() {
		return map.entrySet();
	}

	@Override
	public Integer get(Object key) {
		return map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<Double> keySet() {
		return map.keySet();
	}

	/**
	 * Has the same functionality as put(double) value'll be ignored. Return
	 * value is 1 every time because return value of put(double) is void
	 * 
	 */
	@Override
	public Integer put(Double key, Integer value) {
		put(key);
		return 1;
	}

	/**
	 * Has the same functionality as multiple invocations of put(double)
	 * value'll be ignored. Return value is 1 every time because return value of
	 * put(double) is void
	 * 
	 */
	@Override
	public void putAll(Map<? extends Double, ? extends Integer> m) {
		for (double d : m.keySet()) {
			put(d);
		}
	}

	@Override
	public Integer remove(Object key) {
		return map.remove(key);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<Integer> values() {
		return map.values();
	}
	/**
	 * This method returns the keys and values as a double[][]
	 * Please use this method only for displaying purpose because the result is being "changed"
	 * so that it can be displayed nicely (cluster size is determined dynamically so that the result
	 * contains MAXIMUM_AMOUNT_OF_DIFFERENT_VALUES different values).
	 * @return 
	 * 		the result as a double array for displaying it with an XYChart
	 */
	public double[][] toDoubleArray(){
		int newAmountOfDiffValues = amountOfDifferentValues;
		double diff = map.lastKey() - map.firstKey();
		double increment = diff / amountOfDifferentValues * MAXIMUM_AMOUNT_OF_DIFFERENT_VALUES;
		double i = 1;
		DistributionMap newMap = null;
		
		int z = 0;
		log.debug("Durchlauf: " + z++ + " diffValues: " + newAmountOfDiffValues);
		while(newAmountOfDiffValues > MAXIMUM_AMOUNT_OF_DIFFERENT_VALUES){
			i += increment;
			newMap = new DistributionMap(i);
			for(Entry<Double, Integer> e : this.entrySet()){
				double value = e.getKey();
				for(int count = 0; count < e.getValue();count++){
					newMap.put(value);
				}
			}
			newAmountOfDiffValues = newMap. getAmountOfDifferentValues();
			log.debug("Durchlauf: " + z++ + " diffValues: " + newAmountOfDiffValues);
			
			if(newAmountOfDiffValues < 40){
				newAmountOfDiffValues = 51;
				i = i / 2;
				increment = increment / 2;
			}
			
		}
		double[][] result = null;
		maxAmountOfValuesInCluster = 0;
		if(newMap == null){
			result = new double[map.keySet().size()][2]; 
			int j = 0;
			for(Entry<Double, Integer> e : map.entrySet()){
				result[j][0] = e.getKey();
				int value = e.getValue();
				result[j][1] = value;
				if(value > maxAmountOfValuesInCluster)
					maxAmountOfValuesInCluster = value;
				j++;
			}
			return result;
		}else{
			result = new double[newMap.keySet().size()][2];
			int j = 0;
			for(Entry<Double, Integer> e : newMap.entrySet()){
				result[j][0] = e.getKey();
				int value = e.getValue();
				result[j][1] = value;
				if(value > maxAmountOfValuesInCluster)
					maxAmountOfValuesInCluster = value;
				j++;
			}
			return result;
		}
	}

	public int getAmountOfDifferentValues() {
		return amountOfDifferentValues;
	}

	public int getAmountOfValues() {
		return amountOfValues;
	}

	public int getMaxAmountOfValuesInCluster() {
		return maxAmountOfValuesInCluster;
	}
	
	// Methoden für die Zeitreihenanalyse
	
	public void setTimeSeries(ITimeSeriesProcess TSprocess, TreeMap<Integer, Double> tsMap, TreeMap<Integer, Double>[] tsMapCompare){
		TimeSeries = true;
		this.TimeSeriesMap = tsMap;
		this.TimeSeriesMapCompare = tsMapCompare;
		this.TSprocess = TSprocess;
	}
	
	public void setTimeSeriesCompare(TreeMap<Integer, Double>[] tsMapCompare){
		TimeSeries = true;
		this.TimeSeriesMapCompare = tsMapCompare;
	}
	
	public ITimeSeriesProcess getTimeSeriesProcess(){
		return TSprocess;
	}
	
	public boolean isTimeSeries(){
		return TimeSeries;
	}
	
	public double[][] toDoubleArrayTS(){
		double[][] result2 = new double[TimeSeriesMap.keySet().size()][2];
		int j = 0;
		for(Entry<Integer, Double> e : TimeSeriesMap.entrySet()){
			result2[j][0] = e.getKey();
			double value = e.getValue();
			result2[j][1] = value;
			j++;
		}
		return result2;
		
	}
	
	public Object[][] toObjectArrayTS(){
		Object[][] result2 = new Object[TimeSeriesMap.keySet().size()][2];
		int j = 0;
		DecimalFormat f = new DecimalFormat("#0.00");
		for(Entry<Integer, Double> e : TimeSeriesMap.entrySet()){
			result2[j][0] = e.getKey();
//			double value = Math.round((e.getValue()*100)/100);
			int value = new Double(e.getValue()).intValue();
			result2[j][1] = value;
			j++;
		}
		return result2;
		
	}
	
	public double[][] getIsCashflow(){
		double[][] result2 = new double[TimeSeriesMapCompare[0].keySet().size()][2];
		int j = 0;
		for(Entry<Integer, Double> e : TimeSeriesMapCompare[0].entrySet()){
			result2[j][0] = e.getKey();
			double value = e.getValue();
			result2[j][1] = value;
			j++;
		}
		return result2;
		
	}
	public double[][] getCompareCashflow(){
		double[][] result2 = new double[TimeSeriesMapCompare[1].keySet().size()][2];
		int j = 0;
		for(Entry<Integer, Double> e : TimeSeriesMapCompare[1].entrySet()){
			result2[j][0] = e.getKey();
			double value = e.getValue();
			result2[j][1] = value;
			j++;
		}
		return result2;
		
	}
}
