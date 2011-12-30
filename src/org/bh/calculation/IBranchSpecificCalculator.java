package org.bh.calculation;

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
	
	/**
	 * This method calculates the normed value of the cashflows based on the 
	 * first cashflow value.
	 * 
	 * @return normed Value
	 */
	
	DTOBusinessData getNormedCFValue(String choice, DTOBusinessData businessData);
	
	/**
	 * This method calculates the arithmetic average.
	 * 
	 * @return arithmetic average
	 */
	
	DTOBranchSpecificRep[] getArithmeticAverage(String choice, DTOBusinessData businessDataNormed);
	
	/**
	 * This method calculates the rating of the branch.
	 *
	 */
	double getRating(DTOBranch[] branchRating);

}
