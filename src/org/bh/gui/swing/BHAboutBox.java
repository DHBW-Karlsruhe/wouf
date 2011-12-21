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
package org.bh.gui.swing;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.bh.platform.PlatformKey;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * About Box.
 * <p>
 * This class defines the About Dialog box.
 * 
 * @author Thiele.Klaus
 * @author Karithonov.Anton
 * 
 * @version 1.0, 2010/01/22
 * 
 */
@SuppressWarnings("serial")
public final class BHAboutBox extends JDialog implements ActionListener {

	private static final String IT_AUTHORS = "it_authors";
	private static final String BWL_AUTHORS = "bwl_authors";
	
	/**
	 * Okay button
	 */
	private JButton ok;
	
	/**
	 * translation instance
	 */
	private final ITranslator translator = Services.getTranslator();

	/**
	 * Constructor.
	 * 
	 * @param contentFrame main frame.
	 */
	public BHAboutBox(JFrame contentFrame) {
		super(contentFrame, true);
		this.initialize(contentFrame);
	}

	/**
	 * Inits the AboutBox.
	 *
	 * @param frame main frame.
	 */
	private void initialize(JFrame frame) {
		String rowDef = "p:grow,p:grow,p:grow,p:grow,p:grow,40px,20px,20px"; //
		String colDef = "2px,p:grow,p:grow,2px";			//Spaltendefinition


		FormLayout layout = new FormLayout(colDef, rowDef);

		CellConstraints cons = new CellConstraints();

		this.setLayout(layout);
		this.setTitle(this.translator.translate(PlatformKey.HELPINFO));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.ok = new JButton(this.translator.translate("Bokay"));
		this.ok.addActionListener(this);

		ImageIcon image = new ImageIcon(BHAboutBox.class
				.getResource("/org/bh/images/Aboutbox2.jpg"));
		int x = (frame.getWidth() - 480) / 2;
		int y = (frame.getHeight() - 600) / 2;
		JLabel frame_2 = new JLabel(image);
		this.add(new JLabel("<html>" + translator.translate("it_authors")  + translator.translate(IT_AUTHORS, ITranslator.LONG) +"<br\\><br\\>"
				  + translator.translate("bwl_authors") + translator.translate(BWL_AUTHORS, ITranslator.LONG) + "<br\\></html>"), cons.xywh(2, 2, 2, 2, "left, bottom"));
		this.add(frame_2, cons.xywh(2, 2, 2, 1));
		
		
		//this.add(new JLabel("<html>" + translator.translate("website") + ": " + translator.translate("website", ITranslator.LONG) + "</html>"), cons.xy(2, 4, "left, center"));
		//this.add(new JLabel("<html>" + translator.translate("email") + ": " + translator.translate("email", ITranslator.LONG) + "</html>"), cons.xy(2, 6, "left, center"));
		
		JEditorPane jep = new JEditorPane("text/html", translator.translate("website") + ": " + translator.translate("website", ITranslator.LONG));
		jep.setEditable(false);
        jep.setOpaque(false);
            jep.addHyperlinkListener(new HyperlinkListener() {
                public void hyperlinkUpdate(HyperlinkEvent hle) {
                      Desktop desk = Desktop.getDesktop();
                      if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                           try {
                                 desk.browse(new URI("http://www.businesshorizon.de"));
                           } catch (IOException e) {
                                 // TODO Auto-generated catch block
                                 e.printStackTrace();
                           } 
                           catch (URISyntaxException e) {
                                 // TODO Auto-generated catch block
                          	 e.printStackTrace();
                           }
                           System.out.println(hle.getURL());
                      }
                }
            });       
		this.add(jep,  cons.xy(2, 4, "center, center"));
		
		JEditorPane jep_2 = new JEditorPane("text/html", translator.translate("email") + ": " + translator.translate("email", ITranslator.LONG));
		this.add(jep_2,  cons.xy(2, 6, "center, center"));
		
		
		this.add(this.ok, cons.xywh(2, 8, 2, 1, "center, center"));
		this.setLocation(x, y);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);

	}
	
	/**
	 * Handle buttonclick.
	 */
	public void actionPerformed(ActionEvent e) {
		this.dispose();
	}
}