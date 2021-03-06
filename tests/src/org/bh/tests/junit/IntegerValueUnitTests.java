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
package org.bh.tests.junit;

import static org.junit.Assert.assertTrue;

import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.IntegerValue;
import org.bh.platform.Services;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for IntegerValues
 * 
 * @author Norman
 * 
 * @version 0.3, 04.01.2010
 */
public class IntegerValueUnitTests {

	public static final int MAX = 100000;

	IntegerValue iv1;
	IntegerValue iv2;
	IntegerValue iv3;
	IntegerValue iv4;
	IntegerValue iv5;
	IntegerValue iv6;

	int i1;
	int i2;
	int i3;
	int i4;
	int i5;
	int i6;

	@Before
	public void setUp() throws Exception {
		Services.initNumberFormats();
		
		i1 = getRandom();
		i2 = getRandom();
		i3 = getRandom();
		i4 = getRandom();
		i5 = getRandom();
		i6 = getRandom();

		iv1 = new IntegerValue(i1);
		iv2 = new IntegerValue(i2);
		iv3 = new IntegerValue(i3);
		iv4 = new IntegerValue(i4);
		iv5 = new IntegerValue(i5);
		iv6 = new IntegerValue(i6);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void IntegerValuesAdd(){
		assertTrue(iv1.add(iv3).getClass() == IntegerValue.class);
		
		assertTrue(iv1.add(iv2).equals(new IntegerValue(i1 + i2)));
		assertTrue(iv1.add(iv3).equals(new IntegerValue(i1 + i3)));
		assertTrue(iv1.add(iv2,iv3,iv4,iv5,iv6).equals(new IntegerValue(i1 + i2 + i3 + i4 + i5 + i6)));
	}

	@Test
	public void IntegerValuesSub() {
		assertTrue(iv1.sub(iv3).getClass() == IntegerValue.class);
		
		assertTrue(iv1.sub(iv2).equals(new IntegerValue(i1 - i2)));
		assertTrue(iv1.sub(iv3).equals(new IntegerValue(i1 - i3)));
		assertTrue(iv6.sub(iv4).sub(iv1).equals(new IntegerValue(i6 - i4 - i1)));
	}

	@Test
	public void IntegerValuesMul() {
		assertTrue(iv1.mul(iv3).getClass() == IntegerValue.class);
		
		assertTrue(iv1.mul(iv2).equals(new IntegerValue(i1 * i2)));
		assertTrue(iv1.mul(iv3).equals(new IntegerValue(i1 * i3)));
		assertTrue(iv1.mul(iv2,iv3,iv4,iv5,iv6).equals(new IntegerValue(i1 * i2 * i3 * i4 * i5 * i6)));
	}

	@Test
	public void IntegerValuesDiv() {
		assertTrue((iv1.div(iv3).getClass() == DoubleValue.class));
		
		assertTrue(iv1.div(iv2).equals(new DoubleValue((double) i1 / i2)));
		assertTrue(iv1.div(iv3).equals(new DoubleValue((double) i1 / i3)));
		assertTrue(iv6.div(iv4).div(iv1).equals(new DoubleValue((double)i6 / i4 / i1)));
	}

	@Test
	public void IntegerValuesPow() {
		assertTrue((iv1.pow(new IntegerValue(5)).getClass() == DoubleValue.class));
		
		assertTrue((iv3.pow(new IntegerValue(2)).equals(new DoubleValue(Math.pow(i3,2)))));
		Calculable c1 = iv4.pow(new IntegerValue(2)).pow(new IntegerValue(3));
		double d2 = Math.pow(Math.pow(i4,2),3);
		assertTrue(c1.equals(new DoubleValue(d2)));
	}

	@Test
	public void IntegerValuesSqrt() {
		assertTrue((iv1.pow(new IntegerValue(5)).getClass() == DoubleValue.class));
		assertTrue(iv5.sqrt().equals(new DoubleValue(Math.sqrt(i5))));
	}

	int getRandom() {
		return (int) (Math.random() * MAX + 1);
	}
}
