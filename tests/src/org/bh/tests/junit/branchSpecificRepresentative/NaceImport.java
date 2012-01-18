package org.bh.tests.junit.branchSpecificRepresentative;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.bh.companydata.importExport.INACEImport;
import org.bh.platform.Services;
import org.bh.plugin.branchSpecificRepresentative.nace.ReadNACE;

import junit.framework.TestCase;

/**
 * This is just a test!
 * DO NOT USE! THIS IS NOT A UNIT TEST!
 *
 * @author Tim Herzenstiel
 * @version 1.0, 08.01.2012
 *
 */
public class NaceImport extends TestCase {	
	
	public void testImport() throws IOException 
	{	
		String key;
		String text;
		//try{
			//FileWriter de_file = new FileWriter("test.properties");
			//FileWriter en_file = new FileWriter("BHGUIKeys_en.properties");
			//BufferedWriter bf = new BufferedWriter(de_file);
	
		
		ReadNACE naceobj = new ReadNACE("src/org/bh/companydata/nace/en.xml");
		//naceobj.parseXML();
		//INACEImport naceReader = Services.getNACEReader();
		Map<String, String> branches = naceobj.getBranch();
		
		for (Map.Entry<String, String> entry : branches.entrySet()) {
			if(entry.getKey().length() == 1){
				
				text = entry.getKey();
				//text = text.replace(".", "");
				key = text + " = " + text +": " +entry.getValue();
				System.out.println(key);
				//bf.write(key);
				//bf.newLine();
				
				for (Map.Entry<String, String> entry1 : branches.entrySet()) {
					if(entry1.getKey().length() == 4 && entry1.getKey().substring(0, 1).equals(entry.getKey())){
						
						text = entry1.getKey();
						//text = text.replace(".", "");
						key = text + " = " + text +": " +entry1.getValue();
						System.out.println(key);
						//bf.write(key);
						//bf.newLine();
						
						for (Map.Entry<String, String> entry2 : branches.entrySet()) {
							if(entry2.getKey().length() > 4 && entry2.getKey().substring(0, 4).equals(entry1.getKey())){
								
								text = entry2.getKey();
								//text = text.replace(".", "");
								key = text + " = " + text +": " +entry2.getValue();
								System.out.println(key);
								//bf.write(key);
								//bf.newLine();
							}
							
						}
					}
					
				}
				//de_file.close();
			}
			
		}
		//}
		//catch(IOException e){
			
		//}
		
//		two of the following parameters need to have an empty String
		//System.out.println(naceobj.getName("", "", "05"));
//		System.out.println(naceobj.getBranch());
	}

}
