/*******************************************************************************
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
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
