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
package org.bh.gui.swing.tree;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import org.bh.controller.InputController;
import org.bh.data.DTO;
import org.bh.data.DTOAccessException;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.StringValue;
/**
 * 
 * Special TreeNode for use in Business Horizon.
 * Necessary because of non existing toString() of DTOs. 
 * This class makes it possible that Nodes' (= DTOs) names can be changed in the tree.
 *
 * @author Alexander Schmalzhaf
 * @version 1.0, 30.12.2009
 *
 */
@SuppressWarnings("serial")
public class BHTreeNode extends DefaultMutableTreeNode{
	
	private InputController controller;
	private JScrollPane resultPane;
	private int dividerLocation;
	
	public BHTreeNode(Object ob){
		super(ob);
	}
	
	@Override
	public String toString(){
		if(this.getUserObject() instanceof DTOProject){
			try{
				return ((StringValue)((DTO<?>)userObject).get(DTOProject.Key.NAME)).getString();
			}catch(DTOAccessException e){
				return "";
			}
			
		}else if(this.getUserObject() instanceof DTOScenario){
			try{
				return ((StringValue)((DTO<?>)userObject).get(DTOScenario.Key.NAME)).getString();
			}catch(DTOAccessException e){
				return "";
			}
			
		}else if(this.getUserObject() instanceof DTOPeriod){
			try{
				return ((StringValue)((DTO<?>)userObject).get(DTOPeriod.Key.NAME)).getString();
			}catch(DTOAccessException e){
				return "";
			}
		}
		
		return "Type not known - must be implemented in BHTreeNode";
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
