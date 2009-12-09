package org.bh.data;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bh.calculation.sebi.Value;

/**
 * General Data Transfer Object 
 * @author Marcus
 *
 * @param <Value> Type of the values.
 * @param <G>
 */
public abstract class DTO implements IDTO {	
	
	/**
	 * Possible keys with which the user can access this DTO.
	 */
	protected List<String> availableKeys;
	
	/**
	 * Possible methods with which the user can call simple methods for
	 * calculation.
	 */
	protected List<String> availableMethods;	
	
	/**
	 * All children assigned to this DTO.
	 */
	protected List<IDTO> children = new ArrayList<IDTO>();
		
	
	/**
	 * Prefix of the methods.
	 * Method name: methodPrefix + key
	 */
	protected String methodPrefix = "get";
	
	/**
	 * Placeholder which is used in the list of availableKeys in order to 
	 * have the possibility to keep the order of the keys.
	 * 
	 */
	protected String methodPlaceholder = "METHOD";
	
	/**
	 * In the sandbox mode a valid copy of the DTO is made
	 * and can be used as fallback data if a validation
	 * check returns false.
	 */
	protected Boolean sandBoxMode = false;
	
	/**
	 * The actual map which contains all values.
	 */
	protected Map<String, Value> values = new HashMap<String, Value>();	
	
	/**
	 * Fallback data in order to provide a kind of undo functionality.
	 */
	protected Map<String, Value> fallBackValues = new HashMap<String, Value>();	
	
	@Override
	public Value get(String key) throws DTOAccessException {
		// If the key is an actual key then return the corresponding value
		if (availableKeys.contains(key.toLowerCase()))
		{
			Value result = values.get(key);
			if (result != null)
				return result;
			else
			{
				if (availableMethods.contains(key))
					return invokeMethod(key);
				else
					throw new DTOAccessException("There is no value assigned to the key: '" + key + "'");
			}
				
		}
		// If the key is the name of a method then invoke that method and
		// return its value
		else if (availableMethods.contains(key))
		{			
			return invokeMethod(key);			
		}
		// The key is neither part of the key list nor of the method list
		else
			throw new DTOAccessException("The key '" + key + "' is not part of this DTO!");		
	}

	@Override
	public Value get(Integer pos) throws DTOAccessException {
		// The position has to be in the range of the availableKey List
		try {
			// Get the name of the key at the position pos
			String key = availableKeys.get(pos);
			// If that key is a placeholder for a method then
			// get the name of the key of the acutal method
			if (key.equals(methodPlaceholder))
			{
				int methIdx = getMethodIndex(pos);
				key = availableMethods.get(methIdx);
			}
			// Call the get method with the key
			return get(key);
		} catch (IndexOutOfBoundsException e) {
			throw new DTOAccessException("The given position '" + pos + "' has no equivalent key!");
		}	
	}

	@Override
	public void put(String key, Value value) throws DTOAccessException {
		if (availableKeys.contains(key.toLowerCase()))
		{
			values.put(key, value);
		}
		else
			throw new DTOAccessException("The key '" + key + "' is not part of this DTO!");
	}

	@Override
	public void put(Integer pos, Value value) throws DTOAccessException {
		try {
			String key = availableKeys.get(pos);
			if (key.equals(methodPlaceholder))
				throw new DTOAccessException("A value cannot be assigned to a method.");
			put(key, value);
		} catch (IndexOutOfBoundsException e) {
			throw new DTOAccessException("The given position '" + pos + "' has no equivalent key!");
		}		
	}
	
	/**
	 * Invokes a method via reflection of this object.
	 * @param key
	 * @return
	 * @throws DTOAccessException
	 */
	@SuppressWarnings("unchecked")
	private Value invokeMethod(String key) throws DTOAccessException
	{
		// Get all methods
		Method[] methods = getClass().getDeclaredMethods();
		// Result
		Value result = null;
		// Complete method name with a prefix
		String methodName = methodPrefix + key;
		// Go through each method and check if the wanted method
		// is available
		for (Method method : methods)
		{
			if (method.getName().equalsIgnoreCase(methodName))
			{
				try
				{
					// Invoke method and store result
					result = (Value) method.invoke(this, (Object[]) null);
				}
				catch (InvocationTargetException e)
				{
					throw new DTOAccessException(e.getTargetException());
				} catch (Exception e) {
					throw new DTOAccessException("The specified method '" + key + "' could not be invoked.");
				}
				break;
			}
		}
		if (result == null)
			throw new DTOAccessException("The method '" + key + "' returned no proper result.");
		// Return result
		return result;
	}
	
	/**
	 * Returns the equivalent index of the availableMethod list to the index of the
	 * availableKey list.
	 * @param overallPos
	 * @return
	 */
	private Integer getMethodIndex(Integer overallPos)
	{
		int result = 0;
		for (int i = 0; i < overallPos; i++)
		{
			if (availableKeys.get(i).equals(methodPlaceholder))
				result++;
		}
		return result;
	}
	
	@Override
	public String toString() {
		return values.toString();
	}

	/**
	 * Adds a child to this DTO
	 * @param child
	 * @throws DTOAccessException 
	 */
	public IDTO addChild(IDTO child) throws DTOAccessException
	{
		if (!children.contains(child))
		{
			children.add(child);
			return child;
		}
		else
			throw new DTOAccessException("The child is already assigned to this DTO!");
		
	}
	
	
	/**
	 * Returns the child at the given position. 
	 * @param index
	 * @return
	 * @throws DTOAccessException
	 */
	public IDTO getChild(int index) throws DTOAccessException
	{
		IDTO result = null;
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
	
	/**
	 * Returns all children assigned to this DTO.
	 * @return
	 */
	public List<IDTO> getChildren()
	{
		return children;
	}
	
	/**
	 * Removes a child relation from this DTO.
	 * @param index
	 * @throws DTOAccessException
	 */
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
	
	/**
	 * Returns the number of children assigned to this DTO.
	 * @return
	 */
	public int getChildrenSize()
	{
		return children.size();
	}

	@Override
	public Boolean getSandBoxMode() {
		return sandBoxMode;
	}

	@Override
	public void setSandBoxMode(Boolean mode) {
		if (mode)
		{
			fallBackValues.clear();
			values.putAll(((DTO) clone()).values);		
		}
		sandBoxMode = mode;
		
	}

	@Override
	public abstract Boolean validate();

	@Override
	public DTO clone() throws DTOAccessException {
		DTO result = null;
		try {
			// Try to instantiate a new instance of the class of this DTO
			result = this.getClass().newInstance();
			// Go through each value, copy it and put it into the new instance
			for (Map.Entry<String, Value> entry: values.entrySet())
			{
				result.put(entry.getKey(), entry.getValue().clone());
				// Copy and add children to the new instance
				for (IDTO child : children)
				{
					result.addChild(child.clone());
				}
			}			
		}
		catch (Exception e) {
			throw new DTOAccessException("An error occured during the cloning of a DTO. Class: " 
					+ getClass().getName());
		}
		
		return result;
	}
}
