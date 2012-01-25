package org.bh.plugin.gcc.branchspecific;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultTreeModel;

import org.bh.data.types.StringValue;
import org.bh.data.DTOBranch;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

/**
 * <short_description>
 * 
 * <p>
 * <detailed_description>
 * 
 * @author simon
 * @version 1.0, 18.01.2012
 * 
 */
public class BHBDTreePopup extends JPopupMenu {
	ITranslator translator;
	static BHBDTree bhbdTree;

	public enum Key {
		BranchAdd, BranchRemove, CompanyAdd, CompanyRemove, PeriodAdd, CompanyAddDesc, PeriodRemove;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}

	public enum Type {
		BRANCH, COMPANY, PERIOD;
	}

	public BHBDTreePopup(Type type, BHBDTree bhbdTree) {
		this.bhbdTree = bhbdTree;
		translator = BHTranslator.getInstance();
		if (type == Type.BRANCH) {

			JMenuItem brem = new JMenuItem(
					translator.translate(BHBDTreePopup.Key.BranchRemove));

			brem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {

					// REM NODE
					BHBusinessDataTreeNode bhdfwzgz = (BHBusinessDataTreeNode) BHBDTreePopup.bhbdTree
							.getSelectionPath().getLastPathComponent();
					BHBusinessDataTreeNode parentbranch = (BHBusinessDataTreeNode) bhdfwzgz
							.getParent();
					DTOBusinessData mybd = (DTOBusinessData) parentbranch
							.getUserObject();
					mybd.removeChild((DTOBranch) bhdfwzgz.getUserObject());
					DefaultTreeModel model = (DefaultTreeModel) BHBDTreePopup.bhbdTree
							.getModel();
					model.removeNodeFromParent(bhdfwzgz);

				}
			});

			this.add(brem);

		} else if (type == Type.COMPANY) {

			JMenuItem compadd = new JMenuItem(
					translator.translate(BHBDTreePopup.Key.CompanyAdd));

			compadd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {

					// NEW NODE
					String inputname = JOptionPane.showInputDialog(
							null,
							translator
									.translate(BHBDTreePopup.Key.CompanyAddDesc),
							translator.translate(BHBDTreePopup.Key.CompanyAdd),
							1);
					BHBusinessDataTreeNode bhdfwzgz = (BHBusinessDataTreeNode) BHBDTreePopup.bhbdTree
							.getSelectionPath().getLastPathComponent();
					BHBusinessDataTreeNode parentbranch = (BHBusinessDataTreeNode) bhdfwzgz
							.getParent();
					DTOBranch branch = (DTOBranch) parentbranch.getUserObject();

					DTOCompany newCompanyDTO = new DTOCompany();
					newCompanyDTO.put(DTOCompany.Key.NAME, new StringValue(
							inputname));
					branch.addChild(newCompanyDTO);
					DefaultTreeModel model = (DefaultTreeModel) BHBDTreePopup.bhbdTree
							.getModel();
					model.insertNodeInto(new BHBusinessDataTreeNode(
							newCompanyDTO), parentbranch, parentbranch
							.getChildCount());

				}
			});

			JMenuItem comprem = new JMenuItem(
					translator.translate(BHBDTreePopup.Key.CompanyRemove));

			comprem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {

					// REM NODE
					BHBusinessDataTreeNode bhdfwzgz = (BHBusinessDataTreeNode) BHBDTreePopup.bhbdTree
							.getSelectionPath().getLastPathComponent();
					BHBusinessDataTreeNode parentbranch = (BHBusinessDataTreeNode) bhdfwzgz
							.getParent();
					DTOBranch branch = (DTOBranch) parentbranch.getUserObject();
					branch.removeChild((DTOCompany) bhdfwzgz.getUserObject());
					DefaultTreeModel model = (DefaultTreeModel) BHBDTreePopup.bhbdTree
							.getModel();
					model.removeNodeFromParent(bhdfwzgz);

				}
			});

			this.add(compadd);
			this.add(comprem);

		} else if (type == Type.PERIOD) {
			this.add(new JMenuItem(translator
					.translate(BHBDTreePopup.Key.PeriodAdd)));
			
			
			
			
			JMenuItem prem = new JMenuItem(
					translator.translate(BHBDTreePopup.Key.PeriodRemove));

			prem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {

					// REM NODE
					BHBusinessDataTreeNode bhdfwzgz = (BHBusinessDataTreeNode) BHBDTreePopup.bhbdTree
							.getSelectionPath().getLastPathComponent();
					BHBusinessDataTreeNode parentbranch = (BHBusinessDataTreeNode) bhdfwzgz
							.getParent();
					DTOCompany mybd = (DTOCompany) parentbranch.getUserObject();
					mybd.removeChild((DTOPeriod) bhdfwzgz.getUserObject());
					DefaultTreeModel model = (DefaultTreeModel) BHBDTreePopup.bhbdTree.getModel();
					model.removeNodeFromParent(bhdfwzgz);

				}
			});

			this.add(prem);
		}
	}

}
