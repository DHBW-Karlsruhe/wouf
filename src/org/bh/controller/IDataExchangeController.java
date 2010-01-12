package org.bh.controller;

import java.awt.Container;
import java.util.List;

import javax.swing.JPanel;

import org.bh.data.IDTO;
import org.bh.platform.IDisplayablePlugin;

/**
 * 
 * <short_description>
 *
 * <p>
 * <detailed_description>
 *
 * @author Marcus
 * @version 1.0, 09.01.2010
 *
 */
public interface IDataExchangeController extends IDisplayablePlugin {
	
	/**
	 * 
	 * @return
	 */
	String getDataFormat();
	
	/**
	 * 
	 * @param type TODO
	 * @return
	 */
	JPanel getExportPanel(String type, Container cont);
	
	/**
	 * 	
	 * @param type TODO
	 * @return
	 */
	JPanel getImportPanel(String type, Container cont);
	
	
	/**
	 * 
	 * @param dtos
	 */
	void setModelAsList(List<IDTO<?>> dtos);
	
	/**
	 * 
	 * @return
	 */
	List<IDTO<?>> getModelAsList();
	
	
	/**
	 * 
	 * @param dto
	 */
	void setModel(IDTO<?> dto);
	
	/**
	 * 
	 * @return
	 */
	IDTO<?> getModel();
	

}
