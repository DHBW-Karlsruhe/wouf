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
package org.bh.plugin.gcc.data;

import org.apache.log4j.Logger;
import org.bh.data.DTO;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;

/**
 * GCCProfitLossStatementCostOfSales DTO.
 * 
 * <p>
 * Data Transfer Object to handle GCCProfitLossStatementCostOfSales values and
 * methods
 * 
 * @author Michael Löckelt
 * @version 0.2, 16.12.2009
 * 
 */

@SuppressWarnings("unchecked")
public class DTOGCCProfitLossStatementCostOfSales extends DTO implements
		IPeriodicalValuesDTO {
	private static final long serialVersionUID = 5411394776750186305L;
	private static final String UNIQUE_ID = "gcc_pls_costofsales";
	private static final Logger log = Logger.getLogger(DTOGCCProfitLossStatementCostOfSales.class);

	public enum Key {

		/**
		 * (+) Umsatzerlöse
		 */
		@Stochastic
		UE,

		/**
		 * (-) Herstellkosten der zur Erzielung der Umsatzerlöse erbrachten
		 * Leistungen
		 */
		@Stochastic
		HK,

		/**
		 * (=) Bruttoergebnis vom Umsatz
		 */
		@Method
		BUM,

		/**
		 * (-) Vertriebskosten
		 */
		VERTK,

		/**
		 * (-) Verwaltungskosten
		 */
		VERWK,

		/**
		 * (+) sonstige betriebliche Erträge
		 */
		@Stochastic
		SBE,

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
		JUJF,
		
		/**
		 * Vertriebskosten + allg. Verwaltungskosten +sonstige betriebliche Aufwendungen
		 * (von der BWL Gruppe als extra Feld gewünscht)
		 */
		@Stochastic
		VVSBA;
		
		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}	

	}

	public DTOGCCProfitLossStatementCostOfSales() {
		super(Key.class);
		log.debug("Object created!");
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}

	/**
	 * intermediate result: gross profit on sales
	 * 
	 * @return Calculable
	 */
	public Calculable getBUM() {
		return null;
	}

	/**
	 * intermediate result: operating income
	 * 
	 * @return Calculable
	 */
	public Calculable getBERG() {
		return null;
	}

	/**
	 * intermediate result: result of ordinary activities
	 * 
	 * @return Calculable
	 */
	public Calculable getEGG() {
		return null;
	}

	/**
	 * intermediate result: extraordinary result
	 * 
	 * @return Calculable
	 */
	public Calculable getAUERG() {
		return null;
	}

	/**
	 * intermediate result: net income
	 * 
	 * @return Calculable
	 */
	public Calculable getJUJF() {
		return null;
	}

	public static String getUniqueIdStatic() {
		return UNIQUE_ID;
	}

}
