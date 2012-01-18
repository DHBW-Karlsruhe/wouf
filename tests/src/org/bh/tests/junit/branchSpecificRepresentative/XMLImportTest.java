package org.bh.tests.junit.branchSpecificRepresentative;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.bh.data.DTO;
import org.bh.data.DTOAccessException;
import org.bh.data.DTOBranch;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.platform.Services;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementCostOfSales;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementTotalCost;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * This is just a test!
 * DO NOT USE! THIS IS NOT A UNIT TEST!
 *
 * @author Lukas Lochner
 * @version 1.0, 02.12.2011
 *
 */

public class XMLImportTest extends TestCase {	
	
	@Before
	public void setUp(){
		DTO.setThrowEvents(false); //Damit die Logfiles nicht so groß werden.
	}
	
	@After
	public void tearDown(){
		DTO.setThrowEvents(true);
	}
	
	public void testImport()
	{		
		
		// Init Number Serices
		Services.initNumberFormats();		
		
		XMLImport myImport = new XMLImport("src/org/bh/companydata/periods.xml");
		try {
			DTOBusinessData myDTO = (DTOBusinessData) myImport.startImport();
			
			//
			// just play with the BusinessData DTO! :-)
			// Like...
			//
			
			int counter = 0;
			
			List<DTOBranch> branchList = myDTO.getChildren();
			
			// Iterate all Company DTOs
			Iterator<DTOBranch> itr = branchList.iterator(); 
			while(itr.hasNext()) {
	    		DTOBranch currBranch = itr.next();			
	    			
	    		// echos the branch keys
	    		System.out.print(currBranch.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY));
	    		System.out.print(currBranch.get(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY));
	    		System.out.println(currBranch.get(DTOBranch.Key.BRANCH_KEY_SUB_CATEGORY));	    		
	    		
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
		        		counter++;
		        		
		        		//
		        		//	NEW ENHANCEMENT 04.01.2012
		        		//
		        		
		        		// get the Balance Sheet
		        		DTOGCCBalanceSheet myCurrBalanceSheet = (DTOGCCBalanceSheet) currPeriod.getChild(0);		
		        		
		        				        				     		        	
		        		//
		        		// TotalCosts or CostOfSales Method?
		        		
		        		// get the ProfitLossStatements
		        		IPeriodicalValuesDTO myProfitStatement = currPeriod.getChild(1);		        		
		        		// TotalCost Method
		        		if(myProfitStatement.getUniqueId() == DTOGCCProfitLossStatementTotalCost.getUniqueIdStatic()) {
									        		
		        			DTOGCCProfitLossStatementTotalCost myProfitStatementCasted = (DTOGCCProfitLossStatementTotalCost) myProfitStatement;		        			
		        			
		        		}
		        		
		        		// CostOfSales Method
		        		if(myProfitStatement.getUniqueId() == DTOGCCProfitLossStatementCostOfSales.getUniqueIdStatic()) {									        			        
			        		
		        			DTOGCCProfitLossStatementCostOfSales myProfitStatementCasted = (DTOGCCProfitLossStatementCostOfSales) myProfitStatement;
		        			
		        		}
		        		
		        				        		       		        
		        		
		    		} 	 	        		
	        		
	        		
	    		} 	    		
	    		
	    		
			} 
			
			System.out.println(counter);
									
			
		} catch (XMLNotValidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testDataReadable(){
		// Init Number Serices
		Services.initNumberFormats();		
		
		XMLImport myImport = new XMLImport("src/org/bh/companydata/periods.xml");
		try {
			DTOBusinessData myDTO = (DTOBusinessData) myImport.startImport();
			
			List<DTOBranch> branches = myDTO.getChildren();
			
			File f = new File("BROKEN_DATA.data");
			System.out.println(f.getAbsolutePath());
			
			PrintWriter outputStream = new PrintWriter(f);
			
			for(DTOBranch branch: branches){
				
				for(DTOCompany company : branch.getChildren()){
					
					int i = 0;
					
					for(DTOPeriod period: company.getChildren()){
						try{
							if(i != 0){
								period.get(DTOPeriod.Key.FCF);
							}
						
							period.get(DTOPeriod.Key.LIABILITIES);
						
						} catch(DTOAccessException ae){
							ae.printStackTrace(outputStream);
							outputStream.println(branch + " " + company + " " + period);
						}
							
						i++;
					}
					
				}
				
			}
			outputStream.close();
			
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
}
