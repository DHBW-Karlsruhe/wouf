package org.bh.data;

import java.util.List;

import org.bh.calculation.sebi.Value;

public interface IDTO {

	/**
	 * Returns a value assigned to the passed key.
	 * @param key - The key of the value.
	 * @return
	 * @throws DTOAccessException
	 */
	Value get(String key) throws DTOAccessException;

	/**
	 * Returns a value by means of its position in the map.
	 * @param pos
	 * @return
	 * @throws DTOAccessException
	 */
	Value get(Integer pos) throws DTOAccessException;

	/**
	 * Puts a value into the DTO and assigns it to a key.
	 * @param key
	 * @param value
	 * @throws DTOAccessException
	 */
	void put(String key, Value value) throws DTOAccessException;

	/**
	 * Puts a value to the specified position into the DTO 
	 * @param pos
	 * @param value
	 * @throws DTOAccessException
	 */
	void put(Integer pos, Value value) throws DTOAccessException;

	
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
	Boolean getSandBoxMode();
	
	/**
	 * Validates the data of the DTO.
	 * @return	True - Data is valid.
	 * 			False - Data is invalid.
	 */
	Boolean validate();
	
	/**
	 * Returns a copy of the DTO.
	 * @return
	 */
	IDTO clone();
	
	/**
	 * Adds a child to this DTO
	 * @param child
	 * @throws DTOAccessException 
	 */
	public IDTO addChild(IDTO child) throws DTOAccessException;
	
	/**
	 * Returns the child at the given position. 
	 * @param index
	 * @return
	 * @throws DTOAccessException
	 */
	public IDTO getChild(int index) throws DTOAccessException;
	
	/**
	 * Returns all children assigned to this DTO.
	 * @return
	 */
	public List<IDTO> getChildren();
	
	/**
	 * Removes a child relation from this DTO.
	 * @param index
	 * @throws DTOAccessException
	 */
	public void removeChild(int index) throws DTOAccessException;
	
	/**
	 * Returns the number of children assigned to this DTO.
	 * @return
	 */
	public int getChildrenSize();
}