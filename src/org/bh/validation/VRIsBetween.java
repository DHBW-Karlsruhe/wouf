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
package org.bh.validation;

import org.bh.gui.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

/**
 * This class contains validation rules to check a textfield's value
 * being between two other values (using validation rules VRIsGreaterThan
 * and VRIsLowerThan).
 * 
 * @author Robert Vollmer, Patrick Heinz
 * @version 1.0, 22.01.2010
 * @update 17.01.2012 Lukas Lochner
 * 
 */
public class VRIsBetween extends ValidationRule {
	public static final VRIsBetween BETWEEN0AND100 = new VRIsBetween(0, 100);
	public static final VRIsBetween BETWEEN1900AND2100 = new VRIsBetween(1900, 2100);
	public static final VRIsBetween BETWEEN100AND5000 = new VRIsBetween(100, 5000);
	public static final VRIsBetween BETWEEN1970AND2030 = new VRIsBetween(1970, 2030);		
	
	private VRIsGreaterThan vrIsGreaterThan;
	private VRIsLowerThan vrIsLowerThan;
	
	private int lowerBound;
	private int upperBound;
	
	public VRIsBetween(int lowerBound, int upperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		vrIsGreaterThan = new VRIsGreaterThan(lowerBound);
		vrIsLowerThan = new VRIsLowerThan(upperBound);
	}
	
	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if ( vrIsGreaterThan.validate(comp).hasMessages()
				|| vrIsLowerThan.validate(comp).hasMessages()) {
			validationResult.addError(translator.translate("Efield") + " '"
					+ translator.translate(comp.getKey()) + "' "
					+ translator.translate("EisBetween") + " "
					+ lowerBound + " "
					+ translator.translate("Eand") + " "
					+ upperBound + ".");
		}
		return validationResult;
	}
}
