package org.bh.platform;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.IStochasticProcess;
import org.bh.controller.IPeriodController;
import org.bh.controller.InputController;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.StringValue;
import org.bh.gui.ICompValueChangeListener;
import org.bh.gui.IDeterministicResultAnalyser;
import org.bh.gui.View;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHComboBox;
import org.bh.gui.swing.BHMainFrame;
import org.bh.gui.swing.BHScenarioForm;
import org.bh.gui.swing.BHSelectionList;
import org.bh.gui.swing.BHStochasticInputForm;
import org.bh.gui.swing.BHTree;
import org.bh.gui.swing.BHTreeNode;
import org.bh.gui.swing.IBHModelComponent;
import org.bh.platform.PlatformEvent.Type;

public class ScenarioController extends InputController {
	protected static final BHComboBox.Item[] DCF_METHOD_ITEMS = getDcfMethodItems();
	protected static final BHComboBox.Item[] PERIOD_TYPE_ITEMS = getPeriodTypeItems();
	protected static final BHComboBox.Item[] STOCHASTIC_METHOD_ITEMS = getStochasticProcessItems();

	public ScenarioController(View view, IDTO<?> model, final BHMainFrame bhmf) {
		super(view, model);

		BHComboBox cbDcfMethod = (BHComboBox) view
				.getBHComponent(DTOScenario.Key.DCF_METHOD);
		if (cbDcfMethod != null) {
			cbDcfMethod.setSorted(true);
			cbDcfMethod.setValueList(DCF_METHOD_ITEMS);
		}

		final BHComboBox cbPeriodType = (BHComboBox) view
				.getBHComponent(DTOScenario.Key.PERIOD_TYPE);
		if (cbPeriodType != null) {
			cbPeriodType.setValueList(PERIOD_TYPE_ITEMS);
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
												Services
														.getTranslator()
														.translate(
																"PstochasticPeriodTypeChangeText"),
												Services
														.getTranslator()
														.translate(
																"PstochasticPeriodTypeChangeHeader"));
								if (action == JOptionPane.YES_OPTION) {
									bhmf.getBHTree().removeAllPeriods(scenario);
								}else if (action == JOptionPane.NO_OPTION) {
									cbPeriodType.setSelectedIndex(cbPeriodType
											.getLastSelectedIndex());
									return;
								}
							}

							updateStochasticFieldsList();
						}
					});
		}

		BHComboBox cbStochasticMethod = (BHComboBox) view
				.getBHComponent(DTOScenario.Key.STOCHASTIC_PROCESS);
		if (cbStochasticMethod != null) {
			cbStochasticMethod.setSorted(true);
			cbStochasticMethod.setValueList(STOCHASTIC_METHOD_ITEMS);
		}

		((BHButton) view.getBHComponent(PlatformKey.CALCSHAREHOLDERVALUE))
				.addActionListener(new CalculationListener(PlatformController
						.getInstance().getMainFrame().getBHTree()));

		BHButton calcStochasticParameters = ((BHButton) view
				.getBHComponent(BHStochasticInputForm.Key.CALC_PARAMETERS));
		BHButton resetStochasticParameters = ((BHButton) view
				.getBHComponent(BHStochasticInputForm.Key.RESET_PARAMETERS));
		if (calcStochasticParameters != null
				&& resetStochasticParameters != null) {
			calcStochasticParameters.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					DTOScenario scenario = (DTOScenario) getModel();
					IStochasticProcess process = scenario
							.getStochasticProcess().createNewInstance();
					process.setScenario(scenario);
					JPanel parametersPanel = process.calculateParameters();
					BHStochasticInputForm form = (BHStochasticInputForm) ((Component) e
							.getSource()).getParent();
					form.setParametersPanel(parametersPanel);
				}
			});

			resetStochasticParameters.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					throw new UnsupportedOperationException(
							"This method has not been implemented");
				}
			});
		}

		setCalcEnabled(getModel().isValid(true));

		loadAllToView();
		updateStochasticFieldsList();
	}

	protected void updateStochasticFieldsList() {
		DTOScenario scenario = (DTOScenario) getModel();
		BHComboBox cbPeriodType = (BHComboBox) view
				.getBHComponent(DTOScenario.Key.PERIOD_TYPE);
		if (cbPeriodType != null && !scenario.isDeterministic()) {
			BHSelectionList slStochasticKeys = (BHSelectionList) getView()
					.getBHComponent(DTOScenario.Key.STOCHASTIC_KEYS);
			List<DTOKeyPair> keyPair = Services.getPeriodController(
					cbPeriodType.getValue().toString()).getStochasticKeys();
			slStochasticKeys.setModel(keyPair.toArray());
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
		((Component) view.getBHComponent(PlatformKey.CALCSHAREHOLDERVALUE))
				.setEnabled(calculationEnabled);
		((Component) view
				.getBHComponent(BHScenarioForm.Key.CANNOT_CALCULATE_HINT))
				.setVisible(!calculationEnabled);
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

	protected class CalculationListener implements ActionListener {

		private final ImageIcon LOADING = Services.createImageIcon(
				"/org/bh/images/loading.gif", null);
		private BHTree bhTree;

		public CalculationListener(BHTree bhTree) {
			this.bhTree = bhTree;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			final JButton b = (JButton) e.getSource();
			b.setIcon(this.LOADING);

			Runnable r = new Runnable() {

				@Override
				public void run() {
					DTOScenario scenario = (DTOScenario) getModel();
					// start calculation
					// TODO Marco.Hammel call of all avaiable dcf methods
					Map<String, Calculable[]> result = Collections
							.synchronizedMap(new HashMap<String, Calculable[]>());

					if (scenario.getDCFMethod() != null) {// TODO Marco.Hammel
						// getDCFMethod().equals(ALLDCF)
						result = scenario.getDCFMethod().calculate(scenario);
					} else {
						// TODO Marco.Hammel ways to increase stability and
						// performance by using threads?
						for (IShareholderValueCalculator dcfMethod : Services
								.getDCFMethods().values()) {
							if (dcfMethod != null)
								result.putAll(dcfMethod.calculate(scenario));
						}
					}

					// TODO maybe setResult should return the panel of the view
					JPanel panel = new JPanel();

					// FIXME selection of result analyser plugin
					for (IDeterministicResultAnalyser analyser : PluginManager
							.getInstance().getServices(
									IDeterministicResultAnalyser.class)) {
						analyser.setResult(scenario, result, panel);
						break;
					}
					JSplitPane crForm = Services.createContentResultForm(panel);
					BHTreeNode tn = (BHTreeNode) bhTree.getSelectionPath()
							.getPathComponent(2);

					tn.setBackgroundPane(crForm);

					b.setIcon(null);
				}
			};
			new Thread(r, "Calculation Thread").start();
		}
	}
}
