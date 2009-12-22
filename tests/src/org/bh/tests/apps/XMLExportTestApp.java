package org.bh.tests.apps;

import java.awt.FlowLayout;
import java.util.ServiceLoader;

import javax.swing.JFrame;

import org.bh.controller.IController;
import org.bh.platform.PluginManager;
import org.bh.plugin.xmldataexchange.XMLDataExchangeController;

public class XMLExportTestApp extends JFrame {


	public static void main(String[] args)
	{
		new XMLExportTestApp();
	}
	
	
	
	public XMLExportTestApp()
	{
		setSize(300, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setVisible(true);
		
		
		PluginManager.getInstance().loadAllServices(IController.class);
		ServiceLoader<IController> controller = PluginManager.getInstance().getServices(IController.class);
		XMLDataExchangeController exportController = null;
		for (IController contrl : controller)
		{			
			contrl.getClass().getPackage().getName().equals("org.bh.plugin.xmldataexchange");
			{
				exportController = (XMLDataExchangeController) contrl;
				break;
			}
		}
		
		if (exportController == null)
			return;
		
		add(exportController.getView());
		
	}
	
	
}
