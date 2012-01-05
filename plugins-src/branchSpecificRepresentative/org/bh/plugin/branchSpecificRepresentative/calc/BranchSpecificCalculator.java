package org.bh.plugin.branchSpecificRepresentative.calc;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.bh.calculation.IBranchSpecificCalculator;
import org.bh.data.DTOBranch;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.types.DoubleValue;

/**
 * <short_description>
 * 
 * <p>
 * <detailed_description>
 * 
 * @author Denis Roster, Tim Herzenstiel, Sebastian Schumacher
 * @update Günter Hesse
 * @version 1.0, 21.12.2011
 * 
 */
public class BranchSpecificCalculator implements IBranchSpecificCalculator {

	private static double ratingBSR;
	private static DTOBranch selectedBranch = null;

	public DTOCompany calculateBSR(DTOBusinessData businessData) {
		DTOBranch currBranch = getSelectedBranch(businessData);

		// echos the branch keys
		System.out.println(currBranch
				.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY));

		// Do the Branch has Companies?
		List<DTOCompany> companyList = currBranch.getChildren();
		Iterator<DTOCompany> CompanyItr = companyList.iterator();

		while (CompanyItr.hasNext()) {
			DTOCompany currCompany = CompanyItr.next();

			// echo them
			System.out.println("----" + currCompany.get(DTOCompany.Key.NAME));

			// Norm all FCF-Period-Values
			getNormedCFValue("", currCompany);

		}

		// mit normierter BusinessData die Mittelwerte berechnen
		DTOCompany dtoBSRaverage = new DTOCompany();

		dtoBSRaverage = getArithmeticAverage("", currBranch);

		computeRating(dtoBSRaverage, businessData);

		return dtoBSRaverage;

	}

	public void getNormedCFValue(String choice, DTOCompany currCompany) {

		DoubleValue firstFCF = null;
		DoubleValue normedFCF = null;
		DoubleValue helpFCF = null;
		double helpDouble = 0;
		double firstDouble = 0;

		// Do the Company has any Periods?
		List<DTOPeriod> periodList = currCompany.getChildren();
		Iterator<DTOPeriod> PeriodItr = periodList.iterator();

		if (PeriodItr.hasNext()) {
			DTOPeriod firstPeriod = PeriodItr.next();

			// !!!! HERE: Insert a function to convert the CF and FCF
			/*
			 * 
			 */

			firstFCF = (DoubleValue) firstPeriod.get(DTOPeriod.Key.FCF);
			firstDouble = firstFCF.getValue();
		}
		while (PeriodItr.hasNext()) {
			DTOPeriod currPeriod = PeriodItr.next();

			// !!!! HERE: Insert a function to convert the CF and FCF
			/*
			 * 
			 */

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

	public DTOCompany getArithmeticAverage(String choice,
			DTOBranch currNormedBranch) {

		double[][] companiesAndPeriods = getNumberOfCompaniesAndPeriods(currNormedBranch);
		DTOCompany arithmeticAverage = new DTOCompany();
		double rowSum = 0;
		double[] avgValues = new double[companiesAndPeriods[0].length];

		// set name of branch
		arithmeticAverage.put(DTOCompany.Key.NAME,
				currNormedBranch.get(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY));

		// Do the Branch has Companies?
		List<DTOCompany> companyList = currNormedBranch.getChildren();
		Iterator<DTOCompany> CompanyItr = companyList.iterator();
		int innerCompanyCounter = 0;

		while (CompanyItr.hasNext()) {
			DTOCompany currCompany = CompanyItr.next();

			// Do the Company has any Periods?
			List<DTOPeriod> periodList = currCompany.getChildren();
			Iterator<DTOPeriod> PeriodItr = periodList.iterator();
			DoubleValue helper = null;
			int innerPeriodCounter = 0;

			while (PeriodItr.hasNext()) {
				DTOPeriod currPeriod = PeriodItr.next();

				helper = (DoubleValue) currPeriod.get(DTOPeriod.Key.FCF);
				companiesAndPeriods[innerCompanyCounter][innerPeriodCounter] = helper
						.getValue();
				innerPeriodCounter++;

			}

			innerCompanyCounter++;

		}

		if (choice == "") {

			for (int j = 0; j < companiesAndPeriods[0].length; j++) {

				for (int i = 0; i < companiesAndPeriods.length; i++) {
					rowSum = rowSum + companiesAndPeriods[i][j];
				}

				avgValues[j] = rowSum;

			}

			// Mittelwert berechnen für einzelne Perioden

			for (int i = 0; i < avgValues.length; i++) {
				avgValues[i] = avgValues[i] / companiesAndPeriods.length;
			}

		} else {

			double[] helpSort = new double[companiesAndPeriods.length];

			// Werte in Zeile von klein nach groß sortieren

			for (int i = 0; i < companiesAndPeriods[0].length; i++) {

				for (int j = 0; j < companiesAndPeriods.length; j++) {
					helpSort[j] = companiesAndPeriods[j][i];
				}

				Arrays.sort(helpSort);

				for (int j = 0; j < companiesAndPeriods.length; j++) {
					companiesAndPeriods[j][i] = helpSort[j];
				}

			}

			// Zeilenwerte addieren

			for (int j = 0; j < companiesAndPeriods[0].length; j++) {

				for (int i = 1; i < companiesAndPeriods.length - 1; i++) {
					rowSum = rowSum + companiesAndPeriods[i][j];
				}

				avgValues[j] = rowSum;

			}

			// Mittelwert berechnen für einzelne Perioden

			for (int i = 0; i < avgValues.length; i++) {
				avgValues[i] = avgValues[i] / companiesAndPeriods.length - 2;
			}

		}

		// Werte der DTOPeriods im DTOCompany setzen

		DTOPeriod[] periods = new DTOPeriod[companiesAndPeriods[0].length];

		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR) - 2;

		for (int i = 0; i < periods.length; i++) {
			periods[i] = new DTOPeriod();
			periods[i].put(DTOPeriod.Key.NAME, new DoubleValue(year - i));
			periods[i].put(DTOPeriod.Key.FCF, new DoubleValue(avgValues[i]));
			arithmeticAverage.addChild(periods[i]);
		}

		return arithmeticAverage;

	}

	private void computeRating(DTOCompany branchSpecificRepresentative,
			DTOBusinessData businessData) {

		DTOBranch currBranch = getSelectedBranch(businessData);

		// echos the branch keys
		System.out.println(currBranch
				.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY));

		// Do the Branch has Companies?
		List<DTOCompany> companyList = currBranch.getChildren();
		Iterator<DTOCompany> CompanyItr = companyList.iterator();

		double result = 0;

		while (CompanyItr.hasNext()) {
			DTOCompany currCompany = CompanyItr.next();

			// echo them
			System.out.println("----" + currCompany.get(DTOCompany.Key.NAME));

			// Norm all FCF-Period-Values
			getNormedCFValue("", currCompany);

			List<DTOPeriod> periodList = currCompany.getChildren();
			Iterator<DTOPeriod> periodItr = periodList.iterator();

			// iteriert über die einzelnen Perioden der Unternehmen der
			// selektierten Branche
			// zur Güteberechnung des vorher berechneten, branchenspezifischen
			// Vertreters
			for (int count = 0; periodItr.hasNext(); count++) {
				DTOPeriod currPeriod = periodItr.next();

				DoubleValue normedCFforPeriod = (DoubleValue) currPeriod
						.get(DTOPeriod.Key.FCF);

				// result = result + ( (normedCFforPeriod - [count]) *
				// (normedCFforPeriod - BSR[count]) );

			}

		}
		result = result / (companyList.size());
	}

	private DTOBranch getSelectedBranch(DTOBusinessData businessData) {
		if (this.selectedBranch != null)
			return this.selectedBranch;

		List<DTOBranch> branchList = businessData.getChildren();

		String selectedBranch = DTOScenario.Key.INDUSTRY.toString();

		// Iterate Company DTOs
		Iterator<DTOBranch> itr = branchList.iterator();
		while (itr.hasNext()) {
			DTOBranch currBranch = itr.next();
			String currKey = ""
					+ (currBranch.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY))
					+ (currBranch.get(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY))
					+ (currBranch.get(DTOBranch.Key.BRANCH_KEY_SUB_CATEGORY));

			if (currKey == selectedBranch)
				this.selectedBranch = currBranch;
			return currBranch;
		}
		// selected branch not found
		// TODO create an exception
		return null;
	}

	public double getRating() {
		return BranchSpecificCalculator.ratingBSR;
	}

	private double[][] getNumberOfCompaniesAndPeriods(DTOBranch currNormedBranch) {
		int companyCounter = 0, periodCounter = 0;


		List<DTOCompany> companyList = currNormedBranch.getChildren();
		Iterator<DTOCompany> CompanyItr = companyList.iterator();
		companyCounter = companyList.size();

		DTOCompany currCompany = CompanyItr.next();
		List<DTOPeriod> periodList = currCompany.getChildren();
		periodCounter = periodList.size();


		return new double[companyCounter][periodCounter];
	}
	

}
