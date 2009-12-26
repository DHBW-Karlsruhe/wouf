package org.bh.plugin.xmldataexchange.xmlimport;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.bh.data.DTOProject;
import org.bh.data.IDTO;
import org.bh.data.types.IValue;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.SAXException;

public class XMLImport {
	
	/**
	 * Path to the export file. 
	 */
	private String importFilePath = null;
	
	/**
	 * Reference to the export file.
	 */
	private File importFile = null;
	
	private Namespace bhDataNS = Namespace.getNamespace("http://www.bh.org/dataexchange");
	

	public XMLImport(String importFilePath) {
		super();
		this.importFilePath = importFilePath;
	}
	
	
	public IDTO startImport()
	{
		// Check if the path is a valid input 
		if (!checkFile())
		{
			// TODO import exception
		}
		
		DTOProject project = null;
		
		if (validateXMLInput())
		{
			// Parse XML Document
			
			// Create a SAX builder object to parse the xml document
			SAXBuilder saxBuilder = new SAXBuilder();
			try {
				Document doc = saxBuilder.build(importFile);
				
				project = (DTOProject) getDTOFromXML(doc.getRootElement());
				
				
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			
		}
		
		return project;		
	}
	
	
	private IDTO getDTOFromXML(Element node)
	{
		// Get class name
		String className = node.getAttributeValue("class");
		
		IDTO dto = null;
		
		try {
			dto = (IDTO) Class.forName(className).newInstance();
			
			// Get a list of all value nodes
			List<Element> values =  node.getChild("values", bhDataNS).getChildren("value", bhDataNS);
			
			// Put all values into the DTO
			for (Element val : values)
			{				
				Object[] value = DataTypeConverter.getIValueRepresenation(val, bhDataNS);
				dto.put(value[0], (IValue) value[1]);
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
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dto;
	}


	private boolean validateXMLInput() {
		try {
			// Get XML Schema
			URL schemaURL = getClass().getResource("BHDataExchange.xsd");
			
			// Create validator for validating the input against a xml schema
			Validator validator = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
									.newSchema(schemaURL).newValidator();		
			// Validate
			validator.validate(new StreamSource(importFile));
			
		} catch (SAXException e) {
			return false;
		} catch (IOException e) {
			return false;
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
		
		if (!importFile.isFile() || !importFile.canRead() ||
				!importFile.canWrite())
			return false;
		
		return true;
	}
	
	
	
}
