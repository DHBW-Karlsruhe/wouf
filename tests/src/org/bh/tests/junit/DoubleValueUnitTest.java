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
package org.bh.tests.junit;

import static org.junit.Assert.assertTrue;

import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.IntegerValue;
import org.bh.platform.Services;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for DoubleValues
 * 
 * @author Norman
 * 
 * @version 0.3, 04.01.2010
 */
public class DoubleValueUnitTest {
	public static final int MAX = 100000;

	DoubleValue dv1;
	DoubleValue dv2;
	DoubleValue dv3;
	DoubleValue dv4;
	DoubleValue dv5;
	DoubleValue dv6;

	double d1;
	double d2;
	double d3;
	double d4;
	double d5;
	double d6;

	@Before
	public void setUp() throws Exception {
		Services.initNumberFormats();
		
		d1 = getRandom();
		d2 = getRandom();
		d3 = getRandom();
		d4 = getRandom();
		d5 = getRandom();
		d6 = getRandom();

		dv1 = new DoubleValue(d1);
		dv2 = new DoubleValue(d2);
		dv3 = new DoubleValue(d3);
		dv4 = new DoubleValue(d4);
		dv5 = new DoubleValue(d5);
		dv6 = new DoubleValue(d6);
	}

	@Test
	public void DoubleValuesAdd() {
		assertTrue(dv1.add(dv3).getClass() == DoubleValue.class);

		assertTrue(dv1.add(dv2).equals(new DoubleValue(d1 + d2)));
		assertTrue(dv1.add(dv3).equals(new DoubleValue(d1 + d3)));
		assertTrue(dv1.add(dv2, dv3, dv4, dv5, dv6).equals(
				new DoubleValue(d1 + d2 + d3 + d4 + d5 + d6)));
	}

	@Test
	public void DoubleValuesSub() {
		assertTrue(dv1.sub(dv3).getClass() == DoubleValue.class);

		assertTrue(dv1.sub(dv2).equals(new DoubleValue(d1 - d2)));
		assertTrue(dv1.sub(dv3).equals(new DoubleValue(d1 - d3)));
		assertTrue(dv6.sub(dv4).sub(dv1).equals(new DoubleValue(d6 - d4 - d1)));
	}

	@Test
	public void DoubleValuesMul() {
		assertTrue(dv1.mul(dv3).getClass() == DoubleValue.class);

		assertTrue(dv1.mul(dv2).equals(new DoubleValue(d1 * d2)));
		assertTrue(dv1.mul(dv3).equals(new DoubleValue(d1 * d3)));
		assertTrue(dv1.mul(dv2, dv3, dv4, dv5, dv6).equals(
				new DoubleValue(d1 * d2 * d3 * d4 * d5 * d6)));
	}

	@Test
	public void DoubleValuesDiv() {
		assertTrue((dv1.div(dv3).getClass() == DoubleValue.class));

		assertTrue(dv1.div(dv2).equals(new DoubleValue(d1 / d2)));
		assertTrue(dv1.div(dv3).equals(new DoubleValue(d1 / d3)));
		assertTrue(dv6.div(dv4).div(dv1).equals(new DoubleValue(d6 / d4 / d1)));
	}

	@Test
	public void DoubleValuesPow() {
		assertTrue((dv1.pow(new IntegerValue(5)).getClass() == DoubleValue.class));

		assertTrue((dv3.pow(new IntegerValue(2)).equals(new DoubleValue(Math
				.pow(d3, 2)))));
		Calculable c1 = dv4.pow(new DoubleValue(2)).pow(new DoubleValue(3));
		double d2 = Math.pow(Math.pow(d4, 2), 3);
		assertTrue(c1.equals(new DoubleValue(d2)));
	}

	@Test
	public void DoubleValuesSqrt() {
		assertTrue((dv1.pow(new IntegerValue(5)).getClass() == DoubleValue.class));
		assertTrue(dv5.sqrt().equals(new DoubleValue(Math.sqrt(d5))));
	}

	double getRandom() {
		return (Math.random() * MAX + 1);
	}
}
