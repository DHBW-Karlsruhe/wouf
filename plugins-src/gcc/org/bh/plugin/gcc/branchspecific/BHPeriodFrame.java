package org.bh.plugin.gcc.branchspecific;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.gui.swing.forms.BHPeriodHeadForm;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementCostOfSales;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementTotalCost;
import org.bh.validation.VRIsBetween;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

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
public class BHPeriodFrame {
	
	public enum Name{
		PERIOD;
	}
	DTOPeriod period;

	public BHPeriodFrame(DTOPeriod period) {
		this.period = period;
	}

	public JPanel getFrame() {
		JPanel p = null;

		IPeriodicalValuesDTO myProfitStatement = period.getChild(1);
		// TotalCost Method
		if (myProfitStatement.getUniqueId() == DTOGCCProfitLossStatementTotalCost
				.getUniqueIdStatic()) {
			BHBDTotalCostController i = new BHBDTotalCostController();
			p = ((JPanel) i.editDTO(period));

		}

		// CostOfSales Method
		if (myProfitStatement.getUniqueId() == DTOGCCProfitLossStatementCostOfSales
				.getUniqueIdStatic()) {
			BHBDCostOfSalesController r = new BHBDCostOfSalesController();
			p = ((JPanel) r.editDTO(period));
		}
	
		
	
		return p;
	}
}
