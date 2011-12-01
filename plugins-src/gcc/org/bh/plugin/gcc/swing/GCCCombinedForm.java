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
package org.bh.plugin.gcc.swing;

import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.bh.gui.swing.comp.BHButton;

import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("serial")
public class GCCCombinedForm extends JPanel {
	private JPanel pbuttons;
	private BHButton btnExport;
	private BHButton btnImport;
	public GCCCombinedForm(JPanel balanceSheetForm, JPanel plsForm) {
		setLayout(new FormLayout( "4px,p,4px,p,4px", "p,p,p"));
		add(this.getPbuttos(),"2,1,2,1,fill,default");
		add(balanceSheetForm, "2,2,2,1,left,default");
		add(plsForm, "2,3,1,1,left,default");
		
	}
	
	public JPanel getPbuttos(){
		if (pbuttons == null){
			pbuttons = new JPanel();
			pbuttons.setLayout(new FormLayout("4px,p,4px,p,4px", "p,4px,p,4px"));
			btnExport = new BHButton("Bbsexport");
			btnImport = new BHButton("Bbsimport");
			
			pbuttons.add(btnImport, "2,1,1,1,right,bottom");
			pbuttons.add(btnExport, "4,1,1,1,right,bottom");
			pbuttons.add(new JSeparator(), "2,3,4,1");
		}
		return pbuttons;
	}

	public BHButton getBtnExport() {
		return btnExport;
	}

	public BHButton getBtnImport() {
		return btnImport;
	}	
}
