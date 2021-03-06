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
import java.util.List;
import java.util.Map;

import org.bh.data.types.Calculable;
import org.bh.data.types.IValue;

@SuppressWarnings("unchecked")
public interface IDTO<ChildT extends IDTO> extends Cloneable, Serializable, Iterable<Map.Entry<String, IValue>> {

	/**
	 * Returns a value assigned to the passed key.
	 * 
	 * @param key
	 *            - The key of the value.
	 * @return
	 * @throws DTOAccessException
	 */
	IValue get(Object key) throws DTOAccessException;

	/**
	 * Returns a value assigned to the passed key as {@link Calculable}.
	 * 
	 * @param key
	 *            - The key of the value.
	 * @return
	 * @throws DTOAccessException
	 */
	Calculable getCalculable(Object key) throws DTOAccessException;

	/**
	 * Puts a value into the DTO and assigns it to a key.
	 * 
	 * @param key
	 * @param value
	 * @throws DTOAccessException
	 */
	void put(Object key, IValue value) throws DTOAccessException;
	
	/**
	 * Removes a key-value-pair from DTO.
	 * 
	 * @param key
	 * @throws DTOAcessException
	 */
	void remove(Object key) throws DTOAccessException;
	
	/**
	 * In the sandbox mode a valid copy of the DTO is made and can be used as
	 * fallback data if a validation check returns false.
	 * 
	 * @param mode
	 *            True - Turns the sandbox mode on False - Turns the sandbox
	 *            mode off
	 */
	void setSandBoxMode(boolean mode);

	/**
	 * Returns the sandboxmode.
	 * 
	 * @return
	 */
	boolean getSandBoxMode();

	/**
	 * Returns a copy of the DTO.
	 * 
	 * @return
	 */
	IDTO<ChildT> clone();

	/**
	 * Adds a child to this DTO at the end of the list.
	 * 
	 * @param child
	 * @throws DTOAccessException
	 */
	public ChildT addChild(ChildT child) throws DTOAccessException;

	/**
	 * Adds a child to this DTO.
	 * 
	 * @param child
	 * @param addLast
	 *            Whether to add the child to the beginning or the end of the
	 *            list.
	 * @throws DTOAccessException
	 */
	public ChildT addChild(ChildT child, boolean addLast)
			throws DTOAccessException;
	
	/**
	 * Adds a child to a specified position
	 * 
	 * @param child
	 * @param position
	 */
	
	public void addChildToPosition (ChildT child, int pos);

	/**
	 * Returns the child at the given position.
	 * 
	 * @param index
	 * @return
	 * @throws DTOAccessException
	 */
	
	public ChildT getChild(int index) throws DTOAccessException;

	/**
	 * Returns all children assigned to this DTO.
	 * 
	 * @return
	 */
	public List<ChildT> getChildren();

	/**
	 * Removes a child relation from this DTO.
	 * 
	 * @param index
	 * @return The child which has been removed.
	 * @throws DTOAccessException
	 */
	public ChildT removeChild(int index) throws DTOAccessException;
	
	/**
	 * Removes a child relation from this DTO.
	 * 
	 * @param child The child to remove.
	 * @throws DTOAccessException
	 */
	public void removeChild(ChildT child) throws DTOAccessException;

	/**
	 * Removes all children from this DTO.
	 */
	public void removeAllChildren();

	/**
	 * Returns the number of children assigned to this DTO.
	 * 
	 * @return Number of children.
	 */
	public int getChildrenSize();

	/**
	 * Returns the last child.
	 * 
	 * @return The last child.
	 */
	public ChildT getLastChild();
	
	/**
	 * Returns the first child.
	 * 
	 * @return The first Child
	 */
	public ChildT getFirstChild();
	
	/**
	 * Returns all available keys.
	 * 
	 * @return List of keys.
	 */
	public List<String> getKeys();

	/**
	 * Sets the validation status of the DTO.
	 * 
	 * @param valid
	 *            The new validation status.
	 */
	public void setValid(boolean valid);

	/**
	 * Returns the validation status of the DTO.
	 * 
	 * @param recursive
	 *            Whether the validity of the children should be considered.
	 * @return The validation status.
	 */
	public boolean isValid(boolean recursive);
	

	/**
	 * gets the number of entries in the values map
	 */
	int getNoOfValues();
	
	/**
	 * Checks weather the given DTO is the DTO itself or one of its children
	 * 
	 * @param dto
	 * @return
	 */
	boolean isMeOrChild(Object checkDto);

	/**
	 * Convert all intervals to double values in this DTO and its children.
	 */
	public void convertIntervalToDouble();
}