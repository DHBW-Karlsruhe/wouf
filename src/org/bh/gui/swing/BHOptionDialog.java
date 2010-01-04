package org.bh.gui.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.WindowConstants;

import org.bh.platform.Services;
import org.bh.platform.i18n.BHTranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class BHOptionDialog extends JDialog implements ActionListener {

	private CellConstraints cons;

	private BHLabel language;
	JComboBox combo;
	private BHButton apply;

	public BHOptionDialog() {
		this.setProperties();

		// setLayout to the status bar
		String rowDef = "p";
		String colDef = "0:grow(0.05),0:grow(0.3),0:grow(0.3),0:grow(0.3),0:grow(0.05)";
		setLayout(new FormLayout(colDef, rowDef));
		cons = new CellConstraints();

		// create select language components
		language = new BHLabel("", BHTranslator.getInstance().translate(
				"MoptionsLanguage"));

		combo = new JComboBox(Services.getTranslator().getAvaiableLocales());
		combo.setRenderer(new BHLanguageRenderer());
		

		apply = new BHButton("");
		apply.setText(BHTranslator.getInstance().translate("Bapply"));
		apply.addActionListener(this);

		// add components
		add(language, cons.xywh(2, 1, 1, 1, "right,center"));
		add(combo, cons.xywh(3, 1, 1, 1));
		add(apply, cons.xywh(4, 1, 1, 1));

	}

	private void setProperties() {
		this.setTitle(BHTranslator.getInstance().translate("MoptionsDialog"));
		this.setSize(400, 200);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Services.getTranslator().setLocale((Locale) combo.getSelectedItem());
	}
	
	public class BHLanguageRenderer implements ListCellRenderer {
		private JLabel l = null;
		
		public BHLanguageRenderer() {
			l = new JLabel();
			l.setPreferredSize(new Dimension(l.getPreferredSize().width, 30));
		}
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			
			l.setText(((Locale) value).getDisplayLanguage(Services.getTranslator().getLocale()));
			return l;
			
		}
			
	}
}
