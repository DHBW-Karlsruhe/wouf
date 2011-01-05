package org.bh.tests.junit;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.bh.data.types.*;
import org.bh.plugin.timeSeries.TimeSeriesCalculator_v2;

/**
 * 
 * Testklasse für TimeSeriesCalculator (TimeSeries Plug-In)
 *
 * @author Andreas Wußler
 * @version 1.0, 05.01.2011
 *
 */
public class TimeSeriesCalculator_v2UnitTests {
	/**
	 * p steht für die Anzahl der zu berücksichtigenden vergangenen Perioden
	 */
	int p = 2;
	
	/**
	 * p_future steht für die Anzahl der zu prognostizierenden Cashflows
	 */
	int p_future = 10;
	
	/*
	 * Cashflow liste
	 */
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
	
	/**
	 * Cashflow-Liste
	 */
	List <Calculable> cashflows = new LinkedList<Calculable>();
	
	@Before
	public void setUp() {
		System.out.println("---- TimeSeriesCalculatorUnitTests: setUp() ----");
		cashflows.add(cashfolw_tm1);
		cashflows.add(cashfolw_t0);
		cashflows.add(cashfolw_t1);
		cashflows.add(cashfolw_t2);
		cashflows.add(cashfolw_t3);
		cashflows.add(cashfolw_t4);
		cashflows.add(cashfolw_t5);
		cashflows.add(cashfolw_t6);
		cashflows.add(cashfolw_t7);
		cashflows.add(cashfolw_t8);
		
		for(Calculable cashflow : cashflows){
			System.out.println("Cashflow: "+cashflow.toNumber().doubleValue());
		}
		
		System.out.println("---- TimeSeriesCalculatorUnitTests: setUp() beendet ----");
	}
	
	@Test
	public void getDummyNextCashflows_Test() {
		boolean fehler_gefunden = false;
		//Parameter
		System.out.println("---- TimeSeriesCalculatorUnitTests: getDummyNextCashflows_Test ----");
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v2 calculator = new TimeSeriesCalculator_v2(cashflows);
		List <Calculable> cashflows_inkl_prognostizierte = new LinkedList<Calculable>();
		cashflows_inkl_prognostizierte = calculator.getDummyNextCashflows(3);
		System.out.println("--neue Cashflowliste:");
		for (Calculable cashflow : cashflows_inkl_prognostizierte){
			System.out.println("-- "+cashflow.toNumber());
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: getDummyNextCashflows_Test beendet----");
		assertTrue(!fehler_gefunden);
	}
	
	
	
	
	@Test
	public void calculateCashflows_Test(){
		boolean fehler_gefunden = false;
		System.out.println("---- TimeSeriesCalculatorUnitTests: calculateCashflows_Test p="+p+" p_future="+p_future+"----");
		
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v2 calculator = new TimeSeriesCalculator_v2(cashflows);
		List <Calculable> cashflows_inkl_prognostizierte = new LinkedList<Calculable>();
		cashflows_inkl_prognostizierte = calculator.calculateCashflows(p_future, p);
		
		for (Calculable cashflow : cashflows_inkl_prognostizierte){
			System.out.println("-- "+cashflow.toNumber());
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: calculateCashflows_Test p="+p+" p_future="+p_future+" beendet----");
	}
	
	@Test
	public void kalkuliere_differenz_rechnung_test(){
		boolean fehler_gefunden = false;
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_differenz_rechnung_Test ----");
		
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v2 calculator = new TimeSeriesCalculator_v2(cashflows);
		List <DoubleValue> differenz_Rechnung;
		differenz_Rechnung = calculator.kalkuliere_differenz_rechnung(cashflows);
		
		//Ausgabe
		for(Calculable differenz : differenz_Rechnung){
			System.out.println("Differenz: "+differenz.toNumber().doubleValue());
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_differenz_rechnung_Test beendet----");
	}
	
	@Test
	public void kalkuliere_my_test(){
		boolean fehler_gefunden = false;
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_my_Test ----");
		
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v2 calculator = new TimeSeriesCalculator_v2(cashflows);
		List <DoubleValue> differenz_Rechnung;
		differenz_Rechnung = calculator.kalkuliere_differenz_rechnung(cashflows);
		System.out.println("Differenzrechnung siehe voriger Test...");
		System.out.println("Griechisch My entspricht "+calculator.kalkuliere_my(differenz_Rechnung));
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_my_Test beendet----");
	}
	
	@Test
	public void kalkuliere_Yt_m_My_Test(){
		boolean fehler_gefunden = false;
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_Yt_m_My_Test ----");
		
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v2 calculator = new TimeSeriesCalculator_v2(cashflows);
		List <DoubleValue> differenz_Rechnung;
		differenz_Rechnung = calculator.kalkuliere_differenz_rechnung(cashflows);
		System.out.println("Differenzrechnung siehe vor-voriger Test...");
		double my = calculator.kalkuliere_my(differenz_Rechnung);
		System.out.println("Griechisch My siehe voriger Test...");
		List <DoubleValue> yt_m_my;
		yt_m_my = calculator.kalkuliere_Yt_m_My(my, differenz_Rechnung, p);
		//Ausgabe
		for(Calculable yt_m_my_Wert : yt_m_my){
			System.out.println("yt_m_my_Wert: "+yt_m_my_Wert.toNumber().doubleValue());
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_Yt_m_My_Test beendet----");
	}
	
	@Test
	public void kalkuliere_abcdef_Test(){
		boolean fehler_gefunden = false;
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_abcdef_Test ----");
		
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v2 calculator = new TimeSeriesCalculator_v2(cashflows);
		List <DoubleValue> differenz_Rechnung;
		differenz_Rechnung = calculator.kalkuliere_differenz_rechnung(cashflows);
		System.out.println("Differenzrechnung siehe vor-vor-voriger Test...");
		double my = calculator.kalkuliere_my(differenz_Rechnung);
		System.out.println("Griechisch My siehe vor-voriger Test...");
		List <DoubleValue> yt_m_my;
		yt_m_my = calculator.kalkuliere_Yt_m_My(my, differenz_Rechnung, p);
		System.out.println("yt_m_my -Berechnung siehe voriger Test...");
		List <DoubleValue> abcde = calculator.kalkuliere_abcdef(yt_m_my, my, p, differenz_Rechnung, cashflows);
		//Ausgabe
		for(Calculable buchstabe : abcde){
			System.out.println("buchstabe: "+buchstabe.toNumber().doubleValue());
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_abcdef_Test beendet----");
	}
	
	@Test
	public void kalkuliere_c0_bis_cx_Test(){
		boolean fehler_gefunden = false;
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_c0_bis_cx_Test ----");
		
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v2 calculator = new TimeSeriesCalculator_v2(cashflows);
		List <DoubleValue> differenz_Rechnung;
		differenz_Rechnung = calculator.kalkuliere_differenz_rechnung(cashflows);
		System.out.println("Differenzrechnung siehe vor-vor-vor-voriger Test...");
		double my = calculator.kalkuliere_my(differenz_Rechnung);
		System.out.println("Griechisch My siehe vor-vor-voriger Test...");
		List <DoubleValue> yt_m_my;
		yt_m_my = calculator.kalkuliere_Yt_m_My(my, differenz_Rechnung, p);
		System.out.println("yt_m_my -Berechnung siehe vor-voriger Test...");
		List <DoubleValue> abcde = calculator.kalkuliere_abcdef(yt_m_my, my, p, differenz_Rechnung, cashflows);
		System.out.println("a, b, c -Berechnung siehe voriger Test...");
		List <DoubleValue> c0c1c2 = calculator.kalkuliere_c0_bis_cx(abcde);
		//Ausgabe
		for(Calculable cx : c0c1c2){
			System.out.println("cx: "+cx.toNumber().doubleValue());
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_c0_bis_cx_Test beendet----");
	}
	
	@Test
	public void kalkuliere_NextCashfolw_Test(){
		boolean fehler_gefunden = false;
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_NextCashfolw_Test ----");
		
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v2 calculator = new TimeSeriesCalculator_v2(cashflows);
		List <DoubleValue> differenz_Rechnung;
		differenz_Rechnung = calculator.kalkuliere_differenz_rechnung(cashflows);
		System.out.println("Differenzrechnung siehe vor-vor-vor-vor-voriger Test...");
		double my = calculator.kalkuliere_my(differenz_Rechnung);
		System.out.println("Griechisch My siehe vor-vor-vor-voriger Test...");
		List <DoubleValue> yt_m_my;
		yt_m_my = calculator.kalkuliere_Yt_m_My(my, differenz_Rechnung, p);
		System.out.println("yt_m_my -Berechnung siehe vor-vor-voriger Test...");
		List <DoubleValue> abcde = calculator.kalkuliere_abcdef(yt_m_my, my, p, differenz_Rechnung, cashflows);
		System.out.println("a, b, c -Berechnung siehe vor-voriger Test...");
		List <DoubleValue> c0c1c2 = calculator.kalkuliere_c0_bis_cx(abcde);
		System.out.println("cx Berechnung siehe voriger Test");
		double nextCashflow = calculator.kalkuliere_NextCashfolw(c0c1c2, cashflows);
		System.out.println("nächster Cashflow ist "+nextCashflow);
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_NextCashfolw_Testauft beendet----");
	}

}
