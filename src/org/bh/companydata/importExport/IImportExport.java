package org.bh.companydata.importExport;

import java.util.HashMap;

/**
 * <short_description>
 *
 * <p>
 * <detailed_description>
 *
 * @author Matze
 * @version 1.0, 04.01.2012
 *
 */
public interface IImportExport {
	
	/**
	 * 
	 * @param midCat
	 * @param subCat
	 * @return
	 */
	String getName(String firstCat, String midCat, String subCat);

	/**
	 * 
	 * @return
	 */
	HashMap<String, String> getBranch();
	
	/**
	 * 
	 * @return
	 */
	IImportExport createNewInstance();
}
