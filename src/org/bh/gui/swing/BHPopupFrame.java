package org.bh.gui.swing;

import java.awt.Image;

import javax.swing.JFrame;

import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

/**
 * This abstract class should be implemented by every popup.
 *
 * <p>
 * This abstract class should be implemented by every popup.
 *
 * @author Yannick Rödl
 * @version 1.0, 12.12.2011
 *
 */

@SuppressWarnings("serial")
public abstract class BHPopupFrame extends JFrame implements IPlatformListener {

	protected ITranslator translator = BHTranslator.getInstance();
	
	public enum ID{
		MAINTAIN_COMPANIES;
		
		public String toString(){
			return getClass().getName() + "." + super.toString();
		}
	}

	public BHPopupFrame(){
		super();
		
		Services.addPlatformListener(this);
		
		this.setTitle(translator.translate(this.getTitleKey()));
		
		this.setSize(400, 400);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImages(Services.setIcon());
	}
	
	public void setAdditionalMenuEntriesInMainFrame(BHMenuBar mainFrameMenuBar){}
	
	public String getUniqueId(){ return null; }
	
	public abstract String getTitleKey();
	
	@Override
	public void platformEvent(PlatformEvent e) {
		if(e.getEventType() == PlatformEvent.Type.LOCALE_CHANGED){
			//Reload the title of the popup
			this.setTitle(translator.translate(this.getTitleKey()));
		}
	}
	
	@Override
	public void dispose(){
//		Services.removePlatformListener(this);
		
		super.dispose();
	}
}
