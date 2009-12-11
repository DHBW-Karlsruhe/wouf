package org.bh;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ServiceLoader;

import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.sebi.DoubleValue;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.platform.PluginManager;
import org.bh.plugin.directinput.DTODirectInput;
import org.bh.plugin.gcc.DTOGCCBalanceSheet;
import org.bh.plugin.gcc.DTOGCCProfitLossStatementCostOfSales;

/**
 *
 * This is the entry class for Business Horizon. 
 *
 * The main method of this class will be called when Business
 * Horizon starts.
 *
 * @author Robert Vollmer
 * @version 0.1, 06.12.2009
 *
 */

public class BusinessHorizon {
	private static final Logger log = Logger.getLogger(BusinessHorizon.class);
	
	/**
	 * @param args Commandline arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		log.info("Business Horizon is starting...");
		
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				log.error("Uncaught exception", e);
			}
		});
		

		/*PluginManager pluginManager = PluginManager.getInstance();
		ServiceLoader<IPeriodicalValuesController> controllers = pluginManager.getServices(IPeriodicalValuesController.class);
		for (IPeriodicalValuesController controller : controllers) {
			IPeriodicalValuesDTO dto = controller.editDTO(null, null);
			log.debug(dto.toString());
		}*/
		
		PluginManager.getInstance().loadAllServices(IPeriodicalValuesDTO.class);
		
		// usually, the controller would create the DTOs
		double[] fk = {1000, 1100, 1200};
		double[] fcf = {555555, 110, 120};
		double[] dummy = {500, 520, 540};
		double[] value1 = {-1, 30, 35};
		double[] value2 = {-1, 60, 65};
		
		DTOScenario scenario = new DTOScenario();		
		scenario.put(DTOScenario.Key.REK, new DoubleValue(0.11));
		scenario.put(DTOScenario.Key.RFK, new DoubleValue(0.10));
		scenario.put(DTOScenario.Key.SG, new DoubleValue(0.1694));
		scenario.put(DTOScenario.Key.SKS, new DoubleValue(0.26375));
		
		for (int i = 0; i < fk.length; i++) {
			DTOPeriod period = new DTOPeriod();
			
			if (i > 1) {
				IPeriodicalValuesDTO direct = new DTODirectInput();
				direct.put(DTODirectInput.Key.FCF, new DoubleValue(fcf[i]));
				direct.put(DTODirectInput.Key.LIABILITIES, new DoubleValue(fk[i]));			
				
				period.addChild(direct);
			} else {
			    
				DTOGCCBalanceSheet bs = new DTOGCCBalanceSheet();
				bs.put(DTOGCCBalanceSheet09.DTOGCCBalanceSheet.LIABILITIES, new DoubleValue(fk[i]));
				bs.put(DTOGCCBalanceSheet09.DTOGCCBalanceSheet.DUMMY, new DoubleValue(dummy[i]));
				period.addChild(bs);
				
				if (i > 0) {
					DTOGCCProfitLossStatementCostOfSales pls = new DTOGCCProfitLossStatementCostOfSales();
					pls.put(DTOGCCProfitLossStatementCostOfSales.Key.VALUE1, new DoubleValue(value1[i]));
					pls.put(DTOGCCProfitLossStatementCostOfSales.Key.VALUE2, new DoubleValue(value2[i]));
					period.addChild(pls);
				}
			}
			
			scenario.addChild(period);
		} 
		
		ServiceLoader<IShareholderValueCalculator> calculators = PluginManager.getInstance().getServices(IShareholderValueCalculator.class);
		for (IShareholderValueCalculator calculator : calculators) {
			log.debug(calculator.calculate(scenario));
		}
	}
}
