package org.bh.data.types;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;
/**
 * 
 * This class provides the functionalities for a mathematical distribution.
 * 
 * @author Sebastian
 * @version 0.2, 06.01.2010
 *
 */
public class DistributionMap{
	
	private double tolerance;
	private TreeMap<Double, Integer> map;
	private int amountOfValues;
	private double sumOfValues;
	
	/**
	 * The constructor for the distribution map.
	 * @param tolerance
	 * 			Values which are added to the map can be clustered so that they fit into a specific range.
	 * 			The parameter tolerance describes the width of the cluster.
	 * 			E.g. tolerance = 5 => values from 0 to 4 are added into the same cluster.
	 */
	public DistributionMap(double tolerance){
		this.amountOfValues = 0;
		sumOfValues = 0;
		this.tolerance = tolerance;
		map = new TreeMap<Double, Integer>(new Comparator<Double>() {
			public int compare(Double o1, Double o2) {
				if(o1 < o2)
					return -1;
				else if(o1 > o2)
					return 1;
				else 
					return 0;
			}
		});
	}
	/**
	 * This method adds the value to the map. 
	 * If the map contains the value already the occurrence will be increased 
	 * so that the map describes how often one value has been put into the map.
	 * @param value
	 * 			The value that should be added to the Map.
	 */
	public void put(double value){
		if(tolerance == 0){
			if(map.containsKey(value)){
				int number = map.get(value);
				map.put(value, ++number);
			}else{
				map.put(value, 1);
			}
		}else{
			double bucket = (Math.round(value / tolerance)) * tolerance;
			if(map.containsKey(bucket)){
				int number = map.get(bucket);
				map.put(bucket, ++number);
			}else{
				map.put(bucket, 1);
			}
		}
		amountOfValues++;
		sumOfValues += value;
	}
	/**
	 * This method returns the iterator for the map for displaying purpose.
	 * @return
	 * 		the Iterator as an Entry with Key = value and Value = Occurrence of the value.
	 */
	public Iterator<Entry<Double, Integer>> iterator(){
		return map.entrySet().iterator();
	}
	/**
	 * Returns the average of the distribution. Implemented as SUM_OF_ALL_VALUES / OVERALL_NUMBER_OF_VALUES.
	 * @return
	 * 		the average.
	 */
	public double getAverage(){
		return sumOfValues / amountOfValues;
	}
	/**
	 * Returns the standard deviation of the distribution.</br>
	 * StandardDeviation = &radic;((&sum;(Value² * Occurrence) / OVERALL_NUMBER_OF_VALUES) - AVERAGE²)
	 * @return
	 * 		the standard deviation.
	 */
	public double getStandardDeviation(){
		double sum = 0;
		for(Entry<Double, Integer> e : map.entrySet()){
			sum += Math.pow(e.getKey(), 2) * e.getValue();
		}
		return Math.sqrt((sum / amountOfValues) - Math.pow(getAverage(), 2));
	}
	/**
	 * This method does a value at risk analysis on the distribution.
	 * 
	 * @param confidenceCoefficient
	 * 		This parameter describes the confidence of the user 
	 * 		with which he wants to know the range of the values.
	 * @return
	 * 		The range of values which still fulfill his confidence.</br>
	 * 		E.g. confidence = 95% => the "real" value will be in between 
	 * 		the returned borders with a likeliness of 95%
	 */
	public double[] valueAtRisk(double confidenceCoefficient){
		double[] result = new double[2];
		double n = amountOfValues * ((1 - confidenceCoefficient) / 2);
		
		int sum = 0;
		for(Entry<Double, Integer> e : map.entrySet()){
			sum += e.getValue();
			if(sum > n)
				result[0] = e.getKey();
			if(sum > amountOfValues - n)
				result[1] = e.getKey();
		}
		return result;
	}
//	public double calculateQuality(double standardDeviation, double mean) throws MathException{
//	System.out.println(standardDeviation);
//	double qualityIndicator = 0;
//	NormalDistributionImpl ndi = new NormalDistributionImpl(mean, standardDeviation);
//	//NormalDistributionImpl ndi = new NormalDistributionImpl(mean, 30);
//	//ndi.cumulativeProbability(0.01);
//	for(Entry<Double, Integer> e : map.entrySet()){
//		qualityIndicator += Math.pow(ndi.cumulativeProbability(e.getKey()) - (e.getValue() / amountOfValues), 2);
//	}
//	return qualityIndicator / amountOfValues;
//}
}
