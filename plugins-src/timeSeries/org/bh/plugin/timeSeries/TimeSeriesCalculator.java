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
package org.bh.plugin.timeSeries;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.bh.data.types.*;
/**
 * Diese Klasse stellt die Berechnung für die Zeitreihenanalyse bereit
 *
 * <p>
 * <detailed_description>
 * 
 * <p>
 * TODO NOT finally implemented
 * @author Andreas Wussler
 * @version 1.0, 28.12.2010
 *
 */
public class TimeSeriesCalculator {
	
	/**
	 * Anzahl der vergangen Perioden, die für die Berechnung berücksichtigt werden sollen
	 */
	private int p = -1;
	
	/**
	 * Liste mit den Cashflows, sortiert nach Perioden
	 */
	private List<Calculable> cashflows = null;
	
	/**
	 * Standardkonstruktor
	 * @param p Anzahl der vergangen Perioden, die für die Berechnung berücksichtigt werden sollen (Yule-Walker p)
	 * @param cashflows Liste mit den Cashflows, sortiert nach Perioden
	 */
	public TimeSeriesCalculator(int p, List<Calculable> cashflows){
		this.p = p;
		this.cashflows = cashflows;
	}
	
	/**
	 * Berechne anhand des YULE-Walkers-Verfahren Y(Gamma)
	 * @return berechnet Y(Gamma) von Cashflows
	 */
	public double get_Ygamma_for_cashflowlist(List<Calculable> cashflows){
		//schritt 1: mü für p berechnen
		double mue = -1;
		int counter = 0;
		double summe_cashflows_bis_p = 0;
		for (Calculable cashflow : cashflows){
			summe_cashflows_bis_p = summe_cashflows_bis_p + cashflow.toNumber().doubleValue();
			counter++;
			if(counter == p){//falls bis p summiert
				break;//abbrechen
			}
		}
		mue = summe_cashflows_bis_p/p;
		System.out.println("TimeSeriesCalculator: mue="+mue);
		
		//Schritt 2: Yt-p-mü berechnen
		List<Double> Yt_m_p_m_mue_liste = new LinkedList<Double>();
		double Yt_m_p_m_mue;
		for (int i = 0; i< p ; i++){
			Yt_m_p_m_mue = cashflows.get(i).toNumber().doubleValue() - mue;
			Yt_m_p_m_mue_liste.add(Yt_m_p_m_mue);
			System.out.println("TimeSeriesCalculator: Yt-"+(p-(i))+"-mü ="+Yt_m_p_m_mue);//umgedreht ausgeben, da liste später umgedreht wird
		}
		Collections.reverse(Yt_m_p_m_mue_liste);
		//Schritt 3: Y(Gamma) aus (Yt-1-µ)*(Yt-2-µ) + (Yt-2-µ)*(Yt-3-µ) + ... berechnen
		Iterator iterator = Yt_m_p_m_mue_liste.iterator();
		double Y_Gamma = 0;
		double Yt_m_p1_m_mue = 0; //Yt-(p+1)-µ
		counter = 0;
		while (iterator.hasNext()){ //Yt_m_p_m_mue_liste durchlaufen
			iterator = Yt_m_p_m_mue_liste.iterator();//iterator neu initalisieren
			for(int i=0; i<counter; i++){//iterator "zurückspulen" ersetzt reverse option von iterator
				iterator.next();
			}
			Yt_m_p_m_mue =  (Double) iterator.next(); //nächstes element holen
			Yt_m_p1_m_mue =  (Double) iterator.next(); //übernächstes element holen
			System.out.println("TimeSeriesCalculator: Yt_m_p_m_mue="+Yt_m_p_m_mue+ " Yt_m_p1_m_mue="+Yt_m_p1_m_mue+ " multiplizieren ergibt:"+(Yt_m_p_m_mue * Yt_m_p1_m_mue));
			Y_Gamma = Y_Gamma + (Yt_m_p_m_mue * Yt_m_p1_m_mue);
			counter++;
		}
		counter = 0;
		Y_Gamma = Math.abs(Y_Gamma/p);
		System.out.println("TimeSeriesCalculator: Y(Gamma)="+Y_Gamma);
		//Ergebnis zurückgeben
		return Y_Gamma;
	}
	
	/**
	 * Berechnet Y(Gamma) für alle Cashflows
	 * @return List<DoubleValue>, welche alle "Y(Gamma)"s beinhaltet
	 */
	public List<DoubleValue> getYgammaList (){
		List <DoubleValue> y_gammas = new LinkedList<DoubleValue>();
		List<Calculable> nach_p_getrennte_cashflows = new LinkedList<Calculable>();
		Collections.reverse(this.cashflows);//Liste umdrehen
		Iterator i_cashflows = this.cashflows.iterator();
		Iterator i_cashflows_hilf = this.cashflows.iterator();
		int counter = 0;//schleifen zaehler
		double y_gamma;
		while(i_cashflows.hasNext()){
			nach_p_getrennte_cashflows = new LinkedList<Calculable>();
			i_cashflows_hilf = this.cashflows.iterator();//hilfsiterator zurücksetzen
			for(int i=1; i<= counter; i++){//hilfsiterator auf den gleich stand bringen wie iterator -- ersetzt fehlende kopierfunktion von iterator
				i_cashflows_hilf.next();
			}
			for(int i=1; i<= this.p; i++){
				if(i_cashflows.hasNext() && i_cashflows_hilf.hasNext()){
					DoubleValue cashflow = (DoubleValue) i_cashflows_hilf.next();
					System.out.println(cashflow.toNumber() + " zu getrennte liste hinzufügen");
					nach_p_getrennte_cashflows.add(cashflow);
				}
			}
			if(nach_p_getrennte_cashflows.size() == this.p){
				y_gamma = get_Ygamma_for_cashflowlist(nach_p_getrennte_cashflows);
//				System.out.println("y(gamma)="+y_gamma);
				y_gammas.add(new DoubleValue(y_gamma) );
			}
			i_cashflows.next();
			counter++;
		}
		Collections.reverse(this.cashflows);//Liste wieder zurückdrehen
		return y_gammas;
	}
	
	/**
	 * Liefert eine Liste mit gefaketen Cashflows
	 * @param anzahl_der_zu_prognostizierenden_cashflows anzahl der gefakten cashflows
	 * @return List<Calculable>
	 */
	public List<Calculable> getDummyNextCashflows(int anzahl_der_zu_prognostizierenden_cashflows){
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
	 * Getter-Methode für p
	 * @return p
	 */
	public int getP() {
		return p;
	}
	
	/**
	 * Setter-Methode für p
	 * @param p
	 */
	public void setP(int p) {
		this.p = p;
	}
	
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
