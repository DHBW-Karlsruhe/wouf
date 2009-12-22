package org.bh.plugin.xmldataexchange.export;

import java.util.ArrayList;
import java.util.List;

import org.bh.data.types.DoubleValue;
import org.bh.data.types.IntegerValue;
import org.bh.data.types.IValue;
import org.jdom.Attribute;
import org.jdom.Element;

public class DataTypeConverter {
	
	
	public static Element getXMLRepresentation(IValue val)
	{
		// Value node
		Element value = new Element("value");
		// Add attribute to determine the data type
		Attribute attrClass = new Attribute("class", val.getClass().getName());
		value.setAttribute(attrClass);
		
		
		return null;
		
	}
	
	public static List<Element> getValueInXML(DoubleValue val)
	{
		Element value = new Element("doubleValue");
		value.addContent(val.getValue()+"");
		List<Element> result = new ArrayList<Element>();
		result.add(value);
		
		return result;
		
	}
	
	public static List<Element> getValueInXML(IntegerValue val)
	{
		return null;
		
	}
	
}
