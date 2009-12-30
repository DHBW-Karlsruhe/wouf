package org.bh.data;

import org.apache.log4j.Logger;

/**
 * Project DTO
 * 
 * <p>
 * This DTO contains projectdata and acts as a root-element
 * 
 * @author Michael Löckelt
 * @version 0.2, 16.12.2009
 * 
 */
public class DTOProject extends DTO<DTOScenario> {
	private static final Logger log = Logger.getLogger(DTOProject.class);

	public enum Key {
		/**
		 * project name
		 */
		NAME,
		
		/**
		 * comment
		 */
		COMMENT;
	}

	public DTOProject() {
		super(Key.values());
		log.debug("Object created!");
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void regenerateMethodsList() {
		regenerateMethodsList(Key.values());
	}

}
