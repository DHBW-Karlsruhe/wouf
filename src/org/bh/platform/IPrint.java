package org.bh.platform;

import java.util.List;
import java.util.Map;

import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.jfree.chart.JFreeChart;

/**
 * Interface for plugins which can print DTOs 
 * 
 * 
 * @author Norman
 * @version 1.0, 15.01.2010
 * 
 */
public interface IPrint {
	
	static final int PRINT_PROJECT		 	= 1 << 0;
	static final int PRINT_PROJECT_RES		= 1 << 1;
	
	static final int PRINT_SCENARIO		 	= 1 << 2;
	static final int PRINT_SCENARIO_RES		= 1 << 3;
	
	void printProject(DTOProject project);
	void printScenario(DTOScenario scenario);
	void printScenarioResults(DTOScenario scenario, Map<String, Calculable[]> results, List<JFreeChart> charts);
	void printScenarioResults(DTOScenario scenario, DistributionMap results, List<JFreeChart> charts);
	
	int getSupportedMethods();
	String getUniqueId();
}
