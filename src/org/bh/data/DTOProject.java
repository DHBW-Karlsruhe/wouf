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
	private static final long serialVersionUID = -4854118820969934676L;
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
		
		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}	
	}

	public DTOProject() {
		super(Key.values());
	}
}
