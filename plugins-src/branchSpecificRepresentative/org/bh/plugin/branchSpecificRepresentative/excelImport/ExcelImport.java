/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber * : Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.plugin.branchSpecificRepresentative.excelImport;
/*
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;
import org.bh.data.DTOAccessException;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOPeriod;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.StringValue;
import org.bh.data.DTOBranch;
import org.bh.data.DTOCompany;
import org.bh.platform.Services;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementCostOfSales;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementTotalCost;
*/

/**
 * Class for importing company data (branch and period data) from given Excel files
 * The method importData() should be called one after or while the program starts,
 * for generating the business data for further calculations.
 * 
 * THIS SHOULD BE CALLED ONCE FOR CONVERTING THE EXCEL TO XML!
 * 
 * @author Lukas Lochner
 * @version 1.0, 02.12.2011
 * 
 */


/*
public class ExcelImport {

	private static final Logger log = Logger.getLogger(ExcelImport.class);
	
	public void importCompanyData(DTOBusinessData businessData)
	{					
		
		// Init Number Serices
		Services.initNumberFormats();
		
		try {
			
			// Create new File Objects
			File companyData  = new File("src/org/bh/companydata/periods.xls");		
			
			// Set Excel Settings / Encoding
			WorkbookSettings worbSet = new WorkbookSettings();
			worbSet.setEncoding("Cp1252");			
		
        	// Get Workbook out of Files
        	Workbook companyWorkbook = Workbook.getWorkbook(companyData, worbSet);        	
        	
        	// Get Sheets out of Workbook Objects
        	Sheet companySheet = companyWorkbook.getSheet(0);       	
 
       		// Set Result Objects
        	DTOBranch currBranchDTO;
        
        	// Iterate the complete company data
        	for(int i = 6; i < companySheet.getRows(); i++)
        	{        		
        		
        		// Main Company Data
            	Cell branch 		= companySheet.getCell(0, i);
            	Cell companyName 	= companySheet.getCell(1, i);        	
            	
            	// First Step  => check if Branch DTO does exist to the current Branch
    			// Split the String
    			String[] branchKey = branch.getContents().split("\\.");            	
            	currBranchDTO = this.getBranchOutOfBusinessDataDTO(businessData, branchKey[0], branchKey[1], branchKey[2]);
            	// if not found, throw exception
            	if(currBranchDTO == null)  log.error("Could't find Branch! ");       
            	            	
            	
            	// Second Step => check if Branch has a Company Child that is currently given
            	// 				  if not, create a child (done in the called method)            	
                DTOCompany currCompanyDTO = this.getCompanyOutOfBranchDTO(currBranchDTO, companyName.getContents());           	
            	
            	
            	// Third Step  => add current period data to company
                DTOPeriod currPeriod = new DTOPeriod();

                // Put relevant Business Data into DTO
                // Year - name of the period
                currPeriod.put(DTOPeriod.Key.NAME, new StringValue(companySheet.getCell(2, i).getContents()));
                
                //
                //
                // If the FCF and the Liabilities are pregiven
                               
                               
	            // Liabilities
	            if(companySheet.getCell(13, i).getType() == CellType.NUMBER) {
	             	
	            	try {	              	
	            		currPeriod.put(DTOPeriod.Key.LIABILITIES, new DoubleValue(Double.parseDouble(companySheet.getCell(13, i).getContents())));
	            	} catch (Exception e) {
						e.printStackTrace();
					}
	            	
	            }
	                
	            // Free Cash Flow
	            if(companySheet.getCell(27, i).getType() == CellType.NUMBER) {
	                	
	            	try {	              	
	            		currPeriod.put(DTOPeriod.Key.FCF, new DoubleValue(Double.parseDouble(companySheet.getCell(27, i).getContents())));
	            	} catch (Exception e) {
						e.printStackTrace();
					}
	            	
	            }
				
                
                //
                // Create new BalanceSheet
                DTOGCCBalanceSheet currBalanceSheet = new DTOGCCBalanceSheet();
                
                // Fill in the Parameters
                
                // Imaterielle Vermögensgegenstände
                if(companySheet.getCell(4, i).getType() == CellType.NUMBER) {
                	currBalanceSheet.put(DTOGCCBalanceSheet.Key.IVG, new DoubleValue(Double.parseDouble(companySheet.getCell(4, i).getContents())));
                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 4, " + i);
                
                // Sachanlagen
                if(companySheet.getCell(5, i).getType() == CellType.NUMBER) {                
                	currBalanceSheet.put(DTOGCCBalanceSheet.Key.SA, new DoubleValue(Double.parseDouble(companySheet.getCell(5, i).getContents())));
                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 5, " + i);                
                
                // Finanzanlagen
                if(companySheet.getCell(6, i).getType() == CellType.NUMBER) {                
                	currBalanceSheet.put(DTOGCCBalanceSheet.Key.FA, new DoubleValue(Double.parseDouble(companySheet.getCell(6, i).getContents())));
                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 6, " + i);                

            	// Vorräte
                if(companySheet.getCell(7, i).getType() == CellType.NUMBER) {                
                	currBalanceSheet.put(DTOGCCBalanceSheet.Key.VOR, new DoubleValue(Double.parseDouble(companySheet.getCell(7, i).getContents())));
                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 7, " + i);                         
                
                // Forderungen und sonstige Vermögensgegenstände
                if(companySheet.getCell(8, i).getType() == CellType.NUMBER) {                
                	currBalanceSheet.put(DTOGCCBalanceSheet.Key.FSVG, new DoubleValue(Double.parseDouble(companySheet.getCell(8, i).getContents())));
                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 8, " + i);                
                
            	// Aktien
                if(companySheet.getCell(9, i).getType() == CellType.NUMBER) {                
                	currBalanceSheet.put(DTOGCCBalanceSheet.Key.WP, new DoubleValue(Double.parseDouble(companySheet.getCell(9, i).getContents())));
                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 9, " + i);                
                
            	// Kassenbestand, Bundesbankguthaben, Guthaben bei Kreditinstituten
                if(companySheet.getCell(10, i).getType() == CellType.NUMBER) {                
                	currBalanceSheet.put(DTOGCCBalanceSheet.Key.KBGGKS, new DoubleValue(Double.parseDouble(companySheet.getCell(10, i).getContents())));
                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 10, " + i);                
           
                // Eigenkapital
                if(companySheet.getCell(11, i).getType() == CellType.NUMBER) {                
                	currBalanceSheet.put(DTOGCCBalanceSheet.Key.EK, new DoubleValue(Double.parseDouble(companySheet.getCell(11, i).getContents())));
                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 11, " + i);                
                
                // Rückstellungen
                if(companySheet.getCell(12, i).getType() == CellType.NUMBER) {                
                	currBalanceSheet.put(DTOGCCBalanceSheet.Key.RS, new DoubleValue(Double.parseDouble(companySheet.getCell(12, i).getContents())));
                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 12, " + i);                
                                      
                // Verbindlichkeiten
                if(companySheet.getCell(13, i).getType() == CellType.NUMBER) {                
                	currBalanceSheet.put(DTOGCCBalanceSheet.Key.VB, new DoubleValue(Double.parseDouble(companySheet.getCell(13, i).getContents())));                
                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 13, " + i);                
                                        
                // Add the BalanceSheet to the Period
                currPeriod.addChild(currBalanceSheet);
                
                
                //
                //	NACH UMSATZKOSTENVERFAHREN
                // 
                
                if(companySheet.getCell(24, i).getType() == CellType.NUMBER) {
                	
	                //
	                // Create new ProfitLossStatementCostOfSales
	                DTOGCCProfitLossStatementCostOfSales currProfitStatementCostOfSales = new DTOGCCProfitLossStatementCostOfSales();
	                
	                //
	                // Fill in the Parameters       
	                //
                	
	                // Umsatzerlöse
	                if(companySheet.getCell(22, i).getType() == CellType.NUMBER) {                
	                	currProfitStatementCostOfSales.put(DTOGCCProfitLossStatementCostOfSales.Key.UE, new DoubleValue(Double.parseDouble(companySheet.getCell(22, i).getContents())));                
	                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 22, " + i); 
	                
	                // Sonstige betriebliche Erträge
	                if(companySheet.getCell(23, i).getType() == CellType.NUMBER) {                
	                	currProfitStatementCostOfSales.put(DTOGCCProfitLossStatementCostOfSales.Key.SBE, new DoubleValue(Double.parseDouble(companySheet.getCell(23, i).getContents())));                
	                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 23, " + i); 	                

	                // Herstellkosten
	                if(companySheet.getCell(24, i).getType() == CellType.NUMBER) {                
	                	currProfitStatementCostOfSales.put(DTOGCCProfitLossStatementCostOfSales.Key.HK, new DoubleValue(Double.parseDouble(companySheet.getCell(24, i).getContents())));                
	                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 24, " + i);	    
	                
	                // Vertreibskosten + allg. Verwaltungskosten + sonst. Betriebliche Aufwendungen
	                if(companySheet.getCell(25, i).getType() == CellType.NUMBER) {                
	                	currProfitStatementCostOfSales.put(DTOGCCProfitLossStatementCostOfSales.Key.VVSBA, new DoubleValue(Double.parseDouble(companySheet.getCell(25, i).getContents())));                
	                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 25, " + i);	   
                	
	                // Add the ProfitStatement to the Period
	                currPeriod.addChild(currProfitStatementCostOfSales); 	                
                	
                }
                else
                {
                                
	                //
	                // Create new ProfitLossStatementTotal
	                DTOGCCProfitLossStatementTotalCost currProfitStatementTotal = new DTOGCCProfitLossStatementTotalCost();
	                
	                //
	                // Fill in the Parameters       
	                //
	                
	                // Umsatzerlöse
	                if(companySheet.getCell(14, i).getType() == CellType.NUMBER) {                
	                	currProfitStatementTotal.put(DTOGCCProfitLossStatementTotalCost.Key.UE, new DoubleValue(Double.parseDouble(companySheet.getCell(14, i).getContents())));                
	                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 14, " + i);  
	                
	                // Sonstige betriebliche Erträge
	                if(companySheet.getCell(15, i).getType() == CellType.NUMBER) {                
	                	currProfitStatementTotal.put(DTOGCCProfitLossStatementTotalCost.Key.SBE, new DoubleValue(Double.parseDouble(companySheet.getCell(15, i).getContents())));                
	                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 15, " + i);          
	                
	                // Andere aktivierte Eigenleistungen
	                if(companySheet.getCell(16, i).getType() == CellType.NUMBER) {                
	                	currProfitStatementTotal.put(DTOGCCProfitLossStatementTotalCost.Key.AAE, new DoubleValue(Double.parseDouble(companySheet.getCell(16, i).getContents())));                
	                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 16, " + i);     
	                
	                // Sonstige betriebliche Aufwendungen
	                if(companySheet.getCell(17, i).getType() == CellType.NUMBER) {                
	                	currProfitStatementTotal.put(DTOGCCProfitLossStatementTotalCost.Key.SBA, new DoubleValue(Double.parseDouble(companySheet.getCell(17, i).getContents())));                
	                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 17, " + i);                        
	
	                // Zinsen und ähnliche Aufwendungen
	                if(companySheet.getCell(18, i).getType() == CellType.NUMBER) {                
	                	currProfitStatementTotal.put(DTOGCCProfitLossStatementTotalCost.Key.ZA, new DoubleValue(Double.parseDouble(companySheet.getCell(18, i).getContents())));                
	                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 18, " + i);                        
	                
	                // Materialaufwand
	                if(companySheet.getCell(19, i).getType() == CellType.NUMBER) {                
	                	currProfitStatementTotal.put(DTOGCCProfitLossStatementTotalCost.Key.MA, new DoubleValue(Double.parseDouble(companySheet.getCell(19, i).getContents())));                
	                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 19, " + i);
	                
	                // Personalaufwand
	                if(companySheet.getCell(20, i).getType() == CellType.NUMBER) {                
	                	currProfitStatementTotal.put(DTOGCCProfitLossStatementTotalCost.Key.PA, new DoubleValue(Double.parseDouble(companySheet.getCell(20, i).getContents())));                
	                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 20, " + i);                    
	
	                // Abschreibungen
	                if(companySheet.getCell(21, i).getType() == CellType.NUMBER) {                
	                	currProfitStatementTotal.put(DTOGCCProfitLossStatementTotalCost.Key.ABSCH, new DoubleValue(Double.parseDouble(companySheet.getCell(21, i).getContents())));                
	                } else System.out.println("NOT A NUMBER! CHECK EXCEL ON 21, " + i);
	                
	               
	                // Add the ProfitStatement to the Period
	                currPeriod.addChild(currProfitStatementTotal);    
	                
                }
                
                // Add Period to Company
                currCompanyDTO.addChild(currPeriod);                
            	
        	}
        			
		}
		
        catch(IOException e)
        {
        	log.error("Could't find File! ", e);        	
        }
        catch(BiffException e)
        {
        	log.error("Binary Interchange File Format seems to be corrupted! ", e);
        }
        
	}
    
	public DTOBusinessData importBranchData()
	{
		
		// defintion of returning value
		DTOBusinessData myBranches = new DTOBusinessData();		
		
		try {
					
			// Create new File Objects
			File branchData  = new File("src/org/bh/companydata/periods.xls");			
			
			// Set Excel Settings / Encoding
			WorkbookSettings worbSet = new WorkbookSettings();
			worbSet.setEncoding("Cp1252");			
		
        	// Get Workbook out of Files
        	Workbook branchWorkbook = Workbook.getWorkbook(branchData, worbSet);        	
        	
        	// Get Sheets out of Workbook Objects
        	Sheet branchSheet = branchWorkbook.getSheet(0);       	
 
       		// Set Result Object
        	DTOBranch branchDTO;
       	
        	
        	// Parse Branch Data
        	HashSet<String> uniqueBranchs = new HashSet<String>();
        	for(int i = 6; i < branchSheet.getRows(); i++)
        	{
        		uniqueBranchs.add(branchSheet.getCell(0, i).getContents());
        	}             
        	
        	System.out.println("Branches: " + uniqueBranchs);
        	
        	// Iterate unique Branches
        	Iterator<String> hashSetIterator = uniqueBranchs.iterator();        	 
        	while(hashSetIterator.hasNext())
        	{
        		
        		try {
        			
        			// Try to initialize a new Branches DTO
        			branchDTO = new DTOBranch();            	
        			
        			// Split the String
        			String[] branchKey = hashSetIterator.next().split("\\.");        		
        			
        			// Put data into Branch DTO
        			branchDTO.put(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY, new StringValue(branchKey[0]));
        			branchDTO.put(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY, new StringValue(branchKey[1]));
        			branchDTO.put(DTOBranch.Key.BRANCH_KEY_SUB_CATEGORY, new StringValue(branchKey[2]));        			

        			
        			// add current Branch to Root DTO
        			myBranches.addChild(branchDTO);      	
        			
        		
        		} catch (DTOAccessException e) {
        			log.debug("A DTO coudn't be accessed properbly");
        		}          		
        		
        	}
        	
		
		}
        catch(IOException e)
        {
        	log.error("Could't find File! ", e);        	
        }
        catch(BiffException e)
        {
        	log.error("Binary Interchange File Format seems to be corrupted! ", e);
        }
        
        // return array
		return myBranches;       
		
	}
	
	private DTOBranch getBranchOutOfBusinessDataDTO(DTOBusinessData businessData, String branchKeyMain,  String branchKeyMiddle,  String branchKeySub )
	{
		// returns a certain BranchDTO out of the BusinessDataDTO
   		// Set Result Objects
    	DTOBranch branchDTO = null;
    	
		// Convert into a List
		List<DTOBranch> branchList = businessData.getChildren();    	
    	
    	// Iterate all Branches
    	Iterator<DTOBranch> itr = branchList.iterator();
    	while (itr.hasNext()) {
    		DTOBranch currBranch = itr.next();
    		if(currBranch.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY).toString().equals(branchKeyMain) && currBranch.get(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY).toString().equals(branchKeyMiddle) && currBranch.get(DTOBranch.Key.BRANCH_KEY_SUB_CATEGORY).toString().equals(branchKeySub))
    		{
    			return currBranch;
    		}            		
    	}
    	// return null, if not found
		return branchDTO;    			
	}
	
	private DTOCompany getCompanyOutOfBranchDTO(DTOBranch branch, String companyName)
	{
   		// Set Result Objects
		DTOCompany returnCompanyDTO;		
		
		// Check if Branch has already the Company Child
		List<DTOCompany> companyList = branch.getChildren();
		
		// Iterate all Company DTOs
		Iterator<DTOCompany> itr = companyList.iterator(); 
		while(itr.hasNext()) {
    		DTOCompany currCompany = itr.next();			
    		if(currCompany.get(DTOCompany.Key.NAME).toString().equals(companyName))
    		{
    			// return the existing company
    			return currCompany;
    		}
		} 
		
		// If Company was not found, create new Company
		returnCompanyDTO = new DTOCompany();            	
		
		// Put data into Company DTO
		returnCompanyDTO.put(DTOCompany.Key.NAME, new StringValue(companyName));		
		
		// add to Branch DTO
		branch.addChild(returnCompanyDTO);
		
		// return new Company DTO
		return returnCompanyDTO;
	}	
	
}
*/