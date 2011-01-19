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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.plugin.resultAnalysis;



import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.chart.BHChartPanel;

/**
 * View for FCF results containing FCF charts
 * @author Marco Hammel
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BH_FCF_ResultPanel extends JPanel {

    @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(BH_FCF_ResultPanel.class);

    public BH_FCF_ResultPanel(boolean isAllSelected) {
        this.initialize(isAllSelected);
    }

    public void initialize(boolean isAllSelected) {
      
        this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
    
        //All charts
        //Schön Darstellen!!!!
        BHChartPanel fcf_shareholderValue = BHChartFactory.getWaterfallChart(BHResultController.ChartKeys.FCF_WF_SV, false, true);
        BHChartPanel fcf_freecashflow = BHChartFactory.getBarChart(BHResultController.ChartKeys.FCF_BC_FCF, true, true);
        BHChartPanel fcf_returnrate = BHChartFactory.getBarChart(BHResultController.ChartKeys.FCF_BC_RR, true, true);
       
        //add components to Result Pane
        if (!isAllSelected) {
        	c.fill = GridBagConstraints.HORIZONTAL;
    		c.gridx = 0;
    		c.gridy = 0;
    		c.insets = new Insets(30,0,0,0); //border top 30
    		c.weightx = 1.0;
            this.add(fcf_shareholderValue, c);
            
            c.fill = GridBagConstraints.HORIZONTAL;
    		c.gridx = 0;
    		c.gridy = 1;
    		c.insets = new Insets(30,0,0,0); //border top 30
    		c.weightx = 1.0;
            this.add(fcf_freecashflow, c);
            
            c.fill = GridBagConstraints.HORIZONTAL;
    		c.gridx = 0;
    		c.gridy = 2;
    		c.insets = new Insets(30,0,30,0); //border top 30 border bottom 30
    		c.weightx = 1.0;
            this.add(fcf_returnrate, c);
        }else{
        	c.fill = GridBagConstraints.HORIZONTAL;
    		c.gridx = 0;
    		c.gridy = 0;
    		c.insets = new Insets(30,0,0,0); //border top 30
    		c.weightx = 1.0;
            this.add(fcf_freecashflow, c);
            
            c.fill = GridBagConstraints.HORIZONTAL;
    		c.gridx = 0;
    		c.gridy = 1;
    		c.insets = new Insets(30,0,30,0); //border top 30 border bottom 30
    		c.weightx = 1.0;
            this.add(fcf_returnrate, c);
        }
    }
}
