package org.bh.tests.junit.branchSpecificRepresentative;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bh.data.DTOBranch;
import org.bh.data.DTOBranchSpecificRep;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.plugin.branchSpecificRepresentative.calc.BranchSpecificCalculator;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;

import junit.framework.TestCase;


/**
 * This is just a test!
 * DO NOT USE! THIS IS NOT A UNIT TEST!
 *
 * @author Denis Roster
 * @version 1.0, 03.01.2012
 *
 */

public class BranchCalcTest extends TestCase {	
	
	public void testBranchCalculator()
	{		
		
		XMLImport myImport = new XMLImport("src/org/bh/companydata/periods.xml");
		try {
			// BusinessData aufbauen
			DTOBusinessData myDTO = (DTOBusinessData) myImport.startImport();
			
			// Default-Konstruktor aufrufen
			BranchSpecificCalculator bsc = new BranchSpecificCalculator();
			
			// Branch-Specific-Representative ermitteln lassen (Normierung & Mittelwert)
			ArrayList<DTOBranchSpecificRep> result = new ArrayList<DTOBranchSpecificRep>();
			
			result = bsc.calculateBSR(myDTO);
			
			// Ergebnisse ausgeben
			Iterator it = result.iterator();
			while(it.hasNext()){
				System.out.println(it.next());
			}
			
									
			
		} catch (XMLNotValidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
		
}
