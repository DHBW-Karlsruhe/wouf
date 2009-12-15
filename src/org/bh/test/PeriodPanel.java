package org.bh.test;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ServiceLoader;
import java.util.TreeSet;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.bh.controller.IPeriodGUIController;
import org.bh.data.DTOPeriod;
import org.bh.platform.PluginManager;
import org.bh.platform.i18n.BHTranslator;

public class PeriodPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -693286590811941534L;
	private static final BHTranslator t = BHTranslator.getInstance();
	
	ButtonGroup buttonGroup = new ButtonGroup();
	private DTOPeriod dto;
	JPanel pluginView;
	JRadioButton lastSelection = null;
	IPeriodGUIController lastController = null;
	TreeSet<PeriodRadioButton> radioButtonWrappers = new TreeSet<PeriodRadioButton>();

	public PeriodPanel(String year, DTOPeriod dto) {
		this.dto = dto; 
		this.setLayout(new GridLayout(0, 1));		
		
		PluginManager pluginManager = PluginManager.getInstance();
		ServiceLoader<IPeriodGUIController> periodControllers = pluginManager.getServices(IPeriodGUIController.class);
		for (IPeriodGUIController periodController : periodControllers) {
			radioButtonWrappers.add(new PeriodRadioButton(periodController));
		}
		
		this.add(new JLabel(year));
		
		for (PeriodRadioButton rb : radioButtonWrappers) {
			this.add(rb.getRadioButton());
		}
		
		pluginView = new JPanel();
		add(pluginView);
	}
	
	private class PeriodRadioButton implements Comparable<PeriodRadioButton> {
		private JRadioButton radioButton;
		private IPeriodGUIController guiController;
		
		PeriodRadioButton(IPeriodGUIController guiController) {
			this.guiController = guiController;
			this.radioButton = new JRadioButton(t.translate(guiController.getGuiKey()));
			buttonGroup.add(this.radioButton);
			this.radioButton.addActionListener(PeriodPanel.this);
		}

		@Override
		public int compareTo(PeriodRadioButton o) {
			return o.guiController.getGuiPriority() - this.guiController.getGuiPriority();
		}
		
		public JRadioButton getRadioButton() {
			return radioButton;
		}

		public IPeriodGUIController getGuiController() {
			return guiController;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == lastSelection)
			return;
		lastSelection = (JRadioButton) e.getSource();
		
		IPeriodGUIController guiController = null;
		for (PeriodRadioButton rb : radioButtonWrappers) {
			if (rb.getRadioButton() == lastSelection) {
				guiController = rb.getGuiController();
			}
		}
		
		if (lastController != null)
			lastController.stopEditing();
		
		try {
			lastController = guiController.getClass().newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		pluginView.removeAll();
		lastController.editDTO(dto, pluginView);
	}
	
	@Override
	public Dimension getPreferredSize() {
		Dimension size = super.getPreferredSize();
		size.width = 400;
		return size;
	}
	
	public void stopEditing() {
		if (lastController != null)
			lastController.stopEditing();
	}
}
