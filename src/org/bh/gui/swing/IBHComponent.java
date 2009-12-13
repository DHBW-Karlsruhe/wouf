package org.bh.gui.swing;

/**
 * Interface for all Business Horizon Swing components.
 * <p>
 * This Interface defines a Business Horizon Swing component. All Swing
 * components used in Business Horizon must implement this interface.
 * 
 * @author Thiele.Klaus
 * @version 0.1, 2009/12/13
 * 
 */
public interface IBHComponent {
    /**
     * Returns the ID of the component.
     * 
     * @return ID as <code>String</code>
     */
    String getID();
}
