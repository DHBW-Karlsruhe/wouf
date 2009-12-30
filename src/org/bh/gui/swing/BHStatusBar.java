package org.bh.gui.swing;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 * BHStatusBar to display a status bar on the bottom of the screen
 * 
 * @author Tietze.Patrick
 * @version 0.1, 2009/12/05
 * 
 **/

public class BHStatusBar extends JPanel {

	private static BHStatusBar instance = null;
	JLabel bh;
	static JLabel lToolTip;
	CellConstraints cons;

	private BHStatusBar() {
				
		//setLayout to the status bar
		String rowDef = "p";
		String colDef = "0:grow(0.4),0:grow(0.2),0:grow(0.4)";
		setLayout(new FormLayout(colDef, rowDef));
		cons = new CellConstraints();

		// create tool tip label
		lToolTip = new JLabel();
		lToolTip.setText("");

		// create BH logo label
		bh = new JLabel(new ImageIcon(BHSplashScreen.class
				.getResource("/org/bh/images/bh-logo.jpg")));

		// add components to panel
		add(lToolTip, cons.xywh(1, 1, 1, 1));
		add(bh, cons.xywh(2, 1, 1, 1));
	}

	
	public static BHStatusBar getInstance() {
		if (instance == null) {
			instance = new BHStatusBar();
		}
		return instance;
	}

	/**
	 * create the status bar with the tooltip included
	 * 
	 * @param toolTip
	 */
	public void setToolTipLabel(JLabel toolTip) {
		//TODO Ausprogrammieren: Entweder "" oder Fehler anzeigen, falls vorhanden (evtl. mit "Link" zu 
		//Popup mit allen Fehlermeldungen. Beispiel:
		//Fremdkapital darf keine Buchstaben enthalten (hier klicken für alle Meldungen...)
		this.remove(lToolTip);
		lToolTip=toolTip;
		add(lToolTip, cons.xywh(1, 1, 1, 1));
		this.revalidate();
	}
	
	public void setToolTip(String toolTip){
		lToolTip.setText(toolTip);
		add(lToolTip, cons.xywh(1, 1, 1, 1));
		this.revalidate();
	}
	
	public void setToolTip(String toolTip, boolean alert){
		lToolTip.setText(toolTip);
		if(alert)lToolTip.setForeground(Color.red);
		add(lToolTip, cons.xywh(1, 1, 1, 1));
		this.revalidate();
	}
	
	public void removeToolTip(){
		lToolTip.setText(" ");
		add(lToolTip, cons.xywh(1, 1, 1, 1));
		this.revalidate();
	}

	public void setValidationToolTip(JScrollPane pane) {
		//TODO InfoText festlegen: ob erster Fehler aus Liste oder allgemeiner Hinweis!?
		//Key verwenden zwecks Übersetzbarkeit!
		lToolTip.setText("mehrere Fehler liegen vor (klicken)");
		//noch nicht fertig!
		add(lToolTip, cons.xywh(1, 1, 1, 1));
		this.revalidate();
	}

}
