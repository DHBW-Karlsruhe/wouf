/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.calculation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.platform.Services;

/**
 *
 * @author Marco Hammel
 */
public class DummyCalculator implements IShareholderValueCalculator {

    /**
     * control for processes 
     */
    public enum Keys {
    /**
     * Trigger all dcf processes
     */
        allDCF;
    }
    private Keys calcDummy;

    public DummyCalculator(Keys calcDummy) {
        this.calcDummy = calcDummy;
    }

    public Map<String, Calculable[]> calculate(DTOScenario scenario) {
        Map<String, Calculable[]> result =
                Collections.synchronizedMap(new HashMap<String, Calculable[]>());
        switch (this.calcDummy) {
            case allDCF:
                for (IShareholderValueCalculator dcf : Services.getDCFMethods().values()) {
                    if (!dcf.getUniqueId().equals(this.getUniqueId())) {
                        result.putAll(dcf.calculate(scenario));
                    }
                }
                break;
        }
        return result;
    }

    public String getUniqueId() {
        return calcDummy.toString();
    }

    public String getGuiKey() {
        return "";
    }
}
