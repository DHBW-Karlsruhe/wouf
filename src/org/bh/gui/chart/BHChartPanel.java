/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.gui.chart;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import org.bh.gui.swing.IBHComponent;
import org.bh.gui.swing.ValidationResultViewFactory;
import org.bh.platform.Services;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import com.jgoodies.forms.layout.FormLayout;

/**
 *
 * @author Marco Hammel
 */
public class BHChartPanel extends JPanel implements IBHComponent, IBHAddValue, IBHAddGroupValue{
    private static final long serialVersionUID = -8018664370176080809L;

    private String key;
    private String inputHint;
    private Class<? extends IBHAddValue> chartClass;
    private IBHAddValue chartInstance;
    private JFreeChart chart;

    public BHChartPanel(Object key, JFreeChart chart, Class<? extends IBHAddValue> chartClass, IBHAddValue chartInstance){
    	this.key = key.toString();
        this.chartClass = chartClass;
        this.chartInstance = chartInstance;
    	this.setLayout(new FormLayout("pref,pref","p"));
        this.add(new ChartPanel(chart), "1,1");
        JTextArea description = new JTextArea(1,20);
        description.setText(Services.getTranslator().translate(key + BHChart.DESC));
        description.setEditable(false);
        description.setVisible(false);
        description.setAutoscrolls(true);
        description.setBorder(BorderFactory.createLoweredBevelBorder());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
//        description.setPreferredSize(new Dimension(250, description.getPreferredSize().height));
        
//    	this.add(description, BorderLayout.EAST);
    	JPanel panel = new JPanel(new FormLayout("pref","p,p"));
    	
    	JToggleButton info = new JToggleButton();
    	info.setIcon(ValidationResultViewFactory.getLargeInfoIcon());
    	info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton button = (JToggleButton)e.getSource();
				JTextArea ta = new JTextArea();
				for (Component comp : button.getParent().getComponents()){
					if(comp instanceof JTextArea)
						ta = (JTextArea)comp;
				}
				if (button.isSelected()){
					ta.setVisible(true);
				}
				else{
					ta.setVisible(false);
				}
			}
		});
    	panel.add(info,"1,1,left,top");
    	panel.add(description, "1,2");
    	
    	this.add(panel, "2,1,left,top");
    	this.chart = chart;
    }
 

    /**
     * get the semantic object for the chart by changing dataset at runtime
     * @return
     */
    public Class<? extends IBHAddValue> getChartClass(){
        return this.chartClass;
    }
    
    public JFreeChart getChart(){
    	return chart;
    }
    public IBHAddValue getChartInstance(){
        return this.chartInstance;
    }

    public String getHint() {
        return this.inputHint;
    }

    public String getKey() {
        return this.key;
    }

    protected void reloadText() {
//	inputHint = Services.getTranslator().translate(key, ITranslator.LONG);
//	setToolTipText(inputHint);
    }

    public void addSeries(Comparable<String> key, double[] values, int bins, double minimum, double maximum) {
        this.chartInstance.addSeries(key, values, bins, minimum, maximum);
    }

    public void addSeries(Comparable<String> seriesKey, double[][] data, Integer amountOfValues, Integer average) {
        this.chartInstance.addSeries(key, data, amountOfValues, average);
    }

    public void addValue(Number value, Comparable<String> columnKey) {
        this.chartInstance.addValue(value, columnKey);
    }

    public void addValues(List<?> list) {
        this.chartInstance.addValues(list);
    }

	@Override
	public void addValue(Number value, Comparable rowKey,
			Comparable<String> columnKey) {
		 this.chartInstance.addValue(value, rowKey, columnKey);
	}

    public void setDefaultGroupSettings(String[] categories) {
        ((IBHAddGroupValue) this.chartInstance).setDefaultGroupSettings(categories);
    }

    public void addValue(Number value, Comparable row, Comparable<String> columnKey, int catIdx) {
        ((IBHAddGroupValue) this.chartInstance).addValue(value, row, columnKey, catIdx);
    }




}
