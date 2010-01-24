package org.bh.plugin.xmldataexchange.xmlimport;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import org.bh.plugin.xmldataexchange.XMLDataExchangeController;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.SAXException;

/**
 * This class provides the functionality to import a {@link DTOProject} from
 * a XML document.
 * <p> 
 * @author Marcus Katzor
 * @version 1.0, 27.12.2009
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
	private Namespace bhDataNS = Namespace.getNamespace("http://www.bh.org/dataexchange");
	
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
			SAXBuilder saxBuilder = new SAXBuilder();
			try {
				Document doc = saxBuilder.build(importFile);				
				result = getDTOFromXML(doc.getRootElement());				
			} catch (JDOMException e) {				
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
			Element valuesNode = node.getChild("values", bhDataNS);
			if (valuesNode != null)
			{
				List<Element> values =  valuesNode.getChildren("value", bhDataNS);
				// Put all values into the DTO
				for (Element val : values)
				{				
					Object[] value = DataTypeConverter.getIValueRepresenation(val, bhDataNS);
					dto.put(value[0], (IValue) value[1]);
				}
			}			
			
			// Get a list of all child nodes
			Element nodeChildren = node.getChild("children", bhDataNS);
			if (nodeChildren != null)
			{
				List<Element> children = nodeChildren.getChildren();
				for (Element child : children)
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
