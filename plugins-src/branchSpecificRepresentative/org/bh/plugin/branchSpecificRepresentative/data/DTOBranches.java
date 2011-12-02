package org.bh.plugin.branchSpecificRepresentative.data;

import org.bh.data.DTO;

/**
 * DTO for access to branches
 *
 * <p>
 * This DTO should be used to access branches. Every branch stores its company values.
 *
 * @author Yannick Rödl
 * @version 1.0, 02.12.2011
 *
 */
public class DTOBranches extends DTO<DTOCompany> {

	/**
	 * Generated <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 4029409029283468579L;

	public enum Keys{
		BRANCH_KEY,
		BRANCH
	}
}
