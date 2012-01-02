package org.bh.plugin.branchSpecificRepresentative.calc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bh.calculation.*;
import org.bh.data.DTOBranch;
import org.bh.data.DTOBranchSpecificRep;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.data.types.DoubleValue;

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
			System.out.println(currBranch.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY));

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
		ArrayList<DTOBranchSpecificRep> dtoBSRaverage = getArithmeticAverage(
				"", businessData);

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
		if (PeriodItr.hasNext()) {
			DTOPeriod firstPeriod = PeriodItr.next();
			firstFCF = (DoubleValue) firstPeriod.get(DTOPeriod.Key.FCF);
			firstDouble = firstFCF.getValue();
		}
		while (PeriodItr.hasNext()) {
			DTOPeriod currPeriod = PeriodItr.next();

			helpFCF = (DoubleValue) currPeriod.get(DTOPeriod.Key.FCF);
			helpDouble = helpFCF.getValue();
			if (firstDouble < 0) {
				normedFCF = new DoubleValue(
						((helpDouble / firstDouble) * (-1)) + 1);
				currPeriod.put(DTOPeriod.Key.FCF, normedFCF);

			} else {
				normedFCF = new DoubleValue(((helpDouble / firstDouble) - 1));
				currPeriod.put(DTOPeriod.Key.FCF, normedFCF);

			}

		}

	}

	public ArrayList<DTOBranchSpecificRep> getArithmeticAverage(String choice,
			DTOBusinessData businessDataNormed) {

		ArrayList<DTOBranchSpecificRep> arithmeticAverage = new ArrayList<DTOBranchSpecificRep>();

		List<DTOBranch> branchList = businessDataNormed.getChildren();
		double avgFCF[] = null;

		// Iterate all Company DTOs
		Iterator<DTOBranch> itr = branchList.iterator();
		while (itr.hasNext()) {
			DTOBranch currBranch = itr.next();

			// echos the branch keys
			System.out.println(currBranch.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY));

			//
			// Do the Branch has Companies?
			List<DTOCompany> companyList = currBranch.getChildren();
			Iterator<DTOCompany> CompanyItr = companyList.iterator();
			int counter = 0;
			while (CompanyItr.hasNext()) {
				DTOCompany currCompany = CompanyItr.next();
				counter++;

				// echo them
				System.out.println("----"
						+ currCompany.get(DTOCompany.Key.NAME));

				//
				// Do the Company has any Periods?
				List<DTOPeriod> periodList = currCompany.getChildren();
				Iterator<DTOPeriod> PeriodItr = periodList.iterator();
				double sumFCF = 0;
				DoubleValue helper = null;
				int innerCounter = 0;
				while (PeriodItr.hasNext()) {
					DTOPeriod currPeriod = PeriodItr.next();

					// echo the name of the Period
					System.out.println("---------"
							+ currPeriod.get(DTOPeriod.Key.NAME));
					helper = (DoubleValue) currPeriod.get(DTOPeriod.Key.FCF);
					sumFCF = sumFCF + helper.getValue();
					innerCounter++;
				}
				avgFCF[counter] = sumFCF / innerCounter;

			}

			double avgBranchFCF = 0;

			// Durchschnittlicher FCF für eine Branche berechnen

			if (choice == "") {
				for (int i = 0; i <= avgFCF.length; i++) {
					avgBranchFCF = avgBranchFCF + avgFCF[i];
				}
				avgBranchFCF = avgBranchFCF / avgFCF.length;
				
			} else {
				// else-Routine --> gestutzter Mittelwert
				
				// neues Array, da kleinster und größter Wert aus avgFCF "gelöscht" werden
				double[] avgFCFcuted = new double[avgFCF.length-2];
								
				// avgFCF-Array aufsteigend sortieren
				java.util.Arrays.sort(avgFCF);
				
				// kleinster und größter Wert weglassen und neues Array befüllen
				for(int i = 0; i < avgFCF.length-2;i++){
					avgFCFcuted[i] = avgFCF[i+1];
					avgBranchFCF = avgBranchFCF + avgFCFcuted[i];
				}
				
				avgBranchFCF = avgBranchFCF / avgFCFcuted.length;

			}

			arithmeticAverage.add(new DTOBranchSpecificRep(""
					+ currBranch.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY), avgBranchFCF));

		}

		return arithmeticAverage;

	}

	public double getRating(DTOBranch[] branchRating) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

}
