package org.bh.data;

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

}