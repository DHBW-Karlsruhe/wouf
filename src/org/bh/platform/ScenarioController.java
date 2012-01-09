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
import java.util.Comparator;
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
	private ITimeSeriesProcess TSprocess = null;
	private final BHMainFrame bhmf;
	
	private static final String TIME_SERIES_ID = "timeSeries";

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
		}
		
		final BHCheckBox cbBranchSpecificRepresentative = (BHCheckBox) view.getBHComponent(DTOScenario.Key.BRANCH_SPECIFIC);

		// add ActionListener for calculation button
		((BHButton) view
				.getBHComponent(BHScenarioForm.Key.CALCSHAREHOLDERVALUE))
				.addActionListener(new CalculationListener(PlatformController
						.getInstance().getMainFrame().getBHTree()));
		
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
						
						JPanel timeSeriesPanel = timeSeriesProcess.calculateParameters();
						
						form.setBranchSpecificRepresentativePanel(timeSeriesPanel);
						
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

		addTimeSeriesFunctionality(view, resetStochasticParameters);

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
	
	private void addTimeSeriesFunctionality(final View view, final BHButton resetStochasticParameters){
		//cbrepresentative mit Daten befüllen
		BHComboBox cbrepresentative = (BHComboBox) view.getBHComponent(DTOScenario.Key.REPRESENTATIVE);
		INACEImport naceReader = Services.getNACEReader();
		Map<String, String> branches = naceReader.getBranch();
		
		Item item;
		StringValue value;
		String key;
		
		cbrepresentative.removeAllItems();
		for (Map.Entry<String, String> entry : branches.entrySet()) {
			if(entry.getKey().length() == 1){
				
				key = entry.getKey();
				key = key.replace(".", "");
				value = new StringValue(entry.getKey() + ": " + entry.getValue());
				item = new Item(key, value);
				cbrepresentative.addItem(item);
				
				for (Map.Entry<String, String> entry1 : branches.entrySet()) {
					if(entry1.getKey().length() == 4 && entry1.getKey().substring(0, 1).equals(entry.getKey())){
						
						key = entry1.getKey();
						key = key.replace(".", "");
						value = new StringValue(entry1.getKey() + ": " + entry1.getValue());
						item = new Item(key, value);
						cbrepresentative.addItem(item);
						
						for (Map.Entry<String, String> entry2 : branches.entrySet()) {
							if(entry2.getKey().length() > 4 && entry2.getKey().substring(0, 4).equals(entry1.getKey())){
								
								key = entry2.getKey();
								key = key.replace(".", "");
								value = new StringValue(entry2.getKey() + ": " + entry2.getValue());
								item = new Item(key, value);
								cbrepresentative.addItem(item);
							}
							
						}
					}
					
				}
			}
			
		}
		
		
		// populate the list of TimeSeriesProcess
		BHCheckBox cbTimeSeriesMethod = (BHCheckBox) view
				.getBHComponent(DTOScenario.Key.TIMESERIES_PROCESS);
		if (cbTimeSeriesMethod != null && Services.check4TimeSeriesPlugin()) {
			cbTimeSeriesMethod.setVisible(true);
			cbTimeSeriesMethod.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					BHCheckBox from = (BHCheckBox) e.getSource();
					BHStochasticInputForm form = (BHStochasticInputForm) ((Component) e
							.getSource()).getParent();
					
					//For branch specific representative
					BHCheckBox cbBranchSpecificRepresentative = (BHCheckBox) view.getBHComponent(DTOScenario.Key.BRANCH_SPECIFIC);
					BHComboBox cmbPeriodType = (BHComboBox) view.getBHComponent(DTOScenario.Key.PERIOD_TYPE);
									
					if (from.isSelected()){
						//Branch specific representative
						if(cbBranchSpecificRepresentative != null && (cmbPeriodType.getSelectedIndex() == 0)){
							cbBranchSpecificRepresentative.setVisible(true);
						}
						
						//Time series related
						DTOScenario scenario = (DTOScenario) getModel();
						TSprocess = scenario.getTimeSeriesProcess(ScenarioController.TIME_SERIES_ID);

						JPanel parametersPanel = TSprocess
								.calculateParameters();
						form.setTimeSeriesParametersPanel(parametersPanel);
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
										if (resetStochasticParameters
												.isVisible())
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
							view.revalidate();
							boolean allValid = !view.revalidate().hasErrors();
							((Component) getView().getBHComponent(
									BHScenarioForm.Key.CALCSHAREHOLDERVALUE))
									.setEnabled(allValid);
						} catch (ViewException e1) {
						}
						BHComboBox cbrepresentative1 = (BHComboBox) view.getBHComponent(DTOScenario.Key.REPRESENTATIVE);
						cbrepresentative1.addItem(" ");

					} else {
						
						//For branch specific representative
						BHComboBox cbindustry = (BHComboBox) view.getBHComponent(DTOScenario.Key.INDUSTRY);
						BHComboBox cbrepresentative = (BHComboBox) view.getBHComponent(DTOScenario.Key.REPRESENTATIVE);
						BHDescriptionLabel lrepresentative = (BHDescriptionLabel) view.getBHComponent(DTOScenario.Key.LREPRESENTATIVE);
						BHDescriptionLabel lindustry = (BHDescriptionLabel) view.getBHComponent(DTOScenario.Key.LINDUSTRY);
						cbindustry.setVisible(false);
						cbrepresentative.setVisible(false);
						lrepresentative.setVisible(false);
						lindustry.setVisible(false);
						cbBranchSpecificRepresentative.setVisible(false);
						cbBranchSpecificRepresentative.setSelected(false);
						
						form.removeTimeSeriesParametersPanel();
					}

				}
			});
		}
		
		//BHComboBox cbrepresentative = (BHComboBox) view.getBHComponent(DTOScenario.Key.REPRESENTATIVE);
		
		if(cbrepresentative != null){
			ItemListener itemListener = new ItemListener() {
			      public void itemStateChanged(ItemEvent itemEvent) {
			    	//save selected branch
			    	  if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
						DTOScenario scenario = (DTOScenario) getModel();
						StringValue industry;
						BHComboBox cbrepresentative = (BHComboBox) view.getBHComponent(DTOScenario.Key.REPRESENTATIVE);
						industry = new StringValue(cbrepresentative.getSelectedItem().toString());
						scenario.put(DTOScenario.Key.INDUSTRY, industry);
			    	  }
			      }
			    };
			    cbrepresentative.addItemListener(itemListener);
		}
		
		// populate the ComboBoxes for branch specific representative
		BHCheckBox cbbranchSpecificRepresentative = (BHCheckBox) view
		.getBHComponent(DTOScenario.Key.BRANCH_SPECIFIC);
		
		if(cbbranchSpecificRepresentative != null){
			cbbranchSpecificRepresentative.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					BHCheckBox from = (BHCheckBox) e.getSource();
//					BHStochasticInputForm form = (BHStochasticInputForm) ((Component) e
//							.getSource()).getParent();
					
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
						
						/*
						PlatformController platformController = PlatformController.getInstance();
						DTOBusinessData businessData = platformController.getBusinessDataDTO();
						List<DTOBranch> branchList = businessData.getChildren();
						Iterator<DTOBranch> itr = branchList.iterator(); 
						while(itr.hasNext()) {
				    		DTOBranch currBranch = itr.next();			
				    			
				    		System.out.print(currBranch.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY));
				    		System.out.print(currBranch.get(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY));
				    		System.out.println(currBranch.get(DTOBranch.Key.BRANCH_KEY_SUB_CATEGORY));
				    		
				    		//Item industry = new Item(currBranch.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY).toString(), currBranch.get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY));
				    		//Item representative = new Item(currBranch.get(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY).toString(), currBranch.get(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY));
				    		
				    		//cbindustry.addItem(industry);
				    		//cbrepresentative.addItem(representative);
						}
						*/
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
				
				private Component showStochasticResult(DTOScenario scenario){

					if (log.isInfoEnabled()) {
						start = System.currentTimeMillis();
					}
					
					process.updateParameters();//This one only writes the GUI Keys into internal keys
					DistributionMap result = process.calculate();
                    
					// 12.12.2011: checks wether we got a result or not
					if ( (result.getAmountOfValues()) == 0)
						return null;
					
					BHCheckBox timeSeries = (BHCheckBox) view
							.getBHComponent(DTOScenario.Key.TIMESERIES_PROCESS);
					
					BHCheckBox cbBranchSpecific = (BHCheckBox) view.getBHComponent(DTOScenario.Key.BRANCH_SPECIFIC);
					
					
					DistributionMap resultBranchSpecificData = null;
					
					if(cbBranchSpecific.isSelected()){
						//The Panel contains all data necessary such as slope and standard deviation.
						//TODO maybe manipulate data here, because it is normalized?!
						progressBar.setVisible(true);
						
						process.calculateParameters(true);
						resultBranchSpecificData = process.calculate();
						
						//Do branch specific calculation
						TSprocess.updateParameters();
						try{
							
							resultBranchSpecificData.setTimeSeries(TSprocess,
								TSprocess.calculate(true),
								TSprocess.calculateCompare(3));
						} catch (RuntimeException re){
							Logger.getLogger(getClass()).fatal("Calculation of matrix in time series failed!", re);
							b.doClick(); //Simulate interrupt click
							BHOptionPane.showMessageDialog(bhTree, BHTranslator.getInstance().translate("ExTimeSeriesAnalysisSingularMatrix"), "Error", JOptionPane.ERROR_MESSAGE);
							return new JPanel();
						}
					}
					
					if (timeSeries.isSelected()) {
						((BHScenarioForm) getViewPanel()).addProgressBar();
						progressBar.setVisible(true);
						b.setEnabled(true);
						b.changeText(BHScenarioForm.Key.ABORT);
						TSprocess.setProgressB(progressBar);
						TSprocess.updateParameters();
						try{
							result.setTimeSeries(TSprocess,
								TSprocess.calculate(false),
								TSprocess.calculateCompare(3));
						} catch (RuntimeException re){
							Logger.getLogger(getClass()).fatal("Calculation of matrix in time series failed!", re);
							b.doClick(); //Simulate interrupt click
							BHOptionPane.showMessageDialog(bhTree, BHTranslator.getInstance().translate("ExTimeSeriesAnalysisSingularMatrix"), "Error", JOptionPane.ERROR_MESSAGE);
							return new JPanel();
						}

					}

					Component panel = new JPanel();
					
					for (IStochasticResultAnalyser analyser : PluginManager
							.getInstance().getServices(
									IStochasticResultAnalyser.class)) {
						
						//branchSpecificRepresentative is only calculated in time series mode.
						if(timeSeries.isSelected() && cbBranchSpecific.isSelected()){
							if(analyser.getUniqueID().equals(IStochasticResultAnalyser.Keys.BRANCH_SPECIFIC.toString())){
								//panel = analyser.setResult(scenario, result, testResult);
								panel = analyser.setResult(scenario, result, resultBranchSpecificData);
								break;
							}
							
						} else {
							if(analyser.getUniqueID().equals(IStochasticResultAnalyser.Keys.DEFAULT.toString())){
								//panel = analyser.setResult(scenario, result, testResult);
								panel = analyser.setResult(scenario, result);
								break;
							}
						}
					}
					
					
					if (log.isInfoEnabled()) {
						end = System.currentTimeMillis();
						log.info("Result Analysis View load time: "
								+ (end - start) + "ms");
					}
					
					return panel;
				}
				
				private Component showDeterministicResult(DTOScenario scenario){
					// start calculation
					Map<String, Calculable[]> result = scenario
							.getDCFMethod().calculate(scenario, true);

					// FIXME selection of result analyser plugin
					if (log.isInfoEnabled()) {
						start = System.currentTimeMillis();
					}
					
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

					if (log.isInfoEnabled()) {
						end = System.currentTimeMillis();
						log.info("Result Analysis View load time: "
								+ (end - start) + "ms");
					}
					
					return panel;
				}
			};

			// Prozedur zum Abrechen der Berechnung
			if (calcThread != null) {
				if (calcThread.isAlive()) {
					notInterrupted = false;
					if (progressBar != null) {
						progressBar.setVisible(false);
						TSprocess.setInterrupted();
					}
					b.changeText(BHScenarioForm.Key.CALCSHAREHOLDERVALUE);
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
