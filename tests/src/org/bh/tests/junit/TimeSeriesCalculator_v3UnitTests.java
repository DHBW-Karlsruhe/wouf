package org.bh.tests.junit;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.bh.data.types.*;
import org.bh.plugin.timeSeries.TimeSeriesCalculator_v2;
import org.bh.plugin.timeSeries.TimeSeriesCalculator_v3;

/**
 * 
 * Testklasse für TimeSeriesCalculator (TimeSeries Plug-In)
 *
 * @author Andreas Wußler
 * @version 1.0, 05.01.2011
 *
 */
public class TimeSeriesCalculator_v3UnitTests {
	
	/**
	 * p_future steht für die Anzahl der zu prognostizierenden Cashflows
	 */
	int p_future = 10;
	
	/*
	 * Cashflow liste
	 */
	final Calculable cashfolw_tm9 = new DoubleValue(16681);
	final Calculable cashfolw_tm8 = new DoubleValue(18023);
	final Calculable cashfolw_tm7 = new DoubleValue(16017);
	final Calculable cashfolw_tm6 = new DoubleValue(15944);
	final Calculable cashfolw_tm5 = new DoubleValue(15909);
	final Calculable cashfolw_tm4 = new DoubleValue(13826);
	final Calculable cashfolw_tm3 = new DoubleValue(11060);
	final Calculable cashfolw_tm2 = new DoubleValue(11032);
	final Calculable cashfolw_tm1 = new DoubleValue(14337);
	final Calculable cashfolw_t0 = new DoubleValue(13088); 
	
	/**
	 * Cashflow-Liste
	 */
	List <Calculable> cashflows = new LinkedList<Calculable>();
	
	@Before
	public void setUp() {
		System.out.println("---- TimeSeriesCalculatorUnitTests: setUp() ----");
		cashflows.add(cashfolw_tm9);
		cashflows.add(cashfolw_tm8);
		cashflows.add(cashfolw_tm7);
		cashflows.add(cashfolw_tm6);
		cashflows.add(cashfolw_tm5);
		cashflows.add(cashfolw_tm4);
		cashflows.add(cashfolw_tm3);
		cashflows.add(cashfolw_tm2);
		cashflows.add(cashfolw_tm1);
		cashflows.add(cashfolw_t0);
		
		for(Calculable cashflow : cashflows){
			System.out.println("Cashflow: "+cashflow.toNumber().doubleValue());
		}
		
		System.out.println("---- TimeSeriesCalculatorUnitTests: setUp() beendet ----");
	}
	
	
	
	@Test
	public void calulateNextCashflow_Test(){
		boolean fehler_gefunden = false;
		System.out.println("---- TimeSeriesCalculatorUnitTests: calulateNextCashflow_Test ----");
		
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v3 calculator = new TimeSeriesCalculator_v3(cashflows);
		double nextCashflow = 0;
		nextCashflow = calculator.calulateNextCashflow(cashflows, 3);
	}
	
	@Test
	public void getDummyNextCashflows_Test() {
		boolean fehler_gefunden = false;
		//Parameter
		int anzahl_dummy_werte = 10;
		System.out.println("---- TimeSeriesCalculatorUnitTests: getDummyNextCashflows_Test ----");
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v3 calculator = new TimeSeriesCalculator_v3(cashflows);
		List <Calculable> cashflows_inkl_prognostizierte = new LinkedList<Calculable>();
		cashflows_inkl_prognostizierte = calculator.getDummyNextCashflows(anzahl_dummy_werte);
		System.out.println("--neue Cashflowliste:");
		for (Calculable cashflow : cashflows_inkl_prognostizierte){
			System.out.println("-- "+cashflow.toNumber());
		}
		if(anzahl_dummy_werte != cashflows_inkl_prognostizierte.size() - this.cashflows.size()){
			fehler_gefunden = true;
			System.out.println("Funktion hätte "+anzahl_dummy_werte+"liefern sollen, hat aber "+(cashflows_inkl_prognostizierte.size() - this.cashflows.size())+" geliefert..");
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: getDummyNextCashflows_Test beendet----");
		assertTrue(!fehler_gefunden);
	}
	
	@Test
	public void calcultionTest_4_periods_to_history_Test(){
		boolean keine_fehler = true;
		System.out.println("---- TimeSeriesCalculatorUnitTests: calcultionTest_4_periods_to_history_Test ----");
		
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v3 calculator = new TimeSeriesCalculator_v3(cashflows);
		List <Calculable> cashflows_kalkulations_test = new LinkedList<Calculable>();
		
		cashflows_kalkulations_test = calculator.calcultionTest_4_periods_to_history(2, 3);
		
		//Ausgabe
		for (Calculable cashflow : cashflows_kalkulations_test){
			System.out.println("-- p=2 -- "+cashflow.toNumber());
		}
		
		System.out.println("---- TimeSeriesCalculatorUnitTests: calcultionTest_4_periods_to_history_Test beendet----");
		assertTrue(keine_fehler);
	}

}
