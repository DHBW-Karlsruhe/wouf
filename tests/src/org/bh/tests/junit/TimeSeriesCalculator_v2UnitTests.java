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
	 * p_future steht für die Anzahl der zu prognostizierenden Cashflows
	 */
	int p_future = 10;
	
	/*
	 * Cashflow liste
	 */
	final Calculable cashfolw_tm1 = new DoubleValue(16681);
	final Calculable cashfolw_t0 = new DoubleValue(18023);
	final Calculable cashfolw_t1 = new DoubleValue(16017);
	final Calculable cashfolw_t2 = new DoubleValue(15944);
	final Calculable cashfolw_t3 = new DoubleValue(15909);
	final Calculable cashfolw_t4 = new DoubleValue(13826);
	final Calculable cashfolw_t5 = new DoubleValue(11060);
	final Calculable cashfolw_t6 = new DoubleValue(11032);
	final Calculable cashfolw_t7 = new DoubleValue(14337);
	final Calculable cashfolw_t8 = new DoubleValue(13088); 
	
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
	public void kalkuliere_differenz_rechnung_test(){
		boolean fehler_gefunden = false;
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_differenz_rechnung_Test ----");
		
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v2 calculator = new TimeSeriesCalculator_v2(cashflows);
		List <DoubleValue> differenz_Rechnung;
		differenz_Rechnung = calculator.kalkuliere_differenz_rechnung(cashflows);
		
		//Ausgabe & Überprüfung
		int durchgang = 1;
		double differenz_dbv = 0;
		for(Calculable differenz : differenz_Rechnung){
			differenz_dbv = differenz.toNumber().doubleValue();
			System.out.println("Differenz"+durchgang+" :"+differenz_dbv);
			switch(durchgang){
				case 1: if (differenz_dbv != 1342.0){ fehler_gefunden = true; System.out.println("fehler differenz1="+differenz_dbv + " sollte 1342 sein");}break;
				case 2: if (differenz_dbv != -2006.0){ fehler_gefunden = true; System.out.println("fehler differenz2="+differenz_dbv + " sollte -2006 sein");}break;
				case 3: if (differenz_dbv != -73.0){ fehler_gefunden = true; System.out.println("fehler differenz3="+differenz_dbv + " sollte -73 sein");}break;
				case 4: if (differenz_dbv != -35.0){ fehler_gefunden = true; System.out.println("fehler differenz4="+differenz_dbv + " sollte -35 sein");}break;
				case 5: if (differenz_dbv != -2083.0){ fehler_gefunden = true; System.out.println("fehler differenz5="+differenz_dbv + " sollte -2083 sein");}break;
				case 6: if (differenz_dbv != -2766.0){ fehler_gefunden = true; System.out.println("fehler differenz6="+differenz_dbv + " sollte -2766 sein");}break;
				case 7: if (differenz_dbv != -28.0){ fehler_gefunden = true; System.out.println("fehler differenz7="+differenz_dbv + " sollte -28 sein");}break;
				case 8: if (differenz_dbv != 3305.0){ fehler_gefunden = true; System.out.println("fehler differenz8="+differenz_dbv + " sollte 3305 sein");}break;
				case 9: if (differenz_dbv != -1249.0){ fehler_gefunden = true; System.out.println("fehler differenz9="+differenz_dbv + " sollte -1249 sein");}break;
			}
			durchgang++;
		}
		if(durchgang != 10 ){
			fehler_gefunden=true;
			System.out.println("Differenzen Menge sollte 9 sein, ist aber "+ (durchgang-1));
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_differenz_rechnung_Test beendet----");
		assertTrue(!fehler_gefunden);
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
		
		//Ausgabe & Überprüfung
		double my = calculator.kalkuliere_my(differenz_Rechnung);
		System.out.println("Griechisch My entspricht "+my);
		if(my != -399.22222222222223){
			System.out.println("Fehler griechisch My sollte -399.22222222222223 sein"); fehler_gefunden = true;
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_my_Test beendet----");
		assertTrue(!fehler_gefunden);
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
		yt_m_my = calculator.kalkuliere_Yt_m_My(my, differenz_Rechnung, 2);
		
		//Ausgabe & Überprüfung
		int counter = 1;
		double yt_m_my_Wert_dbv = 0;
		for(Calculable yt_m_my_Wert : yt_m_my){
			yt_m_my_Wert_dbv = yt_m_my_Wert.toNumber().doubleValue();
			System.out.println("yt-my "+counter+": "+yt_m_my_Wert_dbv);
			switch (counter){
				case 1: if (yt_m_my_Wert_dbv != -1606.7777777777778){ fehler_gefunden = true; System.out.println("fehler yt-my 1="+yt_m_my_Wert_dbv + " sollte -1606.7777777777778 sein");}break;
				case 2: if (yt_m_my_Wert_dbv != 326.22222222222223){ fehler_gefunden = true; System.out.println("fehler yt-my 2="+yt_m_my_Wert_dbv + " sollte 326.22222222222223 sein");}break;
				case 3: if (yt_m_my_Wert_dbv != 364.22222222222223){ fehler_gefunden = true; System.out.println("fehler yt-my 3="+yt_m_my_Wert_dbv + " sollte 364.22222222222223 sein");}break;
				case 4: if (yt_m_my_Wert_dbv != -1683.7777777777778){ fehler_gefunden = true; System.out.println("fehler yt-my 4="+yt_m_my_Wert_dbv + " sollte -1683.7777777777778 sein");}break;
				case 5: if (yt_m_my_Wert_dbv != -2366.777777777778){ fehler_gefunden = true; System.out.println("fehler yt-my 5="+yt_m_my_Wert_dbv + " sollte -2366.777777777778 sein");}break;
				case 6: if (yt_m_my_Wert_dbv != 371.22222222222223){ fehler_gefunden = true; System.out.println("fehler yt-my 6="+yt_m_my_Wert_dbv + " sollte 371.22222222222223 sein");}break;
				case 7: if (yt_m_my_Wert_dbv != 3704.222222222222){ fehler_gefunden = true; System.out.println("fehler yt-my 7="+yt_m_my_Wert_dbv + " sollte 3704.222222222222 sein");}break;
				case 8: if (yt_m_my_Wert_dbv != -849.7777777777778){ fehler_gefunden = true; System.out.println("fehler yt-my 8="+yt_m_my_Wert_dbv + " sollte -849.7777777777778 sein");}break;
			}
			counter++;
		}
		if(counter != 9){
			fehler_gefunden = true; System.out.println("Menge von yt-my sollte 8 sein ist aber "+(counter-1));
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_Yt_m_My_Test beendet----");
		assertTrue(!fehler_gefunden);
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
		yt_m_my = calculator.kalkuliere_Yt_m_My(my, differenz_Rechnung, 2);
		System.out.println("yt_m_my -Berechnung siehe voriger Test...");
		List <DoubleValue> abcde = calculator.kalkuliere_abcdef(yt_m_my, my, 2, differenz_Rechnung, cashflows);
		
		//Ausgabe & Überprüfung
		int counter = 1;
		double buchstabe_dbv = 0;
		for(Calculable buchstabe : abcde){
			buchstabe_dbv = buchstabe.toNumber().doubleValue();
			System.out.println("buchstabe"+counter+": "+buchstabe_dbv);
			switch (counter){
				case 1: if(buchstabe_dbv != -310315.56172839506){fehler_gefunden = true; System.out.println("Fehler: a sollte -310315.56172839506 sein ist aber "+buchstabe_dbv);} break;
				case 2: if(buchstabe_dbv != -1472195.2422839506){fehler_gefunden = true; System.out.println("Fehler: b sollte -1472195.2422839506 sein ist aber "+buchstabe_dbv);} break;
			}
			counter++;
		}
		if(counter != 3){
			fehler_gefunden = true; System.out.println("Menge von abcde.. sollte 2 sein ist aber "+(counter-1));
		}
		
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_abcdef_Test beendet----");
		assertTrue(!fehler_gefunden);
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
		yt_m_my = calculator.kalkuliere_Yt_m_My(my, differenz_Rechnung, 2);
		System.out.println("yt_m_my -Berechnung siehe vor-voriger Test...");
		List <DoubleValue> abcde = calculator.kalkuliere_abcdef(yt_m_my, my, 2, differenz_Rechnung, cashflows);
		System.out.println("a, b, c -Berechnung siehe voriger Test...");
		List <DoubleValue> c0c1c2 = calculator.kalkuliere_c0_bis_cx(abcde);
		//Ausgabe & Überprüfung
		double cx_dbv = 0;
		int counter = 1;
		for(Calculable cx : c0c1c2){
			cx_dbv = cx.toNumber().doubleValue();
			System.out.println("c"+counter+": "+cx_dbv);
			switch (counter){
				case 1: if(cx_dbv != 0.1740890215250812){fehler_gefunden = true; System.out.println("Fehler: c1 sollte 0.1740890215250812 sein ist aber "+cx_dbv);} break;
				case 2: if(cx_dbv != 0.8259109784749188){fehler_gefunden = true; System.out.println("Fehler: c2 sollte 0.8259109784749188 sein ist aber "+cx_dbv);} break;
			}
			counter++;
		}
		if(counter != 3){
			fehler_gefunden = true; System.out.println("Menge von cx sollte 2 sein ist aber "+(counter-1));
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_c0_bis_cx_Test beendet----");
		assertTrue(!fehler_gefunden);
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
		yt_m_my = calculator.kalkuliere_Yt_m_My(my, differenz_Rechnung, 2);
		System.out.println("yt_m_my -Berechnung siehe vor-vor-voriger Test...");
		List <DoubleValue> abcde = calculator.kalkuliere_abcdef(yt_m_my, my, 2, differenz_Rechnung, cashflows);
		System.out.println("a, b, c -Berechnung siehe vor-voriger Test...");
		List <DoubleValue> c0c1c2 = calculator.kalkuliere_c0_bis_cx(abcde);
		System.out.println("cx Berechnung siehe voriger Test");
		double nextCashflow = calculator.kalkuliere_NextCashfolw(c0c1c2, cashflows);
		System.out.println("nächster Cashflow ist "+nextCashflow);
		if(nextCashflow != 14119.562812115173){
			fehler_gefunden = true;
			System.out.println("Fehler: nächster Cashflow sollte 14119.562812115173 sein ist aber "+nextCashflow);
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: kalkuliere_NextCashfolw_Testauft beendet----");
		assertTrue(!fehler_gefunden);
	}
	
	@Test
	public void getDummyNextCashflows_Test() {
		boolean fehler_gefunden = false;
		//Parameter
		int anzahl_dummy_werte = 10;
		System.out.println("---- TimeSeriesCalculatorUnitTests: getDummyNextCashflows_Test ----");
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v2 calculator = new TimeSeriesCalculator_v2(cashflows);
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
	public void calculateCashflows_Test(){
		boolean keine_fehler = false;
		System.out.println("---- TimeSeriesCalculatorUnitTests: calculateCashflows_Test p="+2+" p_future="+p_future+"----");
		
		//TimeSeriesCalculator erstellen
		TimeSeriesCalculator_v2 calculator = new TimeSeriesCalculator_v2(cashflows);
		List <Calculable> cashflows_inkl_prognostizierte = new LinkedList<Calculable>();
		cashflows_inkl_prognostizierte = calculator.calculateCashflows(p_future, 2);
		
		for (Calculable cashflow : cashflows_inkl_prognostizierte){
			System.out.println("-- p=2 -- "+cashflow.toNumber());
		}
		
		if(cashflows_inkl_prognostizierte.indexOf(new DoubleValue(14119.562812115173)) == cashflows.size()){//Cashflow richtig prognostiziert
			cashflows_inkl_prognostizierte = calculator.calculateCashflows(p_future, 3); //für p=3 ausführen
			System.out.println("---- TimeSeriesCalculatorUnitTests: calculateCashflows_Test für p=3 p_future="+p_future);
			for (Calculable cashflow : cashflows_inkl_prognostizierte){
				System.out.println("-- p=3 --"+cashflow.toNumber());
			}
			if(cashflows_inkl_prognostizierte.indexOf(new DoubleValue(13992.333257821232)) == cashflows.size()){//Cashflow richtig prognostiziert
				cashflows_inkl_prognostizierte = calculator.calculateCashflows(p_future, 4); //für p=3 ausführen
				System.out.println("---- TimeSeriesCalculatorUnitTests: calculateCashflows_Test für p=3 p_future="+p_future);
				for (Calculable cashflow : cashflows_inkl_prognostizierte){
					System.out.println("-- p=4 -- "+cashflow.toNumber());
				}
				if(cashflows_inkl_prognostizierte.indexOf(new DoubleValue(15382.119113822464)) == cashflows.size()){//Cashflow richtig prognostiziert
					keine_fehler = true;
				}
			}
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: calculateCashflows_Test beendet----");
		assertTrue(keine_fehler);
	}

}
