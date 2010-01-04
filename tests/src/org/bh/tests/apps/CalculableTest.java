package org.bh.tests.apps;

import org.bh.data.types.IntegerValue;


public class CalculableTest {

	/**
	 * ...to test implementation of multiadd and multimul of Calculable
	 * 
	 * @author Alex
	 * 
	 * @version 0.1, 22.11.2009, Alex
	 */
	public static void main(String[] args) {
		
		IntegerValue one = new IntegerValue(4);
		IntegerValue two = new IntegerValue(2);
		IntegerValue three = new IntegerValue(3);
		
		System.out.println("Add einfach...");
		System.out.println(one.add(two).toString());
		System.out.println("alter Wert: "+one.toString());
		
		System.out.println("------------");
		
		one = new IntegerValue(4);
		System.out.println("Add mehrfach...");
		System.out.println(one.add(two,three).toString());
		System.out.println("alter Wert: "+one.toString());
		
	}

}
