package org.bh.plugin.gccbalancesheet09.data;

import java.util.Arrays;
import java.util.List;

import org.bh.platform.DTO;
import org.bh.platform.IPeriodicalValuesDTO;
import org.bh.platform.Value;

/**
 * HGBBalanceSheet DTO.
 * 
 * <p>
 * Data Transfer Object to handle GCCBalanceSheet09 values and methods
 * 
 * @author Michael L�ckelt 
 * @version 0.2, 07.12.2009
 * 
 */

public class DTOGCCBalanceSheet09 extends DTO<Value> implements IPeriodicalValuesDTO {
    
    private static final String UNIQUE_ID = "gccbalancesheet09";

    //@TODO: replace placeholder with verified english expressions
    
    private static final List<String> AVAILABLE_KEYS = Arrays.asList(
	    
	    //- AKTIVA
	    //-- Anlageverm�gen
	    //--- Immaterielle Verm�gensgegenst�nde
	    "placeholder1", 		// 0 - Selbst geschaffene gewerbliche Schutzrechte und �hnliche Rechte und Werte
	    "placeholder2",		// 1 - entgeltlich erworbene Konzessionen, gewerbliche Schutzrechte und �hnliche Rechte und Werte sowie Lizenzen an solchen Rechten und Werten
	    "placeholder3",		// 2 - Gesch�fts- oder Firmenwert
	    "placeholder4",		// 3 - geleistete Anzahlungen
	    
	    //--- Sachanlagen
	    "placeholder5",		// 4 - Grundst�cke, grundst�cksgleiche Rechte und Bauten einschlie�lich der Bauten auf fremden Grundst�cken
	    "placeholder6",		// 5 - technische Anlagen und Maschinen
	    "placeholder7",		// 6 - andere Anlagen, Betriebs- und Gesch�ftsausstattung
	    "placeholder8",		// 7 - geleistete Anzahlungen und Anlagen im Bau
	    
	    //--- Finanzanlagen
	    "placeholder9",		// 8 - Anteile an verbundenen Unternehmen
	    "placeholder10",		// 9 - Ausleihungen an verbundene Unternehmen
	    "placeholder11", 		// 10 - Beteiligungen
	    "placeholder12",		// 11 - Ausleihungen an Unternehmen, mit denen ein Beteiligungsverh�ltnis besteht
	    "placeholder13",		// 12 - Wertpapiere des Anlageverm�gens
	    "placeholder14",		// 13 - sonstige Ausleihungen
	    
	    //-- Umlaufverm�gen
	    //--- Vorr�te
	    "placeholder15",		// 14 - Roh-, Hilfs- und Betriebsstoffe
	    "placeholder16", 		// 15 - unfertige Erzeugnisse, unfertige Leistungen
	    "placeholder17",		// 16 - fertige Erzeugnisse und Waren
	    "placeholder18",		// 17 - geleistete Anzahlungen
	    
	    //--- Forderungen und sonstige Verm�gensgegenst�nde
	    "placeholder19",		// 18 - Forderungen aus Lieferungen und Leistungen
	    "placeholder20",		// 19 - Forderungen gegen verbundene Unternehmen
	    "placeholder21",		// 20 - Forderungen gegen Unternehmen, mit denen ein Beteiligungsverh�ltnis besteht
	    "placeholder22",		// 21 - sonstige Verm�gensgegenst�nde
	    
	    //--- Wertpapiere
	    "placeholder23",		// 22 - Anteile an verbundenen Unternehmen
	    "placeholder24",		// 23 - sonstige Wertpapiere
	    
	    //--- 24 Kassenbestand, Bundesbankguthaben, Guthaben bei Kreditinstituten und Schecks
	    "placeholder25",		
	    
	    //-- 25 Rechnungsabgrenzungsposten
	    "placeholder26",
	    
	    //-- 26 Aktive latente Steuern
	    "placeholder27",
	    
	    //-- 27 Aktiver Unterschiedsbetrag aus der Verm�gensverrechnung.
	    "placeholder28",
	    
	    //- PASSIVA
	    //-- Eigenkapital
	    //--- 28 Gezeichnetes Kapital
	    "placeholder29",
	    
	    //--- 29 Kapitalr�cklage
	    "placeholder30",
	    
	    //--- Gewinnr�cklage
	    "placeholder31",		// 30 - gesetzliche R�cklage
	    "placeholder32",		// 31 - R�cklage f�r Anteile an einem herrschenden oder mehrheitlich beteiligten Unternehmen
	    "placeholder33",		// 32 - satzungsm��ige R�cklagen
	    "placeholder34",		// 33 - andere Gewinnr�cklagen
	    
	    //--- 34 Gewinnvortrag/Verlustvortrag
	    "placeholder35",
	    
	    //--- 35 Jahres�berschu�/Jahresfehlbetrag
	    "placeholder36",
	    
	    //-- R�ckstellungen
	    "placeholder37",		// 36 - R�ckstellungen f�r Pensionen und �hnliche Verpflichtungen
	    "placeholder38",		// 37 - Steuerr�ckstellungen
	    "placeholder39",		// 38 - sonstige R�ckstellungen
	   
	    //-- Verbindlichkeiten
	    "placeholder40",		// 39 - Anleihen, davon konvertibel
	    "placeholder41",		// 40 - Verbindlichkeiten gegen�ber Kreditinstituten
	    "placeholder42",		// 41 - erhaltene Anzahlungen auf Bestellungen
	    "placeholder43",		// 42 - Verbindlichkeiten aus Lieferungen und Leistungen
	    "placeholder44",		// 43 - Verbindlichkeiten aus der Annahme gezogener Wechsel und der Ausstellung eigener Wechsel
	    "placeholder45",		// 44 - Verbindlichkeiten gegen�ber verbundenen Unternehmen
	    "placeholder46",		// 45 - Verbindlichkeiten gegen�ber Unternehmen, mit denen ein Beteiligungsverh�ltnis besteht;
	    
	    //--- Sonstige Verbindlichkeiten
	    "placeholder47",		// 46 - Verbindlichkeiten aus Steuern
	    "placeholder48",		// 47 - Verbindlichkeiten im Rahmen der sozialen Sicherheit
	    
	    //-- 48 Rechnungsabgrenzungsposten
	    "placeholder49",
	    
	    //-- 49 Passive latente Steuern
	    "placeholder50");
    
    
    private static final List<String> AVAILABLE_METHODS = Arrays.asList();
    
    public DTOGCCBalanceSheet09() {
	availableKeys = AVAILABLE_KEYS;
	availableMethods = AVAILABLE_METHODS;
}

    public String getUniqueId() {
	return UNIQUE_ID;
    }

}
