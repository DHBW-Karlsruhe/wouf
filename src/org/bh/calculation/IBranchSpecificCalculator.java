package org.bh.calculation;

import java.awt.Color;
import java.util.ArrayList;

import org.bh.data.*;


/**
 * This interface is implemented by plugins which can execute stochastic
 * processes with addition of a branch specific representative.
 * 
 * 
 * @author Denis Roster, Tim Herzenstiel, Sebastian Schumacher
 * @version 1.0, 21.12.2011
 *
 */

public interface IBranchSpecificCalculator {
	
	DTOCompany calculateBSR(DTOBusinessData businessData, DTOScenario scenario);
	
	/**
	 * This method calculates the normed value of the cashflows based on the 
	 * first cashflow value.
	 * 
	 * @return normed Value
	 */
	
	void getNormedCFValue(String choice, DTOCompany currCompany);
	
	/**
	 * This method calculates the arithmetic average.
	 * 
	 * @return arithmetic average
	 */
	
	DTOCompany getArithmeticAverage(String choice, DTOBranch currNormedBranch);
	
	/**
	 * This method calculates the rating of the branch.
	 *
	 */
	double getRating();
	
	Color getEvaluationOfRating();
	

}
