package org.bh.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * General Data Transfer Object 
 * @author Marcus
 *
 * @param <T> Type of the values.
 * @param <G>
 */
public class DTO<T> implements IDTO<T> {
	
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
	 * The actual map which contains all values.
	 */
	protected Map<String, T> values = new HashMap<String, T>();	
	
	@Override
	public T get(String key) throws DTOAccessException {
		// If the key is an actual key then return the corresponding value
		if (availableKeys.contains(key.toLowerCase()))
		{
			T result = values.get(key);
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
	public T get(Integer pos) throws DTOAccessException {
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
	public void put(String key, T value) throws DTOAccessException {
		if (availableKeys.contains(key.toLowerCase()))
		{
			values.put(key, value);
		}
		else
			throw new DTOAccessException("The key '" + key + "' is not part of this DTO!");
	}

	@Override
	public void put(Integer pos, T value) throws DTOAccessException {
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
	private T invokeMethod(String key) throws DTOAccessException
	{
		// Get all methods
		Method[] methods = getClass().getDeclaredMethods();
		// Result
		T result = null;
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
					result = (T) method.invoke(this, (Object[]) null);
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
}