package org.bh.plugin.gcc.branchspecific;

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

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.bh.data.DTO;
import org.bh.data.DTOBranch;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.data.types.StringValue;

/**
 * <short_description>
 *
 * model for the tree to show branch specific representatives
 * <detailed_description>
 *
 * @author simon
 * @version 1.0, 18.01.2012
 *
 */
public class BHBDTreeModel extends DefaultTreeModel {



	public BHBDTreeModel(TreeNode root) {
		super(root);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		BHBusinessDataTreeNode activeNode = (BHBusinessDataTreeNode) path.getLastPathComponent();
		DTO<?> tempDTO = (DTO<?>) activeNode.getUserObject();
		
		Object key = null;
		if (tempDTO instanceof DTOCompany) {
			key = DTOCompany.Key.NAME;
		} else if (tempDTO instanceof DTOBranch) {

	
		} else if (tempDTO instanceof DTOPeriod) {
			key = DTOPeriod.Key.NAME;
		}

		if (key != null) {
			tempDTO.put(key, new StringValue(newValue.toString()));
			if (activeNode.getController() != null) {
				activeNode.getController().loadToView(key);
			}
		}
	}
}