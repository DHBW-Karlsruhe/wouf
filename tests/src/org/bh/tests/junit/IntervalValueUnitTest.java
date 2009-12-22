package org.bh.tests.junit;

import static org.junit.Assert.assertTrue;
import net.sourceforge.interval.ia_math.IAMath;
import net.sourceforge.interval.ia_math.RealInterval;

import org.bh.data.types.IntervalValue;
import org.junit.Before;
import org.junit.Test;

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
	public void IntervalValuesAdd() {
		
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
		resRi = IAMath.add(IAMath.add(IAMath.add(IAMath.add(IAMath.add(ri1, ri2), ri3),ri4),ri5),ri6);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());
		
		//add further particular tests here

	}

	@Test
	public void IntervalValuesSub() {

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
		
		//add further particular tests here
	}

	@Test
	public void IntervalValuesMul() {
		
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
		resRi = IAMath.mul(IAMath.mul(IAMath.mul(IAMath.mul(IAMath.mul(ri1, ri2), ri3),ri4),ri5),ri6);
		assertTrue(resIv.getMin() == resRi.lo());
		assertTrue(resIv.getMax() == resRi.hi());
		
		//add further particular tests here
	}

	@Test
	public void IntervalValuesDiv() {

		resIv = (IntervalValue) iv1.div(iv2);
		resRi = IAMath.div(ri1, ri2);
		// difference at the last decimal place ist acceptable
		//assertTrue(resIv.getMin() == resRi.lo());
		//assertTrue(resIv.getMax() == resRi.hi());
		assertTrue(Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue(Math.round(resIv.getMax()) == Math.round(resRi.hi()));
		
		resIv = (IntervalValue) iv6.div(iv3);
		resRi = IAMath.div(ri6, ri3);
		// difference at the last decimal place ist acceptable
		//assertTrue(resIv.getMin() == resRi.lo());
		//assertTrue(resIv.getMax() == resRi.hi());
		assertTrue(Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue(Math.round(resIv.getMax()) == Math.round(resRi.hi()));
		
		resIv = (IntervalValue) iv4.div(iv4);
		resRi = IAMath.div(ri4, ri4);
		// difference at the last decimal place ist acceptable
		//assertTrue(resIv.getMin() == resRi.lo());
		//assertTrue(resIv.getMax() == resRi.hi());
		assertTrue(Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue(Math.round(resIv.getMax()) == Math.round(resRi.hi()));
		
		resIv = (IntervalValue) iv5.div(iv4).div(iv5).div(iv2);
		resRi = IAMath.div(IAMath.div(IAMath.div(ri5, ri4),ri5),ri2);
		// difference at the last decimal place ist acceptable
		//assertTrue(resIv.getMin() == resRi.lo());
		//assertTrue(resIv.getMax() == resRi.hi());
		assertTrue(Math.round(resIv.getMin()) == Math.round(resRi.lo()));
		assertTrue(Math.round(resIv.getMax()) == Math.round(resRi.hi()));
		
		//add further particular tests here
	}

	@Test
	public void IntervalValuesPow() {
		//TODO
	}

	@Test
	public void IntervalValuesSqrt() {
		//TODO
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
