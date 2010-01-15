package org.bh.plugin.pdfexport;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.platform.IPrint;

import com.itextpdf.text.pdf.PdfPrinterGraphics2D;

public class PDFPrint implements IPrint {
	private static final Logger log = Logger.getLogger(PDFPrint.class);
	ITextDocumentBuilder db = new ITextDocumentBuilder();

	@Override
	public void printProject(DTOProject project) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void printScenario(DTOScenario scenario) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void printScenarioResults(DTOScenario scenario,
			Map<String, Calculable[]> results) {

		try {
			File tmpFile = File.createTempFile("bh_print", "pdf");
			db.newDocument(tmpFile
					.getAbsolutePath(), scenario);

			db.buildHeadData(scenario);
			db.buildScenarioData(scenario);
			db.buildResultDataDet(results);
			db.closeDocument();
		
			PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
			DocPrintJob printerJob = defaultPrintService.createPrintJob();
			SimpleDoc simpleDoc = new SimpleDoc(tmpFile.toURI().toURL(), DocFlavor.URL.AUTOSENSE, null);
			PrinterJob p = PrinterJob.getPrinterJob();
			PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
			if (p.printDialog(attributes)) {
				log.debug("print started");
				printerJob.print(simpleDoc,attributes);
				log.debug("print ended");
			}
//			Document doc = new Document();
//			
//			PdfWriter pw;
//			
//				pw = PdfWriter.getInstance(doc,
//						new FileOutputStream("test.pdf"));
//				doc.open();
//				doc.add(new Paragraph("Test"));
//				doc.close();
//				PdfContentByte cb = new PdfContentByte(db.pdfWriter);
//				// cb.endText();
//				FontMapper f = new DefaultFontMapper();
//				PdfPrinterGraphics2D pg = new PdfPrinterGraphics2D(cb, 5.0f,
//						5.0f, f, false, true, 8, PrinterJob.getPrinterJob());
//				PrinterJob p = pg.getPrinterJob();
//				new TestFrame(pg);
//
//			
//				PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
//				if (p.printDialog(attributes)) {
//					try {
//						log.debug("print started");
//						p.print(attributes);
//						log.debug("print ended");
//					} catch (PrinterException e) {
//						log.error(e);
//					}
//				}
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PrintException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void printScenarioResults(DTOScenario scenario,
			DistributionMap results) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public int getSupportedMethods() {
		return IPrint.PRINT_SCENARIO + IPrint.PRINT_SCENARIO_RES;
	}

	@Override
	public String getUniqueId() {
		return "pdfprint";
	}
	
	public class TestFrame extends JFrame{
		TestFrame(PdfPrinterGraphics2D pg ){
			setSize(500,500);
			this.paint(pg);
			setVisible(true);
			this.repaint();
		}
	}
}
