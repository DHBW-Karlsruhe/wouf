package org.bh.gui.swing;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class BHAboutBox {

	JDialog about;
	JButton ok;
	final ITranslator translator = Services.getTranslator();

	public BHAboutBox(JFrame contentFrame) {
		this.initialize(contentFrame);
	}

	public void initialize(JFrame frame) {
		String rowDef = "2px,p,4px,p,8px,p,14px,p,4px";
		String colDef = "2px,40px,p:grow,2px";

		FormLayout layout = new FormLayout(colDef, rowDef);

		CellConstraints cons = new CellConstraints();

		about = new JDialog(frame, true);
		about.setLayout(layout);

		ImageIcon image = new ImageIcon(BHAboutBox.class
				.getResource("/org/bh/images/AboutBox.jpg"));
		int x = (frame.getWidth() - 480) / 2;
		int y = (frame.getHeight() - 600) / 2;

		about.add(new JLabel(image), cons.xywh(2, 2, 2, 1));
		about.add(new JLabel("<html><body>" + translator.translate("website")
				+ ": " + translator.translate("website_long")
				+ "</body></html>"), cons.xy(3, 4));
		about.add(
				new JLabel("<html><body>" + translator.translate("email")
						+ ": " + translator.translate("email_long")
						+ "</body></html>"), cons.xy(3, 6));
		about.add(this.getOk(), cons.xywh(2, 8, 2, 1, "center, center"));
		about.setLocation(x, y);
		about.setResizable(false);
		about.pack();
		about.show();

	}

	public JButton getOk() {
		if (ok == null) {
			ok = new JButton("OK");
			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					about.hide();
				}
			});
		}
		return ok;
	}
	// public static void main(String [] args){
	// JFrame frame = new JFrame();
	// frame.setContentPane(new JPanel());
	// BHAboutBox box = new BHAboutBox(frame);
	// frame.addWindowListener(new WindowAdapter() {
	// @Override
	// public void windowClosing(WindowEvent e) {
	// System.exit(0);
	// }
	// });
	// frame.pack();
	// frame.show();
	//	
	// }
}