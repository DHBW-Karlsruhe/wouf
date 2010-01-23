package org.bh.plugin.xmldataexchange.xmlexport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bh.data.DTOAccessException;
import org.bh.data.DTOProject;
import org.bh.data.IDTO;
import org.bh.data.types.IValue;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * This class provides the functionality to export a {@link DTOProject} to
 * a XML document.
 * <p>
 * @author Katzor.Marcus
 * @version 1.0, 27.12.2009
 *
 */
public class XMLExport {
	
	/**
	 * Path to the export file. 
	 */
	private String exportFilePath = null;
	
	/**
	 * Reference to the export file.
	 */
	private File exportFile = null;
	
	/**
	 * Reference to the model which should
	 * be exported. 
	 */
	private IDTO<?> model = null;
	
	/**
	 * Namespace for all Business Horizon elements in the XML document.
	 */
	private Namespace bhDataNS = Namespace.getNamespace("http://www.bh.org/dataexchange");
	
	
	/**
	 * 
	 * @param exportFilePath
	 * @param model
	 */
	public XMLExport(String exportFilePath, IDTO<?> model) {
		super();
		this.exportFilePath = exportFilePath;
		this.model = model;
	}	
	
	/**
	 * Starts the export and writes the model into
	 * the specified file.
	 * @return
	 */
	public boolean startExport() throws IOException
	{
		// Check if the path is a valid input 
		if (!checkFile())
		{
			throw new IOException();
		}
		// Convert the "DTO tree" into a "XML tree"
		Element project = getDTOInXML(model);
		
		// Add XML Schema Definition
		Namespace xsiNS = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		Attribute xsd = new Attribute("schemaLocation",
				"http://www.bh.org/dataexchange http://newsgymson.de/Marcus/BHDataExchange.xsd", xsiNS);
		project.setAttribute(xsd);


		// Append the project to a document
		Document doc = new Document(project);
		
		
		// Create an instance of XMLOutputter to write 
		// the XML into the file
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());				
		
		// Create FileWriter
		FileWriter writer = new FileWriter(exportFile);	
		// Write into the file	
		out.output(doc, writer);
		
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
		if(!exportFilePath.endsWith(".xml")){
			exportFilePath = exportFilePath + ".xml";
		}
		exportFile = new File(exportFilePath);
		
		if (exportFile.exists()) {
			if (!exportFile.canWrite()) {
				return false;
			}
		}
		// f does not exist
		try {
			exportFile.createNewFile();
		} catch (IOException e) {
			// no write rights 
			return false;
		}
		// can write
		return true;

//		TODO Fix Marcus - quickfix Norman s.o. --> exportFile.getParentFile().canWrite() immer false --> kann nicht in Ordner schreiben 
//		exportFile = new File(exportFilePath);
//		
//		if (exportFile.exists() && (!exportFile.isFile() || !exportFile.canRead() ||
//				!exportFile.canWrite()))
//			return false;
//		else if (!exportFile.exists() && exportFile.getParentFile().isDirectory() && (!exportFile.getParentFile()
//				.canRead() || !exportFile.getParentFile().canWrite() ))
//			return false;
//		
//		return true;
	}
	
	/**
	 * Converts a complete DTO in a XML node.
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Element getDTOInXML(IDTO dto)	
	{		
		// Create a JDOM Element with a corresponding name 
		Element result = new Element(getNodeName(dto.getClass().getSimpleName()), bhDataNS);
				
		// Save class as attribute
		Attribute attrClass = new Attribute("class", dto.getClass().getName());
		result.setAttribute(attrClass);
		
		// Node for grouping all values
		Element elValues = new Element("values", bhDataNS);
		
		// Node for grouping all children
		Element elChildren = new Element("children", bhDataNS);
		
		// Get keys in order to iterate through all values
		List<String> keys = dto.getKeys();
		
		List<Element> values = new ArrayList<Element>(); 
		if (keys != null)
		{
			for (String key : keys)
			{
				// Get value and convert it into a JDOM Element
				try
				{
					IValue val = dto.get(key);
					Element value = DataTypeConverter.getXMLRepresentation(key, val, bhDataNS);
					values.add(value);
				}
				catch (DTOAccessException e)
				{			
				}
			}
		}
		
		// Add all values to the corresponding node
		if (values.size() > 0)
		{
			elValues.addContent(values);
			result.addContent(elValues);
		}
		
		
		// Iterate through all children and convert them
		// into a JDOM Element
		int childrenSize = dto.getChildrenSize();
		List<Element> children = new ArrayList<Element>();
		for (int i = 0; i < childrenSize; i++)
		{
			Element child = getDTOInXML(dto.getChild(i));
			children.add(child);
		}
		
		// Add all children to the corresponding node
		if (children.size() > 0)
		{
			elChildren.addContent(children);
			result.addContent(elChildren);
		}
		

		applyDTOSpecificData(dto, result);
		
		return result;
	}
	
	/**
	 * Extracts the actual name of the DTO.
	 * E.g.: DTOProject -> project
	 * @param className
	 * @return
	 */
	private String getNodeName(String className)
	{
		return className.replace("DTO", "").toLowerCase();
	}
	
	/**
	 * Adds DTO specific data to the XML node.
	 * @param dto - The DTO with specific data
	 * @param elDTO - The XML representation of the DTO
	 */
	@SuppressWarnings("unchecked")
	private void applyDTOSpecificData(IDTO dto, Element elDTO)
	{
		/*
		if (dto instanceof DTOScenario)
		{
			DTOScenario sec = (DTOScenario) dto;
		}
		*/
	}
}
