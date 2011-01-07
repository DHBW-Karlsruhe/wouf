package org.bh.plugin.timeSeries;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;

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
 * @version 2.0, 05.01.2011
 *
 */
public class TimeSeriesCalculator_v2 {
	/**
	 * Liste mit den Cashflows, sortiert nach Perioden
	 */
	private List<Calculable> cashflows = null;
	
	/**
	 * Standardkonstruktor
	 * @param cashflows Liste mit den Cashflows, sortiert nach Perioden (Vergangenheit nach Zukunft)
	 */
	public TimeSeriesCalculator_v2(List<Calculable> cashflows){
		this.cashflows = new LinkedList<Calculable>();//initalisiere this.cashflows
		for(Calculable cashflow : cashflows){//kopiere parametisierte Liste in Objektspeicher
			this.cashflows.add(cashflow);
		}
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
	 * @return  Liste mit Test-Prognostizierungen für die Vergangenheit
	 */
	public List<Calculable> calcultionTest_4_periods_to_history(int periods_to_history, int puffer) throws NumberFormatException{
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
		
		ListIterator lI_cashflows_n_beruecks = cashflows_nicht_beruecksichtig.listIterator();
		//Vorinitalisierung
		double nextCashflow = 0;
		List<DoubleValue> differenz_rechnung = null;
		double my = 0;
		List <DoubleValue> yt_m_my = null; //Yt-µ
		List <DoubleValue> abcdef = null;
		List <DoubleValue> c0c1c2 = null;
		Calculable cashflow_unberuecksichtigt = null;
		while(lI_cashflows_n_beruecks.hasNext()){//Liste der noch nicht berücksichtigten Cashflows durchlaufen..
			//prognostiziere Cashflow
			nextCashflow = calulateNextCashflow(cashflows_beruecksichtigt, periods_to_history);
			//nicht berücksichtigten Cashflow holen und speichern
			cashflow_unberuecksichtigt = (Calculable) lI_cashflows_n_beruecks.next(); 
			//prognostizierter Cashflow KalkulationsTest-Liste hinzufügen
			cashflows_calculationTest.add(new DoubleValue(nextCashflow));
			//realer unberücksichtigter Cashfolw der Liste "berücksichtigt" hinzufügen
			cashflows_beruecksichtigt.add(cashflow_unberuecksichtigt);
		}
		return cashflows_calculationTest;
	}
	
	
	/**
	 * @param periods_calc_to_future Anzahl der für die Zukunft zu prognostizierenden Cashflows
	 * @param periods_calc_to_history steht für die Anzahl der zu berücksichtigenden vergangenen Perioden (auch mit p abgekuerzt)
	 * @return Cashflowliste mit prognostizierten Cashflows
	 */
	public List<Calculable> calculateCashflows (int periods_calc_to_future, int periods_calc_to_history){
		List<Calculable> cashflows_manipuliert = new LinkedList<Calculable>(); //zu manipulierende Liste initalisieren
		for(Calculable cashflow : this.cashflows){//original Liste in zu manipulierende Liste kopieren
			cashflows_manipuliert.add(cashflow);
		}
		
		//Vorinitalisierung
		List<DoubleValue> differenz_rechnung = null;
		double my = 0;
		List <DoubleValue> yt_m_my = null; //Yt-µ
		List <DoubleValue> abcdef = null;
		List <DoubleValue> c0c1c2 = null;
		double nextCashflow = 0 ;
		
		//Iteratives Berechnen der Cashflows..
		for(int i=0; i<periods_calc_to_future; i++){
			nextCashflow = calulateNextCashflow(cashflows_manipuliert, periods_calc_to_history);
			cashflows_manipuliert.add(new DoubleValue(nextCashflow));
		}

		return cashflows_manipuliert;
	}
	
	public double calulateNextCashflow(List<Calculable> cashflow_liste, int periods_calc_to_history){
		
		List<DoubleValue> differenz_rechnung = null;
		double my = 0;
		List <DoubleValue> yt_m_my = null; //Yt-µ
		List <DoubleValue> abcdef = null;
		List <DoubleValue> c0c1c2 = null;
		double nextCashflow = 0 ;
		
		differenz_rechnung = kalkuliere_differenz_rechnung(cashflow_liste); //Differenzen zwischen den Cashflows der einzelnen Perioden
		my = kalkuliere_my(differenz_rechnung); // µ
		yt_m_my = kalkuliere_Yt_m_My(my, differenz_rechnung, periods_calc_to_history); //Yt-µ
		abcdef = kalkuliere_abcdef(yt_m_my, my, periods_calc_to_history, differenz_rechnung, cashflow_liste);
		c0c1c2 = kalkuliere_c0_bis_cx(abcdef);
		nextCashflow = kalkuliere_NextCashfolw(c0c1c2, cashflow_liste);
		
		return nextCashflow;
	}
	
	/**
	 * Berechnet Differenzen zwischen den Cashflows der einzelnen Perioden
	 * @param cashflows_manipuliert eventuell Manipulierte Cashflowliste...
	 * @return Differenz in Form einer List mit DoubleValue
	 */
	public List<DoubleValue> kalkuliere_differenz_rechnung(List<Calculable> cashflows_manipuliert){
		List<DoubleValue> differenz_rechnung = new LinkedList<DoubleValue>(); //liste, die die differenz_rechnung speichern soll (return-wert)
		
		ListIterator listIterator = cashflows_manipuliert.listIterator();//listiterator
		double cashflow_speicher0 = 0; //speichert "oberen" cashflow
		double cashflow_speicher1 = 0; //speichert "unteren" cashflow
		double differenz_speicher = 0; //speichert differenz zwischen unten und oben
		
		while(listIterator.hasNext()){//durchlaufe Cashflowliste
			cashflow_speicher0 = ((Calculable)listIterator.next()).toNumber().doubleValue();//hole oberen Cashflow
			if(listIterator.hasNext()){//nur falls es weitere Cashflows gibt
				cashflow_speicher1 = ((Calculable)listIterator.next()).toNumber().doubleValue();//hole unteren Cashflow
				differenz_speicher =  cashflow_speicher1 - cashflow_speicher0; //bilde differenz
//				System.out.println("TimeSeriesCalculator_v2: get_differenz_rechnung(): differenz_rechnung("+differenz_speicher+");");
				differenz_rechnung.add(new DoubleValue(differenz_speicher));//differenz der liste hinzufügen
			}
			else{//ansonsten abbruch
				break;
			}
			if(listIterator.hasPrevious()){//nur falls ein voriges Element in der Liste gibt
				listIterator.previous();//listIterator auf voriges Element zurücksetzen
			}
			else{//ansonsten abbruch
				break;
			}
		}
		
		return differenz_rechnung;
	}
	
	/**
	 * Kalkuliert µ
	 * @param differenzRechnung bereits kalkulierte Differenz-Rechnung
	 * @return µ als double-Wert
	 */
	public double kalkuliere_my(List<DoubleValue> differenzRechnung){
		double mittelwert = 0;//der mittelwert der liste entspricht µ
		for (DoubleValue differenz : differenzRechnung){//durchlaufe Differenz-Rechnung
			mittelwert = mittelwert + differenz.toNumber().doubleValue();//aufsummieren
		}
		mittelwert = mittelwert / differenzRechnung.size(); //mittelwert milden
		return mittelwert;
	}
	
	/**
	 * Kalkuliert Yt-µ
	 * @param my vorher kalkuliertes µ als double-Wert
	 * @param differenz_Rechnung vorher kalkulierte Differenz-Rechnung als List mit DoubleValue
	 * @param p steht für die Anzahl der zu berücksichtigenden vergangenen Perioden (periods_calc_to_history)
	 * @return Yt-µ als List mit DoubleValue
	 */
	public List<DoubleValue> kalkuliere_Yt_m_My (double my, List<DoubleValue> differenz_Rechnung, int p){
		List<DoubleValue> yt_m_my = new LinkedList<DoubleValue>(); //Yt-µ Liste (return-Wert)
		
		ListIterator listIterator = differenz_Rechnung.listIterator(p-1); //hole iterator der Differenz-Rechnung beim index p-1
		double yt_m_my_Wert = 0; //zwischenspeicher für schleife, speichert einen Yt-µ Wert
		while(listIterator.hasNext()){//durchlaufe Differenz-Rechnung..
			yt_m_my_Wert = ((DoubleValue) listIterator.next()).toNumber().doubleValue() - my;
			yt_m_my.add(new DoubleValue(yt_m_my_Wert));
		}
		return yt_m_my;
	}
	
	/**
	 * kalkuliert a, b, c, d, e, ... (falls p=2 exisitert a, b; falls p=3 exisitert a, b, c; usw)<br>
	 * a= Mittelwert(Yt-µ * (Yt-1-µ)) ; b = Mittelwert(Yt-µ * (Yt-2-µ)) ; c = Mittelwert(Yt-µ * (Yt-3-µ))  ; usw.
	 * @param yt_m_My vorher kalkulierte Yt-µ Liste als List mit DoubleValue
	 * @param my vorher vorher kalkuliertes µ als double-Wert
	 * @param p steht für die Anzahl der zu berücksichtigenden vergangenen Perioden (periods_calc_to_history)
	 * @param differenz_Rechnung vorher kalkulierte Differenz-Rechnung als List mit DoubleValue
	 * @param cashflows_manipuliert eventuell Manipulierte Cashflowliste...
	 * @return
	 */
	public List<DoubleValue> kalkuliere_abcdef(List<DoubleValue> yt_m_My, double my, int p, List<DoubleValue> differenz_Gleichung, List<Calculable> cashflows_manipuliert){
		List<DoubleValue> abcdef = new LinkedList<DoubleValue>(); //Liste a, b, c, d, ... beinhalten soll (return-Wert)
		
		ListIterator yt_m_My_listIterator = null; //deklariere ListIterator der "Yt-µ"-Liste
		double yt_m_My_Wert = 0;
		double yt_m_x_m_My_Wert = 0;
		double multiplikation = 0;
		double multiplikation_summiert = 0;
		int counter = 0;
		int index_of_differenz_gleichung = 0;
		
		for(int i=0; i<p; i++){//durchlaufe p => 0=a, 1=b, 2=c, usw...
			yt_m_My_listIterator = yt_m_My.listIterator();//hole ListIterator der "Yt-µ"-Liste
			counter = 0; //counter zurücksetzen
			multiplikation_summiert = 0; //zurücksetzen
			while(yt_m_My_listIterator.hasNext()){//summiere multiplikationen: (Yt-µ)*(Yt-1-µ) für a bzw. (Yt-µ)*(Yt-2-µ) für b
				yt_m_My_Wert = ((DoubleValue)yt_m_My_listIterator.next()).toNumber().doubleValue(); //hole Yt-µ-Wert
				index_of_differenz_gleichung = (p-i-1 +counter); //p-i-1 ergibt startwert, counter dazu addiert ergibt index
//				System.out.println("index_of_differenz_gleichung= "+index_of_differenz_gleichung);
				if(index_of_differenz_gleichung < 1 ){//falls index kleiner als 1
					yt_m_x_m_My_Wert = 0-my;
				}else{//ansonsten
					yt_m_x_m_My_Wert = (((DoubleValue)differenz_Gleichung.get(index_of_differenz_gleichung-1)).toNumber().doubleValue())-my;
				}
				multiplikation = yt_m_My_Wert * yt_m_x_m_My_Wert;
				multiplikation_summiert = multiplikation_summiert + multiplikation;
//				System.out.println(i+" yt_m_My="+yt_m_My_Wert+ " (Yt-"+(i+1)+"-my)= " +yt_m_x_m_My_Wert+"  multipliziert ergibt: "+multiplikation);
				counter++;
			}
			abcdef.add(new DoubleValue(multiplikation_summiert/counter));
		}
		return abcdef;
	}
	
	/**
	 * kalkuliert c0, c1, c2, ... cn
	 * @param abcdef Liste mit a, b, c, d, ... in Form einer List mit DoubleValue
	 * @return kalkulierte "c0, c1, c2, ... cn" -Werte in Form einer List mit DoubleValue
	 */
	public List<DoubleValue> kalkuliere_c0_bis_cx (List<DoubleValue> abcdef){
		List<DoubleValue> c0c1c2 = new LinkedList<DoubleValue>(); //Liste a, b, c, d, ... beinhalten soll (return-Wert)
		ListIterator listIterator_abcdef = abcdef.listIterator();
		double summe = 0;
		while (listIterator_abcdef.hasNext()){//summe bilden
			summe = summe + ((DoubleValue)listIterator_abcdef.next()).toNumber().doubleValue();
		}
		listIterator_abcdef = abcdef.listIterator();
		while (listIterator_abcdef.hasNext()){//c0, c1, c2, ... bilden
			c0c1c2.add(new DoubleValue(((DoubleValue) listIterator_abcdef.next()).toNumber().doubleValue() / summe));
		}
		return c0c1c2;
	}
	
	/**
	 * kalkuliert den nächsten Cashflow
	 * @param c0c1c2 vorher kalkulierte "c0, c1, c2, ... cn"-Liste
	 * @param cashflows_manipuliert eventuell Manipulierte Cashflowliste...
	 * @return nächster Cashflow als double
	 */
	public double kalkuliere_NextCashfolw(List<DoubleValue> c0c1c2, List<Calculable> cashflows_manipuliert ){
		double nextCashflow = 0;
		ListIterator listIterator_cashflows = cashflows_manipuliert.listIterator(cashflows_manipuliert.size());//am Ende starten -> wird nachher rückwärts durchlaufen
		ListIterator listIterator_c0c1c2 = c0c1c2.listIterator();
		double cashflow = 0;
		double cx = 0;
		while(listIterator_cashflows.hasPrevious() && listIterator_c0c1c2.hasNext()){//Cashflow Liste umgekehrt durchlaufen & c0c1c2-Liste vorwärts durchlaufen
			cashflow = ((DoubleValue)listIterator_cashflows.previous()).toNumber().doubleValue();
			cx = ((DoubleValue)listIterator_c0c1c2.next()).toNumber().doubleValue();
			nextCashflow = nextCashflow + (cashflow * cx);
//			System.out.println("Cashflow= "+cashflow + " * cx="+cx);
		}
		return nextCashflow;
	}
	
	
	/*
	 * Getter- & Setter-Methoden
	 */
	
	/**
	 * Getter-Methode für cashflows
	 * @return cashflows
	 */
	public List<Calculable> getCashflows() {
		return cashflows;
	}
	
	/**
	 * Setter-Methode für Cashflows
	 * @param cashflows
	 */
	public void setCashflows(List<Calculable> cashflows) {
		this.cashflows = cashflows;
	}
}
