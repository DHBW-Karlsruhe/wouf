package org.bh.tests.junit.branchSpecificRepresentative;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.plugin.branchSpecificRepresentative.calc.BranchSpecificCalculator;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;


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
			DTOCompany result = new DTOCompany();
			
//			
// TODO wieder einklammern. Sorgt dafür, dass der Build fehlschlägt. Es schmeißt eine ArrayIndexOutOfBoundsException			
			
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
