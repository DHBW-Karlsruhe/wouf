package org.bh.data;

public class DTOScenario extends DTO {
	
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
	public IDTO addChild(IDTO child) throws DTOAccessException {
		IDTO result = super.addChild(child);
		refreshPeriodReferences();
		return result;
	}
	
	@Override
	public IDTO removeChild(int index) throws DTOAccessException {
		DTOPeriod removedPeriod = (DTOPeriod) super.removeChild(index);
		removedPeriod.next = removedPeriod.previous = null;
		refreshPeriodReferences();
		return removedPeriod;
	}

	private void refreshPeriodReferences() {
		DTOPeriod previous = null;
		for (IDTO child1 : children) {
			DTOPeriod child = (DTOPeriod)child1;
			child.previous = previous;
			if (previous != null)
				previous.next = child;
			previous = child;
		}
		if (previous != null)
			previous.next = null;
	}
}
