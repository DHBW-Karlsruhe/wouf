/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
 *
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
package org.bh.gui.swing.forms;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import org.bh.data.DTOProject;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;


/**
 * This class contains the form for ProjectHeadData
 * 
 * @author Anton Kharitonov
 * @version 0.2, 03.01.2010
 * 
 */

@SuppressWarnings("serial")
public final class BHProjectInputForm extends JPanel implements KeyListener{

	private BHDescriptionLabel lproject;
	private BHDescriptionLabel lprojectname;
	private BHDescriptionLabel lcomment;
	private BHTextField tfprojectname;
	private BHTextField tfcomment;

	ITranslator translator = Services.getTranslator();

	public BHProjectInputForm() {

		String rowDef = "4px:grow,p,4px,p,4px,p,4px:grow";
		String colDef = "4px:grow,right:pref,4px,150px:grow,4px:grow";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.getLprojectname(), cons.xywh(2, 4, 1, 1));
		this.add(this.getTfprojectname(), cons.xywh(4, 4, 1, 1));
		this.add(this.getLcomment(), cons.xywh(2, 6, 1, 1));
		this.add(this.getTfcomment(), cons.xywh(4, 6, 1, 1));
	}
	
	public BHDescriptionLabel getLproject() {
		if (lproject == null) {
			lproject = new BHDescriptionLabel("project");
		}
		return lproject;
	}

	public BHDescriptionLabel getLcomment() {
		if (lcomment == null) {
			lcomment = new BHDescriptionLabel(DTOProject.Key.COMMENT);
		}
		return lcomment;
	}
	
	public BHDescriptionLabel getLprojectname() {
		if (lprojectname == null) {
			lprojectname = new BHDescriptionLabel(DTOProject.Key.NAME);
			lprojectname.setVisible(true);
		}
		return lprojectname;
	}

	/**
	 * Getter method for component tfprojectname.
	 * 
	 * @return BHTextField
	 */
	public BHTextField getTfprojectname() {
		if (tfprojectname == null) {
			tfprojectname = new BHTextField(DTOProject.Key.NAME, false);
			tfprojectname.addKeyListener(this);
			ValidationRule[] rules = { VRMandatory.INSTANCE };
			tfprojectname.setValidationRules(rules);
		}
		return tfprojectname;
	}

	/**
	 * Getter method for component tfcomment.
	 * 
	 * @return BHTextField
	 */
	public BHTextField getTfcomment() {
		if (tfcomment == null) {
			tfcomment = new BHTextField(DTOProject.Key.COMMENT, false);
			tfcomment.addKeyListener(this);
		}
		return tfcomment;
	}
	
	/**
	 * 01.12.2011, D. Roster
	 * KeyEvents für das Erstellen eines Szenarios durch "ENTER-Taste": 
	 * Enter im Feld Projektname sprint zu Kommentarfeld,
	 * Enter im Feld Kommentar ruft die Methode für ein neues Szenario auf,
	 * indem ein Tastendruck auf "F4" simuliert wird.
	 */
	
	public void keyReleased(KeyEvent evt) {
	}

	public void keyTyped(KeyEvent evt) {
	}

	public void keyPressed(KeyEvent evt) {

		if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
			  if (evt.getSource() == tfprojectname){
				  tfcomment.requestFocusInWindow();

			  }
			  if (evt.getSource() == tfcomment){
				 Robot robot;
				try {
					robot = new Robot();
					robot.keyPress(KeyEvent.VK_F4);
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			  }
			}
	}
}
