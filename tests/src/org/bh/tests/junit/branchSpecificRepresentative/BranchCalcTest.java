package org.bh.tests.junit.branchSpecificRepresentative;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.bh.data.DTO;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.platform.PlatformController;
import org.bh.platform.Services;
import org.bh.plugin.branchSpecificRepresentative.calc.BranchSpecificCalculator;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.StringValue;

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
			PlatformController.setBusinessDataDTO(myDTO);
			DTOScenario scenario = new DTOScenario();
			scenario.put(DTOScenario.Key.INDUSTRY, new StringValue("C.25.9"));

			// Default-Konstruktor aufrufen
			BranchSpecificCalculator bsc = new BranchSpecificCalculator();

			// Branch-Specific-Representative ermitteln lassen (Normierung &
			// Mittelwert)
			DTOCompany result = new DTOCompany();

			// Branchspezifischen Vertreter ermitteln (= normieren der CFs &
			// Mittelwertsberechnung (normal/gestutzt))
			result = bsc.calculateBSR(myDTO, scenario);
			
			// Ergebnisse prüfen
			List<DTOPeriod> periodList = result.getChildren();
			Iterator<DTOPeriod> PeriodItr = periodList.iterator();
			while (PeriodItr.hasNext()) {
				DTOPeriod currPeriod = PeriodItr.next();
				DoubleValue wert = (DoubleValue) currPeriod.get(DTOPeriod.Key.FCF);
				double dwert = wert.getValue();
				String name = "" + currPeriod.get(DTOPeriod.Key.NAME);
				
				if(name.equals("2.010")){
					Assert.assertTrue(dwert == -1.0444681283571562);
				}else if (name.equals("2.009")){
					Assert.assertTrue(dwert == -0.08466592699973594);
				}else if (name.equals("2.008")){
					Assert.assertTrue(dwert == 0.6606482682053639);
				}else if (name.equals("2.007")){
					Assert.assertTrue(dwert == 0.04321855144556013);
				} else if (name.equals("2.006")){
					Assert.assertTrue(dwert == 0.0);
				}
			}
			

		} catch (XMLNotValidException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
