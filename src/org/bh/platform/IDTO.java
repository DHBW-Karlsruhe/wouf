package org.bh.platform;

import java.util.List;

public interface IDTO<T> {

	/**
	 * Returns a value assigned to the passed key.
	 * @param key - The key of the value.
	 * @return
	 * @throws DTOAccessException
	 */
	T get(String key) throws DTOAccessException;

	/**
	 * Returns a value by means of its position in the map.
	 * @param pos
	 * @return
	 * @throws DTOAccessException
	 */
	T get(Integer pos) throws DTOAccessException;

	/**
	 * Puts a value into the DTO and assigns it to a key.
	 * @param key
	 * @param value
	 * @throws DTOAccessException
	 */
	void put(String key, T value) throws DTOAccessException;

	/**
	 * Puts a value to the specified position into the DTO 
	 * @param pos
	 * @param value
	 * @throws DTOAccessException
	 */
	void put(Integer pos, T value) throws DTOAccessException;

	/**
	 * Adds a child to this DTO
	 * @param child
	 * @throws DTOAccessException 
	 */
	IDTO<T> addChild(DTO<T> child) throws DTOAccessException;

	/**
	 * Returns the child at the given position. 
	 * @param index
	 * @return
	 * @throws DTOAccessException
	 */
	IDTO<T> getChild(int index) throws DTOAccessException;

	/**
	 * Returns all children assigned to this DTO.
	 * @return
	 */
	List<DTO<T>> getChildren();

	/**
	 * Removes a child relation from this DTO.
	 * @param index
	 * @throws DTOAccessException
	 */
	void removeChild(int index) throws DTOAccessException;

	/**
	 * Returns the number of children assigned to this DTO.
	 * @return
	 */
	int getChildrenSize();

}