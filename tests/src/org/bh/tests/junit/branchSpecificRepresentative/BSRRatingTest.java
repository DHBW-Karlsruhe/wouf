package org.bh.tests.junit.branchSpecificRepresentative;

import java.io.IOException;

import junit.framework.TestCase;

import org.bh.data.DTO;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOScenario;
import org.bh.data.types.StringValue;
import org.bh.platform.PlatformController;
import org.bh.platform.Services;
import org.bh.plugin.branchSpecificRepresentative.calc.BranchSpecificCalculator;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * <short_description>
 * 
 * <p>
 * <detailed_description>
 * 
 * @author Günter Hesse
 * @version 1.0, 11-Jan-2012
 * 
 */
public class BSRRatingTest extends TestCase {

	@Before
	public void setUp() {
		DTO.setThrowEvents(false); // Damit die Logfiles nicht so groß werden.
	}

	@After
	public void tearDown() {
		DTO.setThrowEvents(true);
	}

	public void testBranchCalculator() {

		// Init Number Serices
		Services.initNumberFormats();

		XMLImport myImport = new XMLImport("src/org/bh/companydata/periods.xml");

		// BusinessData aufbauen
		DTOBusinessData myDTO = null;
		DTOScenario scenario = new DTOScenario();
		
		try {
			myDTO = (DTOBusinessData) myImport.startImport();
			PlatformController.setBusinessDataDTO(myDTO);
			
			scenario.put(DTOScenario.Key.INDUSTRY, new StringValue("C.25.9"));
		} catch (XMLNotValidException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Default-Konstruktor aufrufen
		BranchSpecificCalculator bsc = new BranchSpecificCalculator();

		// Branch-Specific-Representative ermitteln lassen (Normierung &
		// Mittelwert)
		DTOCompany bsr = new DTOCompany();

		// Branchspezifischen Vertreter ermitteln (= normieren der CFs &
		// Mittelwertsberechnung (normal/gestutzt))
		bsr = bsc.calculateBSR(myDTO, scenario);

		Assert.assertTrue(bsc.getRating() == 316637.4899836928);

	}

}
