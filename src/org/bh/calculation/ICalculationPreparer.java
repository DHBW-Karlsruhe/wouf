package org.bh.calculation;

import org.bh.calculation.sebi.Value;
import org.bh.data.DTOPeriod;

public interface ICalculationPreparer {
	Value getFCF(DTOPeriod period);
	Value getFremdkapital(DTOPeriod period); // @TODO translate
}
