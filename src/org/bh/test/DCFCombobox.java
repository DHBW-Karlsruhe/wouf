package org.bh.test;

import java.util.ArrayList;
import java.util.ServiceLoader;

import javax.swing.JComboBox;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.platform.PluginManager;
import org.bh.platform.i18n.BHTranslator;

public class DCFCombobox extends JComboBox {
	private static final long serialVersionUID = 4377969322269439578L;
	private static final BHTranslator t = BHTranslator.getInstance();
	private static DCFWrapper[] dcfs;
	
	static {
		ArrayList<DCFWrapper> list = new ArrayList<DCFWrapper>();
		ServiceLoader<IShareholderValueCalculator> calculators = PluginManager.getInstance().getServices(IShareholderValueCalculator.class);
		for (IShareholderValueCalculator calculator : calculators) {
			list.add(new DCFWrapper(calculator));
		}
		dcfs = list.toArray(new DCFWrapper[0]);
	}
	
	public DCFCombobox() {
		super(dcfs);
	}
	
	public IShareholderValueCalculator getCalculator() {
		return ((DCFWrapper)getSelectedItem()).getCalculator();
	}
	
	private static class DCFWrapper {
		IShareholderValueCalculator calculator;
		
		public DCFWrapper(IShareholderValueCalculator calculator) {
			this.calculator = calculator;
		}

		public IShareholderValueCalculator getCalculator() {
			return calculator;
		}
		
		@Override
		public String toString() {
			return t.translate(calculator.getGuiKey());
		}
	}
}
