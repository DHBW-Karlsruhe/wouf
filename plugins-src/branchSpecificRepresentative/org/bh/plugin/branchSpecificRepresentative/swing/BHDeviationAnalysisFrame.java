package org.bh.plugin.branchSpecificRepresentative.swing;

import org.bh.gui.swing.BHPopupFrame;

/**
 * Popup for deviation analysis.
 *
 * <p>
 * This popup shows the deviation analysis of the calculation
 * of the branch specific representative. This means, that we will create a
 * chart displaying the different company values normed to an index and then
 * write all the business data we have into that chart including the 
 * industry specific representative. This last one must be better viewable
 * than all the other entries. So it should be added last.
 *
 * @author Yannick Rödl
 * @version 1.0, 12.12.2011
 *
 */

public class BHDeviationAnalysisFrame extends BHPopupFrame {

	public enum GUI_KEYS{
		TITLE;
		
		public String toString(){
			return getClass().getName() + "." + super.toString();
		}
	}
	
	/**
	 * Automatically generated <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 9090107160317277320L;

	/**
	 * Standard constructor initializing the chart and all the comboBoxes needed.
	 */
	public BHDeviationAnalysisFrame(){
		super();
		
		//Chart to display the deviation analysis
		
		//If possible: ComboBox to switch between algorithms
		
		
		this.setVisible(true);
	}
	
	@Override
	public String getTitleKey(){
		return BHDeviationAnalysisFrame.GUI_KEYS.TITLE.toString();
	}
	

}
