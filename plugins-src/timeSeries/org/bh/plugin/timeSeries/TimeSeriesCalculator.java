package org.bh.plugin.timeSeries;

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
	 * Berechne anhand des YULE-Walkers-Verfahren den nächsten Cashflow
	 * @return prognoszierte Cashflow in Abhängigkeit von p
	 */
	public double calculateNextPeriod(){
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
			System.out.println("TimeSeriesCalculator: Yt-"+(i+1)+"-mü ="+Yt_m_p_m_mue);
		}
		//Schritt 3: Y(Gamma) aus (Yt-1-µ)*(Yt-2-µ) + (Yt-2-µ)*(Yt-3-µ) + ... berechnen
		Iterator iterator = Yt_m_p_m_mue_liste.iterator();
		double Y_Gamma = 0;
		counter = 0;
		while (iterator.hasNext()){ //Yt_m_p_m_mue_liste durchlaufen
			Yt_m_p_m_mue =  (Double) iterator.next(); //element holen
			System.out.println(Yt_m_p_m_mue);
			Y_Gamma = Y_Gamma + Yt_m_p_m_mue * (Double) iterator.next(); 
		}
		Y_Gamma = Y_Gamma/p;
		System.out.println("TimeSeriesCalculator: Y(Gamma)="+Y_Gamma);
		//Schritt 4:
		
		//TODO
		
		//ergebnis zurückliefern..
		return -1;
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
