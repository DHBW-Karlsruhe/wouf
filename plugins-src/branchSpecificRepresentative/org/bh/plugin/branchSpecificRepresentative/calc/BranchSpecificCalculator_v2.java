package org.bh.plugin.branchSpecificRepresentative.calc;

import java.awt.Color;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.bh.calculation.IBranchSpecificCalculator;
import org.bh.data.DTOAccessException;
import org.bh.data.DTOBranch;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.StringValue;

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
public class BranchSpecificCalculator_v2 implements IBranchSpecificCalculator {

	private double ratingBSR;
	private DTOBranch selectedBranch = null;
	private DTOScenario scenario = null;
	private Logger log = Logger.getLogger(BranchSpecificCalculator_v2.class);

	public DTOCompany calculateBSR(DTOBusinessData businessData,
			DTOScenario scenario) {
		DTOBranch currBranch = getSelectedBranch(businessData);

		this.scenario = scenario;

		// Tabelle erstellen + Durchschnitt der Unternehmen + 3. Wurzel
		double[][] companiesAndPeriodsNotNormed = createTable(currBranch);
		double[] averageCompanyNotNormed = getAverage(
				companiesAndPeriodsNotNormed, "company");
		double[] cubeRoot = getCubeRoot(averageCompanyNotNormed);

		// Do the Branch has Companies?
		List<DTOCompany> companyList = currBranch.getChildren();
		Iterator<DTOCompany> CompanyItr = companyList.iterator();

		while (CompanyItr.hasNext()) {
			DTOCompany currCompany = CompanyItr.next();

			// Norm all FCF-Period-Values
			if (!currCompany.isNormed) {
				getNormedCFValue("", currCompany);
			}

		}

		// Normierte Tabelle erstellen
		double[][] companiesAndPeriodsNormed = createTable(currBranch);

		// Stutzung der normierten Tabelle
		double[][] companyAndPeriodsNormedTrimmed = trimmedAverage(
				companiesAndPeriodsNormed, 2.5);

		// Spezifizierung der gestutzten normierten Tabelle
		double[] bsrArray = getSpecificAverage(companyAndPeriodsNormedTrimmed,
				cubeRoot);

		// Werte dem Ergebnis DTOCompany-Objekt zuweisen
		DTOCompany dtoBSRaverage = new DTOCompany();

		DTOPeriod[] periods = new DTOPeriod[bsrArray.length];

		// ACHTUNG: Hardcoded Jahreszahl --> keine neueren Unternehmensdaten für
		// die Jahre NACH 2010!!!
		int year = 2010;

		for (int i = 0; i < periods.length; i++) {
			periods[i] = new DTOPeriod();
			periods[i].put(DTOPeriod.Key.NAME, new DoubleValue(year - i));
			periods[i].put(DTOPeriod.Key.FCF, new DoubleValue(bsrArray[i]));
			dtoBSRaverage.addChild(periods[i]);
		}

		// computeRating(dtoBSRaverage, businessData);

		 this.scenario.setBsrCalculatorWithRating(this);

		return dtoBSRaverage;

	}

	public void getNormedCFValue(String choice, DTOCompany currCompany) {

		DoubleValue firstFCF = null;
		DoubleValue normedFCF = null;
		DoubleValue helpFCF = null;
		double helpDouble = 0;
		double firstDouble = 0;
		boolean firstPeriod = true;

		// update References in company.
		currCompany.updateReferences(scenario);

		// Do the Company has any Periods?
		List<DTOPeriod> periodList = currCompany.getChildren();
		Iterator<DTOPeriod> PeriodItr = periodList.iterator();

		int i = 0;
		while (PeriodItr.hasNext()) {
			DTOPeriod currPeriod = PeriodItr.next();

			// Berechnung der richtigen Unternehmensdaten
			try {
				currPeriod.put(DTOPeriod.Key.FCF, currPeriod.getFCF());
			} catch (DTOAccessException dtoaccess) {
				if (i > 0) {
					log.error(
							"Period not readable "
									+ currCompany.get(DTOCompany.Key.NAME)
									+ " year: "
									+ currPeriod.get(DTOPeriod.Key.NAME),
							dtoaccess);
					currPeriod.put(DTOPeriod.Key.FCF, new DoubleValue(0.0));
				}
			}
			try {
				currPeriod.put(DTOPeriod.Key.LIABILITIES,
						currPeriod.getLiabilities());
			} catch (DTOAccessException dtoaccess) {
				log.error(
						"Period not readable: "
								+ currCompany.get(DTOCompany.Key.NAME)
								+ " year: "
								+ currPeriod.get(DTOPeriod.Key.NAME), dtoaccess);
				currPeriod.put(DTOPeriod.Key.LIABILITIES, new DoubleValue(0.0));
			}

			// TODO Überlegen, ob wir hier mit einer Periode weniger arbeiten,
			// weil interne Berechnung fehl schlägt!
			// Sollte dies nicht gewählt werden, bitte Rücksprache mit Yannick

			if (firstPeriod) {

				firstFCF = (DoubleValue) currPeriod.get(DTOPeriod.Key.FCF);
				firstDouble = firstFCF.getValue();
				firstPeriod = false;

			}

			helpFCF = (DoubleValue) currPeriod.get(DTOPeriod.Key.FCF);
			helpDouble = helpFCF.getValue();
			if (firstDouble < 0) {
				normedFCF = new DoubleValue(
						((helpDouble / firstDouble) * (-1)) + 1);
				currPeriod.put(DTOPeriod.Key.FCF, normedFCF);

			} else if (firstDouble > 0) {
				normedFCF = new DoubleValue(((helpDouble / firstDouble) - 1));
				currPeriod.put(DTOPeriod.Key.FCF, normedFCF);

			}

			i++;
		}

		firstPeriod = true;
		currCompany.setNormed(true);
	}

	private void computeRating(DTOCompany branchSpecificRepresentative,
			DTOBusinessData businessData) {

		DoubleValue normedCFforPeriod = null;
		DoubleValue bsrCF = null;
		DTOPeriod currPeriod = null;
		DTOBranch currBranch = getSelectedBranch(businessData);

		// Do the Branch has Companies?
		List<DTOCompany> companyList = currBranch.getChildren();
		Iterator<DTOCompany> CompanyItr = companyList.iterator();

		double result = 0;

		while (CompanyItr.hasNext()) {
			DTOCompany currCompany = CompanyItr.next();

			// echo them
			System.out.println("----" + currCompany.get(DTOCompany.Key.NAME));

			List<DTOPeriod> periodList = currCompany.getChildren();
			Iterator<DTOPeriod> periodItr = periodList.iterator();

			// iteriert über die einzelnen Perioden der Unternehmen der
			// selektierten Branche
			// zur Güteberechnung des vorher berechneten, branchenspezifischen
			// Vertreters
			for (int count = 0; periodItr.hasNext(); count++) {
				currPeriod = periodItr.next();

				normedCFforPeriod = (DoubleValue) currPeriod
						.get(DTOPeriod.Key.FCF);

				bsrCF = getBSRCFforPeriod(count, branchSpecificRepresentative);

				result = result
						+ ((normedCFforPeriod.getValue() - bsrCF.getValue()) * (normedCFforPeriod
								.getValue() - bsrCF.getValue()));

			}

		}
		this.ratingBSR = result / (companyList.size());
	}

	private DoubleValue getBSRCFforPeriod(int period,
			DTOCompany branchSpecificRepresentative) {
		DTOPeriod currPeriod = null;

		List<DTOPeriod> periodList = branchSpecificRepresentative.getChildren();
		Iterator<DTOPeriod> periodItr = periodList.iterator();

		for (int count = 0; periodItr.hasNext();) {
			currPeriod = periodItr.next();
			if (count == period)
				break;
		}
		return ((DoubleValue) currPeriod.get(DTOPeriod.Key.FCF));
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
		return this.ratingBSR;
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

	private double[] getAverage(double[][] table, String choice) {
		double[] result = null;

		if (choice == "company") {
			result = new double[table.length];
			for (int spalte = 0; spalte < table.length; spalte++) {
				for (int zeile = 0; zeile < table[0].length; zeile++) {
					result[spalte] = result[spalte] + table[spalte][zeile];
				}
				result[spalte] = result[spalte] / table[0].length;

			}

		} else if (choice == "year") {
			result = new double[table[0].length];
			for (int zeile = 0; zeile < table[0].length; zeile++) {
				for (int spalte = 0; spalte < table.length; spalte++) {
					result[zeile] = result[zeile] + table[zeile][spalte];
				}
				result[zeile] = result[zeile] / table.length;
			}

		}
		return result;
	}

	private double[] getCubeRoot(double[] averageCompanyNotNormed) {
		double[] result = new double[averageCompanyNotNormed.length];
		for (int i = 0; i < averageCompanyNotNormed.length; i++) {
			result[i] = Math.pow(Math.abs(averageCompanyNotNormed[i]), (1 / 3));
		}

		return result;
	}

	private double[][] trimmedAverage(double[][] table, double factor) {
		double[] avg = getAverage(table, "year");

		for (int zeile = 0; zeile < table[0].length; zeile++) {
			for (int spalte = 1; spalte < table.length; spalte++) {
				if ((avg[zeile] * (-1) * factor) > table[spalte][zeile]) {
					table[spalte][zeile] = Double.MAX_VALUE;
				} else if ((avg[zeile] * factor) < table[spalte][zeile]) {
					table[spalte][zeile] = Double.MAX_VALUE;
				}
			}
		}
		return table;
	}

	private double[] getSpecificAverage(double[][] table, double[] cubeRoot) {
		double weight = 0;
		double[] result = new double[cubeRoot.length];

		for (int zeile = 0; zeile < table[0].length; zeile++) {
			for (int spalte = 0; spalte < table.length; spalte++) {
				if (table[spalte][zeile] != Double.MAX_VALUE) {
					result[zeile] = result[zeile] + table[spalte][zeile]
							* cubeRoot[zeile];
					weight = weight + cubeRoot[zeile];

				}

			}
			result[zeile] = result[zeile] / weight;
			weight = 0;
		}
		return result;
	}

	private double[][] createTable(DTOBranch currBranch) {

		double[][] companiesAndPeriods = getNumberOfCompaniesAndPeriods(currBranch);

		// Do the Branch has Companies?
		List<DTOCompany> companyList = currBranch.getChildren();
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
		return companiesAndPeriods;
	}

	/* Specified by interface/super class. */
	@Override
	public Color getEvaluationOfRating() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	/* Specified by interface/super class. */
	@Override
	public DTOCompany getArithmeticAverage(String choice,
			DTOBranch currNormedBranch) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

}
