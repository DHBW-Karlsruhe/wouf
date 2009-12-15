import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Visual class ViewStochasticData1.
 *
 * Created with Mogwai FormMaker 0.6.
 */
public class ViewStochasticData1 extends JPanel {

	private javax.swing.JLabel m_lscenname;
	private javax.swing.JLabel m_lscendescript;
	private javax.swing.JLabel m_lequityyield;
	private javax.swing.JLabel m_ldeptyield;
	private javax.swing.JLabel m_ltradetax;
	private javax.swing.JLabel m_lcorporatetax;
	private javax.swing.JLabel m_lbaseyear;
	private javax.swing.JTextField m_tfscenname;
	private javax.swing.JTextField m_tfscendescript;
	private javax.swing.JTextField m_tfequityyeild;
	private javax.swing.JTextField m_tfdeptyield;
	private javax.swing.JTextField m_tftradetax;
	private javax.swing.JTextField m_tfcorporatetax;
	private javax.swing.JTextField m_tfbaseyear;
	private javax.swing.JLabel m_lpercentequity;
	private javax.swing.JLabel m_lpercentdept;
	private javax.swing.JLabel m_lpercenttrade;
	private javax.swing.JLabel m_lpercentcorporate;
	private javax.swing.JLabel m_ldcfchoise;
	private javax.swing.JComboBox m_cbdcfchoise;
	private javax.swing.JLabel m_lstochprocess;
	private javax.swing.JRadioButton m_rbrandomwalk;
	private javax.swing.JRadioButton m_rbwienerprocess;
	private javax.swing.JLabel m_lrandomwalk;
	private javax.swing.JLabel m_lwienerprocess;
	private javax.swing.JLabel m_lrange;
	private javax.swing.JTextField m_tfrange;
	private javax.swing.JLabel m_lprobab;
	private javax.swing.JTextField m_wahrscheinlichkeit;
	private javax.swing.JLabel m_lpercentprobab;
	private javax.swing.JCheckBox m_chbcalculatequest;
	private javax.swing.JLabel m_component_61;

	/**
	 * Constructor.
	 */
	public ViewStochasticData1() {
		this.initialize();
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {

		String rowDef="2dlu,p,2dlu,p,2dlu,p,2dlu,p,4dlu,p,2dlu,p,2dlu,p,20dlu,p,2dlu,p,12dlu,p,2dlu,p,2dlu";
		String colDef="2dlu,2dlu,left:pref,pref,2dlu,max(40dlu;min),2dlu,left:5dlu,12dlu:grow,pref,2dlu,pref,2dlu,pref,2dlu,pref:grow,2dlu,pref,2dlu,pref,2dlu";

		FormLayout layout=new FormLayout(colDef,rowDef);
		this.setLayout(layout);

		CellConstraints cons=new CellConstraints();

		this.add(this.getlscenName(),cons.xywh(3,4,1,1));
		this.add(this.getlscenDescript(),cons.xywh(3,6,1,1));
		this.add(this.getlequityYield(),cons.xywh(3,10,1,1));
		this.add(this.getldeptYield(),cons.xywh(3,12,1,1));
		this.add(this.getltradeTax(),cons.xywh(12,10,3,1));
		this.add(this.getlcorporateTax(),cons.xywh(12,12,3,1));
		this.add(this.getlbaseYear(),cons.xywh(3,8,1,1));
		this.add(this.gettfscenName(),cons.xywh(6,4,4,1));
		this.add(this.gettfscenDescript(),cons.xywh(6,6,13,1));
		this.add(this.gettfequityYeild(),cons.xywh(6,10,1,1));
		this.add(this.gettfdeptYield(),cons.xywh(6,12,1,1));
		this.add(this.gettftradeTax(),cons.xywh(16,10,1,1));
		this.add(this.gettfcorporateTax(),cons.xywh(16,12,1,1));
		this.add(this.gettfbaseYear(),cons.xywh(6,8,1,1));
		this.add(this.getlpercentEquity(),cons.xywh(8,10,1,1));
		this.add(this.getlpercentDept(),cons.xywh(8,12,1,1));
		this.add(this.getlpercentTrade(),cons.xywh(18,10,1,1));
		this.add(this.getlpercentCorporate(),cons.xywh(18,12,1,1));
		this.add(DefaultComponentFactory.getInstance().createSeparator("null"),cons.xywh(3,14,10,1));
		this.add(this.getlDCFchoise(),cons.xywh(3,16,1,1));
		this.add(this.getcbDCFchoise(),cons.xywh(6,16,3,1));
		this.add(this.getlstochProcess(),cons.xywh(12,16,1,1));
		this.add(this.getrbRandomWalk(),cons.xywh(16,16,1,1));
		this.add(this.getrbwienerProcess(),cons.xywh(16,18,1,1));
		this.add(this.getlrandomWalk(),cons.xywh(14,16,1,1));
		this.add(this.getlwienerProcess(),cons.xywh(14,18,1,1));
		this.add(this.getlrange(),cons.xywh(3,20,1,1));
		this.add(this.gettfrange(),cons.xywh(6,20,1,1));
		this.add(this.getlprobab(),cons.xywh(3,22,1,1));
		this.add(this.getWahrscheinlichkeit(),cons.xywh(6,22,1,1));
		this.add(this.getlpercentProbab(),cons.xywh(8,22,1,1));
		this.add(this.getchbcalculateQuest(),cons.xywh(16,22,1,1));
		this.add(this.getComponent_61(),cons.xywh(14,22,1,1));

		this.buildGroups();
	}

	/**
	 * Getter method for component lscenName.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlscenName() {

		if (this.m_lscenname==null) {
			this.m_lscenname=new javax.swing.JLabel();
			this.m_lscenname.setName("lscenName");
			this.m_lscenname.setText("Szenarioname");
		}

		return this.m_lscenname;
	}

	/**
	 * Getter method for component lscenDescript.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlscenDescript() {

		if (this.m_lscendescript==null) {
			this.m_lscendescript=new javax.swing.JLabel();
			this.m_lscendescript.setName("lscenDescript");
			this.m_lscendescript.setText("Beschreibung");
		}

		return this.m_lscendescript;
	}

	/**
	 * Getter method for component lequityYield.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlequityYield() {

		if (this.m_lequityyield==null) {
			this.m_lequityyield=new javax.swing.JLabel();
			this.m_lequityyield.setName("lequityYield");
			this.m_lequityyield.setText("Renditeforderung Eigenkapital");
		}

		return this.m_lequityyield;
	}

	/**
	 * Getter method for component ldeptYield.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getldeptYield() {

		if (this.m_ldeptyield==null) {
			this.m_ldeptyield=new javax.swing.JLabel();
			this.m_ldeptyield.setName("ldeptYield");
			this.m_ldeptyield.setText("Renditeforderung Fremdkapital");
		}

		return this.m_ldeptyield;
	}

	/**
	 * Getter method for component ltradeTax.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getltradeTax() {

		if (this.m_ltradetax==null) {
			this.m_ltradetax=new javax.swing.JLabel();
			this.m_ltradetax.setName("ltradeTax");
			this.m_ltradetax.setText("Gewerbesteuersatz");
		}

		return this.m_ltradetax;
	}

	/**
	 * Getter method for component lcorporateTax.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlcorporateTax() {

		if (this.m_lcorporatetax==null) {
			this.m_lcorporatetax=new javax.swing.JLabel();
			this.m_lcorporatetax.setName("lcorporateTax");
			this.m_lcorporatetax.setText("Körperschaftssteuer und Solidaritätszuschlag");
		}

		return this.m_lcorporatetax;
	}

	/**
	 * Getter method for component lbaseYear.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlbaseYear() {

		if (this.m_lbaseyear==null) {
			this.m_lbaseyear=new javax.swing.JLabel();
			this.m_lbaseyear.setName("lbaseYear");
			this.m_lbaseyear.setText("Basisjahr");
		}

		return this.m_lbaseyear;
	}

	/**
	 * Getter method for component tfscenName.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JTextField gettfscenName() {

		if (this.m_tfscenname==null) {
			this.m_tfscenname=new javax.swing.JTextField();
			this.m_tfscenname.setName("tfscenName");
			this.m_tfscenname.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handletfscenNameActionPerformed(e.getActionCommand());
				}
			});
		}

		return this.m_tfscenname;
	}

	/**
	 * Getter method for component tfscenDescript.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JTextField gettfscenDescript() {

		if (this.m_tfscendescript==null) {
			this.m_tfscendescript=new javax.swing.JTextField();
			this.m_tfscendescript.setName("tfscenDescript");
			this.m_tfscendescript.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handletfscenDescriptActionPerformed(e.getActionCommand());
				}
			});
		}

		return this.m_tfscendescript;
	}

	/**
	 * Getter method for component tfequityYeild.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JTextField gettfequityYeild() {

		if (this.m_tfequityyeild==null) {
			this.m_tfequityyeild=new javax.swing.JTextField();
			this.m_tfequityyeild.setName("tfequityYeild");
			this.m_tfequityyeild.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handletfequityYeildActionPerformed(e.getActionCommand());
				}
			});
		}

		return this.m_tfequityyeild;
	}

	/**
	 * Getter method for component tfdeptYield.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JTextField gettfdeptYield() {

		if (this.m_tfdeptyield==null) {
			this.m_tfdeptyield=new javax.swing.JTextField();
			this.m_tfdeptyield.setName("tfdeptYield");
			this.m_tfdeptyield.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handletfdeptYieldActionPerformed(e.getActionCommand());
				}
			});
		}

		return this.m_tfdeptyield;
	}

	/**
	 * Getter method for component tftradeTax.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JTextField gettftradeTax() {

		if (this.m_tftradetax==null) {
			this.m_tftradetax=new javax.swing.JTextField();
			this.m_tftradetax.setName("tftradeTax");
			this.m_tftradetax.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handletftradeTaxActionPerformed(e.getActionCommand());
				}
			});
		}

		return this.m_tftradetax;
	}

	/**
	 * Getter method for component tfcorporateTax.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JTextField gettfcorporateTax() {

		if (this.m_tfcorporatetax==null) {
			this.m_tfcorporatetax=new javax.swing.JTextField();
			this.m_tfcorporatetax.setName("tfcorporateTax");
			this.m_tfcorporatetax.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handletfcorporateTaxActionPerformed(e.getActionCommand());
				}
			});
		}

		return this.m_tfcorporatetax;
	}

	/**
	 * Getter method for component tfbaseYear.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JTextField gettfbaseYear() {

		if (this.m_tfbaseyear==null) {
			this.m_tfbaseyear=new javax.swing.JTextField();
			this.m_tfbaseyear.setName("tfbaseYear");
			this.m_tfbaseyear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handletfbaseYearActionPerformed(e.getActionCommand());
				}
			});
		}

		return this.m_tfbaseyear;
	}

	/**
	 * Getter method for component lpercentEquity.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlpercentEquity() {

		if (this.m_lpercentequity==null) {
			this.m_lpercentequity=new javax.swing.JLabel();
			this.m_lpercentequity.setName("lpercentEquity");
			this.m_lpercentequity.setText("%");
		}

		return this.m_lpercentequity;
	}

	/**
	 * Getter method for component lpercentDept.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlpercentDept() {

		if (this.m_lpercentdept==null) {
			this.m_lpercentdept=new javax.swing.JLabel();
			this.m_lpercentdept.setName("lpercentDept");
			this.m_lpercentdept.setText("%");
		}

		return this.m_lpercentdept;
	}

	/**
	 * Getter method for component lpercentTrade.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlpercentTrade() {

		if (this.m_lpercenttrade==null) {
			this.m_lpercenttrade=new javax.swing.JLabel();
			this.m_lpercenttrade.setName("lpercentTrade");
			this.m_lpercenttrade.setText("%");
		}

		return this.m_lpercenttrade;
	}

	/**
	 * Getter method for component lpercentCorporate.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlpercentCorporate() {

		if (this.m_lpercentcorporate==null) {
			this.m_lpercentcorporate=new javax.swing.JLabel();
			this.m_lpercentcorporate.setName("lpercentCorporate");
			this.m_lpercentcorporate.setText("%");
		}

		return this.m_lpercentcorporate;
	}

	/**
	 * Getter method for component lDCFchoise.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlDCFchoise() {

		if (this.m_ldcfchoise==null) {
			this.m_ldcfchoise=new javax.swing.JLabel();
			this.m_ldcfchoise.setName("lDCFchoise");
			this.m_ldcfchoise.setText("Discounted Cashflow Verfahren");
		}

		return this.m_ldcfchoise;
	}

	/**
	 * Getter method for component cbDCFchoise.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JComboBox getcbDCFchoise() {

		if (this.m_cbdcfchoise==null) {
			this.m_cbdcfchoise=new javax.swing.JComboBox();
			this.m_cbdcfchoise.setName("cbDCFchoise");
			this.m_cbdcfchoise.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handlecbDCFchoiseActionPerformed(e.getActionCommand());
				}
			});
		}

		return this.m_cbdcfchoise;
	}

	/**
	 * Getter method for component lstochProcess.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlstochProcess() {

		if (this.m_lstochprocess==null) {
			this.m_lstochprocess=new javax.swing.JLabel();
			this.m_lstochprocess.setName("lstochProcess");
			this.m_lstochprocess.setText("Sochastischer Prozess:");
		}

		return this.m_lstochprocess;
	}

	/**
	 * Getter method for component rbRandomWalk.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JRadioButton getrbRandomWalk() {

		if (this.m_rbrandomwalk==null) {
			this.m_rbrandomwalk=new javax.swing.JRadioButton();
			this.m_rbrandomwalk.setName("rbRandomWalk#Group1!rw");
			this.m_rbrandomwalk.setSelected(true);
			this.m_rbrandomwalk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handlerbRandomWalkActionPerformed(e.getActionCommand());
				}
			});
			this.m_rbrandomwalk.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					handlerbRandomWalkStateChanged();
				}
			});
		}

		return this.m_rbrandomwalk;
	}

	/**
	 * Getter method for component rbwienerProcess.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JRadioButton getrbwienerProcess() {

		if (this.m_rbwienerprocess==null) {
			this.m_rbwienerprocess=new javax.swing.JRadioButton();
			this.m_rbwienerprocess.setName("rbwienerProcess#Group1!wp");
			this.m_rbwienerprocess.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handlerbwienerProcessActionPerformed(e.getActionCommand());
				}
			});
			this.m_rbwienerprocess.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					handlerbwienerProcessStateChanged();
				}
			});
		}

		return this.m_rbwienerprocess;
	}

	/**
	 * Getter method for component lrandomWalk.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlrandomWalk() {

		if (this.m_lrandomwalk==null) {
			this.m_lrandomwalk=new javax.swing.JLabel();
			this.m_lrandomwalk.setName("lrandomWalk");
			this.m_lrandomwalk.setText("Random Walk");
		}

		return this.m_lrandomwalk;
	}

	/**
	 * Getter method for component lwienerProcess.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlwienerProcess() {

		if (this.m_lwienerprocess==null) {
			this.m_lwienerprocess=new javax.swing.JLabel();
			this.m_lwienerprocess.setName("lwienerProcess");
			this.m_lwienerprocess.setText("Wiener Prozess");
		}

		return this.m_lwienerprocess;
	}

	/**
	 * Getter method for component lrange.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlrange() {

		if (this.m_lrange==null) {
			this.m_lrange=new javax.swing.JLabel();
			this.m_lrange.setName("lrange");
			this.m_lrange.setText("Schrittweite");
		}

		return this.m_lrange;
	}

	/**
	 * Getter method for component tfrange.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JTextField gettfrange() {

		if (this.m_tfrange==null) {
			this.m_tfrange=new javax.swing.JTextField();
			this.m_tfrange.setName("tfrange");
			this.m_tfrange.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handletfrangeActionPerformed(e.getActionCommand());
				}
			});
		}

		return this.m_tfrange;
	}

	/**
	 * Getter method for component lprobab.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlprobab() {

		if (this.m_lprobab==null) {
			this.m_lprobab=new javax.swing.JLabel();
			this.m_lprobab.setName("lprobab");
			this.m_lprobab.setText("Wahrscheinlichkeit");
		}

		return this.m_lprobab;
	}

	/**
	 * Getter method for component Wahrscheinlichkeit.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JTextField getWahrscheinlichkeit() {

		if (this.m_wahrscheinlichkeit==null) {
			this.m_wahrscheinlichkeit=new javax.swing.JTextField();
			this.m_wahrscheinlichkeit.setName("Wahrscheinlichkeit");
			this.m_wahrscheinlichkeit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handleWahrscheinlichkeitActionPerformed(e.getActionCommand());
				}
			});
		}

		return this.m_wahrscheinlichkeit;
	}

	/**
	 * Getter method for component lpercentProbab.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getlpercentProbab() {

		if (this.m_lpercentprobab==null) {
			this.m_lpercentprobab=new javax.swing.JLabel();
			this.m_lpercentprobab.setName("lpercentProbab");
			this.m_lpercentprobab.setText("%");
		}

		return this.m_lpercentprobab;
	}

	/**
	 * Getter method for component chbcalculateQuest.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JCheckBox getchbcalculateQuest() {

		if (this.m_chbcalculatequest==null) {
			this.m_chbcalculatequest=new javax.swing.JCheckBox();
			this.m_chbcalculatequest.setName("chbcalculateQuest");
			this.m_chbcalculatequest.setSelected(true);
			this.m_chbcalculatequest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handlechbcalculateQuestActionPerformed(e.getActionCommand());
				}
			});
			this.m_chbcalculatequest.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					handlechbcalculateQuestStateChanged();
				}
			});
		}

		return this.m_chbcalculatequest;
	}

	/**
	 * Getter method for component Component_61.
	 *
	 * @return the initialized component
	 */
	public javax.swing.JLabel getComponent_61() {

		if (this.m_component_61==null) {
			this.m_component_61=new javax.swing.JLabel();
			this.m_component_61.setName("Component_61");
			this.m_component_61.setText("Werte berechnen");
		}

		return this.m_component_61;
	}

	/**
	 * Initialize method.
	 */
	private void buildGroups() {

		ButtonGroup Group1=new ButtonGroup();
		Group1.add(this.getrbRandomWalk());
		Group1.add(this.getrbwienerProcess());
	}

	/**
	 * Getter for the group value for group Group1.
	 *
	 * @return the value for the current selected item in the group or null if nothing was selected
	 */
	public String getGroup1Value() {

		if (this.getrbRandomWalk().isSelected()) return "rw";
		if (this.getrbwienerProcess().isSelected()) return "wp";
		return null;
	}

	/**
	 * Setter for the group value for group Group1.
	 *
	 * @param the value for the current selected item in the group or null if nothing is selected
	 */
	public void setGroup1Value(String value) {

		this.getrbRandomWalk().setSelected("rw".equals(value));
		this.getrbwienerProcess().setSelected("wp".equals(value));
	}

	/**
     * Action listener implementation for tfscenName.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handletfscenNameActionPerformed(String actionCommand) {
	}

	/**
     * Action listener implementation for tfscenDescript.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handletfscenDescriptActionPerformed(String actionCommand) {
	}

	/**
     * Action listener implementation for tfequityYeild.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handletfequityYeildActionPerformed(String actionCommand) {
	}

	/**
     * Action listener implementation for tfdeptYield.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handletfdeptYieldActionPerformed(String actionCommand) {
	}

	/**
     * Action listener implementation for tftradeTax.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handletftradeTaxActionPerformed(String actionCommand) {
	}

	/**
     * Action listener implementation for tfcorporateTax.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handletfcorporateTaxActionPerformed(String actionCommand) {
	}

	/**
     * Action listener implementation for tfbaseYear.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handletfbaseYearActionPerformed(String actionCommand) {
	}

	/**
     * Action listener implementation for cbDCFchoise.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handlecbDCFchoiseActionPerformed(String actionCommand) {
	}

	/**
     * Action listener implementation for rbRandomWalk.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handlerbRandomWalkActionPerformed(String actionCommand) {
	}

	/**
     * Change listener implementation for rbRandomWalk.
	 *
	 * @param item the selected item
	 */
	public void handlerbRandomWalkStateChanged() {
	}

	/**
     * Action listener implementation for rbwienerProcess.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handlerbwienerProcessActionPerformed(String actionCommand) {
	}

	/**
     * Change listener implementation for rbwienerProcess.
	 *
	 * @param item the selected item
	 */
	public void handlerbwienerProcessStateChanged() {
	}

	/**
     * Action listener implementation for tfrange.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handletfrangeActionPerformed(String actionCommand) {
	}

	/**
     * Action listener implementation for Wahrscheinlichkeit.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handleWahrscheinlichkeitActionPerformed(String actionCommand) {
	}

	/**
     * Action listener implementation for chbcalculateQuest.
	 *
	 * @param actionCommand the spanned action command
	 */
	public void handlechbcalculateQuestActionPerformed(String actionCommand) {
	}

	/**
     * Change listener implementation for chbcalculateQuest.
	 *
	 * @param item the selected item
	 */
	public void handlechbcalculateQuestStateChanged() {
	}

	/**
	 * Test main method.
	 */
	public static void main(String args[]) {

		JFrame test=new JFrame("Test for ViewStochasticData1");
		test.setContentPane(new ViewStochasticData1());
		test.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		test.pack();
		test.show();
	}
}
