package org.bh.gui.swing;

import java.util.Arrays;
import java.util.Comparator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.bh.data.types.IValue;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

public class BHComboBox extends JComboBox implements IBHModelComponent {
	private static final ITranslator translator = Services.getTranslator();
	private String key;
	private boolean sorted = false;
	private Item[] items = new Item[0];

	public BHComboBox(Object key) {
		this.key = key.toString();
	}

	public void setValueList(Item[] items) {
		this.items = items;
		if (sorted) {
			sortItems();
		}
		setModel(new DefaultComboBoxModel(this.items));
		updateUI();
	}

	@Override
	public IValue getValue() {
		return ((Item) this.getSelectedItem()).getValue();
	}

	@Override
	public void setValue(IValue value) {
		if (value == null)
			return;
		
		for (int i = 0; i < this.getItemCount(); i++) {
			Item item = (Item) this.getItemAt(i);
			if (value.equals(item.getValue())) {
				this.setSelectedIndex(i);
				return;
			}
		}
	}

	@Override
	public String getBHHint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public void reloadText() {
		this.updateUI();
	}

	@Override
	public void platformEvent(PlatformEvent e) {
	}

	@Override
	public int[] getValidateRules() {
		return new int[0];
	}

	@Override
	public void setValidateRules(int[] validateRules) {
		// nothing to do
	}

	public void setSorted(boolean sorted) {
		this.sorted = sorted;
		if (sorted) {
			sortItems();
		}
	}

	public boolean isSorted() {
		return sorted;
	}

	public void sortItems() {
		Arrays.sort(items, new Comparator<Item>() {
			@Override
			public int compare(Item o1, Item o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
	}

	public static class Item {
		private String key;
		private IValue value;

		public Item(String key, IValue value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String toString() {
			return translator.translate(key);
		}
		
		public String getKey() {
			return key;
		}

		public IValue getValue() {
			return value;
		}
	}
}
