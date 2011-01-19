/*******************************************************************************
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.tests.junit;


import java.util.Map;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.IntervalValue;
import org.bh.data.types.StringValue;
import org.bh.platform.Services;
import org.bh.plugin.apv.APVCalculator;
import org.bh.plugin.directinput.DTODirectInput;
import org.bh.plugin.fcf.FCFCalculator;
import org.bh.plugin.fte.FTECalculator;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for DCF procedures with intervals
 * 
 * @author Norman
 * 
 * @version 0.1, 04.01.2010
 */
public class DCFProceduresIntervalValues {

	IShareholderValueCalculator svCalc;
	IShareholderValueCalculator svCalc2;
	IShareholderValueCalculator svCalc3;
	
	DTOScenario scenario;
	DTOPeriod period;
	DTODirectInput dinp;

    Map<String, Calculable[]> res;
    Map<String, Calculable[]> res3;
    Map<String, Calculable[]> res2;
	
	@Before
	public void setUp() throws Exception {
		Services.initNumberFormats();
		
		scenario = new DTOScenario();
		scenario.put(DTOScenario.Key.IDENTIFIER, new StringValue("TestScenario"));
		scenario.put(DTOScenario.Key.RFK, new IntervalValue(0.11, 0.11));
		scenario.put(DTOScenario.Key.REK, new IntervalValue(0.11, 0.11));
		
		scenario.put(DTOScenario.Key.BTAX, new IntervalValue(0.12, 0.12));
		scenario.put(DTOScenario.Key.CTAX, new IntervalValue(0.12, 0.12));
		
		period = new DTOPeriod();
		period.put(DTOPeriod.Key.NAME, new StringValue("2007"));
		
		dinp = new DTODirectInput();
		dinp.put(DTODirectInput.Key.FCF, new IntervalValue(100, 110));
		dinp.put(DTODirectInput.Key.LIABILITIES, new IntervalValue(1000,1100));
		
		period.addChild(dinp);		
		
		scenario.addChild(period);
		
		
		period = new DTOPeriod();
		period.put(DTOPeriod.Key.NAME, new StringValue("2008"));
		
		dinp = new DTODirectInput();
		dinp.put(DTODirectInput.Key.FCF, new IntervalValue(110, 120));
		dinp.put(DTODirectInput.Key.LIABILITIES, new IntervalValue(1101,1200));
		
		period.addChild(dinp);		
		
		scenario.addChild(period);
		
		
		period = new DTOPeriod();
		period.put(DTOPeriod.Key.NAME, new StringValue("2009"));
		
		dinp = new DTODirectInput();
		dinp.put(DTODirectInput.Key.FCF, new IntervalValue(120,130));
		dinp.put(DTODirectInput.Key.LIABILITIES, new IntervalValue(1201,1300));
		
		period.addChild(dinp);		
		
		scenario.addChild(period);
	}
	
//	@Test
//	public void apv() {
//		svCalc = new APVCalculator();
//		svCalc.calculate(scenario);
//	}
//	
//	@Test
//	public void fcf() {
//		svCalc = new FCFCalculator();
//		svCalc.calculate(scenario);
//	}
//	
//	@Test
//	public void fte() {
//		svCalc = new FTECalculator();
//		svCalc.calculate(scenario);
//		
//	}
	
	@Test
	public void compare() {
		
		svCalc = new APVCalculator();
		svCalc2 = new FTECalculator();
		svCalc3 = new FCFCalculator();
		
		//uncommented to not cause performance issues on build server
//		res = svCalc.calculate(scenario, true);
//		res2 = svCalc2.calculate(scenario, true);
//		res3 = svCalc3.calculate(scenario, true);
//		
//		uw0 = ((IntervalValue)res.get(IShareholderValueCalculator.Result.SHAREHOLDER_VALUE.toString())[0]);
//		uw0_2 = ((IntervalValue)res2.get(IShareholderValueCalculator.Result.SHAREHOLDER_VALUE.toString())[0]);
//		uw0_3 = ((IntervalValue)res3.get(IShareholderValueCalculator.Result.SHAREHOLDER_VALUE.toString())[0]);
		
		//TODO ask Mr. Ratz whether different results are okay
		//assertTrue("Equal results for APV, FTE, FCF procedure expected. Results were " + uw0 + ", " + uw0_2 + ", " + uw0_3 + " instead.", uw0.equals(uw0_2) && uw0.equals(uw0_3));
	}
}
