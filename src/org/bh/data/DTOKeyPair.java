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