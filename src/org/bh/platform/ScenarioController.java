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
package org.bh.platform;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;
import org.bh.calculation.IBranchSpecificCalculator;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.IStochasticProcess;
import org.bh.calculation.ITimeSeriesProcess;
import org.bh.companydata.importExport.INACEImport;
import org.bh.controller.IPeriodController;
import org.bh.controller.InputController;
import org.bh.data.DTO;
import org.bh.data.DTOBranch;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.data.types.StringValue;
import org.bh.gui.IBHModelComponent;
import org.bh.gui.swing.BHMainFrame;
import org.bh.gui.swing.BHOptionPane;
import org.bh.gui.swing.comp.BHButton;
import org.bh.gui.swing.comp.BHCheckBox;
import org.bh.gui.swing.comp.BHComboBox;
import org.bh.gui.swing.comp.BHComboBox.Item;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHProgressBar;
import org.bh.gui.swing.comp.BHSelectionList;
import org.bh.gui.swing.forms.BHScenarioForm;
import org.bh.gui.swing.forms.BHScenarioHeadForm;
import org.bh.gui.swing.forms.BHScenarioHeadIntervalForm;
import org.bh.gui.swing.forms.BHStochasticInputForm;
import org.bh.gui.swing.tree.BHTree;
import org.bh.gui.swing.tree.BHTreeNode;
import org.bh.gui.view.ICompValueChangeListener;
import org.bh.gui.view.IViewListener;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewEvent;
import org.bh.gui.view.ViewException;
import org.bh.platform.PlatformEvent.Type;
import org.bh.platform.i18n.BHTranslator;
import org.bh.validation.ValidationMethods;

/**
 * This is the controller for the scenario input form. It includes
 * ActionListeners for the buttons which trigger the calculation of the
 * shareholder value and parameters for stochastic processes.
 * 
 * @author Alexander Schmalzhaf
 * @author Robert Vollmer
 * @author Klaus Thiele
 * @version 1.0, 29.01.2010
 * @update 23.12.2010 Timo Klein
 * @update 09.12.2011 Yannick Rödl
 * @update 12.12.2011 Guenter Hesse
 */
public class ScenarioController extends InputController {
	protected static final BHComboBox.Item[] DCF_METHOD_ITEMS = getDcfMethodItems();
	protected static final BHComboBox.Item[] DCF_STOCHASTIC_METHOD_ITEMS = getStochasticDcfMethodItems();
	protected static final BHComboBox.Item[] PERIOD_TYPE_ITEMS = getPeriodTypeItems();
	protected static final BHComboBox.Item[] STOCHASTIC_METHOD_ITEMS = getStochasticProcessItems();
	private IStochasticProcess process = null;
//	private ITimeSeriesProcess TSprocess = null;
	private final BHMainFrame bhmf;
	
	public ScenarioController(View view, IDTO<?> model, final BHMainFrame bhmf) {
		super(view, model);

		this.bhmf = bhmf;
		DTOScenario scenario = (DTOScenario) getModel();

		// populate the list of DCF methods
		BHComboBox cbDcfMethod = (BHComboBox) view
				.getBHComponent(DTOScenario.Key.DCF_METHOD);
		if (cbDcfMethod != null) {
			if (scenario.isDeterministic()) {
				cbDcfMethod.setSorted(true);
				cbDcfMethod.setValueList(DCF_METHOD_ITEMS);
			} else {
				cbDcfMethod.setSorted(true);
				cbDcfMethod.setValueList(DCF_STOCHASTIC_METHOD_ITEMS);
			}
		}

		reloadTopPanel();

		// populate the list of stochastic processes
		BHComboBox cbStochasticMethod = (BHComboBox) view
				.getBHComponent(DTOScenario.Key.STOCHASTIC_PROCESS);
		if (cbStochasticMethod != null) {
			cbStochasticMethod.setSorted(true);
			cbStochasticMethod.setValueList(STOCHASTIC_METHOD_ITEMS);
			
			StochasticProcessListener listener = new StochasticProcessListener(cbStochasticMethod, view);
			cbStochasticMethod.addItemListener(listener);
			listener.getSelectedItem();
		}
		
		// add ActionListener for calculation button
		((BHButton) view
				.getBHComponent(BHScenarioForm.Key.CALCSHAREHOLDERVALUE))
				.addActionListener(new CalculationListener(PlatformController
						.getInstance().getMainFrame().getBHTree()));
		
		addParameterCalculationFunctionality();

		addDeterministicFunctionality();

		setCalcEnabled(getModel().isValid(true));
		if (!((DTOScenario) model).isDeterministic()) {
			((Component) view
					.getBHComponent(BHScenarioForm.Key.CALCSHAREHOLDERVALUE))
					.setEnabled(false);
		}

		loadToView(DTOScenario.Key.PERIOD_TYPE, false);
		updateStochasticFieldsList();
		loadAllToView();
	}
	
	/**
	 * This method adds all the functionality which is needed for a 
	 * deterministic scenario.
	 */
	private void addDeterministicFunctionality(){
		//Here seems to start the deterministic data.
		// immediately toggle interval arithmetic on and off
		BHCheckBox chkInterval = (BHCheckBox) getView().getBHComponent(
				DTOScenario.Key.INTERVAL_ARITHMETIC);
		if (chkInterval != null) {
			final View v = view;
			chkInterval.getValueChangeManager().addCompValueChangeListener(
					new ICompValueChangeListener() {
						@Override
						public void compValueChanged(IBHModelComponent comp) {
							saveToModel(comp);
							DTOScenario scenario = (DTOScenario) getModel();
							if (!scenario.isIntervalArithmetic()) {
								scenario.convertIntervalToDouble();
							}
							// ---- get selected item of the old ComboBox
							BHComboBox cbPeriodType = (BHComboBox) v
									.getBHComponent(DTOScenario.Key.PERIOD_TYPE);
							Object item = cbPeriodType.getSelectedItem();
							// ----
							((BHScenarioForm) getViewPanel())
									.setHeadPanel(scenario
											.isIntervalArithmetic());
							try {
								reloadView();
							} catch (ViewException e) {
							}
							loadAllToView();
							reloadTopPanel();

							// ---- set selected item in the new ComboBox
							JPanel shf = ((BHScenarioForm) getViewPanel())
									.getScenarioHeadForm();
							if (shf instanceof BHScenarioHeadForm) {
								((BHScenarioHeadForm) shf).getCmbPeriodType()
										.setSelectedItem(item);
							} else if (shf instanceof BHScenarioHeadIntervalForm) {
								((BHScenarioHeadIntervalForm) shf)
										.getCmbPeriodType().setSelectedItem(
												item);
							}
							// ----

							setCalcEnabled(getModel().isValid(true));
						}
					});
		}
	}
	
	/**
	 * This method adds the functionality to calculate all the data
	 * which is needed to calculate the data as input data for the calculation
	 * of the shareholder value. It additionally calls the 
	 * functionality for a time series.
	 */
	private void addParameterCalculationFunctionality(){
		final BHCheckBox cbBranchSpecificRepresentative = (BHCheckBox) view.getBHComponent(DTOScenario.Key.BRANCH_SPECIFIC);

		
		// add ActionListeners for calculate/reset parameters buttons for
		// stochastic processes
		final BHButton calcStochasticParameters = ((BHButton) view
				.getBHComponent(BHStochasticInputForm.Key.CALC_PARAMETERS));
		final BHButton resetStochasticParameters = ((BHButton) view
				.getBHComponent(BHStochasticInputForm.Key.RESET_PARAMETERS));
		if (calcStochasticParameters != null
				&& resetStochasticParameters != null) {
			calcStochasticParameters.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// calculate the parameters for the stochastic process and
					// display a panel to change them if necessary
					DTOScenario scenario = (DTOScenario) getModel();
					process = scenario.getStochasticProcess();
					
					BHStochasticInputForm form = (BHStochasticInputForm) ((Component) e
							.getSource()).getParent();
					
					//BranchSpecificRepresentative specific code.
					if(cbBranchSpecificRepresentative.isSelected() && cbBranchSpecificRepresentative.isVisible()){
						
						ITimeSeriesProcess timeSeriesProcess = scenario.getTimeSeriesProcess(ITimeSeriesProcess.Key.BRANCH_SPECIFIC_REPRESENTATIVE.toString());
						

						JPanel branchSpecificRepresentativePanel = timeSeriesProcess.calculateParameters(false);
						
						form.setBranchSpecificRepresentativePanel(branchSpecificRepresentativePanel);
						
						//calculate branch specific representative
						// get business data for the calculation below (branch specific values)
						DTOBusinessData businessData = (PlatformController.getInstance()).getBusinessDataDTO();
						
						//Load Calculator Plugin(s) - should be one
						ServiceLoader<IBranchSpecificCalculator> calculators = PluginManager
						.getInstance().getServices(IBranchSpecificCalculator.class);
						// calculate branch specific values
						
						DTOCompany branchSpecificRep = null;
						for(IBranchSpecificCalculator calculator : calculators){
							DTO.setThrowEvents(false);
							// berechne Branch Specific Representative und dessen Güte
							branchSpecificRep = calculator.calculateBSR(businessData, scenario);		
							DTO.setThrowEvents(true);
						}
						scenario.setBranchSpecificRep(branchSpecificRep);

					}
					
					//Original parameters code
					JPanel parametersPanel = process.calculateParameters(false);
					
					form.setParametersPanel(parametersPanel);
						
					if (parametersPanel instanceof Container)
						Services.setFocus((Container) parametersPanel);
					try {
						final View view = new View(parametersPanel,
								new ValidationMethods());
						// enable the calculation button depending on the
						// validation status
						view.addViewListener(new IViewListener() {
							@Override
							public void viewEvent(ViewEvent e) {
								switch (e.getEventType()) {
								case VALUE_CHANGED:
									boolean allValid = !view.revalidate()
											.hasErrors();
									((Component) getView()
											.getBHComponent(
													BHScenarioForm.Key.CALCSHAREHOLDERVALUE))
											.setEnabled(allValid);
									break;
								case VALIDATION_FAILED:
									((Component) getView()
											.getBHComponent(
													BHScenarioForm.Key.CALCSHAREHOLDERVALUE))
											.setEnabled(false);
									break;
								}
							}
						});
						boolean allValid = !view.revalidate().hasErrors();
						((Component) getView().getBHComponent(
								BHScenarioForm.Key.CALCSHAREHOLDERVALUE))
								.setEnabled(allValid);
					} catch (ViewException e1) {
					}

					resetStochasticParameters.setVisible(true);
					calcStochasticParameters.setVisible(false);
					((Component) getView().getBHComponent(
							DTOScenario.Key.STOCHASTIC_KEYS)).setEnabled(false);
					((Component) getView().getBHComponent(
							DTOScenario.Key.STOCHASTIC_PROCESS))
							.setEnabled(false);
				}
			});

			resetStochasticParameters.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					BHStochasticInputForm form = (BHStochasticInputForm) ((Component) e
							.getSource()).getParent();
					
					process = null;
					
					form.removeParametersPanel();
					form.removeBranchSpecificRepresentativePanel();
					
					calcStochasticParameters.setVisible(true);
					resetStochasticParameters.setVisible(false);
					((Component) getView().getBHComponent(
							BHScenarioForm.Key.CALCSHAREHOLDERVALUE))
							.setEnabled(false);
					((Component) getView().getBHComponent(
							DTOScenario.Key.STOCHASTIC_KEYS)).setEnabled(true);
					((Component) getView().getBHComponent(
							DTOScenario.Key.STOCHASTIC_PROCESS))
							.setEnabled(true);
				}
			});
		}
		
		addBranchSpecificRepresentativeFunctionality(view, resetStochasticParameters);
	}
	
	/**
	 * This method is used to make all the time series functionality available.
	 * It might be necessary to totally redefine this method, because the Time Series
	 * is loaded in the combo Box.
	 * @param view the view with the stochastic input form
	 * @param resetStochasticParameters the button to reset the calculation
	 */
	private void addBranchSpecificRepresentativeFunctionality(final View view, final BHButton resetStochasticParameters){
		//cbrepresentative mit Daten befüllen
		BHComboBox cbrepresentative = (BHComboBox) view.getBHComponent(DTOScenario.Key.REPRESENTATIVE);
		
		if (cbrepresentative == null){
			return; // If we have no representative checkbox, there is no stochastic scenario, so no time series as well
		} else {
			
			DTOBusinessData businessData = (PlatformController.getInstance()).getBusinessDataDTO();
			
			List<DTOBranch> branchList = businessData.getChildren();
			
			String key;
			String main;
			String mid;
			String sub;
			Item item;
			List<String> branch = new ArrayList<String>();
			
			Iterator<DTOBranch> itr = branchList.iterator();
			while(itr.hasNext()) {
	    		DTOBranch currBranch = itr.next();
	    		main = currBranch.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY).toString();
	    		mid = currBranch.get(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY).toString();
	    		sub = currBranch.get(DTOBranch.Key.BRANCH_KEY_SUB_CATEGORY).toString();
	    		key = main + "." + mid + "." + sub;
	    		branch.add(key);
	    		
			}
			
			Collections.sort(branch);
			
			Iterator<String> itr1 = branch.iterator();
			
			while(itr1.hasNext()){
				key = itr1.next();
				item = new Item(key, null);
				cbrepresentative.addItem(item);
				//cbrepresentative.sortItems();
			}
		}
		
//		INACEImport naceReader = Services.getNACEReader();
//		Map<String, String> branches = naceReader.getBranch();
//
//		
//		Item item;
//		StringValue value;
//		String key;
//		
//		INACEImport naceReader = Services.getNACEReader();
//		Map<String, String> branches = naceReader.getBranch();
//		
//		if(cbrepresentative != null && cbrepresentative.getItemCount() > 0){
//			cbrepresentative.removeAllItems();
//		}
//		
//		for (Map.Entry<String, String> entry : branches.entrySet()) {
//			if(entry.getKey().length() == 1){
//				
//				key = entry.getKey();
//				//key = key.replace(".", "");
//				value = new StringValue(entry.getKey() + ": " + entry.getValue());
//				item = new Item(key, value);
//				cbrepresentative.addItem(item);
//				
//				for (Map.Entry<String, String> entry1 : branches.entrySet()) {
//					if(entry1.getKey().length() == 4 && entry1.getKey().substring(0, 1).equals(entry.getKey())){
//						
//						key = entry1.getKey();
//						//key = key.replace(".", "");
//						value = new StringValue(entry1.getKey() + ": " + entry1.getValue());
//						item = new Item(key, value);
//						cbrepresentative.addItem(item);
//						
//						for (Map.Entry<String, String> entry2 : branches.entrySet()) {
//							if(entry2.getKey().length() > 4 && entry2.getKey().substring(0, 4).equals(entry1.getKey())){
//								
//								key = entry2.getKey();
//								//key = key.replace(".", "");
//								value = new StringValue(entry2.getKey() + ": " + entry2.getValue());
//								item = new Item(key, value);
//								cbrepresentative.addItem(item);
//							}
//							
//						}
//					}
//					
//				}
//			}
//			
//		}
//		
//		if(cbrepresentative != null){
//			ItemListener itemListener = new ItemListener() {
//			      public void itemStateChanged(ItemEvent itemEvent) {
//			    	//save selected branch
//			    	  if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
//						DTOScenario scenario = (DTOScenario) getModel();
//						StringValue industry;
//						BHComboBox cbrepresentative = (BHComboBox) view.getBHComponent(DTOScenario.Key.REPRESENTATIVE);
//						industry = new StringValue(cbrepresentative.getSelectedItem().toString());
//						scenario.put(DTOScenario.Key.INDUSTRY, industry);
//			    	  }
//			      }
//			    };
//			    cbrepresentative.addItemListener(itemListener);
//		}
//		
//		
//		BHComboBox cmbPeriodType = (BHComboBox) view.getBHComponent(DTOScenario.Key.PERIOD_TYPE);
//		
//		if(cmbPeriodType != null){
//			cmbPeriodType.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e){
//					BHComboBox periodType = (BHComboBox) e.getSource();
//					
//					BHCheckBox cbbranchSpecificRepresentative = (BHCheckBox) view
//					.getBHComponent(DTOScenario.Key.BRANCH_SPECIFIC);
//					
//					if(periodType.getSelectedIndex() != 0){
//						BHComboBox cbrepresentative = (BHComboBox) view.getBHComponent(DTOScenario.Key.REPRESENTATIVE);
//						BHDescriptionLabel lrepresentative = (BHDescriptionLabel) view.getBHComponent(DTOScenario.Key.LREPRESENTATIVE);
//						
//						cbrepresentative.setVisible(false);
//						lrepresentative.setVisible(false);
//						
//						cbbranchSpecificRepresentative.setVisible(false);
//					}
//
//						if(cbbranchSpecificRepresentative != null && (periodType.getSelectedIndex() == 0)){
//							cbbranchSpecificRepresentative.setVisible(true);
//							cbbranchSpecificRepresentative.setSelected(false);
//						}
//				}
//				
//				
//			});
//		}
		
		// populate the ComboBoxes for branch specific representative
		BHCheckBox cbbranchSpecificRepresentative = (BHCheckBox) view
		.getBHComponent(DTOScenario.Key.BRANCH_SPECIFIC);
		
		if(cbbranchSpecificRepresentative != null){
			cbbranchSpecificRepresentative.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					BHCheckBox from = (BHCheckBox) e.getSource();
					
					//For branch specific representative
					BHComboBox cbrepresentative = (BHComboBox) view.getBHComponent(DTOScenario.Key.REPRESENTATIVE);
					BHDescriptionLabel lrepresentative = (BHDescriptionLabel) view.getBHComponent(DTOScenario.Key.LREPRESENTATIVE);

					
					if (from.isSelected()) {
						if(cbrepresentative != null){
							cbrepresentative.setVisible(true);
						}
						if (lrepresentative != null){
							lrepresentative.setVisible(true);
						}
						
						
						//save selected branch
						DTOScenario scenario = (DTOScenario) getModel();
						StringValue industry;
						industry = new StringValue(cbrepresentative.getSelectedItem().toString());
						scenario.put(DTOScenario.Key.INDUSTRY, industry);
						
					} else {
						if(cbrepresentative != null){
							cbrepresentative.setVisible(false);
						}
						if (lrepresentative != null){
							lrepresentative.setVisible(false);
						}
					}
				}
			});
		}
		

		
	}

	/**
	 * This method returns a list of DCF methods which is available for the
	 * stochastic calculation.
	 * @return item array of dcf methods applicable for stochastic processes
	 */
	private static Item[] getStochasticDcfMethodItems() {
		Collection<IShareholderValueCalculator> dcfMethods = Services
				.getDCFMethods().values();
		ArrayList<BHComboBox.Item> items = new ArrayList<BHComboBox.Item>();
		for (IShareholderValueCalculator dcfMethod : dcfMethods) {
			if (dcfMethod.isApplicableForStochastic())
				items.add(new BHComboBox.Item(dcfMethod.getGuiKey(),
						new StringValue(dcfMethod.getUniqueId())));
		}
		return items.toArray(new BHComboBox.Item[0]);
	}

	/**
	 * Reload the top panel. Where the period list is populated and based on
	 * the seected entry, the list with available entries for the calculation
	 */
	protected void reloadTopPanel() {
		final BHComboBox cbPeriodType = (BHComboBox) view
				.getBHComponent(DTOScenario.Key.PERIOD_TYPE);
		if (cbPeriodType != null) {
			cbPeriodType.setValueList(PERIOD_TYPE_ITEMS);
			// warn user that the period type cannot be changed without throwing
			// away
			// all existing periods in the scenario
			cbPeriodType.getValueChangeManager().addCompValueChangeListener(
					new ICompValueChangeListener() {

						@Override
						public void compValueChanged(IBHModelComponent comp) {
							if (cbPeriodType.getSelectedIndex() == cbPeriodType
									.getLastSelectedIndex()) {
								return;
							}

							DTOScenario scenario = (DTOScenario) getModel();
							if (scenario.getChildrenSize() > 0) {
								cbPeriodType.hidePopup();
								int action = PlatformUserDialog
										.getInstance()
										.showYesNoDialog(
												Services.getTranslator()
														.translate(
																"PstochasticPeriodTypeChangeText"),
												Services.getTranslator()
														.translate(
																"PstochasticPeriodTypeChangeHeader"));
								if (action == JOptionPane.YES_OPTION) {
									bhmf.getBHTree().removeAllPeriods(scenario);
								} else if (action == JOptionPane.NO_OPTION) {
									cbPeriodType.setSelectedIndex(cbPeriodType
											.getLastSelectedIndex());
									return;
								}
							}

							updateStochasticFieldsList();
						}
					});
		}
	}

	/**
	 * Updates the selection list for the fields which can be calculated
	 * stochastically, based on the currently selected period type.
	 */
	protected void updateStochasticFieldsList() {
		DTOScenario scenario = (DTOScenario) getModel();
		BHComboBox cbPeriodType = (BHComboBox) view
				.getBHComponent(DTOScenario.Key.PERIOD_TYPE);
		if (cbPeriodType != null && !scenario.isDeterministic()) {
			BHSelectionList slStochasticKeys = (BHSelectionList) getView()
					.getBHComponent(DTOScenario.Key.STOCHASTIC_KEYS);
			BHDescriptionLabel lNoStochasticKeys = (BHDescriptionLabel) getView()
					.getBHComponent(
							BHStochasticInputForm.Key.NO_STOCHASTIC_KEYS);
			List<DTOKeyPair> keyPair = Services.getPeriodController(
					cbPeriodType.getValue().toString()).getStochasticKeys();
			if (!keyPair.isEmpty()) {
				slStochasticKeys.setModel(keyPair.toArray());
				slStochasticKeys.getParent().getParent().setVisible(true);
				lNoStochasticKeys.setVisible(false);
			} else {
				lNoStochasticKeys.setVisible(true);
				slStochasticKeys.getParent().getParent().setVisible(false);
			}
		}
	}

	@Override
	public void platformEvent(PlatformEvent e) {
		super.platformEvent(e);
		if (e.getEventType() == Type.DATA_CHANGED
				&& getModel().isMeOrChild(e.getSource())) {
			setCalcEnabled(getModel().isValid(true));
		}
	}

	/**
	 * This method is used to set the calculation as enabled.
	 * 
	 * @param calculationEnabled if true calculation is enabled
	 */
	protected void setCalcEnabled(boolean calculationEnabled) {
		if (((DTOScenario) getModel()).isDeterministic()) {
			((Component) view
					.getBHComponent(BHScenarioForm.Key.CALCSHAREHOLDERVALUE))
					.setEnabled(calculationEnabled);
			((Component) view
					.getBHComponent(BHScenarioForm.Key.CANNOT_CALCULATE_HINT))
					.setVisible(!calculationEnabled);
		} else {
			((Component) view
					.getBHComponent(BHStochasticInputForm.Key.CALC_PARAMETERS))
					.setEnabled(calculationEnabled);
			((Component) view
					.getBHComponent(BHScenarioForm.Key.CANNOT_CALCULATE_HINT))
					.setVisible(!calculationEnabled);
		}
	}

	/**
	 * This method returns all DCF methods which are determined via the
	 * plugins.
	 * @return Array of ComboBox items with DCF-Methods
	 */
	protected static BHComboBox.Item[] getDcfMethodItems() {
		Collection<IShareholderValueCalculator> dcfMethods = Services
				.getDCFMethods().values();
		ArrayList<BHComboBox.Item> items = new ArrayList<BHComboBox.Item>();
		for (IShareholderValueCalculator dcfMethod : dcfMethods) {
			items.add(new BHComboBox.Item(dcfMethod.getGuiKey(),
					new StringValue(dcfMethod.getUniqueId())));
		}
		return items.toArray(new BHComboBox.Item[0]);
	}

	/**
	 * This method returns all the items provided in the comboBox for 
	 * stochastic processes. It searches for the relevant plugin and returns the values.
	 * @return list of available stochastic processes
	 */
	protected static BHComboBox.Item[] getStochasticProcessItems() {
		Collection<IStochasticProcess> stochasticProcesses = Services
				.getStochasticProcesses().values();
		ArrayList<BHComboBox.Item> items = new ArrayList<BHComboBox.Item>();
		for (IStochasticProcess stochasticProcess : stochasticProcesses) {
			items.add(new BHComboBox.Item(stochasticProcess.getGuiKey(),
					new StringValue(stochasticProcess.getUniqueId())));
		}
		return items.toArray(new BHComboBox.Item[0]);

	}

	/**
	 * This method returns all the items provided in the ComboBox for
	 * period inputs. It is irrelevant whether this are stochastic processes
	 * or deterministic processes. The list should stay the same here.
	 * @return array of comboBox items as period types
	 */
	protected static BHComboBox.Item[] getPeriodTypeItems() {
		IPeriodController[] periodTypes = Services.getPeriodControllers()
				.values().toArray(new IPeriodController[0]);
		// sort by priority
		Arrays.sort(periodTypes, new Comparator<IPeriodController>() {

			@Override
			public int compare(IPeriodController o1, IPeriodController o2) {
				return o2.getGuiPriority() - o1.getGuiPriority();
			}
		});
		ArrayList<BHComboBox.Item> items = new ArrayList<BHComboBox.Item>();
		for (IPeriodController periodType : periodTypes) {
			items.add(new BHComboBox.Item(periodType.getGuiKey(),
					new StringValue(periodType.getGuiKey())));
		}
		return items.toArray(new BHComboBox.Item[0]);
	}
	
	protected class StochasticProcessListener implements ItemListener{

		private BHComboBox comboBox;
		private View view;
		
		public StochasticProcessListener(BHComboBox comboBox, View view){
			this.comboBox = comboBox;
			this.view = view;
		}

		@Override
		public void itemStateChanged(ItemEvent ie) {
			getSelectedItem();
		}
		
		public void getSelectedItem(){
			BHCheckBox cbBranchSpecRep = (BHCheckBox) view.getBHComponent(DTOScenario.Key.BRANCH_SPECIFIC);
			
			try{
				@SuppressWarnings("unused") //We just need to determine whether this is a time series
				ITimeSeriesProcess process = (ITimeSeriesProcess) Services.getStochasticProcess(comboBox.getValue().toString());
				
				cbBranchSpecRep.setVisible(true);
			} catch (ClassCastException cce){
				cbBranchSpecRep.setSelected(false);
				cbBranchSpecRep.setVisible(false);
			}
		}
		
	}

	/**
	 * ActionListener which starts the calculation of the shareholder value
	 * (both deterministic and stochastic calculation).
	 */
	protected class CalculationListener implements ActionListener {
		
		private final ImageIcon scCalcLoading = Services
				.createImageIcon("/org/bh/images/loading.gif");
		private BHTree bhTree;
		Thread calcThread;
		boolean notInterrupted;
		BHProgressBar progressBar;

		public CalculationListener(BHTree bhTree) {
			this.bhTree = bhTree;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			final BHButton b = (BHButton) e.getSource();
			final BHDescriptionLabel calcImage = (BHDescriptionLabel) getView()
					.getBHComponent(BHScenarioForm.Key.CALCULATING_IMAGE);
			progressBar = (BHProgressBar) getView().getBHComponent(
					BHScenarioForm.Key.PROGRESSBAR);
			progressBar.setValue(0);
			b.setEnabled(false);
			calcImage.setIcon(this.scCalcLoading);
			notInterrupted = true;
			Runnable r = new Runnable() {
				long end;
				long start;
				
				@Override
				public void run() {
					DTOScenario scenario = (DTOScenario) getModel();
					Component panel;
					if (scenario.isDeterministic()) {
						panel = showDeterministicResult(scenario);
						
					} else {
						//This is a stochastic scenario
						panel = showStochasticResult(scenario);
					}

					// set scenario calculated
					scenario.setCalculated(true);

					if (panel != null & notInterrupted) {
						if (progressBar != null) {
							progressBar.setVisible(false);
						}

						BHTreeNode tn = (BHTreeNode) bhTree.getSelectionPath()
								.getPathComponent(2);
						JScrollPane sp = new JScrollPane(panel);
						sp.setWheelScrollingEnabled(true);
						sp.getVerticalScrollBar().setUnitIncrement(10);
						tn.setResultPane(sp);
						// Put it onto screen
						bhmf.moveInResultForm(tn.getResultPane());

					}
					b.setEnabled(true);
					b.changeText(BHScenarioForm.Key.CALCSHAREHOLDERVALUE);
					calcImage.setIcon(null);
				}
				
				private void startTimeMeasure(){
					if (log.isInfoEnabled()) {
						start = System.currentTimeMillis();
					}
				}
				
				private void endTimeMeasure(){
					if (log.isInfoEnabled()) {
						end = System.currentTimeMillis();
						log.info("Result Analysis View load time: "
								+ (end - start) + "ms");
					}
				}
				
				private Component showStochasticResult(DTOScenario scenario){

					startTimeMeasure();
					
					process.updateParameters();//This one only writes the GUI Keys into internal keys
					
					DistributionMap result = null;
					DistributionMap resultBranchSpecificData = null;
					boolean branchSpecific = false;
					
					try{
						ITimeSeriesProcess TSprocess = (ITimeSeriesProcess) process;
						
						BHCheckBox cbBranchSpecific = (BHCheckBox) view.getBHComponent(DTOScenario.Key.BRANCH_SPECIFIC);
					
						((BHScenarioForm) getViewPanel()).addProgressBar();
						
						progressBar.setVisible(true);
						b.setEnabled(true);
						b.changeText(BHScenarioForm.Key.ABORT);
						TSprocess.setProgressB(progressBar);
						
						//Standard time series calculation
						try{
							result = TSprocess.calculate();
						} catch (RuntimeException re){
							Logger.getLogger(getClass()).fatal("Calculation of matrix in time series failed!", re);
							b.doClick(); //Simulate interrupt click
							BHOptionPane.showMessageDialog(bhTree, BHTranslator.getInstance().translate("ExTimeSeriesAnalysisSingularMatrix"), "Error", JOptionPane.ERROR_MESSAGE);
							return new JPanel();
						}
						
						if(cbBranchSpecific.isSelected() && notInterrupted){
							
							branchSpecific = true;
							
							TSprocess.setBranchSpecific(true);
							resultBranchSpecificData = TSprocess.calculate();
							
							try{
								resultBranchSpecificData = TSprocess.calculate();
							} catch (RuntimeException re){
								Logger.getLogger(getClass()).fatal("Calculation of matrix in time series failed!", re);
								b.doClick(); //Simulate interrupt click
								BHOptionPane.showMessageDialog(bhTree, BHTranslator.getInstance().translate("ExTimeSeriesAnalysisSingularMatrix"), "Error", JOptionPane.ERROR_MESSAGE);
								return new JPanel();
							}
						}
						
					} catch (ClassCastException cce){
						//We have a "normal" calculation (RandomWalk/WienerProzess)
						result = process.calculate();
					}
					
					
					Component panel = new JPanel();
					if(notInterrupted  && result.getAmountOfValues() != 0){
						for (IStochasticResultAnalyser analyser : PluginManager
								.getInstance().getServices(
										IStochasticResultAnalyser.class)) {
						
							//branchSpecificRepresentative is only calculated in time series mode.
							if(branchSpecific){
								if(analyser.getUniqueID().equals(IStochasticResultAnalyser.Keys.BRANCH_SPECIFIC.toString())){
									panel = analyser.setResult(scenario, result, resultBranchSpecificData);
									break;
								}
							
							} else {
								if(analyser.getUniqueID().equals(IStochasticResultAnalyser.Keys.DEFAULT.toString())){
									panel = analyser.setResult(scenario, result);
									break;
								}
							}
						}
					}
					
					endTimeMeasure();
					
					return panel;
				}
				
				private Component showDeterministicResult(DTOScenario scenario){
					// start calculation
					Map<String, Calculable[]> result = scenario
							.getDCFMethod().calculate(scenario, true);

					// FIXME selection of result analyser plugin
					startTimeMeasure();
					
					Component panel = new JPanel();
					
					for (IDeterministicResultAnalyser analyser : PluginManager
							.getInstance().getServices(
									IDeterministicResultAnalyser.class)) {
						panel = analyser.setResult(scenario, result);
						break;
					}

					BHTreeNode tn = (BHTreeNode) bhTree.getSelectionPath()
							.getPathComponent(2);

					JScrollPane sp = new JScrollPane(panel);
					sp.setWheelScrollingEnabled(true);
					tn.setResultPane(sp);

					endTimeMeasure();
					
					return panel;
				}
			};

			// Prozedur zum Abrechen der Berechnung
			if (calcThread != null) {
				if (calcThread.isAlive()) {
					notInterrupted = false;
					if (progressBar != null) {
						progressBar.setVisible(false);
						try{
							((ITimeSeriesProcess) process).setInterrupted();
						} catch (NullPointerException npe){}
					}
					b.changeText(BHScenarioForm.Key.CALCSHAREHOLDERVALUE);
					
					BHCheckBox cbBranchSpecific = (BHCheckBox) view.getBHComponent(DTOScenario.Key.BRANCH_SPECIFIC);
					if(cbBranchSpecific != null && cbBranchSpecific.isSelected()){
						BHButton resetStochasticParameters = ((BHButton) view
								.getBHComponent(BHStochasticInputForm.Key.RESET_PARAMETERS));
						resetStochasticParameters.doClick();
						calcImage.setIcon(null);
					}
				} else {
					notInterrupted = true;
					// if(progressBar!=null){
					// progressBar.setVisible(true);}
					calcThread = new Thread(r, "Calculation Thread");
					calcThread.start();
				}
			} else {
				calcThread = new Thread(r, "Calculation Thread");
				calcThread.start();
			}
		}
	}
}
