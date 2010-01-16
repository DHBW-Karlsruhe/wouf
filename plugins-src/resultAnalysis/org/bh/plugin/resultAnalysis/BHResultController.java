/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.resultAnalysis;

import java.util.Map;

import org.bh.controller.OutputController;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.gui.View;
import org.bh.gui.chart.IBHAddValue;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

/**
 * 
 * @author Marco Hammel
 * @author Sebastian Scharfenberger
 */
public class BHResultController extends OutputController {

	public static enum ChartKeys {
		APV_WF_SV, APV_BC_CS, FCF_WF_SV, FCF_BC_CS, FCF_BC_FCF, FCF_BC_RR, FTE_BC_SV, FTE_BC_CS, FTE_BC_FTE;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}

	}

	public BHResultController(View view, Map<String, Calculable[]> result,
			DTOScenario scenario) {
		super(view, result, scenario);
	}

	@Override
	public void setResult(Map<String, Calculable[]> result, DTOScenario scenario) {
		ITranslator translator = Services.getTranslator();
		super.setResult(result, scenario);

		if (scenario.getDCFMethod().getUniqueId().equals("apv")) {

			IBHAddValue comp = super.view.getBHchartComponents().get(
					ChartKeys.APV_WF_SV.toString());
			if (comp != null) {
				comp
						.addValue(
								result
										.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF")[0]
										.parse(),
								translator.translate(ChartKeys.APV_WF_SV),
								translator
										.translate("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF"));
				comp
						.addValue(
								result
										.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_TAX_SHIELD")[0]
										.parse(),
								translator.translate(ChartKeys.APV_WF_SV),
								translator
										.translate("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_TAX_SHIELD"));
				comp
						.addValue(
								(result
										.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[0]
										.parse().doubleValue() * -1),
								translator.translate(ChartKeys.APV_WF_SV),
								translator
										.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT"));
				comp
						.addValue(
								result
										.get("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE")[0]
										.parse(),
								translator.translate(ChartKeys.APV_WF_SV),
								translator
										.translate("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE"));
			}
			IBHAddValue comp2 = super.view.getBHchartComponents().get(
					ChartKeys.APV_BC_CS.toString());
			if (comp2 != null) {
				int length = result
						.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF").length;
				for (int i = 0; i < length; i++) {
					String name = scenario.getChildren().get(i).get(
							DTOPeriod.Key.NAME).toString();
					comp2
							.addValue(
									result
											.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF")[i]
											.parse(),
									translator
											.translate("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF"),
									name);
					comp2
							.addValue(
									result
											.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[i]
											.parse(),
									translator
											.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT"),
									name);
				}
			}

		} else if (scenario.getDCFMethod().getUniqueId().equals("fcf")) {

			IBHAddValue comp = super.view.getBHchartComponents().get(
					ChartKeys.FCF_WF_SV.toString());
			if (comp != null) {
				comp
						.addValue(
								result
										.get("org.bh.plugin.fcf.FCFCalculator$Result.TOTAL_CAPITAL")[0]
										.parse(),
								translator.translate(ChartKeys.FCF_WF_SV),
								translator
										.translate("org.bh.plugin.fcf.FCFCalculator$Result.TOTAL_CAPITAL"));
				comp
						.addValue(
								result
										.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[0]
										.parse().doubleValue()
										* -1,
								translator.translate(ChartKeys.FCF_WF_SV
										.toString()),
								translator
										.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT"));
				comp
						.addValue(
								result
										.get("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE")[0]
										.parse(),
								translator.translate(ChartKeys.FCF_WF_SV
										.toString()),
								translator
										.translate("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE"));
			}
			IBHAddValue comp2 = super.view.getBHchartComponents().get(
					ChartKeys.FCF_BC_CS.toString());
			if (comp2 != null) {
				for (int i = 0; i < result
						.get("org.bh.plugin.fcf.FCFCalculator$Result.PRESENT_VALUE_TAX_SHIELD").length; i++) {
					String name = scenario.getChildren().get(i).get(
							DTOPeriod.Key.NAME).toString();
					comp2
							.addValue(
									result
											.get("org.bh.plugin.fcf.FCFCalculator$Result.PRESENT_VALUE_TAX_SHIELD")[i]
											.parse(),
									translator
											.translate("org.bh.plugin.fcf.FCFCalculator$Result.PRESENT_VALUE_TAX_SHIELD"),
									name);
					comp2
							.addValue(
									result
											.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[i]
											.parse(),
									translator
											.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT"),
									name);
				}
			}
			IBHAddValue comp3 = super.view.getBHchartComponents().get(
					ChartKeys.FCF_BC_FCF.toString());
			if (comp3 != null) {
				if (result
						.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW")[0] != null) {
					String name = scenario.getChildren().get(0).get(
							DTOPeriod.Key.NAME).toString();
					comp3
							.addValue(
									result
											.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW")[0]
											.parse(), translator
											.translate(ChartKeys.FCF_BC_FCF
													.toString()), name);
				}
				for (int i = 1; i < result
						.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW").length; i++) {
					String name = scenario.getChildren().get(i).get(
							DTOPeriod.Key.NAME).toString();
					comp3
							.addValue(
									result
											.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW")[i]
											.parse(), translator
											.translate(ChartKeys.FCF_BC_FCF
													.toString()), name);
				}
			}
			IBHAddValue comp4 = super.view.getBHchartComponents().get(
					ChartKeys.FCF_BC_RR.toString());
			if (comp4 != null) {
				for (int i = 0; i < result
						.get("org.bh.plugin.fcf.FCFCalculator$Result.EQUITY_RETURN_RATE_FCF").length; i++) {
					String name = scenario.getChildren().get(i).get(
							DTOPeriod.Key.NAME).toString();
					comp4
							.addValue(
									result
											.get("org.bh.plugin.fcf.FCFCalculator$Result.EQUITY_RETURN_RATE_FCF")[i]
											.parse(),
									translator
											.translate("org.bh.plugin.fcf.FCFCalculator$Result.EQUITY_RETURN_RATE_FCF"),
									name);
					comp4
							.addValue(
									result
											.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT_RETURN_RATE")[0]
											.parse(),
									translator
											.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT_RETURN_RATE"),
									name);
				}
			}
		} else if (scenario.getDCFMethod().getUniqueId().equals("fte")) {

			IBHAddValue comp = super.view.getBHchartComponents().get(
					ChartKeys.FTE_BC_SV.toString());
			if (comp != null) {
				comp
						.addValue(
								result
										.get("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE")[0]
										.parse(), translator
										.translate(ChartKeys.FTE_BC_SV
												.toString()), translator
										.translate(ChartKeys.FTE_BC_SV
												.toString()));
			}
			IBHAddValue comp2 = super.view.getBHchartComponents().get(
					ChartKeys.FTE_BC_CS.toString());
			if (comp2 != null) {
				for (int i = 0; i < result
						.get("org.bh.plugin.fte.FTECalculator$Result.PRESENT_VALUE_TAX_SHIELD").length; i++) {
					String name = scenario.getChildren().get(i).get(
							DTOPeriod.Key.NAME).toString();
					comp2
							.addValue(
									result
											.get("org.bh.plugin.fte.FTECalculator$Result.PRESENT_VALUE_TAX_SHIELD")[i]
											.parse(),
									translator
											.translate("org.bh.plugin.fte.FTECalculator$Result.PRESENT_VALUE_TAX_SHIELD"),
									name);
					comp2
							.addValue(
									result
											.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[i]
											.parse(),
									translator
											.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT"),
									name);
				}
			}
			IBHAddValue comp3 = super.view.getBHchartComponents().get(
					ChartKeys.FTE_BC_FTE.toString());
			if (comp3 != null) {
				for (int i = 1; i < result
						.get("org.bh.plugin.fte.FTECalculator$Result.FLOW_TO_EQUITY").length; i++) {
					String name = scenario.getChildren().get(i).get(
							DTOPeriod.Key.NAME).toString();
					comp3
							.addValue(
									result
											.get("org.bh.plugin.fte.FTECalculator$Result.FLOW_TO_EQUITY")[i]
											.parse(),
									translator
											.translate("org.bh.plugin.fte.FTECalculator$Result.FLOW_TO_EQUITY"),
									name);
				}
			}
		} else {
			IBHAddValue comp = super.view.getBHchartComponents().get(
					ChartKeys.APV_WF_SV.toString());
			if (comp != null) {
				comp
						.addValue(
								result
										.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF")[0]
										.parse(),
								translator.translate(ChartKeys.APV_WF_SV),
								translator
										.translate("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF"));
				comp
						.addValue(
								result
										.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_TAX_SHIELD")[0]
										.parse(),
								translator.translate(ChartKeys.APV_WF_SV),
								translator
										.translate("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_TAX_SHIELD"));
				comp
						.addValue(
								(result
										.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[0]
										.parse().doubleValue() * -1),
								translator.translate(ChartKeys.APV_WF_SV),
								translator
										.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT"));
				comp
						.addValue(
								result
										.get("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE")[0]
										.parse(),
								translator.translate(ChartKeys.APV_WF_SV),
								translator
										.translate("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE"));
			}
			IBHAddValue comp2 = super.view.getBHchartComponents().get(
					ChartKeys.APV_BC_CS.toString());
			if (comp2 != null) {
				int length = result
						.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF").length;
				for (int i = 0; i < length; i++) {
					String name = scenario.getChildren().get(i).get(
							DTOPeriod.Key.NAME).toString();
					comp2
							.addValue(
									result
											.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF")[i]
											.parse(),
									translator
											.translate("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF"),
									name);
					comp2
							.addValue(
									result
											.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[i]
											.parse(),
									translator
											.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT"),
									name);
				}
			}
			IBHAddValue comp3 = super.view.getBHchartComponents().get(
					ChartKeys.FCF_WF_SV.toString());
			if (comp3 != null) {
				comp3
						.addValue(
								result
										.get("org.bh.plugin.fcf.FCFCalculator$Result.TOTAL_CAPITAL")[0]
										.parse(),
								translator.translate(ChartKeys.FCF_WF_SV),
								translator
										.translate("org.bh.plugin.fcf.FCFCalculator$Result.TOTAL_CAPITAL"));
				comp3
						.addValue(
								result
										.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[0]
										.parse().doubleValue()
										* -1,
								translator.translate(ChartKeys.FCF_WF_SV
										.toString()),
								translator
										.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT"));
				comp3
						.addValue(
								result
										.get("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE")[0]
										.parse(),
								translator.translate(ChartKeys.FCF_WF_SV
										.toString()),
								translator
										.translate("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE"));
			}
			IBHAddValue comp4 = super.view.getBHchartComponents().get(
					ChartKeys.FCF_BC_FCF.toString());
			if (comp4 != null) {
				if (result
						.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW")[0] != null) {
					String name = scenario.getChildren().get(0).get(
							DTOPeriod.Key.NAME).toString();
					comp4
							.addValue(
									result
											.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW")[0]
											.parse(), translator
											.translate(ChartKeys.FCF_BC_FCF
													.toString()), name);
				}
				for (int i = 1; i < result
						.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW").length; i++) {
					String name = scenario.getChildren().get(i).get(
							DTOPeriod.Key.NAME).toString();
					comp4
							.addValue(
									result
											.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW")[i]
											.parse(), translator
											.translate(ChartKeys.FCF_BC_FCF
													.toString()), name);
				}
			}
			IBHAddValue comp5 = super.view.getBHchartComponents().get(
					ChartKeys.FCF_BC_RR.toString());
			if (comp5 != null) {
				for (int i = 0; i < result
						.get("org.bh.plugin.fcf.FCFCalculator$Result.EQUITY_RETURN_RATE_FCF").length; i++) {
					String name = scenario.getChildren().get(i).get(
							DTOPeriod.Key.NAME).toString();
					comp5
							.addValue(
									result
											.get("org.bh.plugin.fcf.FCFCalculator$Result.EQUITY_RETURN_RATE_FCF")[i]
											.parse(),
									translator
											.translate("org.bh.plugin.fcf.FCFCalculator$Result.EQUITY_RETURN_RATE_FCF"),
									name);
					comp5
							.addValue(
									result
											.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT_RETURN_RATE")[0]
											.parse(),
									translator
											.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT_RETURN_RATE"),
									name);
				}
			}
			IBHAddValue comp6 = super.view.getBHchartComponents().get(
					ChartKeys.FTE_BC_FTE.toString());
			if (comp6 != null) {
				for (int i = 1; i < result
						.get("org.bh.plugin.fte.FTECalculator$Result.FLOW_TO_EQUITY").length; i++) {
					String name = scenario.getChildren().get(i).get(
							DTOPeriod.Key.NAME).toString();
					comp6
							.addValue(
									result
											.get("org.bh.plugin.fte.FTECalculator$Result.FLOW_TO_EQUITY")[i]
											.parse(),
									translator
											.translate("org.bh.plugin.fte.FTECalculator$Result.FLOW_TO_EQUITY"),
									name);
				}
			}
		}
	}
}
