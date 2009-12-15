package org.bh.plugin.gcc;

import org.bh.data.DTO;

import org.bh.calculation.sebi.Calculable;
import org.bh.data.IPeriodicalValuesDTO;

/**
 * GCCProfitLossStatementTotalCost DTO.
 * 
 * <p>
 * Data Transfer Object to handle GCCProfitLossStatementTotalCost values and
 * methods
 * 
 * @author Michael Löckelt
 * @version 0.1, 12.12.2009
 * 
 */

@SuppressWarnings("unchecked")
public class DTOGCCProfitLossStatementTotalCost extends DTO implements
		IPeriodicalValuesDTO {
	private static final String UNIQUE_ID = "gcc_pls_totalcost";

	public enum Key {

		/**
		 * (+) Umsatzerlöse
		 */
		UE,

		/**
		 * (+/-) Erhöhung oder Verminderung des Bestands an fertigen und
		 * unfertigen Erzeugnissen
		 */
		EVFUFE,

		/**
		 * (+) andere aktivierte Eigenleistungen
		 */
		AAE,

		/**
		 * (+) sonstige betriebliche Erträge
		 */
		SBE,

		/**
		 * (-) Materialaufwand (Aufwendungen für Roh-, Hilfs- und Betriebsstoffe
		 * und für bezogene Waren sowie Aufwendungen für bezogene Leistungen)
		 */
		MA,

		/**
		 * = Rohergebnis nach dem Gesamtkostenverfahren
		 */
		@Method
		RGKV,

		/**
		 * (-) Personalaufwand (Löhne und Gehälter sowie soziale Abgaben und
		 * Aufwendungen für Altersversorgung und für Unterstützung)
		 */
		PA,

		/**
		 * (-) Abschreibungen
		 */
		ABSCH,

		/**
		 * (-) sonstige betriebliche Aufwendungen
		 */
		SBA,

		/**
		 * (=) Betriebsergebnis
		 */
		@Method
		BERG,

		/**
		 * (+) Erträge aus Beteiligungen
		 */
		EBET,

		/**
		 * (+) Erträge aus anderen Wertpapieren und Ausleihungen an
		 * Finanzanlagevermögens
		 */
		EWAF,

		/**
		 * (+) sonstige Zinsen und ähnliche Erträge
		 */
		SZAE,

		/**
		 * (-) Abschreibungen auf Finanzanlagen und übliche Abschreibungen auf
		 * Wertpapiere des Umalufvermögens
		 */
		AFW,

		/**
		 * (-) Zinsen und ähnliche Aufwendungen
		 */
		ZA,

		/**
		 * (=) Finanzergebnis / Ergebnis der gewöhnlichen Geschäftstätigkeit
		 */
		@Method
		EGG,

		/**
		 * (+) außerordentliche Erträge
		 */
		AUE,

		/**
		 * (-) außerordentliche Aufwendungen
		 */
		AUA,

		/**
		 * (=) außerordentliches Ergebnis
		 */
		@Method
		AUERG,

		/**
		 * (-) Steuern vom Einkommen und vom Ertrag
		 */
		SEE,

		/**
		 * (-) sonstige Steuern (insofern erfolgswirksam)
		 */
		SS,

		/**
		 * (=) Jahresüberschuss / Jahresfehlbetrag
		 */
		@Method
		JUJF;

	}

	public DTOGCCProfitLossStatementTotalCost() {
		super(Key.values());
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}

	/**
	 * intermediate result: gross profit
	 * 
	 * @return Calculable
	 */
	protected Calculable getRGKV() {
		return null;
	}

	/**
	 * intermediate result: operating income
	 * 
	 * @return Calculable
	 */
	protected Calculable getBERG() {
		return null;
	}

	/**
	 * intermediate result: result of ordinary activities
	 * 
	 * @return Calculable
	 */
	protected Calculable getEGG() {
		return null;
	}

	/**
	 * intermediate result: extraordinary result
	 * 
	 * @return Calculable
	 */
	protected Calculable getAUERG() {
		return null;
	}

	/**
	 * intermediate result: net income
	 * 
	 * @return Calculable
	 */
	protected Calculable getJUJF() {
		return null;
	}

}
