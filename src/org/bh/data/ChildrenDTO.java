package org.bh.data;

import java.util.ArrayList;
import java.util.List;

public class ChildrenDTO<T, ChildT> extends DTO<T> implements IChildrenDTO<T, ChildT> {
	/**
	 * All children assigned to this DTO.
	 */
	protected List<ChildT> children = new ArrayList<ChildT>();
	
	@Override
	public ChildT addChild(ChildT child) throws DTOAccessException
	{
		if (!children.contains(child))
		{
			children.add(child);
			return child;
		}
		else
			throw new DTOAccessException("The child is already assigned to this DTO!");
		
	}
	
	@Override
	public ChildT getChild(int index) throws DTOAccessException
	{
		ChildT result = null;
		try
		{
			result = children.get(index);
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new DTOAccessException("There is no child at the given position: " + index);
		}
		return result;
	}
	
	@Override
	public List<ChildT> getChildren()
	{
		return children;
	}
	
	@Override
	public void removeChild(int index) throws DTOAccessException
	{
		try
		{
			children.remove(index);
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new DTOAccessException("There is no child at the given position: " + index);
		}
	}
	
	@Override
	public int getChildrenSize()
	{
		return children.size();
	}
}
