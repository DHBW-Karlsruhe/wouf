/*******************************************************************************
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
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
package org.bh.plugin.xmldataexchange.xmlimport;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.bh.data.DTOAccessException;
import org.bh.data.DTOProject;
import org.bh.data.IDTO;
import org.bh.data.types.IValue;
import org.bh.platform.PluginManager;
import org.xml.sax.SAXException;

import nu.xom.*;

/**
 * This class provides the functionality to import a {@link DTOProject} from
 * a XML document.
 * <p> 
 * @author Marcus Katzor
 * @version 1.1, 12.01.2011
 * @update Vito Masiello, Patrick Maisel
 * Klasse wurde an die XOM Library angepasst 
 *
 */
public class XMLImport {
	
	/**
	 * Path to the export file. 
	 */
	private String importFilePath = null;
	
	/**
	 * Reference to the export file.
	 */
	private File importFile = null;
	
	/**
	 * Namespace for all Business Horizon elements in the XML document.
	 */
	private String bhDataNS = "http://www.bh.org/dataexchange";
	
	/**
	 * For logging.
	 */
	private static final Logger log = Logger.getLogger(PluginManager.class);
	

	/**
	 * Creates an instance of {@link XMLImport}
	 * @param importFilePath Path of the file which should be imported.
	 */
	public XMLImport(String importFilePath) {
		super();
		this.importFilePath = importFilePath;
	}
	
	
	/**
	 * Starts the import process and returns a {@link DTOProject} object.
	 * @return {@link DTOProject} or null if something went wrong.	 
	 * @throws XMLNotValidException The XML document is not valid.
	 * @throws XMLImportException An exception occurred while processing the XML document. 
	 * @throws IOException The passed file path does not lead to an accessible file.
	 */
	public IDTO<?> startImport() throws XMLNotValidException, IOException
	{		
		// Check if the path is a valid input 
		if (!checkFile())
		{
			throw new IOException("The chosen file could not be opened.");
		}
		
		// Initialize result object
		IDTO<?> result = null;
		
		// Validate the XML document
		if (validateXMLInput())
		{
			// Parse XML Document
			
			// Create a SAX builder object to parse the xml document
			Builder Builder = new Builder();
			try {
				Document doc = Builder.build(importFile);				
				result = getDTOFromXML(doc.getRootElement());				
			} catch (ParsingException e) {				
				log.error("Parsing exception while importing a XML document", e);
			} 
		}
		
		return result;		
	}
	
	/**
	 * Converts a XML node into a DTO object.
	 * Recursively called.
	 * @param node - Represents a DTO
	 * @return {@link IDTO} - An instance of a DTO
	 */
	@SuppressWarnings("unchecked")
	private IDTO getDTOFromXML(Element node)
	{
		// Get class name of the DTO
		String className = node.getAttributeValue("class");
		
		// Set result object
		IDTO dto = null;
		
		try {
			
			// Try to initialize a DTO with the class name
			dto = (IDTO) Class.forName(className).newInstance();
			
			// Get a list of all value nodes
			Element valuesNode = node.getFirstChildElement("values", bhDataNS);
			ArrayList<Element> listElement = new ArrayList<Element>();
			if (valuesNode != null)
			{
				Elements values =  valuesNode.getChildElements("value", bhDataNS);
				for(int i=0; i< values.size();i++){
						listElement.add(values.get(i));
				}
				// Put all values into the DTO
				for (Element val : listElement)
				{				
					Object[] value = DataTypeConverter.getIValueRepresenation(val, bhDataNS);
					dto.put(value[0], (IValue) value[1]);
				}
			}			
			
			// Get a list of all child nodes
			Element nodeChildren = node.getFirstChildElement("children", bhDataNS);
			if (nodeChildren != null)
			{
				Elements children = nodeChildren.getChildElements();
				ArrayList<Element> listChildren = new ArrayList<Element>();
				for(int i=0; i< children.size();i++){
					listChildren.add(children.get(i));
			}
				for (Element child : listChildren)
				{
					IDTO iChild = getDTOFromXML(child);
					dto.addChild(iChild);
				}
			}
			
		} catch (DTOAccessException e) {
			log.debug("A DTO coudn't be accessed properbly");
		} catch (InstantiationException e) {
			log.error("A DTO could not be initialized. Maybe there is no standard constructor implemented", e);
		} catch (IllegalAccessException e) {
			log.error("A DTO could not be initialized. Maybe there is no standard constructor implemented", e);
		} catch (ClassNotFoundException e) {
			log.error("A DTO could not be found, hence an import is not possible.", e);
		}		
		
		applyDTOSpecificData(dto, node);
		
		return dto;
	}
	
	private void applyDTOSpecificData(IDTO dto, Element elDTO)
	{
		/*
		if (dto instanceof DTOScenario)
		{
			DTOScenario sec = (DTOScenario) dto;
		}
		*/
	}


	/**
	 * Validates the XML document against the Business Horizon 
	 * XML schema.
	 * @return	True - document is valid 			
	 * @throws XMLNotValidException - The document is not valid
	 * @throws IOException - The XML document could not be read
	 */
	private boolean validateXMLInput() throws XMLNotValidException, IOException 
	{
		try {
			// Get XML Schema
			URL schemaURL = getClass().getResource("BHDataExchange.xsd");
			
			// Create validator for validating the input against a XML schema
			Validator validator = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
									.newSchema(schemaURL).newValidator();		
			// Validate
			validator.validate(new StreamSource(importFile));
			
		} catch (SAXException e) {
			throw new XMLNotValidException("The provided XML document is not valid!", e);
		}
		return true;
	}	
	
	/**
	 * Creates a File instance by using the given path and
	 * checks if the file can be processed.
	 * @return	True 	- File is okay<br>
	 * 			False	- File can not be used
	 */
	private boolean checkFile()
	{
		importFile = new File(importFilePath);
		
		if (importFile == null || !importFile.isFile() || !importFile.canRead() ||
				!importFile.canWrite())
			return false;
		
		return true;
	}	
	
}
