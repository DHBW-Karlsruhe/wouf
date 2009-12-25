package org.bh.plugin.xmldataexchange.export;

import java.util.ArrayList;
import java.util.List;

import org.bh.data.types.DoubleValue;
import org.bh.data.types.IntegerValue;
import org.bh.data.types.IValue;
import org.bh.data.types.IntervalValue;
import org.bh.data.types.StringValue;
import org.jdom.Attribute;
import org.jdom.Element;

/**
 * DataTypeConverter provides static methods to convert BH data types
 * in a xml format.
 *
 * <p> 
 *
 * @author Marcus Katzor
 * @version 1.0, 25.12.2009
 *
 */
public class DataTypeConverter {
	
	/**
	 * Converts an IValue into a XML node. 
	 * @param val - Value to be converted
	 * @return JDOM Element
	 */
	public static Element getXMLRepresentation(String key, IValue val)
	{
		// Value node
		Element value = new Element("value");
		// Key for the DTOs
		Attribute attrKey = new Attribute("key", key);		
		// Attribute to determine the class of the data type
		Attribute attrClass = new Attribute("class", val.getClass().getName());
		// Simple class name - for external programs
		Attribute attrSimpleClass = new Attribute("type", val.getClass().getSimpleName());		
		
		// Add attributes
		value.setAttribute(attrKey);
		value.setAttribute(attrClass);
		value.setAttribute(attrSimpleClass);
		
		// Get actual values as nodes
		List<Element> actValues = null;
		
		if (val instanceof DoubleValue)
			actValues = getDoubleValueInXML((DoubleValue) val);
		else if (val instanceof IntegerValue)
			actValues = getIntegerValueInXML((IntegerValue) val);
		else if (val instanceof IntervalValue)
			actValues = getIntervalValueInXML((IntervalValue) val);
		else if (val instanceof StringValue)
			actValues = getStringValueInXML((StringValue) val);
		
		if (actValues == null)
		{
			// TODO throw export exception
		}
		
		
		// Add actual values
		value.addContent(actValues);
		
		return value;
		
	}
	
	/**
	 * Converts a DoubleValue into a XML node.
	 * @param val
	 * @return
	 */
	private static List<Element> getDoubleValueInXML(DoubleValue val)
	{
		Element value = new Element("doubleValue");
		value.addContent(val.getValue()+"");
		List<Element> result = new ArrayList<Element>();
		result.add(value);		
		return result;
		
	}
	
	/**
	 * Converts an IntegerValue into a XML node.
	 * @param val
	 * @return
	 */
	private static List<Element> getIntegerValueInXML(IntegerValue val)
	{
		Element value = new Element("integerValue");
		value.addContent(val.getValue()+"");
		List<Element> result = new ArrayList<Element>();
		result.add(value);		
		return result;		
	}
	
	/**
	 * Converts an IntervalValue into a XML node.
	 * @param val
	 * @return
	 */
	private static List<Element> getIntervalValueInXML(IntervalValue val)
	{
		Element minValue = new Element("minValue");
		Element maxValue = new Element("maxValue");
		minValue.addContent(val.getMin() + "");
		maxValue.addContent(val.getMax() + "");
		List<Element> result = new ArrayList<Element>();
		result.add(minValue);
		result.add(maxValue);
		return result;		
	}
	
	/**
	 * Converts a StringValue into a XML node.
	 * @param val
	 * @return
	 */
	private static List<Element> getStringValueInXML(StringValue val)
	{
		Element value = new Element("stringValue");
		value.addContent(val.getString());
		List<Element> result = new ArrayList<Element>();
		result.add(value);		
		return result;		
	}
	
}
