/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber * : Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bh.data.DTOKeyPair;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.HashValue;
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
 * @update Vito Masiello, Patrick Maisel, Lukas Lochner
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
		else if (type.equals("HashValue"))
		{
			// Get all childs of hashValue
			Element hashValueChild = node.getFirstChildElement("hashValue", ns);			
			Elements hashElementChilds = hashValueChild.getChildElements("hashElement", ns);
	
			// create return HashMap
			HashMap<String, String> hashMap = new HashMap<String, String>();			
			
			// iterate all elements in node
			for(int i = 0; i < hashElementChilds.size(); i++)
			{
				Element currElement = hashElementChilds.get(i);
				String hashContent = currElement.getValue();
				String hashKey = currElement.getAttributeValue("key");				
				hashMap.put(hashKey, hashContent);				
			}
			
			val = new HashValue(hashMap);
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
