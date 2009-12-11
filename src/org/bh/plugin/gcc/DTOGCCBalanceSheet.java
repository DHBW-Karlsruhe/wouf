package org.bh.plugin.gcc;

import org.bh.calculation.sebi.Calculable;
import org.bh.data.DTO;
import org.bh.data.IPeriodicalValuesDTO;

/**
 * GCCBalanceSheet DTO.
 * 
 * <p>
 * Data Transfer Object to handle GCCBalanceSheet09 values and methods
 * 
 * @author Michael Löckelt 
 * @version 0.3, 07.12.2009
 * 
 */

@SuppressWarnings("unchecked")
public class DTOGCCBalanceSheet extends DTO implements IPeriodicalValuesDTO {
	private static final String UNIQUE_ID = "gccbalancesheet";
	
    public enum Key {

	/**
	 * balance sheet positions
	 * all positions will be named in german
	 */
	    
	
	/**
	 * - AKTIVA
	 */
	    
	    /**
	* -- Anlagevermögen 
	     */
	@Method
	    AV,
	    
	    
	    /**
	* --- Immaterielle Vermögensgegenstände
	     */

	    /**
	* ---- selbst geschaffene gewerbliche Schutzrechte und ähnliche Rechte und Werte
	     */
	    SGGS,
	    
	    /**
	* ---- entgeltlich erworbene Konzessionen, gewerbliche Schutzrechte und ähnliche Rechte und Werte sowie Lizenzen an solchen Rechten und Werten
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
	    
	    /**
	* ---- Grundstücke, grundstücksgleiche Rechte und Bauten einschließlich der Bauten auf fremden Grundstücken
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
	* ---- Ausleihungen an Unternehmen, mit denen ein Beteiligungsverhältnis besteht
	     */
	    ABET,
	    
	    /**
	* ---- Wertpapiere des Anlagevermögens
	     */
	    WAV,
	    
	    /**
	* ---- sonstige Ausleihungen
	     */
	    SA,
	    
	    /**
	* -- Umlaufvermögen
	     */
	    UV,
	    
	    /**
	* --- Vorräte
	     */
	    
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
	    
	    /**
	* ---- Forderungen aus Lieferungen und Leistungen
	     */
	    FLL,
	    
	    /**
	* ---- Forderungen gegen verbundene Unternehmen
	     */
	    FVU,
	    
	    /**
	* ---- Forderungen gegen Unternehmen, mit denen ein Beteiligungsverhältnis besteht
	     */
	    FUBET,
	    
	    /**
	* ---- sonstige Vermögensgegenstände
	     */
	    SVG,
	    
	    /**
	* --- Wertpapiere
	     */
	    
	    /**
	* ---- Anteile an verbundenen Unternehmen
	     */
	    AVU,
	    
	    /**
	* ---- sonstige Wertpapiere
	     */
	    SW,
	    
	    /**
	* -- Kassenbestand, Bundesbankguthaben, Guthaben bei Kreditinstituten und Schecks
	     */
	    KBGGKS,
	    
	    /**
	* -- Rechnungsabgrenzungsposten (AKTIVA)
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
	    
	    /**
	* -- Eigenkapital
	     */
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
	* --- Gewinnrücklage
	     */
	    
	    /**
	* ---- gesetzliche Rücklage
	     */
	    GESR,
	    
	    /**
	* ---- Rücklage für Anteile an einem herrschenden oder mehrheitlich beteiligten Unternehmen
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
	    
	    /**
	* --- Rückstellungen für Pensionen und ähnliche Verpflichtungen
	     */
	    RPV,
	    
	    /**
	* --- Steuerrückstellungen
	     */
	    STR,
	    
	    /**
	* --- sonstige Rückstellungen
	     */
	    SR,
	    
	    /**
	* --- Verbindlichkeiten
	     */
	    VB,
	    
	    /**
	* ---- Anleihen
	     */
	    ANL,
	    
	    /**
	* ---- konvertible Anleihen
	     */
	    KANL,
	    
	    /**
	* ---- Verbindlichkeiten gegenüber Kreditinstituten
	     */
	    VBK,
	    
	    /**
	* ---- erhaltene Anzahlungen auf Bestellungen
	     */
	    EAB,
	    
	    /**
	* ---- Verbindlichkeiten aus Lieferungen und Leistungen
	     */
	    VBLL,
	    
	    /**
	* ---- Verbindlichkeiten aus der Annahme gezogener Wechsel und der Ausstellung eigener Wechsel
	     */
	    VBWE,
	    
	    /**
	* ---- Verbindlichkeiten gegenüber verbundenen Unternehmen
	     */
	    VBVU,
	    
	    /**
	* ---- Verbindlichkeiten gegenüber Unternehmen, mit denen ein Beteiligungsverhältnis besteht
	     */
	    VBBETU,
	    
	    /**
	* --- Sonstige Verbindlichkeiten
	     */
	    SVB,
	    
	    /**
	* ---- Verbindlichkeiten aus Steuern
	     */
	    VBST,
	    
	    /**
	* ---- Verbindlichkeiten im Rahmen der sozialen Sicherheit
	     */
	    VBSS,
	    
	    /**
	* -- (Passive) Rechnungsabgrenzungsposten
	     */
	    PRA,
	    
	    /**
	* -- passive latente Steuern
	     */
	    PLS;
    }

    public DTOGCCBalanceSheet() {
    	super(Key.values());
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}
	
	protected Calculable getAV() {
	    return null;
	}
}
