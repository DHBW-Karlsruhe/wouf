package org.bh.tests.junit.branchSpecificRepresentative;

import java.io.IOException;

import junit.framework.TestCase;

import org.bh.data.DTO;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.platform.Services;
import org.bh.plugin.branchSpecificRepresentative.calc.BranchSpecificCalculator;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;
import org.junit.After;
import org.junit.Before;

/**
 * This is just a test! DO NOT USE! THIS IS NOT A UNIT TEST!
 * 
 * @author Denis Roster, Sebastian Schumacher
 * @version 1.0, 03.01.2012
 * 
 * @update Yannick Rödl, 07.01.2012; Change Events schmeißen ausgeschaltet.
 */

public class BranchCalcTest extends TestCase {

	@Before
	public void setUp(){
		DTO.setThrowEvents(false); //Damit die Logfiles nicht so groß werden.
	}
	
	@After
	public void tearDown(){
		DTO.setThrowEvents(true);
	}
	
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
			result = bsc.calculateBSR(myDTO, null);

		} catch (XMLNotValidException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
