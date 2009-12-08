package org.bh.data;

import java.util.List;

public interface IChildrenDTO<T, ChildT> extends IDTO<T> {
	/**
	 * Adds a child to this DTO
	 * @param child
	 * @throws DTOAccessException 
	 */
	ChildT addChild(ChildT child) throws DTOAccessException;

	/**
	 * Returns the child at the given position. 
	 * @param index
	 * @return
	 * @throws DTOAccessException
	 */
	ChildT getChild(int index) throws DTOAccessException;

	/**
	 * Returns all children assigned to this DTO.
	 * @return
	 */
	List<ChildT> getChildren();

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
