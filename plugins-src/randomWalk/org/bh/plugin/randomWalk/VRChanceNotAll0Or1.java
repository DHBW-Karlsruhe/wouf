package org.bh.plugin.randomWalk;

import java.util.Iterator;
import java.util.Map;

import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;
import org.bh.gui.swing.IBHModelComponent;
import org.bh.validation.ValidationRule;

import com.jgoodies.validation.ValidationResult;

public class VRChanceNotAll0Or1 extends ValidationRule {
	
	public static final VRChanceNotAll0Or1 INSTANCE = VRChanceNotAll0Or1.getInstance();
	
	private static VRChanceNotAll0Or1 instance;
	private Map<String, IBHComponent> map;
	
	public VRChanceNotAll0Or1 (){
		instance = this;
	}
	
	private static VRChanceNotAll0Or1 getInstance(){
		if(VRChanceNotAll0Or1.INSTANCE == null){
			return new VRChanceNotAll0Or1();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		
		boolean allZero = false;
		boolean allOne = false;
		
		Iterator iterator = map.entrySet().iterator();
		int mapsize = map.size();
		
		for (int i = 0; i < mapsize; i++) {
			
			Map.Entry entry = (Map.Entry)iterator.next();
			String key = (String)entry.getKey();
			
			if(key.contains("chance")) {
				BHTextField tf_toCheck = (BHTextField)entry.getValue();
				double value = Double.parseDouble(tf_toCheck.getText());		
				if(value == 0) {
					allZero = true;
				}
				else if (value == 1) {
					allOne = true;
				}
				else {
					allZero = allOne = false;
					break;
				}
			}
		}
		if(allZero == true || allOne == true) {
			validationResult.addWarning(translator.translate("WchanceAll0Or1"));
		}
		return validationResult;
	}
	
	public void setTextCompMap(Map<String, IBHComponent> map) {
		this.map = map;
	}
}
