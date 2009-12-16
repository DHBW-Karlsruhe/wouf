package org.bh.data;

import org.bh.data.types.Tax;

public class DTOScenario extends DTO<DTOPeriod> {
	
	public enum Key {
		/** Rendite Eigenkapital */
		REK,
		/** Rendite Fremdkapital */
		RFK,
		/** Steuern */
		TAX
	}
	
    /**
     * initialize key and method list
     */
	public DTOScenario() {
		super(Key.values());
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
	@Override
	public DTOPeriod addChild(DTOPeriod child) throws DTOAccessException {
		DTOPeriod result = super.addChild(child);
		child.scenario = this;
		refreshPeriodReferences();
		return result;
	}
	
	@Override
	public DTOPeriod removeChild(int index) throws DTOAccessException {
		DTOPeriod removedPeriod = super.removeChild(index);
		removedPeriod.next = removedPeriod.previous = null;
		removedPeriod.scenario = null;
		refreshPeriodReferences();
		return removedPeriod;
	}

	private void refreshPeriodReferences() {
		DTOPeriod previous = null;
		for (DTOPeriod child : children) {
			child.previous = previous;
			if (previous != null)
				previous.next = child;
			previous = child;
		}
		if (previous != null)
			previous.next = null;
	}
	
	/**
	 * Get taxes for scenario.
	 * @return Taxes for scenario.
	 */
	public Tax getTax() {
		return (Tax)get(Key.TAX);
	}
}
