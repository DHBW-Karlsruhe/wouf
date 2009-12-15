package org.bh.data;

/**
 * Project DTO
 * 
 * <p>
 * This DTO contains projectdata and acts as a root-element
 * 
 * @author Michael Löckelt
 * @version 0.1, 15.12.2009
 * 
 */
public class DTOProject extends DTO<DTOScenario> {

	public enum Keys {
		/**
		 * project name
		 */
		NAME;
	}

	public DTOProject() {
		super(Keys.values());
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

}
