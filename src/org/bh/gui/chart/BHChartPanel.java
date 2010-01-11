/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui.chart;

import java.util.List;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Marco Hammel
 */
public class BHChartPanel extends ChartPanel implements IBHComponent{
    private static final long serialVersionUID = -8018664370176080809L;

    private String key;
    private String inputHint;
    private Class<? extends IBHAddValue> chartClass;

    public BHChartPanel(Object key, JFreeChart chart, Class<? extends IBHAddValue> chartClass){
        super(chart);
        this.key = key.toString();
        this.chartClass = chartClass;
    }

    /**
     * get the semantic object for the chart by changing dataset at runtime
     * @return
     */
    public Class<? extends IBHAddValue> getChartClass(){
        return this.chartClass;
    }

    public String getInputHint() {
        return this.inputHint;
    }

    public String getKey() {
        return this.key;
    }

    protected void reloadText() {
	inputHint = Services.getTranslator().translate(key, ITranslator.LONG);
	setToolTipText(inputHint);
    }
}
