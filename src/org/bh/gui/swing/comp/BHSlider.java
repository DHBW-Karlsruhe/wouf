package org.bh.gui.swing.comp;

import javax.swing.JSlider;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.bh.gui.IBHComponent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

public class BHSlider extends JSlider implements IBHComponent {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1447473844655945376L;
	private String key;
	private String hint;
	private static final Logger log = Logger.getLogger(BHSlider.class);

	public BHSlider(Object key, int min, int max, int value) {
		super(SwingConstants.HORIZONTAL, min, max, value);
		this.key = key.toString();
		if (this.key.isEmpty())
			log.debug("Empty key", new IllegalArgumentException());
		this.setMajorTickSpacing(10);
		this.setMinorTickSpacing(2);
		this.setPaintTicks(true);
		this.setPaintLabels(true);
	}

	@Override
	public String getHint() {
		return hint;
	}

	@Override
	public String getKey() {
		return key.toString();
	}

	protected void reloadText() {
		hint = Services.getTranslator().translate(key, ITranslator.LONG);
		setToolTipText(hint);
	}

}