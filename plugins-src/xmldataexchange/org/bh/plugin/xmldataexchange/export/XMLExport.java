package org.bh.plugin.xmldataexchange.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.types.IValue;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;


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
	private DTOProject model = null;
	
	
	/**
	 * 
	 * @param exportFilePath
	 * @param model
	 */
	public XMLExport(String exportFilePath, DTOProject model) {
		super();
		this.exportFilePath = exportFilePath;
		this.model = model;
	}	
	
	/**
	 * Starts the export and writes the model into
	 * the specified file.
	 * @return
	 */
	public boolean startExport()
	{
		// Check if the path is a valid input 
		if (!checkFile())
		{
			// TODO export exception
		}
		// Convert the "DTO tree" into a "XML tree"
		Element project = getDTOInXML(model);
		
		// Append the project to a document
		Document doc = new Document(project);
		
		// Create an instance of XMLOutputter to write 
		// the XML into the file
		XMLOutputter out = new XMLOutputter();
				
		try {
			// Create FileWriter
			FileWriter writer = new FileWriter(exportFile);	
			// Write into the file	
			out.output(doc, writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		exportFile = new File(exportFilePath);
		
		if (!exportFile.isFile() || !exportFile.canRead() ||
				!exportFile.canWrite())
			return false;
		
		return true;
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
		Element result = new Element(getNodeName(dto.getClass().getSimpleName()));
		
		// Node for grouping all values
		Element elValues = new Element("values");
		
		// Node for grouping all children
		Element elChildren = new Element("children");
		
		// Get keys in order to iterate through all values
		List<String> keys = dto.getKeys();
		
		List<Element> values = new ArrayList<Element>(); 
		for (String key : keys)
		{
			// Get value and convert it into a JDOM Element
			IValue val = dto.get(key);
			Element value = DataTypeConverter.getXMLRepresentation(key, val);
			values.add(value);
		}
		
		// Add all values to the corresponding node
		elValues.addContent(values);
		
		
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
		elChildren.addContent(children);
		
		result.addContent(elValues);
		result.addContent(elChildren);
		
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
	
	@SuppressWarnings("unchecked")
	private void applyDTOSpecificData(IDTO dto, Element elDTO)
	{
		if (dto instanceof DTOScenario)
		{
			DTOScenario sec = (DTOScenario) dto;
			// Save the futureValue flag
			Attribute attrFlag = new Attribute("containsValuesInFuture",
					(new Boolean(sec.isFutureValues())).toString());
			elDTO.setAttribute(attrFlag);			
		}
	}
	

}
