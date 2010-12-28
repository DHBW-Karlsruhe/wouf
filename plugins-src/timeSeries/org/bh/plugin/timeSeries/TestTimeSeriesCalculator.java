package org.bh.plugin.timeSeries;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.bh.data.types.*;

public class TestTimeSeriesCalculator {

	@Test
	public void testTest() {
		//Parameter
		int p = 2;
		Calculable cashfolw_tm1 = new DoubleValue(16681);
		Calculable cashfolw_t0 = new DoubleValue(18023);
		Calculable cashfolw_t1 = new DoubleValue(16017);
		//Cashflowliste anlegen + füllen
		List <Calculable> cashflows = new LinkedList<Calculable>();
		//cashflows.add(cashfolw_t1);
		cashflows.add(cashfolw_t0);
		cashflows.add(cashfolw_tm1);
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator calculator = new TimeSeriesCalculator(2, cashflows);
		calculator.calculateNextPeriod();
		assertTrue(true);
	}

}
