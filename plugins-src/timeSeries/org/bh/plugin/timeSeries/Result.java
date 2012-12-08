package org.bh.plugin.timeSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.data.types.DoubleValue;

/**
 * <short_description>
 *
 * <p>
 * <detailed_description>
 *
 * @author Chris
 * @version 1.0, 08.12.2012
 *
 */
public class Result {

	protected DistributionMap resultMap;

	protected TreeMap<Integer, Double> averageCashflows;
	
	protected List<Calculable> cashFlowManipuliert;
	
	/**
	 * @param resultMap
	 */
	public Result(DistributionMap resultMap, TreeMap<Integer, Double> averageCashflows, List<Calculable> cashflowManipuliert) {
		this.resultMap = resultMap;
		this.averageCashflows = averageCashflows;
		this.cashFlowManipuliert = cashflowManipuliert;
	}

	public DistributionMap getResultMap() {
		return resultMap;
	}

	public TreeMap<Integer, Double> getAverageCashflows() {
		return averageCashflows;
	}

	public List<Calculable> getCashFlowManipuliert() {
		return cashFlowManipuliert;
	}
	
}
