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
package org.bh.plugin.gcc;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bh.controller.IPeriodController;
import org.bh.controller.InputController;
import org.bh.data.DTO;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.gui.swing.importexport.BHDataExchangeDialog;
import org.bh.gui.swing.importexport.BHDataExchangeDialog.ImportListener;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewException;
import org.bh.platform.IImportExport;
import org.bh.platform.PlatformController;
import org.bh.platform.Services;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementCostOfSales;
import org.bh.plugin.gcc.swing.BHBalanceSheetForm;
import org.bh.plugin.gcc.swing.BHPLSCostOfSalesForm;
import org.bh.plugin.gcc.swing.GCCCombinedForm;
import org.bh.validation.ValidationMethods;

/**
 * 
 * @author Robert Vollmer
 */
public class PLSCostOfSalesController implements IPeriodController {
	private static final Logger log = Logger.getLogger(PLSCostOfSalesController.class);
	private static final String GUI_KEY = "gcc_input_costofsales";
	private static final int PRIORITY = 51;

	private static final List<DTOKeyPair> STOCHASTIC_KEYS;
	static {
		STOCHASTIC_KEYS = Services.getStochasticKeysFromEnum(DTOGCCBalanceSheet
				.getUniqueIdStatic(), DTOGCCBalanceSheet.Key.values());

		STOCHASTIC_KEYS.addAll(Services.getStochasticKeysFromEnum(
				DTOGCCProfitLossStatementCostOfSales.getUniqueIdStatic(),
				DTOGCCProfitLossStatementCostOfSales.Key.values()));
	}

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	@Override
	public int getGuiPriority() {
		return PRIORITY;
	}

	@Override
	public List<DTOKeyPair> getStochasticKeys() {
		return STOCHASTIC_KEYS;
	}

	@Override
	public Component editDTO(DTOPeriod period) {
		IPeriodicalValuesDTO bs = period.getPeriodicalValuesDTO(DTOGCCBalanceSheet.getUniqueIdStatic());
		IPeriodicalValuesDTO pls = period.getPeriodicalValuesDTO(DTOGCCProfitLossStatementCostOfSales.getUniqueIdStatic());
		if (bs == null || pls == null) {
			bs = new DTOGCCBalanceSheet();
			pls = new DTOGCCProfitLossStatementCostOfSales();
			period.removeAllChildren();
			period.addChild(bs);
			period.addChild(pls);
		}

		try {
			boolean intervalArithmetic = period.getScenario().isIntervalArithmetic();
			
			View bsView = new View(new BHBalanceSheetForm(intervalArithmetic), new ValidationMethods());
			final InputController bsController = new InputController(bsView, bs);
			bsController.loadAllToView();
			
			View plsView = new View(new BHPLSCostOfSalesForm(intervalArithmetic), new ValidationMethods());
			final InputController plsController = new InputController(plsView, pls);
			plsController.loadAllToView();
			
			GCCCombinedForm combinedForm = new GCCCombinedForm(bsView.getViewPanel(), plsView.getViewPanel());
			
			final IPeriodicalValuesDTO finalBS = bs;
			final IPeriodicalValuesDTO finalPLS = pls;
			
			combinedForm.getBtnExport().addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e) {
					BHDataExchangeDialog dialog = PlatformController.getInstance().createBalanceSheetAndPLSExchangeDialog();
					dialog.setAction(IImportExport.EXP_BALANCE_SHEET + IImportExport.EXP_PLS_COST_OF_SALES);
					List<IPeriodicalValuesDTO> model = new ArrayList<IPeriodicalValuesDTO>();
					model.add(finalBS);
					model.add(finalPLS);
					dialog.setModel(model);
					dialog.setVisible(true);
				}
			});
			
			
			combinedForm.getBtnImport().addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e) {
					BHDataExchangeDialog dialog = PlatformController.getInstance().createBalanceSheetAndPLSExchangeDialog();
										
					dialog.setAction(IImportExport.IMP_BALANCE_SHEET + IImportExport.IMP_PLS_COST_OF_SALES);		
					dialog.addImportListener(new ImportListener()
					{

						@SuppressWarnings("unchecked")
						@Override
						public void onImport(Object importedObject) {
							try
							{
								List<DTO<?>> impObjects = (List<DTO<?>>) importedObject;
								if (impObjects.get(0).getClass().equals(finalBS.getClass()))
								{
									DTO.copyValues(impObjects.get(0), (DTO<?>)finalBS);
									if (impObjects.size() == 2)
										DTO.copyValues(impObjects.get(1), (DTO<?>)finalPLS);
								}
								else
								{
									DTO.copyValues(impObjects.get(0), (DTO<?>)finalPLS);
									if (impObjects.size() == 2)
										DTO.copyValues(impObjects.get(1), (DTO<?>)finalBS);
								}
								
								bsController.loadAllToView();
								plsController.loadAllToView();
								
							}
							catch (Exception e)
							{
								log.debug("Returned value of the balance sheet and profit and loss statement " +
										"is not as excepted.");
							}
						}
						
					});
					dialog.setVisible(true);		
				}
				
			});
			
			return combinedForm;
		} catch (ViewException e) {
			log.error("Could not create view", e);
			return null;
		}
	}
}
