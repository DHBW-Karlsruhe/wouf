package org.bh.data.types;

/**
 * Marker value class for values which are determined stochastically.
 * 
 * @author Robert Vollmer
 * @version 1.0, 18.12.2009
 */
public class StochasticValue implements IValue {
	public static final StochasticValue INSTANCE = new StochasticValue();

	private StochasticValue() {
	}

	@Override
	public StochasticValue clone() {
		return INSTANCE;
	}
}
