package org.bh.plugin.randomWalk;

import org.bh.gui.view.View;
import org.bh.gui.view.ViewException;
import org.bh.validation.ValidationMethods;

/**
 *
 * @author Patrick Heinz
 */
public class ViewRandomWalk extends View {

    public ViewRandomWalk() throws ViewException{
        super(new RandomWalk().calculateParameters(), new ValidationMethods());
    }
}
