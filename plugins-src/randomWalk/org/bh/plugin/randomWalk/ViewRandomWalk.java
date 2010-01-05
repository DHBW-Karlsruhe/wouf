package org.bh.plugin.randomWalk;

import org.bh.gui.View;
import org.bh.gui.ViewException;

/**
 *
 * @author Patrick Heinz
 */
public class ViewRandomWalk extends View {

    public ViewRandomWalk() throws ViewException{
    	// FIXME add validation engine
        super(new RandomWalk().calculateParameters(), null);
    }
}
