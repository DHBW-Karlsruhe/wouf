package org.bh.calculation;

import org.bh.calculation.sebi.Calculable;
import org.bh.data.DTOPeriod;

public interface ICalculationPreparer {
	Calculable getFCF(DTOPeriod period);
	Calculable getLiabilities(DTOPeriod period);
}
