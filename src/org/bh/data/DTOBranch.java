package org.bh.data;

import org.apache.log4j.Logger;

/**
 * DTO for access to branches
 *
 * <p>
 * This DTO should be used to access branches. Every branch stores its company values.
 *
 * @author Yannick Rödl, Lukas Lochner
 * @version 1.0, 14.12.2011
 *
 */
public class DTOBranch extends DTO<DTOCompany> {

	/**
	 * Generated <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 4029409029283468579L;
	private static final Logger log = Logger.getLogger(DTOPeriod.class);	

	public enum Key{
		BRANCH_KEY_MAIN_CATEGORY,
		BRANCH_KEY_MID_CATEGORY,
		BRANCH_KEY_SUB_CATEGORY
	}
	
    /**
     * initialize key and method list
     */
	
	public DTOBranch() {
		super(Key.class);
		log.debug("Branch Object created!");
	}	
}
