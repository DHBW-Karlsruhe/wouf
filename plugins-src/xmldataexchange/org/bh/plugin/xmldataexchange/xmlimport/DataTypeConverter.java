package org.bh.plugin.xmldataexchange.xmlimport;

import java.security.KeyPair;

import java.util.ArrayList;
import java.util.List;

import org.bh.data.DTOKeyPair;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.IValue;
import org.bh.data.types.IntegerValue;
import org.bh.data.types.IntervalValue;
import org.bh.data.types.ObjectValue;
import org.bh.data.types.StringValue;
import nu.xom.*;

/**
 * 
 *
 * @author Vito
 * @version 1.0, 12.01.2011
 * @update Vito Masiello, Patrick Maisel
 * Klasse wurde an die XOM Library angepasst 
 *
 */

public class DataTypeConverter {	
	
	public static Object[] getIValueRepresenation(Element node, String ns)
	{
		Object[] result = new Object[2];
		String key = node.getAttributeValue("key");
		String type = node.getAttributeValue("type");	
			
		IValue val = null; 
		if (type.equals("IntegerValue"))
		{
			String strValue = ((Element)node.getChildElements().get(0)).getValue();
			val = new IntegerValue(Integer.parseInt(strValue));
		}
		else if (type.equals("DoubleValue"))
		{
			String strValue = ((Element)node.getChildElements().get(0)).getValue();
			val = new DoubleValue(Double.parseDouble(strValue));
		}
		else if (type.equals("StringValue"))
		{
			String strValue = ((Element)node.getChildElements().get(0)).getValue();
			val = new StringValue(strValue);
		}
		else if (type.equals("IntervalValue"))
		{
			Element child = node.getFirstChildElement("intervalValue", ns);
			String minValue = child.getAttributeValue("minValue");
			String maxValue = child.getAttributeValue("maxValue");
			val = new IntervalValue(Double.parseDouble(minValue), Double.parseDouble(maxValue));
		}
		else if (type.equals("ObjectValue"))
		{
			Element objectValueChild = node.getFirstChildElement("objectValue", ns);
			ArrayList<Element> listElement = new ArrayList<Element>();
			listElement.add(objectValueChild);
			List<DTOKeyPair> keyPairList = new ArrayList<DTOKeyPair>();
			for (Object child : listElement)
			{
				String pairId = ((Element)child).getAttributeValue("id");
				String pairKey = ((Element)child).getAttributeValue("key");
				DTOKeyPair keyPair = new DTOKeyPair(pairId, pairKey);
				keyPairList.add(keyPair);
			}
			val = new ObjectValue(keyPairList);
		}
		result[0] = key;
		result[1] = val;
		return result;		
	}

}
