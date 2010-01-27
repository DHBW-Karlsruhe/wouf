package org.bh.plugin.pdfexport;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.platform.IPrint;
import org.jfree.chart.JFreeChart;

/**
 * Plug-in to print scenario reports
 * 
 * @author Norman
 * @version 1.0, 15.01.2010
 * 
 */
public class PDFPrint implements IPrint {
	
	private static final Logger log = Logger.getLogger(PDFPrint.class);
	
	ITextDocumentBuilder db = new ITextDocumentBuilder();

	@Override
	public void printProject(DTOProject project) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void printScenario(DTOScenario scenario) {
		try {
			PDDocument pDoc;
			File tmpFile;

			tmpFile = File.createTempFile("bh_print", "pdf");

			db.newDocument(tmpFile.getAbsolutePath(), scenario);
			db.buildHeadData(scenario);
			db.buildScenarioData(scenario);
			db.closeDocument();

			pDoc = PDDocument.load(tmpFile);
			pDoc.print();
		} catch (IOException e) {
			log.debug(e);
		} catch (PrinterException e) {
			log.debug(e);
		}
	}

	@Override
	public void printScenarioResults(DTOScenario scenario,
			Map<String, Calculable[]> results, List<JFreeChart> charts) {
		try {
			PDDocument pDoc;
			File tmpFile;
			
			tmpFile = File.createTempFile("bh_print", "pdf");
			
			db.newDocument(tmpFile.getAbsolutePath(), scenario);
			db.buildHeadData(scenario);
			db.buildScenarioData(scenario);
			db.buildResultDataDet(results, charts);
			db.closeDocument();
			
			pDoc = PDDocument.load(tmpFile);
			pDoc.print();
		
		} catch (IOException e) {
			log.error(e);
		} catch (PrinterException e) {
			log.error(e);
		}
	}

	@Override
	public void printScenarioResults(DTOScenario scenario, 
			DistributionMap results, List<JFreeChart> charts) {
		try {
			PDDocument pDoc;
			File tmpFile;

			tmpFile = File.createTempFile("bh_print", ".pdf");

			db.newDocument(tmpFile.getAbsolutePath(), scenario);
			db.buildHeadData(scenario);
			db.buildScenarioData(scenario);
			db.buildResultDataStoch(results, charts);
			db.closeDocument();

			pDoc = PDDocument.load(tmpFile);
			pDoc.print();
			
			tmpFile.deleteOnExit();
		} catch (IOException e) {
			log.debug(e);
		} catch (PrinterException e) {
			log.debug(e);
		}
	}

	@Override
	public int getSupportedMethods() {
		return IPrint.PRINT_SCENARIO + IPrint.PRINT_SCENARIO_RES;
	}

	@Override
	public String getUniqueId() {
		return "pdfprint";
	}
}
