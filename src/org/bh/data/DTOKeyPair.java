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

import java.io.Serializable;

import org.bh.platform.Services;

/**
 * A pair which consists of the unique ID of an {@link IPeriodicalValuesDTO} and
 * one key in this DTO.
 * 
 * @author Robert
 * @version 1.0, 29.01.2010
 * 
 */
public class DTOKeyPair implements Comparable<DTOKeyPair>, Serializable {
	private static final long serialVersionUID = -6139469450820278480L;
	private final String dtoId;
	private final String key;

	public DTOKeyPair(String dtoId, String key) {
		this.dtoId = dtoId;
		this.key = key;
	}

	public DTOKeyPair(String dtoId, Object key) {
		this(dtoId, key.toString());
	}

	public String getDtoId() {
		return dtoId;
	}

	public String getKey() {
		return key;
	}

	@Override
	public int compareTo(DTOKeyPair o) {
		int num = dtoId.compareTo(o.dtoId);
		if (num != 0)
			return num;

		return key.compareTo(o.key);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dtoId == null) ? 0 : dtoId.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof DTOKeyPair))
			return false;
		DTOKeyPair other = (DTOKeyPair) obj;
		return dtoId.equals(other.dtoId) && key.equals(other.key);
	}

	@Override
	public String toString() {
		return Services.getTranslator().translate(key);
	}

}