package org.bh.plugin.branchSpecificRepresentative.swing.maintainCompanyData;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.bh.data.DTOBranch;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.gui.IBHComponent;
import org.bh.gui.swing.BHContent;
import org.bh.gui.swing.BHMenuBar;
import org.bh.gui.swing.BHMenuItem;
import org.bh.gui.swing.BHPopupFrame;
import org.bh.gui.swing.comp.BHButton;
import org.bh.gui.swing.tree.BHTree;
import org.bh.gui.swing.tree.BHTreeTransferHandler;
import org.bh.platform.PlatformController;
import org.bh.platform.PlatformKey;
import org.bh.platform.Services;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.jfree.util.Log;

/**
 * Frame to maintain company data to calculate branch specific representative.
 * 
 * <p>
 * This class should be the base class for maintaining all the company data,
 * which is necessary to calculate the branch specific representative. This is
 * supposed to be the entry point to change and maintain company data.
 * 
 * @author Yannick Rödl
 * @version 1.0, 27.12.2011
 * 
 */
public class MaintainCompanyDataFrame extends BHPopupFrame implements
		ActionListener {
	JTree tree = null;
	JPanel content = null;
	JSplitPane paneV = null;
	private JScrollPane resultForm;
	public enum GUI_KEYS {
		TITLE;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}

	public enum MenuBar {
		MENU_EXTRAS;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}

	/**
	 * Generated <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -6127674860072304710L;

//	wurde in "Extras" eingepflegt
//	private MaintainCompanyBar menuBar;

	public MaintainCompanyDataFrame() {
		super();
		
		 Container desktop = getContentPane();
		 BHButton savebutton = new BHButton("speichern");
		 content = new BHContent();
		
	
		 
	//	XMLImport myImport = new XMLImport("src/org/bh/companydata/periods.xml");

			DTOBusinessData myDTO = PlatformController.getBusinessDataDTO();
			BHBusinessDataTreeNode root1 = new BHBusinessDataTreeNode(myDTO);
			for (DTOBranch branches : myDTO.getChildren()){
				BHBusinessDataTreeNode j = new BHBusinessDataTreeNode(branches);
				for (DTOCompany companies : branches.getChildren()){
					BHBusinessDataTreeNode g = new BHBusinessDataTreeNode(companies);
					j.add(g);
					for (DTOPeriod periods : companies.getChildren()){
						BHBusinessDataTreeNode h = new BHBusinessDataTreeNode(periods);
						g.add(h);
					}
				}
				root1.add(j);	
			}
			tree = new BHBDTree(root1);
			tree.setEditable(true);
			tree.setModel(new BHBDTreeModel(root1));
			tree.setRootVisible(false);
			//new BHBDTreeTransferHandler(tree, DnDConstants.ACTION_COPY_OR_MOVE);
			
			tree.addTreeSelectionListener(new TreeSelectionListener() {
			    public void valueChanged(TreeSelectionEvent e) {
			    	BHBusinessDataTreeNode node = (BHBusinessDataTreeNode)e.getPath().getLastPathComponent();
			    	System.out.println("You selected " + node);
			    /* if nothing is selected */ 
			        if (node.getUserObject() instanceof DTOBusinessData){
			        	content = new BHContent();
			        	paneV.setTopComponent(content);
			        	System.out.println("You have selected content");
			        	content.revalidate();
			        }else if (node.getUserObject() instanceof DTOBranch){
			        	content = new BHContent();
			        	paneV.setTopComponent(content);
			        	System.out.println("You have selected Branch");
			        	content.revalidate();
			        }else if (node.getUserObject() instanceof DTOCompany){
			        	System.out.println("You have selected Company");
			        	content = new BHContent();
			        	paneV.setTopComponent(content);
			        }else if (node.getUserObject() instanceof DTOPeriod){
//			        	BHPeriodFrame p = new BHPeriodFrame((DTOPeriod)node.getUserObject());
//			        	content = p.getFrame();
			        	//content = new GCCCombinedForm(new BHBalanceSheetForm(true), new BHPLSCostOfSalesForm(true));
			        	paneV.setTopComponent(content);


			       
			    }else{
			    	Log.debug("no valid class");
			    }}
			});
			
			 
		
JScrollPane bhTreeScroller = new JScrollPane(tree);
paneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, content, resultForm);
paneV.setOneTouchExpandable(true);
	
	// Create the horizontal split pane and put the treeBar and the content
	// in it.
JSplitPane paneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bhTreeScroller,
			paneV);

paneH.setOneTouchExpandable(true);


Services.initNumberFormats();	
		
	
		//b.addBusinessData((PlatformController.getInstance()).getBusinessDataDTO());
		bhTreeScroller.setMinimumSize(new Dimension(250+UIManager.getInt("JTree.minimumWidth"), bhTreeScroller.getMinimumSize().height));

		desktop.add(paneH, BorderLayout.CENTER);
		this.setSize(1120,720);
		//desktop.add(savebutton, BorderLayout.SOUTH);
	}

	@Override
	public void setAdditionalMenuEntriesInMainFrame(BHMenuBar menuBar) {
		ITranslator translator = BHTranslator.getInstance();

		JMenu extras = new JMenu(translator.translate(MaintainCompanyDataFrame.MenuBar.MENU_EXTRAS.toString()));
		extras.setMnemonic(translator.translate(MaintainCompanyDataFrame.MenuBar.MENU_EXTRAS.toString(),ITranslator.MNEMONIC).charAt(0));

		// All JMenuItems for BSR Company Data
		
		BHMenuItem maintainCompanyData = new BHMenuItem(PlatformKey.MAINTAIN_COMPANY_DATA, 0);
		maintainCompanyData.addActionListener(this);

		BHMenuItem exportCompanyData = new BHMenuItem(PlatformKey.EXPORT_COMPANY_DATA, 0);
		exportCompanyData.addActionListener(this);

		BHMenuItem importCompanyData = new BHMenuItem(
				PlatformKey.IMPORT_COMPANY_DATA, 0);
		importCompanyData.addActionListener(this);

		extras.add(maintainCompanyData);
		extras.addSeparator();
		extras.add(exportCompanyData);
		extras.add(importCompanyData);

		menuBar.add(extras);
	}

	public String getUniqueId() {
		return BHPopupFrame.ID.MAINTAIN_COMPANIES.toString();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		IBHComponent comp = (IBHComponent) arg0.getSource();
		if (comp.getKey().equals("MmaintainCompData")) {
			Logger.getLogger(MaintainCompanyDataFrame.class).info(
					"Popup should be loaded now.");
			this.setVisible(true);

		} else if (comp.getKey().equals("MexportCompanyData")) {
			BSRManualPersistance.saveBranches();

		} else if (comp.getKey().equals("MimportCompanyData")) {
			BSRManualPersistance.loadBranches();

		} else {
			System.out.println("Something definitly went wrong");
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		// TODO We have to do something with the data here.
	}

	/* Specified by interface/super class. */
	@Override
	public String getTitleKey() {
		return MaintainCompanyDataFrame.GUI_KEYS.TITLE.toString();
	}
}
