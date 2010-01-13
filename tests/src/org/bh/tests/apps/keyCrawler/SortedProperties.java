/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.tests.apps.keyCrawler;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 *
 * @author Marco Hammel
 */
public class SortedProperties extends Properties{

    /**
   * Overrides, called by the store method.
   */
  @SuppressWarnings("unchecked")
  public synchronized Enumeration keys() {
     Enumeration keysEnum = super.keys();
     Vector keyList = new Vector();
     while(keysEnum.hasMoreElements()){
       keyList.add(keysEnum.nextElement());
     }
     Collections.sort(keyList);
     return keyList.elements();
  }


}
