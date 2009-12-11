package org.bh.data;

public class DTOScenario extends DTO<DTOPeriod> {
	
	public enum Key {
		/**
		 * Rendite Eigenkapital
		 */
		REK,
		@Method RFK,
		SG,
		SKS,
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
		refreshPeriodReferences();
		return result;
	}
	
	@Override
	public DTOPeriod removeChild(int index) throws DTOAccessException {
		DTOPeriod removedPeriod = super.removeChild(index);
		removedPeriod.next = removedPeriod.previous = null;
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
}
