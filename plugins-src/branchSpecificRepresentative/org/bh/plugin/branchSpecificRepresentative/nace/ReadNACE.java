package org.bh.plugin.branchSpecificRepresentative.nace;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.bh.companydata.importExport.INACEImport;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.i18n.BHTranslator;

/**
 * <short_description>
 * Provides a translation for a given branch.
 * Provides all branches of the first three NACE-level.
 * <p>
 * <detailed_description>
 * This class provides the functionality to read the name of a branch identified by category keys
 * out of a provided XML file. There are XML files in English and German for the translation functionality. 
 * Additionally you can read out all branches belonging to the first three NACE-level.
 * @author Matthias Beste
 * @version 1.0, 02.01.2012
 * 
 */
public class ReadNACE implements INACEImport, IPlatformListener{

	private String translatedBranch, idValue, idLevelValue, branchName, branchIdentifier, lastLevelOne, lastLevelTwo;
	private File importFile = null;
	private Document doc;
	private Element claset,anItem, label, labelText, classification;
	private HashMap<String, String> hirarchicalItems;
	private Locale locale = null;

	private static String DE_FILE = "org/bh/companydata/nace/de.xml";
	private static String EN_FILE = "org/bh/companydata/nace/en.xml";
	
//	add a platform listener
	public ReadNACE(){
		Services.addPlatformListener(this);
	}
	
	
	public ReadNACE(String importFilePath) {
		super();
		this.importFile = new File(importFilePath);
	}

//	Parse XML Document
//	Create a SAX builder object to parse the xml document
//	get the current language and define the required xml file
	public void parseXML() {
		if(locale == null){
			locale = BHTranslator.getLoc();
			if(locale == Locale.GERMAN){
				importFile = new File(DE_FILE);
			} else if(locale == Locale.ENGLISH){
				importFile = new File(EN_FILE);
			}
			if(!importFile.exists()){
				if(locale == Locale.GERMAN){
					importFile = new File("src/" + DE_FILE);
				} else if(locale == Locale.ENGLISH){
					importFile = new File("src/" + EN_FILE);
				}
			}
		} else {
			return;
		}
		
		Builder builder = new Builder();
		try {
			doc = builder.build(importFile);
			claset = doc.getRootElement();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ValidityException e) {
			e.printStackTrace();
		} catch (ParsingException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
//	get the translation for a given branch
	public String getName(String firstCat, String midCat, String subCat){

		parseXML();
//		two categories need to have an empty String
		if(midCat.equals("") & subCat.equals(""))
			branchIdentifier = firstCat;
		else if(firstCat.equals("") & subCat.equals(""))
			branchIdentifier = midCat;
		else if(firstCat.equals("") & midCat.equals(""))
			branchIdentifier = subCat;
		else
			return null;
		
		classification = claset.getFirstChildElement("Classification");
		if (classification != null)
		{
//		get all branches
			Elements Item = classification.getChildElements("Item");
			int anzahl = Item.size();
			for (int i=0; i<anzahl; i++) {
				anItem = (Element) Item.get(i);
				idValue = anItem.getAttributeValue("id");
				
//				compare the branches' id's with the branchIdentifier
				if(idValue.equals(branchIdentifier)){
					label = anItem.getFirstChildElement("Label");
					labelText = label.getFirstChildElement("LabelText");
					translatedBranch = labelText.getValue().trim();

				return translatedBranch;
				}
			}
		}
		return null;
		
		
	}
	
	@Override
	public HashMap<String, String> getBranch(){
		parseXML();
		
		classification = claset.getFirstChildElement("Classification");
		
		if (classification != null)
		{
//			first String: branch id, second String: branch name
			hirarchicalItems = new HashMap<String, String>();
			
//			get all branches
			Elements Item = classification.getChildElements("Item");
			int count = Item.size();
			
			for (int i=0; i<count; i++) {
				anItem = (Element) Item.get(i);
				idValue = anItem.getAttributeValue("id");
				idLevelValue = anItem.getAttributeValue("idLevel");
				
//				only read the first three level
				if(idLevelValue.equals("1") | idLevelValue.equals("2") |idLevelValue.equals("3")){
				
//					produce a hierarchical structure like in the xml
					switch(Integer.parseInt(idLevelValue))
						{
						case 1:
							lastLevelOne = idValue;
							break;
						case 2:	
							lastLevelTwo = idValue;
							idValue = lastLevelOne + "." + lastLevelTwo;
							break;
						case 3:
							idValue = lastLevelOne + "." + lastLevelTwo + "." + idValue.substring(3);
							break;
						}
				
					label = anItem.getFirstChildElement("Label");
					labelText = label.getFirstChildElement("LabelText");
					
//					get the name of the branch without leading spaces
					branchName = labelText.getValue().trim();
					
//					save in the hashmap
					hirarchicalItems.put(idValue, branchName);
				}	
					
			}
			return hirarchicalItems;
		}
		return null;
	}

	/* Specified by interface/super class. */
	@Override
	public INACEImport createNewInstance() {
		return new ReadNACE();
	}

	/* Specified by interface/super class. */
	@Override
	public void platformEvent(PlatformEvent e) {
		if(e.getEventType() == PlatformEvent.Type.LOCALE_CHANGED){
			locale = null;
		}
	}
}