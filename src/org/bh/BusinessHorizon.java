package org.bh;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ServiceLoader;

import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.data.DTOScenario;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.PeriodicalValuesDTOFactory;
import org.bh.data.Value;
import org.bh.platform.PluginManager;

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
		
		// usually, the controller would create the DTOs
		double[] fk = {1000, 1100, 1200};
		double[] fcf = {100, 110, 120};
		
		DTOScenario scenario = new DTOScenario();
		scenario.put("rendite_ek", new Value(0.11));
		scenario.put("rendite_fk", new Value(0.10));
		scenario.put("sg", new Value(0.1694));
		scenario.put("sks", new Value(0.26375));
		
		for (int i = 0; i < fk.length; i++) {
			IPeriodicalValuesDTO period = PeriodicalValuesDTOFactory.getInstance().create("directinput");
			period.put("fcf", new Value(fcf[i]));
			period.put("fremdkapital", new Value(fk[i]));
			scenario.addChild(period);
		}
		
		ServiceLoader<IShareholderValueCalculator> calculators = PluginManager.getInstance().getServices(IShareholderValueCalculator.class);
		for (IShareholderValueCalculator calculator : calculators) {
			log.debug(calculator.calculate(scenario));
		}
	}
}
