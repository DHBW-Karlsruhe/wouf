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
package org.bh.gui.chart;

import java.awt.image.BufferedImage;

import org.bh.gui.IBHComponent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.jfree.chart.JFreeChart;

/**
 * abstract class for BHCharts. Defining central methods for semantic operations with charts
 * @author Marco Hammel
 * @version 1.0
 */
public abstract class BHChart implements IBHComponent {
    /**
     * definig access to get x axis title by runtime
     */
    protected static final String DIMX = ".X";
    /**
     * definig access to get y axis title by runtime
     */
    protected static final String DIMY = ".Y";
    /**
     * definig access to get describtion by runtime
     */
    protected static final String DESC = ".D";

    ITranslator translator;
    /**
     * defined to use View mapper functionality, have to be unique in each JPanel instance
     */
    String key;
    /**
     * refernce to the JFreeChart instance which is responsible for the rendering/painting of the chart
     */
    JFreeChart chart;
    //private String inputHint;
    /**
     * create the whole semantic info of the chart
     * @param key
     */
    public BHChart(String key) {
        this.key = key;
        this.translator = Services.getTranslator();
    }

    @Override
    public String getHint() {
        return "";
    }

    @Override
    public String getKey() {
        return this.key;
    }

    /**
     * returns the created Chart
     * @see JFreeChart
     * @return JFreeChart BarChart
     */
    public JFreeChart getChart() {
        return chart;
    }

    /**
     * Set new text values in case of language change, will be triggered by eventing
     */
    protected void reloadText() {
        this.chart.getPlot().setNoDataMessage(translator.translate("noDataAvailable"));
        //this.chart.setTitle(translator.translate(key));
        //TODO extend method for x and y title exchange
    }
    /**
     * deliver a buffered png image of the current chart
     * @param width in pixel
     * @param height in pixel
     * @return BufferedImage
     * @see BufferedImage
     */
    public BufferedImage getChartAsImage(int width, int height) {
    	return chart.createBufferedImage(width, height);
    }
}
