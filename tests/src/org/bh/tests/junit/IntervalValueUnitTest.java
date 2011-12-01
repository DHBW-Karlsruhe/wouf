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
import net.sourceforge.interval.ia_math.IAMath;
import net.sourceforge.interval.ia_math.RealInterval;

import org.bh.data.types.IntegerValue;
import org.bh.data.types.IntervalValue;
import org.bh.platform.Services;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for IntervalValues
 * 
 * @author Norman
 * 
 * @version 0.3, 04.01.2010
 */
public class IntervalValueUnitTest {
	public static final int MAX = 100000;

	// reference implementation
	RealInterval ri1;
	RealInterval ri2;
	RealInterval ri3;
	RealInterval ri4;
	RealInterval ri5;
	RealInterval ri6;
	//

	IntervalValue iv1;
	IntervalValue iv2;
	IntervalValue iv3;
	IntervalValue iv4;
	IntervalValue iv5;
	IntervalValue iv6;

	double[] vp1;
	double[] vp2;
	double[] vp3;
	double[] vp4;
	double[] vp5;
	double[] vp6;

	IntervalValue resIv;
	RealInterval resRi;

	@Before
	public void setUp() throws Exception {
		Services.initNumberFormats();

		vp1 = getRandomPair();
		vp2 = getRandomPair();
		vp3 = getRandomPair();
		vp4 = getRandomPair();
		vp5 = getRandomPair();
		vp6 = getRandomPair();

		iv1 = new IntervalValue(vp1[0], vp1[1]);
		iv2 = new IntervalValue(vp2[0], vp2[1]);
		iv3 = new IntervalValue(vp3[0], vp3[1]);
		iv4 = new IntervalValue(vp4[0], vp4[1]);
		iv5 = new IntervalValue(vp5[0], vp5[1]);
		iv6 = new IntervalValue(vp6[0], vp6[1]);

		// reference implementation
		ri1 = new RealInterval(vp1[0], vp1[1]);
		ri2 = new RealInterval(vp2[0], vp2[1]);
		ri3 = new RealInterval(vp3[0], vp3[1]);
		ri4 = new RealInterval(vp4[0], vp4[1]);
		ri5 = new RealInterval(vp5[0], vp5[1]);
		ri6 = new RealInterval(vp6[0], vp6[1]);
		//

		resIv = null;
		resRi = null;
	}

	@Test
	public void intervalValuesAdd() {

		resIv = (IntervalValue) iv1.add(iv2);
		resRi = IAMath.add(ri1, ri2);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());

		resIv = (IntervalValue) iv6.add(iv3);
		resRi = IAMath.add(ri6, ri3);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());

		resIv = (IntervalValue) iv4.add(iv4);
		resRi = IAMath.add(ri4, ri4);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());

		resIv = (IntervalValue) iv1.add(iv2, iv3, iv4, iv5, iv6);
		resRi = IAMath.add(IAMath.add(IAMath.add(IAMath.add(IAMath
				.add(ri1, ri2), ri3), ri4), ri5), ri6);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());

		// add further particular tests here

	}

	@Test
	public void intervalValuesSub() {

		resIv = (IntervalValue) iv1.sub(iv2);
		resRi = IAMath.sub(ri1, ri2);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());

		resIv = (IntervalValue) iv6.sub(iv3);
		resRi = IAMath.sub(ri6, ri3);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());

		resIv = (IntervalValue) iv4.sub(iv4);
		resRi = IAMath.sub(ri4, ri4);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());

		// add further particular tests here
	}
	
	@Test
	public void intervalValuesExtendedSub() {
		IntervalValue inf = new IntervalValue(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		IntervalValue upperInf = new IntervalValue(1, Double.POSITIVE_INFINITY);
		IntervalValue lowerInf = new IntervalValue(Double.NEGATIVE_INFINITY, -1);
		IntervalValue nan = new IntervalValue(Double.NaN, Double.NaN);
		
		IntegerValue i = new IntegerValue(5);
		// test all cases i - [z] with r € R and [z] € IR
		
		//cases
		resIv = (IntervalValue) i.sub(inf);
		assertTrue("Result of: " + i + " - " + inf
				+ " wasn't [-inf, +inf] it was " + resIv + "instead", resIv
				.getMin() == Double.NEGATIVE_INFINITY
				&& resIv.getMax() == Double.POSITIVE_INFINITY);
		
		resIv = (IntervalValue) i.sub(upperInf);
		assertTrue("Result of: " + i + " - " + upperInf
				+ " wasn't [-inf, " + (i.getValue() - upperInf.getMin()) + "] it was " + resIv + "instead", resIv
				.getMin() == Double.NEGATIVE_INFINITY
				&& Math.round(resIv.getMax()) == Math.round(i.getValue() - upperInf.getMin()));
		
		resIv = (IntervalValue) i.sub(lowerInf);
		assertTrue("Result of: " + i + " - " + lowerInf
				+ " wasn't [" + (i.getValue() - lowerInf.getMax()) + ", +inf] it was " + resIv + "instead", 
				Math.round(resIv.getMin()) == Math.round(i.getValue() - lowerInf.getMax())
				&& resIv.getMax() == Double.POSITIVE_INFINITY);
		
		resIv = (IntervalValue) i.sub(nan);
		assertTrue("Result of: " + i + " - " + nan
				+ " wasn't [NaN,Nan] it was " + resIv + "instead", 
				Double.isNaN(resIv.getMin())
				&& Double.isNaN(resIv.getMax()));
		
	}

	@Test
	public void intervalValuesMul() {

		resIv = (IntervalValue) iv1.mul(iv2);
		resRi = IAMath.mul(ri1, ri2);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());

		resIv = (IntervalValue) iv6.mul(iv3);
		resRi = IAMath.mul(ri6, ri3);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());

		resIv = (IntervalValue) iv4.mul(iv4);
		resRi = IAMath.mul(ri4, ri4);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());

		resIv = (IntervalValue) iv1.mul(iv2, iv3, iv4, iv5, iv6);
		resRi = IAMath.mul(IAMath.mul(IAMath.mul(IAMath.mul(IAMath
				.mul(ri1, ri2), ri3), ri4), ri5), ri6);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());

		// add further particular tests here
	}

	@Test
	public void intervalValuesDiv() {

		resIv = (IntervalValue) iv1.div(iv2);
		resRi = IAMath.div(ri1, ri2);
		// difference at the last decimal place is acceptable
		// assertTrue(resIv.getMin() == resRi.lo());
		// assertTrue(resIv.getMax() == resRi.hi());
		assertTrue(Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue(Math.round(resIv.getMax()) == Math.round(resRi.hi()));

		resIv = (IntervalValue) iv6.div(iv3);
		resRi = IAMath.div(ri6, ri3);
		// difference at the last decimal place is acceptable
		// assertTrue(resIv.getMin() == resRi.lo());
		// assertTrue(resIv.getMax() == resRi.hi());
		assertTrue(Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue(Math.round(resIv.getMax()) == Math.round(resRi.hi()));

		resIv = (IntervalValue) iv4.div(iv4);
		resRi = IAMath.div(ri4, ri4);
		// difference at the last decimal place is acceptable
		// assertTrue(resIv.getMin() == resRi.lo());
		// assertTrue(resIv.getMax() == resRi.hi());
		assertTrue(Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue(Math.round(resIv.getMax()) == Math.round(resRi.hi()));

		resIv = (IntervalValue) iv5.div(iv4).div(iv5).div(iv2);
		resRi = IAMath.div(IAMath.div(IAMath.div(ri5, ri4), ri5), ri2);
		// difference at the last decimal place is acceptable
		// assertTrue(resIv.getMin() == resRi.lo());
		// assertTrue(resIv.getMax() == resRi.hi());
		assertTrue(Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue(Math.round(resIv.getMax()) == Math.round(resRi.hi()));

		// add further particular tests here
	}

	@Test
	public void intervalValuesDivZero() {
		// interval tests concerning div with zero

		IntervalValue res;

		// a interval
		IntervalValue aZero = new IntervalValue(-1, 1);
		IntervalValue aPos = new IntervalValue(1, 2);
		IntervalValue aNeg = new IntervalValue(-2, -1);

		// b interval
		IntervalValue bZero = new IntervalValue(-1, 1);
		IntervalValue bUpperZero = new IntervalValue(-1, 0);
		IntervalValue bLowerZero = new IntervalValue(0, 1);
		IntervalValue bZeroZero = new IntervalValue(0, 0);

		// case 0 € a & 0 € b
		res = (IntervalValue) aZero.div(bZero);
		assertTrue("Result of: " + aZero + " / " + bZero
				+ " wasn't [-inf, +inf] it was " + res + "instead", res
				.getMin() == Double.NEGATIVE_INFINITY
				&& res.getMax() == Double.POSITIVE_INFINITY);

		// case a negative values & max b = 0
		res = (IntervalValue) aNeg.div(bUpperZero);
		assertTrue("Result of: " + aNeg + " / " + bUpperZero + " wasn't ["
				+ aNeg.getMax() / bUpperZero.getMin() + ";+inf] it was " + res
				+ " instead", 
				Math.round(res.getMin()) == Math.round(aNeg.getMax()
				/ bUpperZero.getMin())
				&& res.getMax() == Double.POSITIVE_INFINITY);

		// case negative values & min b = 0
		res = (IntervalValue) aNeg.div(bLowerZero);
		assertTrue("Result of: " + aNeg + " / " + bLowerZero + " wasn't ["
				+ aNeg.getMax() / bUpperZero.getMax() + ";+inf] it was " + res
				+ " instead", res.getMin() == Double.NEGATIVE_INFINITY
				&& Math.round(res.getMax()) == Math.round(aNeg.getMax() / bLowerZero.getMax()));

		// case a negative values & 0 € b
		res = (IntervalValue) aNeg.div(bZero);
		assertTrue("Result of: " + aNeg + " / " + bZero
				+ " wasn't [-inf;+inf] it was " + res + " instead", res
				.getMin() == Double.NEGATIVE_INFINITY
				&& res.getMax() == Double.POSITIVE_INFINITY);

		// case a positive values & 0 € b
		res = (IntervalValue) aPos.div(bZero);
		assertTrue("Result of: " + aPos + " / " + bZero
				+ " wasn't [-inf;+inf] it was " + res + " instead", res
				.getMin() == Double.NEGATIVE_INFINITY
				&& res.getMax() == Double.POSITIVE_INFINITY);

		// case a positive values & max b = 0
		res = (IntervalValue) aPos.div(bUpperZero);
		assertTrue("Result of: " + aPos + " / " + bUpperZero + " wasn't [-inf;"
				+ aPos.getMin() / bUpperZero.getMin() + "] it was " + res
				+ " instead", res.getMin() == Double.NEGATIVE_INFINITY
				&& Math.round(res.getMax()) == Math.round(aPos.getMin() / bUpperZero.getMin()));

		// case a positive values & min b = 0
		res = (IntervalValue) aPos.div(bLowerZero);
		assertTrue("Result of: " + aPos + " / " + bLowerZero + " wasn't [-inf;"
				+ aPos.getMin() / bLowerZero.getMax() + "] it was " + res
				+ " instead", Math.round(res.getMin()) == Math.round(aPos.getMin()
				/ bLowerZero.getMax())
				&& res.getMax() == Double.POSITIVE_INFINITY);

		// case a positive values & (min b = 0 & max b = 0)
		res = (IntervalValue) aPos.div(bZeroZero);
		assertTrue("Result of: " + aPos + " / " + bZeroZero
				+ " wasn't [/] it was " + res + " instead",
				Double.isNaN(res.getMin()) && Double.isNaN(res.getMax()));
	}

	@Test
	public void intervalValuesPow() {
		// TODO
	}
	
	@Test
	public void sqr() {
		resIv = (IntervalValue) iv1.sqr();
		resRi = IAMath.integerPower(ri1, new RealInterval(2, 2));
		// difference at the last decimal place is acceptable
		assertTrue("" + resIv.getMin() + " ==  " + resRi.lo(),
				Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue("" + resIv.getMax() + " == " + resRi.hi(),
				Math.round(resIv.getMax()) == Math.round(resRi.hi()));
		
		resIv = (IntervalValue) iv2.sqr();
		resRi = IAMath.integerPower(ri2, new RealInterval(2, 2));
		// difference at the last decimal place is acceptable
		assertTrue("" + resIv.getMin() + " ==  " + resRi.lo(),
				Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue("" + resIv.getMax() + " == " + resRi.hi(),
				Math.round(resIv.getMax()) == Math.round(resRi.hi()));
		
		
		resIv = (IntervalValue) iv3.sqr();
		resRi = IAMath.integerPower(ri3, new RealInterval(2, 2));
		// difference at the last decimal place is acceptable
		assertTrue("" + resIv.getMin() + " ==  " + resRi.lo(),
				Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue("" + resIv.getMax() + " == " + resRi.hi(),
				Math.round(resIv.getMax()) == Math.round(resRi.hi()));
	}

	@Test
	public void intervalValuesSqrt() {
		resIv = (IntervalValue) iv1.sqrt();
		resRi = IAMath.integerRoot(ri1, new RealInterval(2, 2));
		// difference at the last decimal place is acceptable
		assertTrue("" + resIv.getMin() + " ==  " + resRi.lo(),
				Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue("" + resIv.getMax() + " == " + resRi.hi(),
				Math.round(resIv.getMax()) == Math.round(resRi.hi()));
		
		resIv = (IntervalValue) iv4.sqrt();
		resRi = IAMath.integerRoot(ri4, new RealInterval(2, 2));
		// difference at the last decimal place is acceptable
		assertTrue("" + resIv.getMin() + " ==  " + resRi.lo(),
				Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue("" + resIv.getMax() + " == " + resRi.hi(),
				Math.round(resIv.getMax()) == Math.round(resRi.hi()));
		
		resIv = (IntervalValue) iv6.sqrt();
		resRi = IAMath.integerRoot(ri6, new RealInterval(2, 2));
		// difference at the last decimal place is acceptable
		assertTrue("" + resIv.getMin() + " ==  " + resRi.lo(),
				Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue("" + resIv.getMax() + " == " + resRi.hi(),
				Math.round(resIv.getMax()) == Math.round(resRi.hi()));
	}

	double[] getRandomPair() {
		double lo = getRandom();
		double hi;
		while ((hi = getRandom()) < lo)
			;

		return new double[] { lo, hi };
	}

	double getRandom() {
		double d;
		d = (Math.random() * MAX + 1);
		if (((int) Math.random() * 2) == 1)
			return -1 * d;
		return d;
	}
}
