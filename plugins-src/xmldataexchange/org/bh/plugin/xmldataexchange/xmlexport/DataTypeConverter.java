package org.bh.plugin.xmldataexchange.xmlexport;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.bh.data.DTOKeyPair;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.IValue;
import org.bh.data.types.IntegerValue;
import org.bh.data.types.IntervalValue;
import org.bh.data.types.ObjectValue;
import org.bh.data.types.StringValue;
import nu.xom.*;

/**
 * DataTypeConverter provides static methods to convert BH data types
 * in a XML format.
 *
 * <p> 
 *
 * @author Marcus Katzor
 * @version 1.1, 12.01.2011
 * @update Vito Masiello, Patrick Maisel
 * Klasse wurde an die XOM Library angepasst  
 */
public class DataTypeConverter {
	
	/**
	 * Converts an IValue into a XML node. 
	 * @param val - Value to be converted
	 * @return XOM Element
	 */
	public static Element getXMLRepresentation(String key, IValue val, String ns)
	{
		// Value node
		Element value = new Element("value", ns);
		// Key for the DTOs
		Attribute attrKey = new Attribute("key", key);		
		// Simple class name - for external programs
		Attribute attrSimpleClass = new Attribute("type", val.getClass().getSimpleName());		
		
		// Add attributes
		value.addAttribute(attrKey);		
		value.addAttribute(attrSimpleClass);
		
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
		else if (val instanceof ObjectValue)
			actValue = getObjectValueInXML((ObjectValue)val, ns);
		
		if (actValue != null)
		{
			// Add actual values
			value.appendChild(actValue);
		}				
		else
			Logger.getLogger(DataTypeConverter.class).debug("A value could not be converted into a XML Node. Type is: " + val.getClass().getSimpleName());
		
				
		
		return value;
		
	}
	
	/**
	 * Converts a DoubleValue into a XML node.
	 * @param val
	 * @return
	 */
	private static Element getDoubleValueInXML(DoubleValue val, String ns)
	{
		Element value = new Element("doubleValue", ns);
		value.appendChild(val.getValue()+"");				
		return value;
		
	}
	
	/**
	 * Converts an IntegerValue into a XML node.
	 * @param val
	 * @return
	 */
	private static Element getIntegerValueInXML(IntegerValue val, String ns)
	{
		Element value = new Element("integerValue", ns);
		value.appendChild(val.getValue()+"");				
		return value;		
	}
	
	/**
	 * Converts an IntervalValue into a XML node.
	 * @param val
	 * @return
	 */
	private static Element getIntervalValueInXML(IntervalValue val, String ns)
	{
		Element value = new Element("intervalValue", ns);
		Attribute attrMin = new Attribute("minValue", val.getMin() + "");
		Attribute attrMax = new Attribute("maxValue", val.getMax() + "");		
		value.addAttribute(attrMin);
		value.addAttribute(attrMax);		
		return value;		
	}
	
	/**
	 * Converts a StringValue into a XML node.
	 * @param val
	 * @return
	 */
	private static Element getStringValueInXML(StringValue val, String ns)
	{
		Element value = new Element("stringValue", ns);
		value.appendChild(val.getString());			
		return value;		
	}
	
	/**
	 * Converts a ObjectValue into a XML node.
	 * @param val
	 * @return
	 */
	private static Element getObjectValueInXML(ObjectValue val, String ns)
	{
		if (val.getObject() instanceof ArrayList)
		{
			Element value = new Element("objectValue", ns);	
			for(Object obj : (ArrayList)val.getObject())
			{
				if (obj instanceof DTOKeyPair)
				{
					Attribute id = new Attribute("id", ((DTOKeyPair)obj).getDtoId());
					Attribute key = new Attribute("key", ((DTOKeyPair)obj).getKey());
					Element keyPair = new Element("keyPair", ns);
					keyPair.addAttribute(id);
					keyPair.addAttribute(key);
					value.appendChild(keyPair);
				}
				else
					return null;
			}	

			return value;
		}
		else					
			return null;		
	}
	
}
