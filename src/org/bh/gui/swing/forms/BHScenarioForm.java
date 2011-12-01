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

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.apache.log4j.Logger;
import org.bh.gui.swing.comp.BHButton;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHProgressBar;
import org.bh.gui.swing.forms.border.BHBorderFactory;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form as an container for scenario forms.
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
@SuppressWarnings("serial")
public final class BHScenarioForm extends JPanel {
	
	public enum Type{
		/**
		 * 
		 */
		STOCHASTIC,
		
		/**
		 * 
		 */
		DETERMINISTIC;
	}
	
	public enum Key {
		/**
		 * 
		 */
		SCENARIO_HEADDATA,
		
		/**
		 * 
		 */
		PROCESS_DATA,
		
		/**
		 * 
		 */
		CANNOT_CALCULATE_HINT,
		
		/**
		 * 
		 */
		CALCULATING_IMAGE,
		
		/**
		 * 
		 */
		CALCSHAREHOLDERVALUE,
		
		/**
		 * 
		 */
		CALCDASHBOARD,
		
		/**
		 * Progressbar for time series calculation
		 */
		PROGRESSBAR,
		/**
		 * Abort-button for calculation
		 */
		ABORT;
		
		
		@Override
		public String toString() {
		    return getClass().getName() + "." + super.toString();
		}
	}
	
	private JPanel pscenario;
	private JPanel pprocess;
	private BHButton bcalculate;
	private BHDescriptionLabel lCannotCalculateHint;
	private BHDescriptionLabel lCalculatingImage;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private BHProgressBar progressB;


	ITranslator translator = Services.getTranslator();
	CellConstraints cons;

	/**
	 * Constructor
	 */
	public BHScenarioForm(BHScenarioForm.Type type, boolean isIntervalArithmethic) {
		super(new FormLayout("4px,pref:grow,4px", "4px,fill:0px:grow,4px,pref,4px"));
		this.initialize(type, isIntervalArithmethic);
	}

	/**
	 * Initialize method.
	 */
	private void initialize(BHScenarioForm.Type type, boolean isIntervalArithmethic) {
		
		/*
		 * Structure of ScenarioForm:
		 * The scenario form consists of 2 areas: 
		 * a big one top which shows all fields and a smaller one bottom
		 * which contains A) the button to start calculations and B) a short Info-Bar 
		 * with shortview of calculated UW with a button to start "slider view" (which holds
		 * all analysis information).
		 */
		
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		
		/*
		 * build topPanel
		 */
		String colDef = "4px,pref:grow,4px";
		String rowDef = "4px,p,14px,p,4px";
		FormLayout topLayout = new FormLayout(colDef, rowDef);
		topPanel.setLayout(topLayout);
		cons = new CellConstraints();
		
		setHeadPanel(isIntervalArithmethic);
		topPanel.add(this.getPprocess(type), cons.xywh(2, 4, 1, 1));
		JScrollPane scrollPane = new JScrollPane(topPanel);
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		//add topPanel to ScenarioForm
		this.add(scrollPane, cons.xywh(2, 2, 1, 1));

		
		
		/*
		 * build bottomPanel
		 */
		colDef = "4px,4px:grow,4px,min,4px,right:pref,4px";
		rowDef = "4px,p,4px,p,4px";
		
		FormLayout bottomLayout = new FormLayout(colDef, rowDef);
		bottomPanel.setLayout(bottomLayout);

		bottomPanel.add(this.getCannotCalculateHint(), cons.xywh(2, 2, 1, 1));
		bottomPanel.add(this.getCalculatingImage(), cons.xywh(4, 2, 1, 1));
		bottomPanel.add(this.getBcalculate(), cons.xywh(6, 2, 1, 1));
		progressB = new BHProgressBar(Key.PROGRESSBAR, 0, 100);
		bottomPanel.add(progressB, cons.xywh(2, 4, 5, 1));
		progressB.setVisible(false);
		this.add(bottomPanel, cons.xywh(2, 4, 1, 1));
		
	}

	
	public void setHeadPanel(boolean isIntervalArithmethic) {
		if (pscenario != null)
			topPanel.remove(pscenario);
		
		if (!isIntervalArithmethic)
			pscenario = new BHScenarioHeadForm();
		else
			pscenario = new BHScenarioHeadIntervalForm();
		
		pscenario.setBorder(BHBorderFactory.getInstacnce()
				.createTitledBorder(BHBorderFactory.getInstacnce().createEtchedBorder(EtchedBorder.LOWERED),BHScenarioForm.Key.SCENARIO_HEADDATA));
		
		CellConstraints cons = new CellConstraints();
		topPanel.add(pscenario, cons.xywh(2, 2, 1, 1));
		this.revalidate();
	}

	private JPanel getPprocess(BHScenarioForm.Type type) {
		switch (type){
		case STOCHASTIC:
			pprocess = new BHStochasticProcessForm();
			Logger.getLogger(BHScenarioForm.class).debug("--StochasticProcessForm is chosen");
			break;
			
		case DETERMINISTIC:
			pprocess = new BHDeterministicProcessForm();
			Logger.getLogger(BHScenarioForm.class).debug("--DeterministicProcessForm is chosen");
			break;
			
		default:
			pprocess = new BHDeterministicProcessForm();
		}

		pprocess.setBorder(BHBorderFactory.getInstacnce()
				.createTitledBorder(BHBorderFactory.getInstacnce().createEtchedBorder(EtchedBorder.LOWERED),BHScenarioForm.Key.PROCESS_DATA));
		return pprocess;
	}
	
	public JPanel getProcessForm(){
		return pprocess;
	}
	
	public JPanel getScenarioHeadForm(){
		return pscenario;
	}

	public BHButton getBcalculate() {
		if (this.bcalculate == null) {
			this.bcalculate = new BHButton(BHScenarioForm.Key.CALCSHAREHOLDERVALUE);
		}
		return bcalculate;
	}
	
	public BHDescriptionLabel getCannotCalculateHint() {
		if (lCannotCalculateHint == null) {
			lCannotCalculateHint = new BHDescriptionLabel(Key.CANNOT_CALCULATE_HINT);
			lCannotCalculateHint.setVisible(false);
		}
		return lCannotCalculateHint;
	}
	
	public BHDescriptionLabel getCalculatingImage() {
		if (lCalculatingImage == null) {
			lCalculatingImage = new BHDescriptionLabel(Key.CALCULATING_IMAGE);
		}
		return lCalculatingImage;
	}
	
	public void addProgressBar(){		
		progressB.setVisible(true);
	}
}
