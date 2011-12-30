package org.bh.plugin.branchSpecificRepresentative.calc;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.bh.calculation.*;
import org.bh.data.DTOBranch;
import org.bh.data.DTOBranchSpecificRep;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.IValue;


/**
 * <short_description>
 * 
 * <p>
 * <detailed_description>
 * 
 * @author Denis
 * @version 1.0, 21.12.2011
 * 
 */
public class BranchSpecificCalculator implements IBranchSpecificCalculator {

	public DTOBusinessData calculateBSR(DTOBusinessData businessData) {
		List<DTOBranch> branchList = businessData.getChildren();

		// Iterate all Company DTOs
		Iterator<DTOBranch> itr = branchList.iterator();
		while (itr.hasNext()) {
			DTOBranch currBranch = itr.next();

			// echos the branch keys
			System.out.println(currBranch.get(DTOBranch.Key.BRANCH_KEY));

			//
			// Do the Branch has Companies?
			List<DTOCompany> companyList = currBranch.getChildren();
			Iterator<DTOCompany> CompanyItr = companyList.iterator();
			while (CompanyItr.hasNext()) {
				DTOCompany currCompany = CompanyItr.next();

				// echo them
				System.out.println("----"
						+ currCompany.get(DTOCompany.Key.NAME));
				
				// Norm all FCF-Period-Values
				getNormedCFValue("", currCompany);
				
				

			}
		}
		
		// mit normierter BusinessData die Mittelwerte berechnen
		DTOBranchSpecificRep[] dtoBSRaverage = getArithmeticAverage("",businessData);
		
		return businessData;

	}

	public void getNormedCFValue(String choice, DTOCompany currCompany) {

		// Do the Company has any Periods?
		List<DTOPeriod> periodList = currCompany.getChildren();
		Iterator<DTOPeriod> PeriodItr = periodList.iterator();
		DoubleValue firstFCF = null;
		double firstDouble = 0;
		DoubleValue helpFCF = null;
		double helpDouble = 0;
		DoubleValue normedFCF;
		if(PeriodItr.hasNext()){
			DTOPeriod firstPeriod = PeriodItr.next();
			firstFCF = (DoubleValue)firstPeriod.get(DTOPeriod.Key.FCF);
			firstDouble = firstFCF.getValue();
		}
		while (PeriodItr.hasNext()) {
			DTOPeriod currPeriod = PeriodItr.next();

			
			System.out.println("---------" + currPeriod.get(DTOPeriod.Key.FCF));
			helpFCF = (DoubleValue)currPeriod.get(DTOPeriod.Key.FCF);
			helpDouble = helpFCF.getValue();
			if(firstDouble < 0){
				normedFCF = new DoubleValue(((helpDouble / firstDouble) * (-1)) + 1);
	///			DTOPeriod.Key.FCF = normedFCF;
				
			}else{
				normedFCF = new DoubleValue(((helpDouble / firstDouble) -1));
	///			DTOPeriod.Key.FCF = normedFCF;
				
				
			}
			
		}


	}

	public DTOBranchSpecificRep[] getArithmeticAverage(String choice,
			DTOBusinessData businessDataNormed) {
		DTOBranchSpecificRep[] array = null;
		
		return array;

	}

	public double getRating(DTOBranch[] branchRating) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

}
