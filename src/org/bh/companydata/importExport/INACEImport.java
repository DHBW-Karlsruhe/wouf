package org.bh.companydata.importExport;

import java.util.HashMap;

/**
 * 
 * Interface for the branch import and the branch translation task
 *
 * @author Matthias Beste
 * @version 1.0, 04.01.2012
 *
 */
public interface INACEImport {
	
	/**
	 * get the translation for a specific branch
	 * Format: two of the following parameters need to have an empty String
	 * @param firstCat branch key e.g. "A"
	 * @param midCat branch key e.g. "AA"
	 * @param subCat branch key e.g. "01"
	 * @return translated branch name
	 */
	String getName(String firstCat, String midCat, String subCat);

	/**
	 * provides all branches of the first three NACE-level.
	 * @return hashmap with branch id as key and branch name as value
	 */
	HashMap<String, String> getBranch();
	
	/**
	 * 
	 * @return instance of INACEImport
	 */
	INACEImport createNewInstance();
}
