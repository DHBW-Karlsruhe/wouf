package org.bh.tests.junit.branchSpecificRepresentative;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.platform.Services;
import org.bh.plugin.branchSpecificRepresentative.calc.BranchSpecificCalculator;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.bh.data.DTOBranch;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.platform.Services;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementCostOfSales;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementTotalCost;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;

/**
 * This is just a test! DO NOT USE! THIS IS NOT A UNIT TEST!
 * 
 * @author Denis Roster, Sebastian Schumacher
 * @version 1.0, 03.01.2012
 * 
 */

public class BranchCalcTest extends TestCase {

	public void testBranchCalculator() {

		// Init Number Serices
		Services.initNumberFormats();

		XMLImport myImport = new XMLImport("src/org/bh/companydata/periods.xml");
		try {
			// BusinessData aufbauen
			DTOBusinessData myDTO = (DTOBusinessData) myImport.startImport();

			// Default-Konstruktor aufrufen
			BranchSpecificCalculator bsc = new BranchSpecificCalculator();

			// Branch-Specific-Representative ermitteln lassen (Normierung &
			// Mittelwert)
			DTOCompany result = new DTOCompany();

			// Branchspezifischen Vertreter ermitteln (= normieren der CFs &
			// Mittelwertsberechnung (normal/gestutzt))
			result = bsc.calculateBSR(myDTO);

		} catch (XMLNotValidException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
