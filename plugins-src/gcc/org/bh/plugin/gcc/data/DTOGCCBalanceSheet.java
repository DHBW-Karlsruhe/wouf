package org.bh.plugin.gcc.data;

import org.apache.log4j.Logger;
import org.bh.data.DTO;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;

/**
 * GCCBalanceSheet DTO.
 * 
 * <p>
 * Data Transfer Object to handle GCCBalanceSheet values and methods
 * 
 * @author Michael Löckelt
 * @version 0.4, 16.12.2009
 * 
 */

@SuppressWarnings("unchecked")
public class DTOGCCBalanceSheet extends DTO implements IPeriodicalValuesDTO {
	private static final String UNIQUE_ID = "gccbalancesheet";
	private static final Logger log = Logger.getLogger(DTOGCCBalanceSheet.class);

	public enum Key {

		/**
		 * balance sheet positions all positions will be named in german
		 */

		/**
		 * - AKTIVA
		 */
		@Method
		AKTIVA,

		/**
		 * -- Anlagevermögen
		 */
		@Method
		AV,

		/**
		 * --- Immaterielle Vermögensgegenstände
		 */
		IVG,

		/**
		 * ---- selbst geschaffene gewerbliche Schutzrechte und ähnliche Rechte
		 * und Werte
		 */
		SGGS,

		/**
		 * ---- entgeltlich erworbene Konzessionen, gewerbliche Schutzrechte und
		 * ähnliche Rechte und Werte sowie Lizenzen an solchen Rechten und
		 * Werten
		 */
		EKGS,

		/**
		 * ---- Geschäfts- oder Firmenwert
		 */
		GFW,

		/**
		 * ---- geleistete Anzahlungen Anlagevermögen
		 */
		AVGA,

		/**
		 * --- Sachanlagen
		 */
		SA,

		/**
		 * ---- Grundstücke, grundstücksgleiche Rechte und Bauten einschließlich
		 * der Bauten auf fremden Grundstücken
		 */
		GRB,

		/**
		 * ---- technische Anlagen und Maschinen
		 */
		TAM,

		/**
		 * ---- andere Anlagen, Betriebs- und Geschäftsausstattung
		 */
		BGA,

		/**
		 * ---- geleistete Anzahlungen und Anlagen im Bau
		 */
		GAAB,

		/**
		 * --- Finanzanlagen
		 */
		FA,

		/**
		 * ---- Anteile an verbundenen Unternehmen
		 */
		ANVU,

		/**
		 * ---- Ausleihungen an verbundene Unternehmen
		 */
		AUVU,

		/**
		 * ---- Beteiligungen
		 */
		BET,

		/**
		 * ---- Ausleihungen an Unternehmen, mit denen ein
		 * Beteiligungsverhältnis besteht
		 */
		ABET,

		/**
		 * ---- Wertpapiere des Anlagevermögens
		 */
		WAV,

		/**
		 * ---- sonstige Ausleihungen
		 */
		SOA,

		/**
		 * -- Umlaufvermögen
		 */
		@Method
		UV,

		/**
		 * --- Vorräte
		 */
		VOR,

		/**
		 * ---- Roh-, Hilfs- und Betriebsstoffe
		 */
		RHB,

		/**
		 * ---- unfertige Erzeugnisse, unfertige Leistungen
		 */
		UEL,

		/**
		 * ---- fertige Erzeugnisse und Waren
		 */
		FEW,

		/**
		 * ---- geleistete Anzahlungen Umlaufvermögen
		 */
		UVGA,

		/**
		 * --- Forderungen und sonstige Vermögensgegenstände
		 */
		FSVG,

		/**
		 * ---- Forderungen aus Lieferungen und Leistungen
		 */
		FLL,

		/**
		 * ---- Forderungen gegen verbundene Unternehmen
		 */
		FVU,

		/**
		 * ---- Forderungen gegen Unternehmen, mit denen ein
		 * Beteiligungsverhältnis besteht
		 */
		FUBET,

		/**
		 * ---- sonstige Vermögensgegenstände
		 */
		SVG,

		/**
		 * --- Wertpapiere
		 */
		WP,

		/**
		 * ---- Anteile an verbundenen Unternehmen
		 */
		AVU,

		/**
		 * ---- sonstige Wertpapiere
		 */
		SW,

		/**
		 * --- Kassenbestand, Bundesbankguthaben, Guthaben bei Kreditinstituten
		 * und Schecks (liquide Mittel)
		 */
		KBGGKS,

		/**
		 * -- (Aktive) Rechnungsabgrenzungsposten
		 */
		ARA,

		/**
		 * -- Aktive latente Steuern
		 */
		ALS,

		/**
		 * -- Aktiver Unterschiedsbetrag aus der Vermögensverrechnung
		 */
		AUBV,

		/**
		 * - PASSIVA
		 */
		@Method
		PASSIVA,

		/**
		 * -- Eigenkapital
		 */
		@Method
		EK,

		/**
		 * --- Gezeichnetes Kapital
		 */
		GEZK,

		/**
		 * --- Kapitalrücklage
		 */
		KR,

		/**
		 * --- Gewinnrücklagen
		 */
		GER,

		/**
		 * ---- gesetzliche Rücklage
		 */
		GESR,

		/**
		 * ---- Rücklage für Anteile an einem herrschenden oder mehrheitlich
		 * beteiligten Unternehmen
		 */
		RAHBETU,

		/**
		 * ---- satzungsmäßige Rücklagen
		 */
		SMR,

		/**
		 * ---- andere Gewinnrücklagen
		 */
		AG,

		/**
		 * --- Gewinnvortrag/Verlustvortrag
		 */
		G3V,

		/**
		 * --- Jahresüberschuß/Jahresfehlbetrag
		 */
		JUJF,

		/**
		 * -- Rückstellungen
		 */
		@Method
		RS,

		/**
		 * --- Rückstellungen für Pensionen und ähnliche Verpflichtungen
		 */
		RSPV,

		/**
		 * --- Steuerrückstellungen
		 */
		RSSTR,

		/**
		 * --- sonstige Rückstellungen
		 */
		RSSR,

		/**
		 * -- Verbindlichkeiten
		 */
		@Method
		VB,

		/**
		 * --- Anleihen
		 */
		ANL,

		/**
		 * --- Verbindlichkeiten gegenüber Kreditinstituten
		 */
		VBK,

		/**
		 * --- erhaltene Anzahlungen auf Bestellungen
		 */
		EAB,

		/**
		 * --- Verbindlichkeiten aus Lieferungen und Leistungen
		 */
		VBLL,

		/**
		 * --- Verbindlichkeiten aus der Annahme gezogener Wechsel und der
		 * Ausstellung eigener Wechsel
		 */
		VBWE,

		/**
		 * --- Verbindlichkeiten gegenüber verbundenen Unternehmen
		 */
		VBVU,

		/**
		 * --- Verbindlichkeiten gegenüber Unternehmen, mit denen ein
		 * Beteiligungsverhältnis besteht
		 */
		VBBETU,

		/**
		 * --- Sonstige Verbindlichkeiten
		 */
		SVB,

		/**
		 * -- (Passive) Rechnungsabgrenzungsposten
		 */
		PRA,

		/**
		 * -- passive latente Steuern
		 */
		PLS;
		
		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}	
	}

	public DTOGCCBalanceSheet() {
		super(Key.values());
		log.debug("Object created!");
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
	 * sums and returns asset values
	 * 
	 * @return Calculable
	 */
	protected Calculable getAKTIVA() {
		return null;
	}

	/**
	 * sums and returns fixed assets values
	 * 
	 * @return Calculable
	 */
	protected Calculable getAV() {
		return null;
	}

	/**
	 * sums and returns current assets values
	 * 
	 * @return Calculable
	 */
	protected Calculable getUV() {
		return null;
	}

	/**
	 * sums and returns equity and liabilities values
	 * 
	 * @return Calculable
	 */
	protected Calculable getPASSIVA() {
		return null;
	}

	/**
	 * sums and returns equity values
	 * 
	 * @return Calculable
	 */
	protected Calculable getEK() {
		return null;
	}

	/**
	 * sums and returns accruals values
	 * 
	 * @return Calculable
	 */
	protected Calculable getRS() {
		return null;
	}

	/**
	 * sums and returns liabilities values
	 * 
	 * @return Calculable
	 */
	protected Calculable getVB() {
		return null;
	}
	
	public void regenerateMethodsList() {
		regenerateMethodsList(Key.values());
	}

}
