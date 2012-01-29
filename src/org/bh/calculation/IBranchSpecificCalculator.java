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
	
	/**
	 * This method calculates a branch specific representative
	 * @param DTOBusinessData businessData
	 * @param DTOScenario scenario (= selected item of the branch specific representative combo-box)
	 * @return DTOCompany object incl. periods with the CF-values of the years = BranchSpecificRepresentative
	 */
	DTOCompany calculateBSR(DTOBusinessData businessData, DTOScenario scenario);
	
	/**
	 * This method calculates the normed value of the cashflows based on the 
	 * first cashflow value.
	 * 
	 * @return normed Value
	 */
	
	void getNormedCFValue(String choice, DTOCompany currCompany);
	
	
	/**
	 * This method calculates the rating of the branch.
	 *
	 */
	double getRating();
	
	/**
	 * This method calculates the rating of the branch rating.
	 *
	 */
	Color getEvaluationOfRating();
	

}
