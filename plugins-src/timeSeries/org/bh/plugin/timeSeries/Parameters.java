package org.bh.plugin.timeSeries;

import java.util.List;

import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;

/**
 * <short_description>
 *
 * <p>
 * <detailed_description>
 *
 * @author Chris
 * @version 1.0, 08.12.2012
 *
 */
public class Parameters {

	protected int periodenVergangenheit;
	
	protected int periodenZukunft;
	
	protected int anzahlIterationen;

	protected boolean nutzeWeissesRauschen;
	
	protected TimeSeriesCalculator_v3.StatusCallback statusCallback;
	
	protected List<Calculable> werte = null;
	
	protected DTOScenario scenario = null;
	
	public Parameters() {
		setStatusCallback(null);
	}
	
	public int getPeriodenVergangenheit() {
		if (periodenVergangenheit > werte.size() - 1) {
			return werte.size() - 1;
		}
		
		return periodenVergangenheit;
	}

	public void setPeriodenVergangenheit(int periodenVergangenheit) {
		this.periodenVergangenheit = periodenVergangenheit;
	}

	public int getPeriodenZukunft() {
		return periodenZukunft;
	}

	public void setPeriodenZukunft(int periodenZukunft) {
		this.periodenZukunft = periodenZukunft;
	}

	/**
	 * Holt die Anzahl der durchzufuehrenden Iterationen. Falls das weisse Rauschen nicht verwendet werden
	 * soll, wird 1 zurueckgegeben, da in diesem Fall keine Zufallsvariablen mit einfliessen.
	 * Dies kann beispielsweise der Fall sein, wenn ein anderer Prozess wie der Random walk verwendet wird.
	 * 
	 * @return int
	 */
	public int getAnzahlIterationen() {
		if(!nutzeWeissesRauschen) {
			return 1;
		}
		
		return anzahlIterationen;
	}

	public void setAnzahlIterationen(int anzahlIterationen) {
		this.anzahlIterationen = anzahlIterationen;
	}

	public boolean isNutzeWeissesRauschen() {
		return nutzeWeissesRauschen;
	}

	public void setNutzeWeissesRauschen(boolean flag) {
		this.nutzeWeissesRauschen = nutzeWeissesRauschen;
	}

	public TimeSeriesCalculator_v3.StatusCallback getStatusCallback() {
		return statusCallback;
	}

	/**
	 * Setze den das Callback fuer den Status. Falls das Callback leer ist (==null), dann wird ein leeres
	 * Callback verwendet.
	 * 
	 * @param statusCallback
	 */
	public void setStatusCallback(TimeSeriesCalculator_v3.StatusCallback statusCallback) {
		if(statusCallback == null) {
			statusCallback = new TimeSeriesCalculator_v3.StatusCallback() {
				@Override
				public void updateStatus(int status) {
				}
			};
		}
		
		this.statusCallback = statusCallback;
	}

	public List<Calculable> getWerte() {
		return werte;
	}

	public void setWerte(List<Calculable> werte) {
		this.werte = werte;
	}

	public DTOScenario getScenario() {
		return scenario;
	}

	public void setScenario(DTOScenario scenario) {
		this.scenario = scenario;
	}
	
	
	
}
