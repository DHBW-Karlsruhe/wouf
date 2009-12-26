package org.bh.plugin.xmldataexchange.xmlexport;

import org.bh.data.types.DoubleValue;
import org.bh.data.types.IntegerValue;
import org.bh.data.types.IValue;
import org.bh.data.types.IntervalValue;
import org.bh.data.types.StringValue;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

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
	public static Element getXMLRepresentation(String key, IValue val, Namespace ns)
	{
		// Value node
		Element value = new Element("value", ns);
		// Key for the DTOs
		Attribute attrKey = new Attribute("key", key);		
		// Simple class name - for external programs
		Attribute attrSimpleClass = new Attribute("type", val.getClass().getSimpleName());		
		
		// Add attributes
		value.setAttribute(attrKey);		
		value.setAttribute(attrSimpleClass);
		
		// Get actual value as node
		Element actValue = null;
		
		if (val instanceof DoubleValue)
			actValue = getDoubleValueInXML((DoubleValue) val, ns);
		else if (val instanceof IntegerValue)
			actValue = getIntegerValueInXML((IntegerValue) val, ns);
		else if (val instanceof IntervalValue)
			actValue = getIntervalValueInXML((IntervalValue) val, ns);
		else if (val instanceof StringValue)
			actValue = getStringValueInXML((StringValue) val, ns);
		
		if (actValue == null)
		{
			// TODO throw export exception
		}				
		
		// Add actual values
		value.addContent(actValue);		
		
		return value;
		
	}
	
	/**
	 * Converts a DoubleValue into a XML node.
	 * @param val
	 * @return
	 */
	private static Element getDoubleValueInXML(DoubleValue val, Namespace ns)
	{
		Element value = new Element("doubleValue", ns);
		value.addContent(val.getValue()+"");				
		return value;
		
	}
	
	/**
	 * Converts an IntegerValue into a XML node.
	 * @param val
	 * @return
	 */
	private static Element getIntegerValueInXML(IntegerValue val, Namespace ns)
	{
		Element value = new Element("integerValue", ns);
		value.addContent(val.getValue()+"");				
		return value;		
	}
	
	/**
	 * Converts an IntervalValue into a XML node.
	 * @param val
	 * @return
	 */
	private static Element getIntervalValueInXML(IntervalValue val, Namespace ns)
	{
		Element value = new Element("intervalValue", ns);
		Attribute attrMin = new Attribute("minValue", val.getMin() + "");
		Attribute attrMax = new Attribute("maxValue", val.getMax() + "");		
		value.setAttribute(attrMin);
		value.setAttribute(attrMax);		
		return value;		
	}
	
	/**
	 * Converts a StringValue into a XML node.
	 * @param val
	 * @return
	 */
	private static Element getStringValueInXML(StringValue val, Namespace ns)
	{
		Element value = new Element("stringValue", ns);
		value.addContent(val.getString());			
		return value;		
	}
	
}
