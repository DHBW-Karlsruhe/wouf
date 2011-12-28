package org.bh.tests.junit.branchSpecificRepresentative;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.bh.data.DTOBusinessData;
import org.bh.data.DTOPeriod;
import org.bh.plugin.branchSpecificRepresentative.data.DTOBranch;
import org.bh.plugin.branchSpecificRepresentative.data.DTOCompany;
import org.bh.plugin.branchSpecificRepresentative.excelImport.ExcelImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;

import junit.framework.TestCase;


/**
 * This is just a test!
 * DO NOT USE! THIS IS NOT A UNIT TEST!
 *
 * @author Lukas Lochner
 * @version 1.0, 02.12.2011
 *
 */

public class XMLImportTest extends TestCase {	
	
	public void testImport()
	{		
		
		XMLImport myImport = new XMLImport("src/org/bh/companydata/periods.xml");
		try {
			DTOBusinessData myDTO = (DTOBusinessData) myImport.startImport();
			
			//
			// just play with the BusinessData DTO! :-)
			// Like...
			//
			
			List<DTOBranch> branchList = myDTO.getChildren();
			
			// Iterate all Company DTOs
			Iterator<DTOBranch> itr = branchList.iterator(); 
			while(itr.hasNext()) {
	    		DTOBranch currBranch = itr.next();			
	    			
	    		// echos the branch keys
	    		System.out.println(currBranch.get(DTOBranch.Key.BRANCH_KEY));
	    		
	    		//
	    		// Do the Branch has Companies?	    		
	    		List<DTOCompany> companyList = currBranch.getChildren();	    		
	    		Iterator<DTOCompany> CompanyItr = companyList.iterator(); 
	    		while(CompanyItr.hasNext()) {
	        		DTOCompany currCompany = CompanyItr.next();			
	        		
	        		// echo them
	        		System.out.println("----" + currCompany.get(DTOCompany.Key.NAME));
	        		
	        		//
	        		// Do the Company has any Periods?
		    		List<DTOPeriod> periodList = currCompany.getChildren();	    		
		    		Iterator<DTOPeriod> PeriodItr = periodList.iterator(); 
		    		while(PeriodItr.hasNext()) {
		        		DTOPeriod currPeriod = PeriodItr.next();			
		        		
		        		// echo the name of the Period
		        		System.out.println("---------" + currPeriod.get(DTOPeriod.Key.NAME));		        			        		
		        		
		    		} 	 	        		
	        		
	        		
	    		} 	    		
	    		
	    		
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
