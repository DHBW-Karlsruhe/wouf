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
package org.bh.tests.apps;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import net.sourceforge.jeuclid.swing.JMathComponent;

import org.bh.data.types.Calculable;
import org.bh.platform.formula.IFormula;
import org.bh.platform.formula.IFormulaFactory;

@SuppressWarnings("serial")
public class FormulaParserTestApp extends JFrame {
    JTextArea txt;
    JTextArea input;
    JPanel jpFormula;
    JPanel jpInput;
    JMathComponent mathComponent;
    JMathComponent mathCompForValues;
    JButton button;
    JLabel result;

    public static void main(String[] args) {
	JFrame t2 = new FormulaParserTestApp();
	t2.setTitle("Formel Demo");
	t2.setVisible(true);
    }

    public FormulaParserTestApp() {
	setSize(1024, 768);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setLayout(new GridLayout(0, 1));

	jpFormula = new JPanel();
	jpFormula.setLayout(new GridLayout(1,2));
	add(jpFormula);

	
	mathComponent = new JMathComponent();
	// mathComponent.setParameter(Parameter.MFRAC_KEEP_SCRIPTLEVEL, true);
	jpFormula.add(mathComponent);
	
	mathCompForValues = new JMathComponent();
	jpFormula.add(mathCompForValues);
	
	

	jpInput = new JPanel();
	jpInput.setLayout(new GridLayout(2, 2));
	add(jpInput);
	jpInput.add(new JLabel("Content MathML:"));
	jpInput.add(new JLabel("Eingabewerte( <Bezeichner>;<Wert>):"));
	
	txt = new JTextArea();
	txt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	jpInput.add( new JScrollPane(txt));
	input = new JTextArea();
	input.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	
	jpInput.add(new JScrollPane(input));

	button = new JButton("anzeigen");
	button.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    IFormula test = IFormulaFactory.instance.createFormula(
			    "test", txt.getText(),false);

		    Map<String, org.bh.data.types.Calculable> values = new HashMap<String, Calculable>();
		    String[] inputText = input.getText().split("\n");
		    for (String s : inputText) {
			String[] ssplit = s.split(";");
			if (ssplit.length != 2) {
			    continue;
			}
			values.put(ssplit[0], org.bh.data.types.Calculable
				.parseCalculable((ssplit[1])));
		    }
		 //   test.setFormulaToJMathComponent(mathComponent);
		    test.setInputValuesToJMathComponent(values, mathCompForValues);
		  
		    //Calaculation
//		    long start = System.nanoTime();
//		    Calculable erg = test.determineLeftHandSideValue(values);
//		    long end = System.nanoTime();
		 
//		    result.setText("Ergebnis: " + erg + " ; Berechnungsdauer: " + (end - start) / (double) 1000000 + "ms");
		} catch (Exception e1) {
		    e1.printStackTrace();
		}
	    }
	});
	add(button);
	result = new JLabel();

	result.setFont(new Font(result.getFont().getFontName(), result
		.getFont().getStyle(), 25));
	result.setHorizontalAlignment(SwingConstants.CENTER);

	add(result);
    }
}
