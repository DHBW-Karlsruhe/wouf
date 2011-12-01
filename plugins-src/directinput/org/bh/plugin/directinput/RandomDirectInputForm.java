/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber * : Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
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
package org.bh.plugin.directinput;
import javax.swing.JPanel;

import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHValueLabel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Visual class ViewPeriodData1.
 * 
 * 
 */
@SuppressWarnings("serial")
public class RandomDirectInputForm extends JPanel {
    private BHDescriptionLabel lfcf;
    private BHValueLabel tffcf;
    private BHDescriptionLabel lliabilities;
    private BHValueLabel tfliabilities;
    private BHDescriptionLabel lcurrency1;
    private BHDescriptionLabel lcurrency2;

    /**
     * Constructor.
     */
    public RandomDirectInputForm() {
	
	String rowDef = "4px,p,4px,p,4px";
	String colDef = "4px,right:pref,10px,right:pref,4px,p,4px";

	FormLayout layout = new FormLayout(colDef, rowDef);
	this.setLayout(layout);

	CellConstraints cons = new CellConstraints();

	this.add(this.getLfcf(), cons.xywh(2, 2, 1, 1));
	this.add(this.getTffcf(), cons.xywh(4, 2, 1, 1));
	this.add(this.getLcurrency1(), cons.xywh(6, 2, 1, 1));
	this.add(this.getLliabilities(), cons.xywh(2, 4, 1, 1));
	this.add(this.getTfliabilities(), cons.xywh(4, 4, 1, 1));
	this.add(this.getLcurrency2(), cons.xywh(6, 4, 1, 1));
    }


    public BHDescriptionLabel getLcurrency1() {
	if (lcurrency1 == null) {
	    lcurrency1 = new BHDescriptionLabel("currency");
	}
	return lcurrency1;
    }
    
    public BHDescriptionLabel getLcurrency2() {
	if (lcurrency2 == null) {
	    lcurrency2 = new BHDescriptionLabel("currency");
	}
	return lcurrency2;
    }

    public BHDescriptionLabel getLfcf() {
	if (lfcf == null) {
	    lfcf = new BHDescriptionLabel(DTODirectInput.Key.FCF);
	}
	return lfcf;
    }

    public BHValueLabel getTffcf() {
	if (tffcf == null) {
	    tffcf = new BHValueLabel(DTODirectInput.Key.FCF);
	}
	return tffcf;
    }

    public BHDescriptionLabel getLliabilities() {
	if (lliabilities == null) {
	    lliabilities = new BHDescriptionLabel(DTODirectInput.Key.LIABILITIES);
	}
	return lliabilities;
    }

    public BHValueLabel getTfliabilities() {
	if (tfliabilities == null) {
	    tfliabilities = new BHValueLabel(DTODirectInput.Key.LIABILITIES);
	}
	return tfliabilities;
    }
    
//    public static void main(String args[]) {
//
//    	JFrame test = new JFrame("Test for ViewPeriodData1");
//    	test.setContentPane(new RandomDirectInputForm());
//    	test.addWindowListener(new WindowAdapter() {
//    	    @Override
//    		public void windowClosing(WindowEvent e) {
//    		System.exit(0);
//    	    }
//    	});
//    	test.pack();
//    	test.show();
//        }
}
