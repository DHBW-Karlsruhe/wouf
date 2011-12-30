/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber
 *
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
package org.bh.data;

import org.apache.log4j.Logger;

/**
 * 
 * DTO for storing company data
 *
 * <p>
 * We have to import and store external company data from balances. Therefore
 * we need this DTO as a wrapper for data which is specific to one company. The
 * company is related to a branch and therefore has to keep this relation. As well
 * the company has a specific name, which we can reuse from the {@link org.bh.data.DTOScenario}.
 *
 * @author Yannick Rödl, Lukas Locner
 * @version 1.0, 21.12.2011
 *
 */
public class DTOBranchSpecificRep{

	/**
	 * Generated <code>serialVersionUID</code>
	 */
//	private static final long serialVersionUID = 5619460732252658937L;
	private static final Logger log = Logger.getLogger(DTOCompany.class);	

	public enum Key {
		NAME,
		VALUE
	}

    /**
     * initialize key and method list
     */
	String name = "";
	double value = 0;

	public DTOBranchSpecificRep(String name, double value) {
		this.name = name;
		this.value = value;
		
		log.debug("DTOBranchSpecificRep Object created!");
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

/*	public String getName (Key k){
		String getName = "";
		switch(k){
		case NAME: getName = this.name;
		break;
		}
		return getName;
	}
	
	public double getValue (Key k){
		double getValue = 0;
		switch(k){
		case VALUE: getValue = this.value;
		break;
		}
		return getValue;
	} */
}
