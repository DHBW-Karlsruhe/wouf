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

import java.util.LinkedList;
import java.util.List;

import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.gui.swing.comp.BHProgressBar;
import org.bh.plugin.timeSeries.TimeSeriesCalculator_v3;
import org.junit.Before;
import org.junit.Test;

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
	int p_future = 3;
	
	/*
	 * Cashflow liste SAP
	 */
	final Calculable cashfolw_tm9 = new DoubleValue(638.065);
	final Calculable cashfolw_tm8 = new DoubleValue(680.011);
	final Calculable cashfolw_tm7 = new DoubleValue(988.846);
	final Calculable cashfolw_tm6 = new DoubleValue(1686.701);
	final Calculable cashfolw_tm5 = new DoubleValue(1504.916);
	final Calculable cashfolw_tm4 = new DoubleValue(1826.945);
	final Calculable cashfolw_tm3 = new DoubleValue(1607.9);
	final Calculable cashfolw_tm2 = new DoubleValue(1846.815);
	final Calculable cashfolw_tm1 = new DoubleValue(1950);
	final Calculable cashfolw_t0 = new DoubleValue(2158); 
	
	/*
	 * Cashflow liste 2
	 */
	final Calculable cashfolw2_tm9 = new DoubleValue(16681);
	final Calculable cashfolw2_tm8 = new DoubleValue(18023);
	final Calculable cashfolw2_tm7 = new DoubleValue(16017);
	final Calculable cashfolw2_tm6 = new DoubleValue(15944);
	final Calculable cashfolw2_tm5 = new DoubleValue(15909);
	final Calculable cashfolw2_tm4 = new DoubleValue(13826);
	final Calculable cashfolw2_tm3 = new DoubleValue(11060);
	final Calculable cashfolw2_tm2 = new DoubleValue(11032);
	final Calculable cashfolw2_tm1 = new DoubleValue(14337);
	final Calculable cashfolw2_t0 = new DoubleValue(13088); 
	
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
	public void calculateCashflows_Test(){
		System.out.println("---- TimeSeriesCalculatorUnitTests: calculateCashflows_Test ----");
		
		//TimeSeriesCalculator erstellen & berechnen
		TimeSeriesCalculator_v3 calculator = new TimeSeriesCalculator_v3(cashflows);
		List<Calculable> cashflows_prognostiziert = calculator.calculateCashflows(10, 3, false, 1, false, null);
		
		//Ausgabe
		System.out.println("--neue Cashflowliste:");
		for (Calculable cashflow : cashflows_prognostiziert){
			System.out.println("-- "+cashflow.toNumber());
		}
		System.out.println("---- TimeSeriesCalculatorUnitTests: calculateCashflows_Test beendet----");
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
		
		cashflows_kalkulations_test = calculator.calcultionTest_4_periods_to_history_v2(3, 1, false);
		
		//Ausgabe
		for (Calculable cashflow : cashflows_kalkulations_test){
			System.out.println("-- p=3 -- "+cashflow.toNumber());
		}
		
		System.out.println("---- TimeSeriesCalculatorUnitTests: calcultionTest_4_periods_to_history_Test beendet----");
		assertTrue(keine_fehler);
	}
	
	@Test
	public void Getter_Setter_test(){
		
		

		boolean keine_fehler = true;
		System.out.println("---- TimeSeriesCalculatorUnitTests: Getter_Setter_test ----");
		
		TimeSeriesCalculator_v3 calculator = new TimeSeriesCalculator_v3(cashflows);
		calculator.setCashflows(new LinkedList<Calculable>());
		calculator.setCashflowsByMakingCopy(new LinkedList<Calculable>());
		calculator.getCashflows();
		calculator.getCashflowsCopy();
		calculator.setProgressBar(new BHProgressBar("", 0, 0));

		System.out.println("---- TimeSeriesCalculatorUnitTests: Getter_Setter_test beendet----");
		assertTrue(keine_fehler);
	}
	
	@Test
	public void weisses_Rauschen(){
		
		

		boolean keine_fehler = true;
		System.out.println("---- TimeSeriesCalculatorUnitTests: weisses_Rauschen ----");
		
		TimeSeriesCalculator_v3 calculator = new TimeSeriesCalculator_v3(cashflows);
		calculator.calculateCashflows(1, 3, true, 10, false, null);

		System.out.println("---- TimeSeriesCalculatorUnitTests: weisses_Rauschen beendet----");
		assertTrue(keine_fehler);
	}

}
