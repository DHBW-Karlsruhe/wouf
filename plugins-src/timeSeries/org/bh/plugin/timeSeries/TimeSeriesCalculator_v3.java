/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber * : Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
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
package org.bh.plugin.timeSeries;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.data.DTO;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.data.types.DoubleValue;
import org.bh.gui.swing.comp.BHProgressBar;
import org.bh.platform.PluginManager;
import org.bh.plugin.directinput.DTODirectInput;

import Jama.Matrix;

/**
 * Diese Klasse stellt die Berechnung für die Zeitreihenanalyse bereit.
 * 
 * Sie unterstützt jetzt auch die Berechnung des Unternehmenswertes auf Basis der errechneten CashFlows der Zeitreihenanalyse.
 * 
 * @author Andreas Wussler, Timo Klein
 * @version 3.2, 01.02.2011
 * @update Yannick Rödl, 22.12.2011
 */
public class TimeSeriesCalculator_v3 {
	/**
	 * Liste mit den Cashflows, sortiert nach Perioden
	 */
	private List<Calculable> cashflows = null;
	/**
	 * Referenz zu einer Progressbar, diese wird falls vorhanden, den Status der
	 * Berechnung übermittelt
	 */
	private BHProgressBar progressB = null;
	private boolean interrupted;
	private DistributionMap resultMap;
	private DTOScenario scenario;
	private Logger log = Logger.getLogger(TimeSeriesCalculator_v3.class);

	/**
	 * Standardkonstruktor, kopiert die übergebene Cashflowliste in den
	 * Objektspeicher
	 * 
	 * @param cashflows
	 *            Liste mit den Cashflows, sortiert nach Perioden (Vergangenheit
	 *            nach Zukunft)
	 */
	public TimeSeriesCalculator_v3(List<Calculable> cashflows) {
		this.cashflows = new LinkedList<Calculable>();// initalisiere
														// this.cashflows
		for (Calculable cashflow : cashflows) {// kopiere parametisierte Liste
												// in Objektspeicher
			this.cashflows.add(cashflow);
		}
	}

	/**
	 * Standardkonstruktor, kopiert die übergebene Cashflowliste in den
	 * Objektspeicher, setzt eine Referenz auf eine JProgressBar
	 * 
	 * @param cashflows
	 *            Liste mit den Cashflows, sortiert nach Perioden (Vergangenheit
	 *            nach Zukunft)
	 * @param progressbar
	 *            Referenz zu einer Progressbar, diese wird falls vorhanden, den
	 *            Status der Berechnung übermittelt
	 * @param resultMap Enthält die DistributionMap zur Speicherung des aktuellen Ergebnisses der Berechnungen
	 * @param scenario Das Szenario, in dem die Daten berechnet werden.
	 */
	public TimeSeriesCalculator_v3(List<Calculable> cashflows,
			BHProgressBar progressbar, DistributionMap resultMap, DTOScenario scenario) {
		this.scenario = scenario;
		this.resultMap = resultMap;
		this.cashflows = new LinkedList<Calculable>();// initalisiere
														// this.cashflows
		for (Calculable cashflow : cashflows) {// kopiere parametisierte Liste
												// in Objektspeicher
			this.cashflows.add(cashflow);
		}
		this.progressB = progressbar;
	}

	/**
	 * Liefert eine Liste mit gefaketen Cashflows
	 * 
	 * @param anzahl_der_zu_prognostizierenden_cashflows
	 *            anzahl der gefakten cashflows
	 * @return List mit Calculable
	 */
	public List<Calculable> getDummyNextCashflows(
			int anzahl_der_zu_prognostizierenden_cashflows) {
		// List<Calculable> cashflows_manipuliert;
		List<Calculable> cashflow_mit_prognostizierung = new LinkedList<Calculable>();
		double summe = 0;
		for (Calculable cashflow : this.cashflows) {// Liste kopieren
			cashflow_mit_prognostizierung.add(cashflow);
			summe = summe + cashflow.toNumber().doubleValue();
		}
		double mittelwert = summe / (cashflow_mit_prognostizierung.size());
		// System.out.println("Mittelwert= "+mittelwert);
		double zufallsabweichung;
		for (int i = 1; i <= anzahl_der_zu_prognostizierenden_cashflows; i++) {
			zufallsabweichung = ((Math.random() * (2 - 1)) + 1) / 10;
			if (Math.random() > 0.5) {
				zufallsabweichung = zufallsabweichung + 1;
			} else {
				zufallsabweichung = 1 - zufallsabweichung;
			}
			// System.out.println(zufallsabweichung);
			// System.out.println("Dummywert= " +
			// (mittelwert*zufallsabweichung));
			cashflow_mit_prognostizierung.add(new DoubleValue(mittelwert
					* zufallsabweichung));
		}
		return cashflow_mit_prognostizierung;
	}

	/**
	 * Kalkulierungstest für p (Berücksichtigung der Perioden der Vergangenheit)<br>
	 * Prognostiziert die bereits bekannten Cashflows, indem für die
	 * Vergangenheit prognostiziert wird.
	 * 
	 * @param periods_to_history
	 *            Faktor p (Berücksichtigung der Perioden der Vergangenheit)
	 * @param anzahlWiederholungen
	 *            Anzahl der Prognostizierungs-Versuche, guter Wert ist 1000
	 * @param weissesRauschenISon
	 *            Weißes Rauschen ein bzw. ausschalten
	 * @return Liste mit Test-Prognostizierungen für die Vergangenheit
	 */
	public List<Calculable> calcultionTest_4_periods_to_history_v2(
			int periods_to_history, int anzahlWiederholungen,
			boolean weissesRauschenISon) {
		List<Calculable> cashflows_beruecksichtigt = new LinkedList<Calculable>(); // Liste
																					// mit
																					// berücksichtigten
																					// Cashflows
		List<Calculable> result = null;
		
		int counter = 1;
		for (Calculable cashflow : this.cashflows) {// original Liste in
													// cashflow-Liste
													// berücksichtig kopieren
			cashflows_beruecksichtigt.add(cashflow);
			if (counter == periods_to_history + 1 && counter >= 4 ) {// p+1
																	// cashflows
																	// sammeln,
																	// aber
																	// mindestens
																	// 4, aber maximal einen mehr als in this.cashflows()
				break;
			}
			counter++;
		}
		
		DTO.setThrowEvents(false);
		result = calculateCashflows(this.cashflows.size()
				- cashflows_beruecksichtigt.size(), periods_to_history,
				weissesRauschenISon, anzahlWiederholungen, false,
				cashflows_beruecksichtigt, false);
		DTO.setThrowEvents(true);
		return result;
	}

	/**
	 * prognostiziert eine beliebige Anzahl von Cashflows in die Zukunft
	 * 
	 * @param periods_calc_to_future
	 *            Anzahl der für die Zukunft zu prognostizierenden Cashflows
	 * @param weissesRauschenISon
	 *            Weißes Rauschen ein bzw. ausschalten
	 * @param periods_calc_to_history
	 *            steht für die Anzahl der zu berücksichtigenden vergangenen
	 *            Perioden (auch mit p abgekuerzt)
	 * @param anzahlWiederholungen
	 *            Anzahl der Prognostizierungs-Versuche, guter Wert ist 1000
	 * @param cashflows
	 *            optional cashflow-Liste, falls null wird die vorher im
	 *            Konstruktor angegebene Cashflowliste genommen..
	 * @param initialCalculation - Determines whether we have to recalculate the shareholder value
	 * @return Cashflowliste-Kopie mit hinzugefügten prognostizierten Cashflows
	 * 
	 */
	public List<Calculable> calculateCashflows(int periods_calc_to_future,
			int periods_calc_to_history, boolean weissesRauschenISon,
			int anzahlWiederholungen, boolean progressBarSetzen,
			List<Calculable> cashflows, boolean initialCalculation) {
		
		log.info("Starting Cashflow calculation");
		// System.out.println("TimeSeriesCalculator_v3: called calculateCashflows...");
		interrupted = false;
		
		// zu manipulierende Liste initalisieren
		List<Calculable> cashflows_manipuliert = getManipulierteCashflows(cashflows);
		
		// Hashmap zum sammeln der prognostizierten Cashflows mal Anzahl der Wdhn.
		HashMap<Integer, Double> cashflow_prognos_MW_sammlung = new HashMap<Integer, Double>();
		
		// vorinitalisieren der prognostizierten CashFlows
		for (int i = 0; i < periods_calc_to_future; i++) {
			cashflow_prognos_MW_sammlung.put(i, 0.);
		}

		//Varianz berechnen
		double varianz = kalkuliereTrendbereinigungVarianz(
				cashflows_manipuliert, kalkuliereStriche(cashflows_manipuliert));
		
		List<Calculable> weisses_Rauschen = null;
		double nextCashflow = 0;

		// falls weißes rauschen augeschaltet ist, setze die anzahl der wdhs auf 1,
		// weil dann offensichtlich ein anderer Prozess wie beispielsweise Random Walk verwendet wird.		
		if (weissesRauschenISon == false) {
			anzahlWiederholungen = 1;
		}
		
		//Um den Unternehmenswert zu berechnen.
		IShareholderValueCalculator dcfMethod = scenario.getDCFMethod();
		
		DTOScenario tempScenario = null;
		
		// Wiederholungen durchkalkulieren
		for (int counter = 0; counter < anzahlWiederholungen; counter++) {
			
			if(initialCalculation){
				//Erzeuge ein temporäres Szenario
				tempScenario =  getTempScenario(periods_calc_to_future);
			}
				
			// ProgressBar, falls verfügbar und erwünscht, aktualisieren
			if (interrupted) {
				counter = anzahlWiederholungen;
			}
			if (progressB != null && progressBarSetzen == true) {
				int fortschritt = (int) (((progressB.getMaximum() / 1.0) / anzahlWiederholungen) * (counter + 1.0));
				progressB.setValue(fortschritt);
			}

			// Variablen leeren
			weisses_Rauschen = null;
			nextCashflow = 0;

			// Berechnung weißes Rauschen
			if (weissesRauschenISon) {
				weisses_Rauschen = kalkuliere_weisses_Rauschen(
						periods_calc_to_future, varianz, 12);
				// System.out.println("weißes rauschen size "+
				// weisses_Rauschen.size());
			}

			// Iteratives Berechnen der Cashflows...
			for (int i = 0; i < periods_calc_to_future; i++) {
				nextCashflow = calulateNextCashflow(cashflows_manipuliert, periods_calc_to_history);
				
				if (weissesRauschenISon) {
					nextCashflow = nextCashflow + ((DoubleValue) weisses_Rauschen.get(i)).toNumber().doubleValue();
				}
				
				//Hier haben wir den nächsten Cashflow gesetzt
				cashflows_manipuliert.add(new DoubleValue(nextCashflow));
				
//				log.info("Periode: " + i + " mit Cashflow " + nextCashflow);
				
				if(initialCalculation){
					DTOPeriod period = tempScenario.getChildren().get(i);
					IPeriodicalValuesDTO pvdto = period.getPeriodicalValuesDTO("directinput");
					pvdto.put(DTODirectInput.Key.FCF, new DoubleValue(nextCashflow));
					pvdto.put(DTODirectInput.Key.LIABILITIES, new DoubleValue(0));
				}
				//Cashflow Wert neu und alten Wert addieren
				cashflow_prognos_MW_sammlung.put(i,
						(cashflow_prognos_MW_sammlung.get(i) + nextCashflow));
			}
			
			if(initialCalculation){
				Map<String, Calculable[]> dcfResult = dcfMethod.calculate( tempScenario, false);

				resultMap.put(((DoubleValue) dcfResult.get(IShareholderValueCalculator.Result.SHAREHOLDER_VALUE.toString())[0]).getValue());
			
			}
			
			// cashflows_manipuliert zurücksetzen
			cashflows_manipuliert = getManipulierteCashflows(cashflows);
		}

		/*
		 *  Durchschnittliche Cashflows berechnen, indem die Summe der 
		 *  CF durch die Anzahl der Wiederholungen geteilt wird
		 */
		for (int i = 0; i < periods_calc_to_future; i++) {
			cashflows_manipuliert
					.add(new DoubleValue(cashflow_prognos_MW_sammlung.get(i)
							/ anzahlWiederholungen));
		}

		// Progressbar wert auf Abweichung prüfen und korrigieren
		if (progressB != null && progressBarSetzen == true) {
			progressB.setValue(progressB.getMaximum());
		}

		log.info("Cashflow calculation done!");
		
		return cashflows_manipuliert;
	}
	
	/**
	 * Create a temporary scenario for the calculation of the shareholder value
	 * @param periods_calc_to_future
	 * @return
	 */
	private DTOScenario getTempScenario(int periods_calc_to_future){
		DTOScenario tempScenario = new DTOScenario(true);
		
		/*
		 * Write Return on equity and all the other scenario data
		 * like tax in temporary scenario 
		 */
		tempScenario.put(DTOScenario.Key.REK, scenario
				.get(DTOScenario.Key.REK));
		tempScenario.put(DTOScenario.Key.RFK, scenario
				.get(DTOScenario.Key.RFK));
		tempScenario.put(DTOScenario.Key.BTAX, scenario
				.get(DTOScenario.Key.BTAX));
		tempScenario.put(DTOScenario.Key.CTAX, scenario
				.get(DTOScenario.Key.CTAX));

		DTOPeriod dto = new DTOPeriod();
		ServiceLoader<IPeriodicalValuesDTO> periods = PluginManager.getInstance().getServices(IPeriodicalValuesDTO.class);
		for (IPeriodicalValuesDTO period : periods) {
			if(period.getUniqueId().equals("directinput")){
				dto.addChild((IPeriodicalValuesDTO) period.clone());
			}
		}
		
		//Write initial number of periods
		for (int j = 0; j < periods_calc_to_future; j++) {
			tempScenario.addChild((DTOPeriod) dto.clone());
		}

		
		return tempScenario;
	}
	
	/**
	 * Setze eine interne Liste mit den berechneten Ursprungswerten der Cashflows. 
	 * Diese werden zur internen Prognose der zukünftigen Cashflows berechnet.
	 * @param cashflows
	 * @return
	 */
	private LinkedList<Calculable> getManipulierteCashflows(List<Calculable> cashflows){
		LinkedList<Calculable> cashflows_manipuliert = new LinkedList<Calculable>();
		
		if (cashflows == null) {// falls keine Cashflowliste übergeben wurde
			// original Liste in zu manipulierende Liste kopieren
			for (Calculable cashflow : this.cashflows) {
				cashflows_manipuliert.add(cashflow);
			}
		} else {
			// übergebene Liste in zu manipulierende Liste kopieren
			for (Calculable cashflow : cashflows) {
				cashflows_manipuliert.add(cashflow);
			}
		}
		
		return cashflows_manipuliert;
	}

	/**
	 * kalkuliert den nächsten Cashflow anhand der übergebenen Cashflowliste
	 * ohne Berücksichtigung des Weißen Rauschens
	 * 
	 * @param cashflow_liste
	 *            Cashflow-Liste
	 * @param periods_calc_to_history
	 *            Anzahl der vergangenen Perioden, die berücksichtigt werden
	 *            sollen
	 * @return prognostizierter Cashflow
	 */
	private double calulateNextCashflow(List<Calculable> cashflow_liste,
			int periods_calc_to_history) {
		double[] striche = kalkuliereStriche(cashflow_liste);
		List<Calculable> bereinigte_Zeitreihe = kalkuliereTrendberechnung(
				cashflow_liste, striche);
		List<Calculable> gamma_Liste = kalkuliereGammaListe(
				bereinigte_Zeitreihe, periods_calc_to_history);
		List<Calculable> cListe = kalkuliere_cListe(gamma_Liste,
				periods_calc_to_history);
		double aR_Berechnung = kalkuliere_AR_Modell(cListe,
				bereinigte_Zeitreihe, striche);
		return aR_Berechnung;
	}

	/**
	 * Methode zum kalkulieren von tStrich_hoch2, xStrich, txStrich_mittelwert,
	 * tStrich, betaDach1, betaDach2
	 * 
	 * @param cashflow_liste
	 *            Cashflow Liste
	 * @return striche[0]=tStrich_hoch2, striche[1]=xStrich,
	 *         striche[2]=txStrich_mittelwert, striche[3]=tStrich,
	 *         strich[4]=betaDach1, strich[5]=betaDach2
	 */
	private double[] kalkuliereStriche(List<Calculable> cashflow_liste) {
		/*
		 * Initalisierung
		 */
		int counter = 1;
		double txStrich_Wert = 0;
		double tStrich = 0;
		double tStrich_hoch2 = 0;
		double xStrich = 0;
		double txStrich_mittelwert = 0;

		/*
		 * Berechnung von
		 * tStrich/tStrich_hoch2/xStrich/txStrich_mittelwert/betaDach2/betaDach1
		 */
		for (Calculable cashflow : cashflow_liste) {// durchlaufe Cashflowliste
			// txStrich-Berchnung
			txStrich_Wert = cashflow.toNumber().doubleValue() * counter;
			txStrich_mittelwert = txStrich_mittelwert + txStrich_Wert;

			// tStrich/tStrich_hoch2/xStrich Berechnung
			tStrich = tStrich + counter;
			tStrich_hoch2 = tStrich_hoch2 + Math.pow(counter, 2);
			xStrich = xStrich + cashflow.toNumber().doubleValue();

			counter++;
		}
		// tStrich/tStrich_hoch2/xStrich/txStrich_mittelwert Berechnung
		tStrich = (tStrich) / (counter - 1);
		tStrich_hoch2 = (tStrich_hoch2) / (counter - 1);
		xStrich = (xStrich) / (counter - 1);
		txStrich_mittelwert = (txStrich_mittelwert) / (counter - 1);

		// Beta dach
		double betaDach2 = (txStrich_mittelwert - (tStrich * xStrich))
				/ (tStrich_hoch2 - (Math.pow(tStrich, 2)));
		double betaDach1 = xStrich - (betaDach2 * tStrich);

		// System.out.println("tStrich = "+tStrich+" tStrich^2= "+tStrich_hoch2+" xStrich="+xStrich+" txStrich_Mittelwert="+txStrich_mittelwert);
		// System.out.println("betaDach2= "+betaDach2+ " betaDach1 ="+
		// betaDach1);

		// Ergebnis zurückgeben
		return new double[] { tStrich_hoch2, xStrich, txStrich_mittelwert,
				tStrich, betaDach1, betaDach2 };
	}

	/**
	 * Trendberechnung auf Basis "2010-01-11 Trendbereinigung Linear.xlsx"
	 * 
	 * @param cashflow_liste
	 *            Cashflow Liste
	 * @return Liste mit bereinigter Zeitreihe
	 */
	private List<Calculable> kalkuliereTrendberechnung(
			List<Calculable> cashflow_liste, double[] striche) {
		/*
		 * Initalisierung
		 */
		int counter = 1;

		double betaDach1 = striche[4];
		double betaDach2 = striche[5];
		double trendGerade_mt = 0;
		double bereinigte_Zeitreihe_Wert = 0;
		List<Calculable> bereinigte_Zeitreihe = new LinkedList<Calculable>(); // return-Liste

		/*
		 * Berechnung von trendGerade_mt & bereinigte Zeitreihe
		 */
		counter = 1;
		for (Calculable cashflow : cashflow_liste) {// durchlaufe Cashflowliste
			trendGerade_mt = betaDach1 + (betaDach2 * counter);
			bereinigte_Zeitreihe_Wert = trendGerade_mt
					- cashflow.toNumber().doubleValue();
			bereinigte_Zeitreihe
					.add(new DoubleValue(bereinigte_Zeitreihe_Wert));
			// System.out.println("Trendgerade mt "+counter+" ="+trendGerade_mt+" bereinigt Zeitreihe Wert "+counter+"="+bereinigte_Zeitreihe_Wert);

			counter++;
		}
		// System.out.println("Summe der bereinigten Zeitreihe= "+summe_bereinigte_Zeitreihe
		// + " sollte annähernd 0 sein"); //sollte 0 ergeben

		return bereinigte_Zeitreihe;
	}

	/**
	 * Berechnet die GammaListe, die zum lösen bzw. aufstellen des LGS benötigt
	 * wird
	 * 
	 * @param bereinigteZeitreihe
	 *            bereits bereinigte Zeitreihe
	 * @param periods_calc_to_history
	 *            Anzahl der Perioden der Vergangenheit (p)
	 * @return Liste mit Gammas
	 */
	private List<Calculable> kalkuliereGammaListe(
			List<Calculable> bereinigteZeitreihe, int periods_calc_to_history) {
		/*
		 * Initalisierung
		 */
		List<Calculable> gammaListe = new LinkedList<Calculable>(); // return-Liste
		double gamma_Wert = 0;
		ListIterator li_bereinigteZeitreihe_mt = null; // List Iterator
														// beginnend an der
														// Stelle
														// bereinigteZeitreihe -
														// t
		ListIterator li_bereinigteZeitreihe = null; // List Iterator beginnend
													// mit erstem Element
		double bereinigteZeitreihe_mt_Wert = 0;
		double bereinigteZeitreihe_Wert = 0;
		int inner_counter = 0;

		/*
		 * Berechnung
		 */
		for (int counter = 0; counter <= periods_calc_to_history; counter++) {// durchlaufe
																				// 1..p
																				// um
																				// Gamma_1
																				// bis
																				// Gamma_p
																				// zu
																				// erhalten
			li_bereinigteZeitreihe = bereinigteZeitreihe.listIterator();
			li_bereinigteZeitreihe_mt = bereinigteZeitreihe
					.listIterator(counter);
			inner_counter = 0;
			gamma_Wert = 0;
			while (li_bereinigteZeitreihe_mt.hasNext()) { // durchlaufe jeweils
															// bereinigteZeitreihe
															// - t
				bereinigteZeitreihe_mt_Wert = ((DoubleValue) li_bereinigteZeitreihe_mt
						.next()).toNumber().doubleValue();
				bereinigteZeitreihe_Wert = ((DoubleValue) li_bereinigteZeitreihe
						.next()).toNumber().doubleValue();
				gamma_Wert = gamma_Wert
						+ (bereinigteZeitreihe_mt_Wert * bereinigteZeitreihe_Wert);
				// System.out.println(counter+ ": "+bereinigteZeitreihe_mt_Wert
				// + " * "+ bereinigteZeitreihe_Wert+ " = "+
				// (bereinigteZeitreihe_mt_Wert * bereinigteZeitreihe_Wert));

				inner_counter++;
			}
			gamma_Wert = gamma_Wert / inner_counter;
			gammaListe.add(new DoubleValue(gamma_Wert));
			// System.out.println("gammaWert "+(counter)+"= "+gamma_Wert);

		}
		return gammaListe;
	}

	/**
	 * Berechnet die CF Liste.
	 * @param gammaListe
	 * @param periods_calc_to_history
	 * @return
	 */
	private List<Calculable> kalkuliere_cListe (List<Calculable> gammaListe, int periods_calc_to_history ){
		/*
		 * Initalisierung
		 */
		List<Calculable> cListe = new LinkedList<Calculable>(); //return-Liste
//		Logger.getLogger(TimeSeriesCalculator_v3.class).debug("Periods in history: " + periods_calc_to_history);
		double[][] arrayA = new  double[periods_calc_to_history][periods_calc_to_history];
		double[] arrayB = new double [periods_calc_to_history];
		double[][] arrayLoesung = null;
		int index_gammaListe = 0;
		
		/*
		 * Arraysfüllen
		 */
		//fülle arrayA
		for(int counter=0; counter<periods_calc_to_history; counter++){
			for(int inner_counter=0; inner_counter<periods_calc_to_history; inner_counter++){
				index_gammaListe = Math.abs((counter-inner_counter));
				arrayA[counter][inner_counter] = ((DoubleValue)gammaListe.get(index_gammaListe)).toNumber().doubleValue();
//				System.out.println("arrayA ["+counter+"]["+inner_counter+"] = " + arrayA[counter][inner_counter]);
			}
		}
		//fülle arrayB
		for(int counter=0; counter<periods_calc_to_history; counter++){
			arrayB[counter] = ((DoubleValue)gammaListe.get(counter+1)).toNumber().doubleValue();
//			System.out.println("arrayB ["+counter+"] = "+ arrayB[counter]);
		}
		/*
		 * LGS nach c auflösen
		 */
		Matrix matrixA = new Matrix(arrayA);
		Matrix matrixB = new Matrix(arrayB, arrayB.length);
		
		try{
			Matrix matrixC = matrixA.solve(matrixB); //Might be singular matrix
			/*
			 * Matrix solving throws an error if A and B were 0 at all points.
			 * We catch that error and try to return 0 as a CashFlow.
			 */
			arrayLoesung = (matrixC.getArray());
			for(int counter=0; counter<arrayLoesung.length; counter++){
//			System.out.println("c "+(counter+1)+" = "+arrayLoesung[counter][0]);
				cListe.add(new DoubleValue(arrayLoesung[counter][0]));
			}
		} catch (RuntimeException re){
			cListe.add(new DoubleValue(0.0));
			return cListe;
		}
		return cListe;
	}

	/**
	 * kalkuliert AR-Modell-Summe, d.h. einen Cashflow ohne Berücksichtigung des
	 * "Weißen-Rauschens"
	 * 
	 * @param cListe
	 *            vorher kalkulierte Liste mit c1, c2, c3, ...
	 * @param bereinigteZeitreihe
	 *            im Vorraus berechnete bereinigte Zeitreihe
	 * @param striche
	 *            mit diesen bereits errechneten werten: strich[4]=betaDach1,
	 *            strich[5]=betaDach2
	 * @return AR-Modell-Summe entspricht einem prognostiziertem Cashflow ohne
	 *         Berücksichtigung des "Weißen-Rauschens"
	 */
	private double kalkuliere_AR_Modell(List<Calculable> cListe,
			List<Calculable> bereinigteZeitreihe, double[] striche) {
		double aR = 0;

		// cListe minus mal bereinigte Zeitreihe ergibt bereinigter ci wert,
		// aufsummiert ergibt bereinigt_ci_summe
		ListIterator li_cListe = cListe.listIterator(cListe.size());
		ListIterator li_bereinigteZeitreihe = bereinigteZeitreihe
				.listIterator(bereinigteZeitreihe.size());
		double bereinigter_ci_Wert = 0;
		double bereinigt_ci_summe = 0;
		
		/*
		 *  rückwärts durchlaufen, bis komplette cListe durchlaufen wurde, 
		 *  die bereinigte Zeitreihe sollte dabei nicht ans Ende der Liste stoßen
		 */
		while (li_cListe.hasPrevious() && li_bereinigteZeitreihe.hasPrevious()) {
			bereinigter_ci_Wert = ((DoubleValue) li_cListe.previous())
					.toNumber().doubleValue()
					* ((DoubleValue) li_bereinigteZeitreihe.previous())
							.toNumber().doubleValue();
			// System.out.println("bereinigtes c="+bereinigter_ci_Wert);
			bereinigt_ci_summe = bereinigt_ci_summe + bereinigter_ci_Wert;
		}
		// System.out.println("summe über alle bereinigte c : "+bereinigt_ci_summe
		// + "bereinigte zeitreihe große "+bereinigteZeitreihe.size());

		double bereinigte_Zeitreihe_tplus1 = striche[4] + striche[5]
				* (bereinigteZeitreihe.size() + 1);// beta^1 + beta^2 * (t+1)
		// System.out.println("bereinigte_Zeitriehe_t+1 "+bereinigte_Zeitreihe_tplus1);

		aR = bereinigte_Zeitreihe_tplus1 - bereinigt_ci_summe; // neuer Cashflow
																// ergibt sich
																// aus
																// bereinigte_Zeitreihe
																// bei t+1 minus
																// die Summe
																// über alle
																// bereinigten
																// c-Werte
		// System.out.println("nächster AR-Cashflow = "+aR);
		return aR;
	}

	/**
	 * Methode zum Kalkulieren der Varianz der bereinigten Zeitreihe
	 * 
	 * @param cashflow_liste
	 *            Cashflowliste
	 * @param striche
	 *            Array mit diesem Inhalt: striche[0]=tStrich_hoch2,
	 *            striche[1]=xStrich, striche[2]=txStrich_mittelwert,
	 *            striche[3]=tStrich
	 * @return Varianz der bereinigten Zeitreihe
	 */
	private double kalkuliereTrendbereinigungVarianz(
			List<Calculable> cashflow_liste, double[] striche) {
		double varianz = 0;
		double xStrich = striche[1];
		// System.out.println("xStrich = "+xStrich);
		for (Calculable cashflow : cashflow_liste) {
			varianz = varianz
					+ Math.pow((cashflow.toNumber().doubleValue() - xStrich), 2);
		}
		varianz = Math.sqrt(varianz);
		// System.out.println("Varianz = "+varianz);
		return varianz;
	}

	/**
	 * Berechnet das weiße Rauschen, das im Prinzip eine Variante zu der 
	 * Zufallsbewegung und dem Wiener Prozess darstellt. Das weiße Rauschen
	 * sorgt für eine Beeinflussung der prognostizierten Cashflows.
	 * @param periods_calc_to_future Anzahl der Perioden in der Zukunft
	 * @param varianz Die Varianz der Berechnung
	 * @param anzahl_zufalls_zahlen Die Anzahl der Zufallszahlen
	 * @return Eine Liste mit Werten, die das weiße Rauschen je Periode darstellt.
	 */
	private List<Calculable> kalkuliere_weisses_Rauschen(
			int periods_calc_to_future, double varianz,
			int anzahl_zufalls_zahlen) {
		/*
		 * Initalisierung
		 */
		List<Calculable> weisses_Rauschen = new LinkedList<Calculable>();
		final double deltaT_mal_Sigma = varianz / anzahl_zufalls_zahlen;
		double zufallszahl = 0;
		NormalDistributionImpl normInv = new NormalDistributionImpl();
		normInv.setMean(0); // Mittelwert auf 0 setzen
		normInv.setStandardDeviation(deltaT_mal_Sigma); // Standardabweichung
		double normInv_wert = 0;
		double weisses_rauschen_vor_wert = 0;
		double summe = 0;
		// System.out.println("deltaT*Sigma "+deltaT_mal_Sigma);
		try {
			for (int outer_counter = 0; outer_counter < periods_calc_to_future; outer_counter++) {
				summe = 0;
				for (int inner_counter = 0; inner_counter < anzahl_zufalls_zahlen; inner_counter++) {
					zufallszahl = Math.random();
					normInv_wert = normInv
							.inverseCumulativeProbability(zufallszahl);
					// System.out.println("zufallszahl "+(inner_counter+1)+" :"+zufallszahl
					// + " ; normalverteilung = "+normInv_wert);
					// System.out.println(zufallszahl);
					summe = summe + normInv_wert;
				}
				// System.out.println("Summe der NV "+summe);
				// System.out.println("Weißes Rauschen "+(outer_counter+1)+" : "+(summe+weisses_rauschen_vor_wert));
				weisses_Rauschen.add(new DoubleValue(summe
						+ weisses_rauschen_vor_wert));
				weisses_rauschen_vor_wert = summe + weisses_rauschen_vor_wert;
			}
		} catch (MathException e) {
			e.printStackTrace();
		}

		return weisses_Rauschen;
	}

	/*
	 * Getter- & Setter-Methoden
	 */

	/**
	 * Getter-Methode für cashflows, Referenz-Methode
	 * 
	 * @return cashflows
	 */
	public List<Calculable> getCashflows() {
		return cashflows;
	}

	/**
	 * Getter-Methode für cashflows, Kopier-Methode
	 * 
	 * @return
	 */
	public List<Calculable> getCashflowsCopy() {
		List<Calculable> cashflows = null;
		cashflows = new LinkedList<Calculable>();// initalisiere this.cashflows
		for (Calculable cashflow : cashflows) {// kopiere parametisierte Liste
												// in Objektspeicher
			this.cashflows.add(cashflow);
		}
		return cashflows;
	}

	/**
	 * Setter-Methode für Cashflows, Referenz-Methode
	 * 
	 * @param cashflows
	 */
	public void setCashflows(List<Calculable> cashflows) {
		this.cashflows = cashflows;
	}

	/**
	 * Setter-Methode für Cashflows, Kopier-Methode
	 * 
	 * @param cashflows
	 */
	public void setCashflowsByMakingCopy(List<Calculable> cashflows) {
		this.cashflows = new LinkedList<Calculable>();// initalisiere
														// this.cashflows
		for (Calculable cashflow : cashflows) {// kopiere parametisierte Liste
												// in Objektspeicher
			this.cashflows.add(cashflow);
		}
	}

	/**
	 * Setter-Methode für eine Referenz auf eine ProgressBar
	 * 
	 * @param progressB
	 */
	public void setProgressBar(BHProgressBar progressB) {
		this.progressB = progressB;
	}

	public void setInterrupted() {
		this.interrupted = true;
	}

}
