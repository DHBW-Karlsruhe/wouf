/*******************************************************************************
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

import javax.swing.JProgressBar;

import Jama.*;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.gui.swing.comp.BHProgressBar;

/**
 * Diese Klasse stellt die Berechnung für die Zeitreihenanalyse bereit
 *
 * <p>
 * <detailed_description>
 * 
 * <p>
 * TODO NOT finally implemented
 * @author Andreas Wussler
 * @since 1.0, 28.12.2010
 * @version 3.0, 12.01.2011
 *
 */
public class TimeSeriesCalculator_v3 {
	/**
	 * Liste mit den Cashflows, sortiert nach Perioden
	 */
	private List<Calculable> cashflows = null;
	/**
	 * Referenz zu einer Progressbar, diese wird falls vorhanden, den Status der Berechnung übermittelt
	 */
	private BHProgressBar progressB = null;
	
	/**
	 * Standardkonstruktor, kopiert die übergebene Cashflowliste in den Objektspeicher
	 * @param cashflows Liste mit den Cashflows, sortiert nach Perioden (Vergangenheit nach Zukunft)
	 */
	public TimeSeriesCalculator_v3(List<Calculable> cashflows){
		this.cashflows = new LinkedList<Calculable>();//initalisiere this.cashflows
		for(Calculable cashflow : cashflows){//kopiere parametisierte Liste in Objektspeicher
			this.cashflows.add(cashflow);
		}
	}
	
	/**
	 * Standardkonstruktor, kopiert die übergebene Cashflowliste in den Objektspeicher, setzt eine Referenz auf eine JProgressBar
	 * @param cashflows Liste mit den Cashflows, sortiert nach Perioden (Vergangenheit nach Zukunft)
	 * @param progressbar Referenz zu einer Progressbar, diese wird falls vorhanden, den Status der Berechnung übermittelt
	 */
	public TimeSeriesCalculator_v3(List<Calculable> cashflows, BHProgressBar progressbar){
		this.cashflows = new LinkedList<Calculable>();//initalisiere this.cashflows
		for(Calculable cashflow : cashflows){//kopiere parametisierte Liste in Objektspeicher
			this.cashflows.add(cashflow);
		}
		this.progressB = progressbar;
	}
	
	/**
	 * Liefert eine Liste mit gefaketen Cashflows
	 * @param anzahl_der_zu_prognostizierenden_cashflows anzahl der gefakten cashflows
	 * @return List mit Calculable
	 */
	public List<Calculable> getDummyNextCashflows(int anzahl_der_zu_prognostizierenden_cashflows){
		List<Calculable> cashflows_manipuliert;
		List<Calculable> cashflow_mit_prognostizierung = new LinkedList<Calculable>();
		double summe = 0;
		for(Calculable cashflow : this.cashflows){//Liste kopieren
			cashflow_mit_prognostizierung.add(cashflow);
			summe = summe + cashflow.toNumber().doubleValue();
		}
		double mittelwert = summe/(cashflow_mit_prognostizierung.size());
//		System.out.println("Mittelwert= "+mittelwert);
		double zufallsabweichung;
		for(int i= 1; i<= anzahl_der_zu_prognostizierenden_cashflows; i++){
			zufallsabweichung = ((Math.random() * (2 - 1)) + 1)/10;
			if(Math.random() > 0.5){
				zufallsabweichung = zufallsabweichung +1;
			}else{
				zufallsabweichung = 1-zufallsabweichung;
			}
//			System.out.println(zufallsabweichung);
//			System.out.println("Dummywert= " + (mittelwert*zufallsabweichung));
			cashflow_mit_prognostizierung.add(new DoubleValue(mittelwert*zufallsabweichung));
		}
		return cashflow_mit_prognostizierung;
	}
	
	/**
	 * Kalkulierungstest für p (Berücksichtigung der Perioden der Vergangenheit)<br>
	 * Prognostiziert die bereits bekannten Cashflows, indem für die Vergangenheit prognostiziert wird.
	 * @param periods_to_history Faktor p (Berücksichtigung der Perioden der Vergangenheit)
	 * @param puffer Anzahl der vergangen Perioden, die nicht prognostiziert werden sollen. => puffer-1 muss >= periods_to_history sein
	 * @param anzahlWiederholungen Anzahl der Prognostizierungs-Versuche, guter Wert ist 1000
	 * @param weissesRauschenISon Weißes Rauschen ein bzw. ausschalten
	 * @return  Liste mit Test-Prognostizierungen für die Vergangenheit
	 */
	public List<Calculable> calcultionTest_4_periods_to_history(int periods_to_history, int puffer, int anzahlWiederholungen, boolean weissesRauschenISon) throws NumberFormatException{
		if(puffer-1 < periods_to_history){
			System.out.println("Fehler puffer-1 muss >= periods_to_history sein puffer="+puffer+" periods_to_history="+periods_to_history);
			throw new NumberFormatException("Fehler puffer-1 muss >= periods_to_history sein puffer="+puffer+" periods_to_history="+periods_to_history);
		}
		
		List<Calculable> cashflows_beruecksichtigt = new LinkedList<Calculable>(); //Liste mit berücksichtigten Cashflows
		List<Calculable> cashflows_nicht_beruecksichtig = new LinkedList<Calculable>(); //Liste mit noch nicht berücksichtigten Cashflows
		List<Calculable> cashflows_calculationTest = new LinkedList<Calculable>(); //Liste mit kalkulations-Test (Rückgabe-Wert)
		int counter =1;
		boolean puffer_uebergelaufen = false;
		for(Calculable cashflow : this.cashflows){//original Liste in zu manipulierende Liste kopieren, bis puffer erreicht ist
			if(!puffer_uebergelaufen){//falls puffer nicht übergelaufen
				cashflows_beruecksichtigt.add(cashflow);
				cashflows_calculationTest.add(cashflow);
//				System.out.println("add to cashflows manipuliert "+cashflow.toNumber().doubleValue());
			}else{//falls puffer übergelaufen
				cashflows_nicht_beruecksichtig.add(cashflow);
//				System.out.println("add to cashflows nicht beruecksichtig "+cashflow.toNumber().doubleValue());
			}
			if(counter >= puffer && counter >= periods_to_history){//puffer checken
				puffer_uebergelaufen = true;
			}
			counter++;
		}
		
		//Für die ProgressBAR:
		int cashflows_n_beruecks_size = cashflows_nicht_beruecksichtig.size();
		int progressbar_haelfte = 0;
		if(progressB != null){
			progressbar_haelfte = progressB.getValue();
		}
		counter = 1;
		//Vorinitalisierung
		ListIterator lI_cashflows_n_beruecks = cashflows_nicht_beruecksichtig.listIterator();
		double nextCashflow = 0;
		Calculable cashflow_unberuecksichtigt = null;
		List<Calculable> nextCashflows = null;
		while(lI_cashflows_n_beruecks.hasNext()){//Liste der noch nicht berücksichtigten Cashflows durchlaufen..
			//prognostiziere Cashflow
			nextCashflows = calculateCashflows(1, periods_to_history, weissesRauschenISon, 100, false);
			nextCashflow = ((DoubleValue)nextCashflows.get(nextCashflows.size()-1)).toNumber().doubleValue();
//			nextCashflow = calulateNextCashflow(cashflows_beruecksichtigt, periods_to_history);
			//nicht berücksichtigten Cashflow holen und speichern
			cashflow_unberuecksichtigt = (Calculable) lI_cashflows_n_beruecks.next(); 
			//prognostizierter Cashflow KalkulationsTest-Liste hinzufügen
			cashflows_calculationTest.add(new DoubleValue(nextCashflow));
			//realer unberücksichtigter Cashfolw der Liste "berücksichtigt" hinzufügen
			cashflows_beruecksichtigt.add(cashflow_unberuecksichtigt);
			
			if(progressB != null){
				progressB.setValue(progressbar_haelfte+(progressbar_haelfte/cashflows_n_beruecks_size)*counter);
			}
			counter++;
		}
		if(progressB != null){//zur Sicherheit...
			progressB.setValue(progressB.getMaximum());
		}
		return cashflows_calculationTest;
	}
	
	
	/**
	 * prognostiziert eine beliebige Anzahl von Cashflows in die Zukunft
	 * @param periods_calc_to_future Anzahl der für die Zukunft zu prognostizierenden Cashflows
	 * @param weissesRauschenISon Weißes Rauschen ein bzw. ausschalten
	 * @param periods_calc_to_history steht für die Anzahl der zu berücksichtigenden vergangenen Perioden (auch mit p abgekuerzt)
	 * @param anzahlWiederholungen Anzahl der Prognostizierungs-Versuche, guter Wert ist 1000
	 * @return Cashflowliste-Kopie mit hinzugefügten prognostizierten Cashflows
	 */
	public List<Calculable> calculateCashflows (int periods_calc_to_future, int periods_calc_to_history, boolean weissesRauschenISon, int anzahlWiederholungen, boolean progressBarSetzen){
//		System.out.println("TimeSeriesCalculator_v3: called calculateCashflows...");
		List<Calculable> cashflows_manipuliert = new LinkedList<Calculable>(); //zu manipulierende Liste initalisieren
		for(Calculable cashflow : this.cashflows){//original Liste in zu manipulierende Liste kopieren
			cashflows_manipuliert.add(cashflow);
		}
		
		HashMap<Integer, Double> cashflow_prognos_MW_sammlung = new HashMap<Integer, Double>();//Hashmap zum sammeln der prognostizierten Cashflows mal Anzahl der Wdhn.
		for(int i= 0; i<periods_calc_to_future; i++){//vorinitalisieren
			cashflow_prognos_MW_sammlung.put(i, 0.);
		}
		
		double varianz = kalkuliereTrendbereinigungVarianz(cashflows_manipuliert, kalkuliereStriche(cashflows_manipuliert));
		List<Calculable> weisses_Rauschen = null;
		double nextCashflow = 0;
		
		//Wiederholungen durchkalkulieren
		for(int counter = 0; counter<anzahlWiederholungen; counter++){
			//ProgressBar, falls verfügbar und erwünscht, aktualisieren
			if(progressB != null && progressBarSetzen == true){
				progressB.setValue((counter+1)/2);
			}
			
			//Variablen leeren
			weisses_Rauschen = null;
			nextCashflow = 0;
						
			//Berechnung weißes Rauschen
			if(weissesRauschenISon){
				weisses_Rauschen = kalkuliere_weisses_Rauschen(periods_calc_to_future, varianz, 12);
//				System.out.println("weißes rauschen size "+ weisses_Rauschen.size());
			}
			
			//Iteratives Berechnen der Cashflows..
			for(int i=0; i<periods_calc_to_future; i++){
				nextCashflow = calulateNextCashflow(cashflows_manipuliert, periods_calc_to_history);
				if(weissesRauschenISon){
					nextCashflow = nextCashflow + ((DoubleValue)weisses_Rauschen.get(i)).toNumber().doubleValue();
				}
				cashflows_manipuliert.add(new DoubleValue(nextCashflow));
				cashflow_prognos_MW_sammlung.put(i, (cashflow_prognos_MW_sammlung.get(i)+nextCashflow));
			}
//			System.out.println("step "+counter);
			
			//cashflows_manipuliert zurücksetzen
			cashflows_manipuliert = new LinkedList<Calculable>(); //zu manipulierende Liste initalisieren
			for(Calculable cashflow : this.cashflows){//original Liste in zu manipulierende Liste kopieren
				cashflows_manipuliert.add(cashflow);
			}
		}
		
		//Durchschnittliche Cashflows berechnen
		for(int i = 0; i<periods_calc_to_future; i++){
			cashflows_manipuliert.add(new DoubleValue(cashflow_prognos_MW_sammlung.get(i)/anzahlWiederholungen));
		}

		
		
		return cashflows_manipuliert;
	}
	
	/**
	 * kalkuliert den nächsten Cashflow anhand der übergebenen Cashflowliste ohne Berücksichtigung des Weißen Rauschens
	 * @param cashflow_liste Cashflow-Liste
	 * @param periods_calc_to_history Anzahl der vergangenen Perioden, die berücksichtigt werden sollen
	 * @return prognostizierter Cashflow
	 */
	private double calulateNextCashflow(List<Calculable> cashflow_liste, int periods_calc_to_history){
		double[] striche = kalkuliereStriche(cashflow_liste);
		List<Calculable> bereinigte_Zeitreihe = kalkuliereTrendberechnung(cashflow_liste, striche);
		List<Calculable> gamma_Liste = kalkuliereGammaListe(bereinigte_Zeitreihe, periods_calc_to_history);
		List<Calculable> cListe = kalkuliere_cListe(gamma_Liste, periods_calc_to_history);
		double aR_Berechnung = kalkuliere_AR_Modell(cListe, cashflow_liste, periods_calc_to_history);
		return aR_Berechnung;
	}
	
	/**
	 * Methode zum kalkulieren von tStrich_hoch2, xStrich, txStrich_mittelwert, tStrich
	 * @param cashflow_liste Cashflow Liste
	 * @return striche[0]=tStrich_hoch2, striche[1]=xStrich, striche[2]=txStrich_mittelwert, striche[3]=tStrich
	 */
	private double[] kalkuliereStriche(List<Calculable> cashflow_liste){
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
		 * Berechnung von tStrich/tStrich_hoch2/xStrich/txStrich_mittelwert/betaDach2/betaDach1
		 */
		for(Calculable cashflow : cashflow_liste){//durchlaufe Cashflowliste
			//txStrich-Berchnung
			txStrich_Wert = cashflow.toNumber().doubleValue() * counter;
			txStrich_mittelwert = txStrich_mittelwert + txStrich_Wert;
			
			//tStrich/tStrich_hoch2/xStrich Berechnung
			tStrich = tStrich + counter;
			tStrich_hoch2 = tStrich_hoch2 + Math.pow(counter, 2);
			xStrich = xStrich + cashflow.toNumber().doubleValue();
			
			counter++;
		}
		//tStrich/tStrich_hoch2/xStrich/txStrich_mittelwert Berechnung
		tStrich = (tStrich)/(counter-1);
		tStrich_hoch2 = (tStrich_hoch2)/(counter-1);
		xStrich = (xStrich)/(counter-1);
		txStrich_mittelwert = (txStrich_mittelwert)/(counter-1);
		
//		System.out.println("tStrich = "+tStrich+" tStrich^2= "+tStrich_hoch2+" xStrich="+xStrich+" txStrich_Mittelwert="+txStrich_mittelwert);
		//Ergebnis zurückgeben
		return new double[]{tStrich_hoch2, xStrich, txStrich_mittelwert, tStrich};
	}
	
	/**
	 * Trenntberechnung auf Basis "2010-01-11 Trendbereinigung Linear.xlsx" 
	 * @param cashflow_liste Cashflow Liste
	 * @return Liste mit bereinigter Zeitreihe
	 */
	private List<Calculable> kalkuliereTrendberechnung(List<Calculable> cashflow_liste, double[] striche){
		/*
		 * Initalisierung
		 */
		int counter = 1;
		//striche[0]=tStrich_hoch2, striche[1]=xStrich, striche[2]=txStrich_mittelwert, striche[3]= tStrich
		double tStrich = striche[3];
		double tStrich_hoch2 = striche[0];
		double xStrich = striche[1];
		double txStrich_mittelwert = striche[2];
		
		double betaDach2 = 0;
		double betaDach1 = 0;
		double trendGerade_mt = 0;
		double bereinigte_Zeitreihe_Wert = 0;
		List<Calculable>  bereinigte_Zeitreihe = new LinkedList<Calculable>(); //return-Liste
		
		//betaDach2/betaDach1 Berechnung
		betaDach2 = (txStrich_mittelwert - (tStrich*xStrich)) / (tStrich_hoch2 -(Math.pow(tStrich, 2)));
		betaDach1 = xStrich - (betaDach2 * tStrich);
//		System.out.println("betaDach2= "+betaDach2+ " betaDach1 ="+ betaDach1);
		
		/*
		 * Berechnung von trendGerade_mt & bereinigte Zeitreihe
		 */
		counter = 1;
		for(Calculable cashflow : cashflow_liste){//durchlaufe Cashflowliste
			trendGerade_mt = betaDach1 + (betaDach2 * counter);
			bereinigte_Zeitreihe_Wert = trendGerade_mt - cashflow.toNumber().doubleValue();
			bereinigte_Zeitreihe.add(new DoubleValue(bereinigte_Zeitreihe_Wert));
//			summe_bereinigte_Zeitreihe = summe_bereinigte_Zeitreihe + bereinigte_Zeitreihe_Wert;
//			System.out.println("Trendgerade mt "+counter+" ="+trendGerade_mt+" bereinigt Zeitreihe Wert "+counter+"="+bereinigte_Zeitreihe_Wert);
			
			counter++;
		}
//		System.out.println("Summe der bereinigten Zeitreihe= "+summe_bereinigte_Zeitreihe + " sollte annähernd 0 sein"); //sollte 0 ergeben
		
		return bereinigte_Zeitreihe;
	}
	
	/**
	 * Berechnet die GammaListe, die zum lösen bzw. aufstellen des LGS benötigt wird
	 * @param bereinigteZeitreihe bereits bereinigte Zeitreihe
	 * @param periods_calc_to_history Anzahl der Perioden der Vergangenheit (p)
	 * @return Liste mit Gammas
	 */
	private List<Calculable> kalkuliereGammaListe(List<Calculable>  bereinigteZeitreihe, int periods_calc_to_history){
		/*
		 * Initalisierung
		 */
		List<Calculable> gammaListe = new LinkedList<Calculable>(); //return-Liste
		double gamma_Wert = 0;
		ListIterator li_bereinigteZeitreihe_mt = null; //List Iterator beginnend an der Stelle bereinigteZeitreihe - t
		ListIterator li_bereinigteZeitreihe = null;  //List Iterator beginnend mit erstem Element
		double bereinigteZeitreihe_mt_Wert = 0;
		double bereinigteZeitreihe_Wert = 0;
		int inner_counter = 0;
		
		/*
		 * Berechnung
		 */
		//y(0) hinzufügen
		//gammaListe.add(new DoubleValue(1));
		for(int counter = 1; counter <= periods_calc_to_history+1; counter++){//durchlaufe 1..p um Gamma_1 bis Gamma_p zu erhalten
			li_bereinigteZeitreihe = bereinigteZeitreihe.listIterator();
			li_bereinigteZeitreihe_mt = bereinigteZeitreihe.listIterator(counter);
			inner_counter = 0;
			gamma_Wert = 0;
			while(li_bereinigteZeitreihe_mt.hasNext()){ //durchlaufe jeweils bereinigteZeitreihe - t
				bereinigteZeitreihe_mt_Wert = ((DoubleValue)li_bereinigteZeitreihe_mt.next()).toNumber().doubleValue();
				bereinigteZeitreihe_Wert = ((DoubleValue)li_bereinigteZeitreihe.next()).toNumber().doubleValue();
				gamma_Wert = gamma_Wert + (bereinigteZeitreihe_mt_Wert * bereinigteZeitreihe_Wert);
//				System.out.println(counter+ ": "+bereinigteZeitreihe_mt_Wert + " * "+ bereinigteZeitreihe_Wert+ " = "+ (bereinigteZeitreihe_mt_Wert * bereinigteZeitreihe_Wert));
				
				inner_counter++;
			}
			gamma_Wert = gamma_Wert/inner_counter;
			gammaListe.add(new DoubleValue(gamma_Wert));
//			System.out.println("gammaWert "+(counter)+"= "+gamma_Wert);
			
		}
		return gammaListe;
	}
	
	
	private List<Calculable> kalkuliere_cListe (List<Calculable> gammaListe, int periods_calc_to_history ){
		/*
		 * Initalisierung
		 */
		List<Calculable> cListe = new LinkedList<Calculable>(); //return-Liste
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
		Matrix matrixC = matrixA.solve(matrixB);
		arrayLoesung = (matrixC.getArray());
		for(int counter=0; counter<arrayLoesung.length; counter++){
//			System.out.println("c "+(counter+1)+" = "+arrayLoesung[counter][0]);
			cListe.add(new DoubleValue(arrayLoesung[counter][0]));
		}
		return cListe;
	}
	
	/**
	 * kalkuliert AR-Modell-Summe, d.h. einen Cashflow ohne Berücksichtigung des "Weißen-Rauschens" 
	 * @param cListe vorher kalkulierte Liste mit c1, c2, c3, ...
	 * @param cashflow_liste Cashflowliste
	 * @param periods_calc_to_history Anzahl der vergangenen Perioden, die berücksichtigt werden sollen
	 * @return AR-Modell-Summe entspricht einem prognostiziertem Cashflow ohne Berücksichtigung des "Weißen-Rauschens"
	 */
	private double kalkuliere_AR_Modell(List<Calculable> cListe, List<Calculable> cashflow_liste, int periods_calc_to_history){
		double aR = 0;
		double c_Wert = 0;
		double y_tmx = 0; //Wert von y(t-x); 
		ListIterator li_cListe = cListe.listIterator();
		for(int i=1; i<= periods_calc_to_history && li_cListe.hasNext(); i++){
			c_Wert = ((DoubleValue)li_cListe.next()).toNumber().doubleValue();
			y_tmx = ((DoubleValue)cashflow_liste.get(cashflow_liste.size()-(i))).toNumber().doubleValue();
//			System.out.println("c"+i+" = "+c_Wert+" y(t-"+i+") = "+ y_tmx);
			aR = aR + (c_Wert * y_tmx);
		}
//		System.out.println("aR = "+ aR);
		
		return aR;
	}
	
	/**
	 * Methode zum Kalkulieren der Varianz der bereinigten Zeitreihe
	 * @param cashflow_liste Cashflowliste
	 * @param striche Array mit diesem Inhalt: striche[0]=tStrich_hoch2, striche[1]=xStrich, striche[2]=txStrich_mittelwert, striche[3]=tStrich
	 * @return Varianz der bereinigten Zeitreihe
	 */
	private double kalkuliereTrendbereinigungVarianz(List<Calculable> cashflow_liste, double[] striche){
		double varianz = 0;
		double xStrich = striche[1];
//		System.out.println("xStrich = "+xStrich);
		for(Calculable cashflow : cashflow_liste){
			varianz = varianz + Math.pow((cashflow.toNumber().doubleValue() - xStrich), 2);
		}
		varianz = Math.sqrt(varianz);
//		System.out.println("Varianz = "+varianz);
		return varianz;
	}
	
	
	private List<Calculable> kalkuliere_weisses_Rauschen(int periods_calc_to_future, double varianz, int anzahl_zufalls_zahlen){
		/*
		 * Initalisierung
		 */
		List<Calculable> weisses_Rauschen = new LinkedList<Calculable>();
		final double deltaT_mal_Sigma = varianz/anzahl_zufalls_zahlen;
		double zufallszahl = 0;
		NormalDistributionImpl normInv = new NormalDistributionImpl();
		normInv.setMean(0); //Mittelwert auf 0 setzen
		normInv.setStandardDeviation(deltaT_mal_Sigma); //Standardabweichung
		double normInv_wert = 0;
		double weisses_rauschen_vor_wert = 0;
		double summe = 0;
//		System.out.println("deltaT*Sigma "+deltaT_mal_Sigma);
		try{
			for(int outer_counter = 0; outer_counter<periods_calc_to_future; outer_counter++){
				summe = 0;
				for(int inner_counter =0; inner_counter<anzahl_zufalls_zahlen; inner_counter++){
					zufallszahl = Math.random();
					normInv_wert = normInv.inverseCumulativeProbability(zufallszahl);
//					System.out.println("zufallszahl "+(inner_counter+1)+" :"+zufallszahl + " ; normalverteilung = "+normInv_wert);
//					System.out.println(zufallszahl);
					summe = summe + normInv_wert;
				}
//				System.out.println("Summe der NV "+summe);
//				System.out.println("Weißes Rauschen "+(outer_counter+1)+" : "+(summe+weisses_rauschen_vor_wert));
				weisses_Rauschen.add(new DoubleValue(summe+weisses_rauschen_vor_wert));
				weisses_rauschen_vor_wert = summe+weisses_rauschen_vor_wert;
			}
		}
		catch(MathException e){
			e.printStackTrace();
		}

		return weisses_Rauschen;
	}
	
	
	/*
	 * Getter- & Setter-Methoden
	 */
	
	/**
	 * Getter-Methode für cashflows, Referenz-Methode
	 * @return cashflows
	 */
	public List<Calculable> getCashflows() {
		return cashflows;
	}
	
	/**
	 * Getter-Methode für cashflows, Kopier-Methode
	 * @return
	 */
	public List<Calculable> getCashflowsCopy(){
		List<Calculable> cashflows = null;
		cashflows = new LinkedList<Calculable>();//initalisiere this.cashflows
		for(Calculable cashflow : cashflows){//kopiere parametisierte Liste in Objektspeicher
			this.cashflows.add(cashflow);
		}
		return cashflows;
	}
	
	/**
	 * Setter-Methode für Cashflows, Referenz-Methode
	 * @param cashflows
	 */
	public void setCashflows(List<Calculable> cashflows) {
		this.cashflows = cashflows;
	}
	
	/**
	 * Setter-Methode für Cashflows, Kopier-Methode
	 * @param cashflows
	 */
	public void setCashflowsByMakingCopy(List<Calculable> cashflows){
		this.cashflows = new LinkedList<Calculable>();//initalisiere this.cashflows
		for(Calculable cashflow : cashflows){//kopiere parametisierte Liste in Objektspeicher
			this.cashflows.add(cashflow);
		}
	}
	
	/**
	 * Setter-Methode für eine Referenz auf eine ProgressBar
	 * @param progressB
	 */
	public void setProgressBar(BHProgressBar progressB){
		this.progressB = progressB;
	}

}
