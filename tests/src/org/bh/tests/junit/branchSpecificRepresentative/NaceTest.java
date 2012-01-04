package org.bh.tests.junit.branchSpecificRepresentative;

import org.bh.plugin.branchSpecificRepresentative.nace.ReadNACE;

import junit.framework.TestCase;

/**
 * <short_description>
 *
 * <p>
 * <detailed_description>
 *
 * @author Matze
 * @version 1.0, 03.01.2012
 *
 */
public class NaceTest extends TestCase {	
	
	public void testImport()
	{	
		
		ReadNACE naceobj = new ReadNACE("src/org/bh/companydata/nace/de.xml");
		naceobj.parseXML();
//		System.out.println("!" + naceobj.getName("01.11"));
		System.out.println(naceobj.getBranch());
	}

}
