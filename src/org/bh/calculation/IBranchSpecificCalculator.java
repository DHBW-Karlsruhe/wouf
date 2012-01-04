package org.bh.calculation;

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
	
	ArrayList<DTOCompany> calculateBSR(DTOBusinessData businessData);
	
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
	
	ArrayList<DTOCompany> getArithmeticAverage(String choice, DTOBusinessData businessDataNormed);
	
	/**
	 * This method calculates the rating of the branch.
	 *
	 */
	double getRating();

}
