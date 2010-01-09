package org.bh.plugin.directinput;

import org.apache.log4j.Logger;
import org.bh.calculation.ICalculationPreparer;
import org.bh.data.DTO;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;


/**
 * DTO for directly inputing
 *
 * Data Transfer Object which holds values for one period which
 * can directly be used for calculating the shareholder value. 
 *
 * @author Robert Vollmer
 * @author Michael Löckelt
 * @version 0.4, 16.12.2009
 *
 */

@SuppressWarnings("unchecked")
public class DTODirectInput extends DTO implements IPeriodicalValuesDTO, ICalculationPreparer {
	private static final long serialVersionUID = 8597865495976356944L;
	private static final String UNIQUE_ID = "directinput";
	private static final Logger log = Logger.getLogger(DTODirectInput.class);
	
	public enum Key {
		/**
		 * FreeCashFlow
		 */
		@Stochastic
		FCF,
		
		/**
		 * Liabilities
		 */
		@Stochastic
		LIABILITIES;
		
		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}	
	}
	
    /**
     * initialize key and method list
     */
	public DTODirectInput() {
		super(Key.values());
		log.debug("Object created!");
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}
	
	public static String getUniqueIdStatic() {
		return UNIQUE_ID;
	}
	
	@Override
	public Calculable getFCF(DTOPeriod period) {
		IPeriodicalValuesDTO dto = period.getPeriodicalValuesDTO("directinput");
		if (dto == null)
		    return null;
		return dto.getCalculable(Key.FCF);
	}

	@Override
	public Calculable getLiabilities(DTOPeriod period) {
		IPeriodicalValuesDTO dto = period.getPeriodicalValuesDTO("directinput");
		if (dto == null)
		    return null;
		return dto.getCalculable(Key.LIABILITIES);
	}
}
