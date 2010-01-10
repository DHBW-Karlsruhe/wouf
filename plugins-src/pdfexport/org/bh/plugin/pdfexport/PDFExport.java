package org.bh.plugin.pdfexport;
import java.util.Map;

import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.platform.IImportExport;


/**
 * Plug-in to export certain scenarios to PDF as a kind of report 
 * 
 * @author Norman
 * @version 1.0, 10.01.2010
 * 
 */
public class PDFExport implements IImportExport {

	@Override
	public void exportProject(DTOProject project) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportProjectResults(DTOProject project,
			Map<String, Calculable[]> results) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportProjectResults(DTOProject project, DistributionMap results) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenario(DTOScenario scenario) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			Map<String, Calculable[]> results) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			DistributionMap results) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public int getSupportedMethods() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public DTOProject importProject() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public DTOScenario importScenario() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

}
