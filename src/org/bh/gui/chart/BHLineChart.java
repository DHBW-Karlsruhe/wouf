package org.bh.gui.chart;

import java.awt.Component;

import org.bh.gui.swing.IBHComponent;
import org.jfree.chart.*;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.*;

public class BHLineChart extends JFreeChart implements IBHComponent{
    
    String ID;
    JFreeChart chart;
    Number value;
    
    public BHLineChart(String title, String XAxis, String YAxis, CategoryDataset dataset, Plot plot, int ID){
	super(plot);
	this.ID = ""+ID;
	chart = ChartFactory.createLineChart(title, XAxis, YAxis, dataset, PlotOrientation.VERTICAL, true, true, false); 
	
    }
    
    public JFreeChart getChart(){
	return chart;
    }

   
	@Override
	public Component add(Component comp) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public String getKey() {
		return ID;
	}

	@Override
	public int[] getValidateRules() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public boolean isTypeValid() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void setValidateRules(int[] validateRules) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
 
}
