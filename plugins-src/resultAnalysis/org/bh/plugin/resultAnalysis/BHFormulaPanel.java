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
package org.bh.plugin.resultAnalysis;





import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sourceforge.jeuclid.swing.JMathComponent;

import org.bh.data.types.Calculable;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.platform.formula.IFormula;
/**
 * responsible for the visualisation of the formulas depending on selected dcf method
 * @version 1.0
 * @author Marco Hammel
 */
@SuppressWarnings("serial")
public class BHFormulaPanel extends JPanel {

    JComboBox box;
    IFormula f;
    JMathComponent formula;
    JMathComponent value;
    GridBagConstraints c;
    JLabel space;

    public enum Keys {
	PERIOD, FORMULA, VALUE;

	@Override
	public String toString() {
	    return getClass().getName() + "." + super.toString();
	}
    }

    public BHFormulaPanel(ActionListener al) {

    	space = new JLabel();
        this.setLayout(new GridBagLayout());
 		c = new GridBagConstraints();
 		
 	//Period textLabel
 	c.fill = GridBagConstraints.NONE;
 	c.ipadx = 80;
 	c.gridx = 0;
 	c.gridy = 0;
 	c.insets = new Insets(30,0,0,0);
 	c.anchor = GridBagConstraints.WEST;
	add(new BHDescriptionLabel(Keys.PERIOD), c);

	box = new JComboBox();
	box.addActionListener(al);

	//Period Box
 	c.gridx = 1;
 	c.gridy = 0;
 	c.insets = new Insets(30,40,0,0);
 	c.anchor = GridBagConstraints.WEST;
	add(box, c);
	
	//Forumla textLabel
	//c.fill = GridBagConstraints.NONE;
 	c.ipadx = 80;
 	c.gridx = 0;
 	c.gridy = 1;
 	c.insets = new Insets(30,0,0,0);
 	c.anchor = GridBagConstraints.WEST;
	add(new BHDescriptionLabel(Keys.FORMULA), c);
	
	//Value textLabel
	//c.fill = GridBagConstraints.HORIZONTAL;
 	c.ipadx = 80;
 	c.gridx = 0;
 	c.gridy = 2;
 	c.insets = new Insets(30,0,30,0);
 	c.anchor = GridBagConstraints.WEST;
	add(new BHDescriptionLabel(Keys.VALUE), c);
	
	
	
	c.gridx = 2;
 	c.gridy = 0;
 	c.weightx = 1.0;
 	add(space, c);
 	c.gridx = 2;
 	c.gridy = 1;
 	c.weightx = 1.0;
 	add(space, c);
 	c.gridx = 2;
 	c.gridy = 2;
 	c.weightx = 1.0;
 	add(space, c);
	
	
    }

    void setFormula(IFormula f) {
	this.f = f;
	if (formula != null) {
	    remove(formula);
	}
	formula = f.getJMathComponent();
	
	//Formula Label
	//c.fill = GridBagConstraints.HORIZONTAL;
	c.anchor = GridBagConstraints.WEST;
	//c.weighty = 1.0;
 	c.gridx = 1;
 	c.gridy = 1;
 	c.insets = new Insets(30,0,0,0);
 	
	//add(formula, "2,3,left,center");
 	add(formula, c);
    }

    void setValues(Map<String, Calculable> values) {
	if (value != null) {
	    remove(value);
	}
	value = f.getJMathComponentForInputValues(values);

	//Value Label
	//c.fill = GridBagConstraints.HORIZONTAL;
	//c.weighty = 1.0;
 	c.gridx = 1;
 	c.gridy = 2;
 	c.insets = new Insets(30,0,30,0);
 	c.anchor = GridBagConstraints.WEST;
	add(value, c);
	//add(value, "2,5,left,center");
    }

    void addEntry(String s) {
	box.addItem(s);
    }

    void setSelectedIndex(int index) {
	box.setSelectedIndex(index);
    }

}
