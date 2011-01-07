package org.bh.tests.junit;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.bh.data.types.*;
import org.bh.plugin.timeSeries.TimeSeriesCalculator;

public class TimeSeriesCalculatorUnitTests {

	@Test
	public void get_Ygamma_for_cashflowlist_Test() {
		boolean alle_tests_erfolgreich = false;
		//Parameter
		int p = 2;
		System.out.println("---- TimeSeriesCalculatorUnitTests: get_Ygamma_for_cashflowlist_Test p="+p+"----");
		Calculable cashfolw_tm1 = new DoubleValue(16681);
		Calculable cashfolw_t0 = new DoubleValue(18023);
		Calculable cashfolw_t1 = new DoubleValue(16017);
		Calculable cashfolw_t2 = new DoubleValue(15944);
		//Cashflowliste anlegen + füllen
		List <Calculable> cashflows = new LinkedList<Calculable>();
		System.out.println("--Test1--");
		cashflows.add(cashfolw_t0); System.out.println(cashfolw_t0.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_tm1);System.out.println(cashfolw_tm1.toNumber() + " zur Cashflowliste hinzufügen");
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator calculator = new TimeSeriesCalculator(p, null);
		double y_gamma = calculator.get_Ygamma_for_cashflowlist(cashflows);
		System.out.println("--Test1 durchlaufen--");
		if(y_gamma == 225120.5){
			System.out.println("--Test1 erfolgreich--");
			System.out.println("--Test2--");
			cashflows = new LinkedList<Calculable>(); //neue Liste erstellen
			cashflows.add(cashfolw_t1); System.out.println(cashfolw_t1.toNumber() + " zur Cashflowliste hinzufügen");
			cashflows.add(cashfolw_t0); System.out.println(cashfolw_t0.toNumber() + " zur Cashflowliste hinzufügen");
			cashflows.add(cashfolw_tm1);System.out.println(cashfolw_tm1.toNumber() + " zur Cashflowliste hinzufügen");
			y_gamma = calculator.get_Ygamma_for_cashflowlist(cashflows);
			System.out.println("--Test2 durchlaufen--");
			if (y_gamma == 503004.5){
				System.out.println("--Test2 erfolgreich--");
				System.out.println("--Test3--");
				cashflows = new LinkedList<Calculable>(); //neue Liste erstellen
				cashflows.add(cashfolw_t2);System.out.println(cashfolw_t2.toNumber() + " zur Cashflowliste hinzufügen");
				cashflows.add(cashfolw_t1); System.out.println(cashfolw_t1.toNumber() + " zur Cashflowliste hinzufügen");
				cashflows.add(cashfolw_t0); System.out.println(cashfolw_t0.toNumber() + " zur Cashflowliste hinzufügen");
				cashflows.add(cashfolw_tm1);System.out.println(cashfolw_tm1.toNumber() + " zur Cashflowliste hinzufügen");
				y_gamma = calculator.get_Ygamma_for_cashflowlist(cashflows);
				System.out.println("--Test3 durchlaufen--");
				if(y_gamma == 666.125){
					System.out.println("--Test3 erfolgreich--");
					alle_tests_erfolgreich = true;
				}
			}
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: get_Ygamma_for_cashflowlist_Test beendet----");
		assertTrue(alle_tests_erfolgreich);
	}
	
	@Test
	public void getYgammaList_Test() {
		boolean fehler_gefunden = false;
		//Parameter
		int p = 2;
		System.out.println("---- TimeSeriesCalculatorUnitTests: getYgammaList_Test p="+p+"----");
		
		Calculable cashfolw_tm1 = new DoubleValue(16681);
		Calculable cashfolw_t0 = new DoubleValue(18023);
		Calculable cashfolw_t1 = new DoubleValue(16017);
		Calculable cashfolw_t2 = new DoubleValue(15944);
		Calculable cashfolw_t3 = new DoubleValue(15909);
		Calculable cashfolw_t4 = new DoubleValue(13826);
		Calculable cashfolw_t5 = new DoubleValue(11060);
		Calculable cashfolw_t6 = new DoubleValue(11032);
		Calculable cashfolw_t7 = new DoubleValue(14337);
		Calculable cashfolw_t8 = new DoubleValue(13088); 

		//Cashflowliste anlegen + füllen
		List <Calculable> cashflows = new LinkedList<Calculable>();
		System.out.println("--Test1--");
		cashflows.add(cashfolw_t8);System.out.println(cashfolw_t8.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_t7);System.out.println(cashfolw_t7.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_t6);System.out.println(cashfolw_t6.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_t5);System.out.println(cashfolw_t5.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_t4);System.out.println(cashfolw_t4.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_t3);System.out.println(cashfolw_t3.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_t2);System.out.println(cashfolw_t2.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_t1); System.out.println(cashfolw_t1.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_t0); System.out.println(cashfolw_t0.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_tm1);System.out.println(cashfolw_tm1.toNumber() + " zur Cashflowliste hinzufügen");
		
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator calculator = new TimeSeriesCalculator(p, cashflows);
		List <DoubleValue> y_gammas = new LinkedList<DoubleValue>();
		y_gammas = calculator.getYgammaList();
		System.out.println("--gesammelte \"Y(Gamma)\"s:");
		for (DoubleValue y_gamma : y_gammas){
			System.out.println(y_gamma.toNumber());
		}
		
		if(y_gammas.indexOf(new DoubleValue(225120.5)) != 0){
			fehler_gefunden = true;
		}
		if(y_gammas.indexOf(new DoubleValue(503004.5)) != 1){
			fehler_gefunden = true;
		}
		if(y_gammas.indexOf(new DoubleValue(666.125)) != 2){
			fehler_gefunden = true;
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: getYgammaList_Test beendet----");
		assertTrue(!fehler_gefunden);
	}
	
	@Test
	public void getDummyNextCashflows_Test() {
		boolean fehler_gefunden = false;
		//Parameter
		System.out.println("---- TimeSeriesCalculatorUnitTests: getDummyNextCashflows_Test ----");
		int p = 2;
		Calculable cashfolw_tm1 = new DoubleValue(16681);
		Calculable cashfolw_t0 = new DoubleValue(18023);
		Calculable cashfolw_t1 = new DoubleValue(16017);
		Calculable cashfolw_t2 = new DoubleValue(15944);
		//Cashflowliste anlegen + füllen
		List <Calculable> cashflows = new LinkedList<Calculable>();
		System.out.println("--Test1--");
		cashflows.add(cashfolw_t2);System.out.println(cashfolw_t2.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_t1); System.out.println(cashfolw_t1.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_t0); System.out.println(cashfolw_t0.toNumber() + " zur Cashflowliste hinzufügen");
		cashflows.add(cashfolw_tm1);System.out.println(cashfolw_tm1.toNumber() + " zur Cashflowliste hinzufügen");
		 
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator calculator = new TimeSeriesCalculator(p, cashflows);
		List <Calculable> cashflows_inkl_prognostizierte = new LinkedList<Calculable>();
		cashflows_inkl_prognostizierte = calculator.getDummyNextCashflows(3);
		System.out.println("--neue Cashflowliste:");
		for (Calculable cashflow : cashflows_inkl_prognostizierte){
			System.out.println("-- "+cashflow.toNumber());
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: getDummyNextCashflows_Test beendet----");
		assertTrue(!fehler_gefunden);
	}

}