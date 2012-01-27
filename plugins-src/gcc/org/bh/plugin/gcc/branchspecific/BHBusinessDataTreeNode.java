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
package org.bh.plugin.gcc.branchspecific;


import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import org.bh.controller.InputController;
import org.bh.data.DTO;
import org.bh.data.DTOAccessException;
import org.bh.data.DTOBranch;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.StringValue;
import org.bh.platform.i18n.BHTranslator;
/**
 * 
 * Special TreeNode for use in Business Horizon.
 * Necessary because of non existing toString() of DTOs. 
 * This class makes it possible that Nodes' (= DTOs) names can be changed in the tree.
 *
 * @author Simon Weber
 * @version 1.0, 16.02.2012
 *
 */
@SuppressWarnings("serial")
public class BHBusinessDataTreeNode extends DefaultMutableTreeNode{
	
	private InputController controller;
	private JScrollPane resultPane;
	private int dividerLocation;
	
	public BHBusinessDataTreeNode(Object dto){
		super(dto);
	}
	
	@Override
	public String toString(){
		if(this.getUserObject() instanceof DTOBusinessData){
			try{
				return "Businessdata";
			}catch(DTOAccessException e){
				return "";
			}
			
		}else if(this.getUserObject() instanceof DTOPeriod){
			try{
				return ((StringValue)((DTO<?>)userObject).get(DTOPeriod.Key.NAME)).getString();
			}catch(DTOAccessException e){
				return "";
			}
		}else if(this.getUserObject() instanceof DTOBranch){
			try{
				String main = ""+((StringValue)((DTO<?>)userObject).get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY)).getString();
				String mid = ""+((StringValue)((DTO<?>)userObject).get(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY)).getString();
				String sub = ""+((StringValue)((DTO<?>)userObject).get(DTOBranch.Key.BRANCH_KEY_SUB_CATEGORY)).getString();
				String nace = main+"."+mid+"."+sub;
				
				String l = BHTranslator.getInstance().translate(nace);
				
				return l;
			}catch(DTOAccessException e){
				return "";
			}
			
		}else if(this.getUserObject() instanceof DTOCompany){
			try{
				return ((StringValue)((DTO<?>)userObject).get(DTOCompany.Key.NAME)).getString();
			}catch(DTOAccessException e){
				return "";
			}
		
	}
		
		return "Type not known - must be implemented in BHBusinessDataTreeNode";
	}
	
	public void setResultPane(JScrollPane resultPane){
		this.resultPane = resultPane;
	}
	
	public JScrollPane getResultPane(){
		return this.resultPane;
	}
	
	public void setController(InputController controller){
		this.controller = controller;
	}
	
	public InputController getController(){
		return controller;
	}
	
	public void setDividerLocation(int dividerLocation){
		this.dividerLocation = dividerLocation;
	}
	
	public int getDividerLocation(){
		return this.dividerLocation;
	}
}

