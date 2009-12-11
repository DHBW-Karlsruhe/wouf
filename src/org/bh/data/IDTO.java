package org.bh.data;

import java.util.List;

import org.bh.calculation.sebi.Calculable;
import org.bh.calculation.sebi.Value;

@SuppressWarnings("unchecked")
public interface IDTO<ChildT extends IDTO> extends Cloneable {

	/**
	 * Returns a value assigned to the passed key.
	 * @param key - The key of the value.
	 * @return
	 * @throws DTOAccessException
	 */
	Value get(Object key) throws DTOAccessException;
	
	/**
	 * Returns a value assigned to the passed key as {@link Calculable}.
	 * @param key - The key of the value.
	 * @return
	 * @throws DTOAccessException
	 */
	Calculable getCalculable(Object key) throws DTOAccessException;
	
	/**
	 * Puts a value into the DTO and assigns it to a key.
	 * @param key
	 * @param value
	 * @throws DTOAccessException
	 */
	void put(Object key, Value value) throws DTOAccessException;
	
	/**
	 * In the sandbox mode a valid copy of the DTO is made
	 * and can be used as fallback data if a validation
	 * check returns false.
	 * @param mode	True - Turns the sandbox mode on
	 * 				False - Turns the sandbox mode off
	 */
	void setSandBoxMode(Boolean mode);
	
	/**
	 * Returns the sandboxmode.
	 * @return
	 */
	boolean getSandBoxMode();
	
	/**
	 * Validates the data of the DTO.
	 * @return	True - Data is valid.
	 * 			False - Data is invalid.
	 */
	boolean validate();
	
	/**
	 * Returns a copy of the DTO.
	 * @return
	 */
	IDTO<ChildT> clone();
	
	/**
	 * Adds a child to this DTO
	 * @param child
	 * @throws DTOAccessException 
	 */
	public ChildT addChild(ChildT child) throws DTOAccessException;
	
	/**
	 * Returns the child at the given position. 
	 * @param index
	 * @return
	 * @throws DTOAccessException
	 */
	public ChildT getChild(int index) throws DTOAccessException;
	
	/**
	 * Returns all children assigned to this DTO.
	 * @return
	 */
	public List<ChildT> getChildren();
	
	/**
	 * Removes a child relation from this DTO.
	 * @param index
	 * @throws DTOAccessException
	 */
	public ChildT removeChild(int index) throws DTOAccessException;
	
	/**
	 * Returns the number of children assigned to this DTO.
	 * @return
	 */
	public int getChildrenSize();
}