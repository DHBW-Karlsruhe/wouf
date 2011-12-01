/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber * : Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
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
package org.bh.plugin.excelexport;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
// imports für xlsx
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.data.types.IValue;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

/**
 * Utility class for ExcelExport
 * 
 * @author Norman
 * @version 1.0, 16.01.2010
 * @update Vito Masiello 05.01.2011
 * @version 1.1
 * @comment XLS-Format wird ausgegeben
 */
public class XSSFDocumentBuilder {

	/** Logger for this class. */
	private static final Logger log = Logger
			.getLogger(XSSFDocumentBuilder.class);

	private static ITranslator trans = BHTranslator.getInstance();

	public enum Keys {
		TITLE, SCENARIO_SHEET, CREATEDAT, DATEFORMAT, PERIOD_SHEET, RESULT_SHEET, ;

		/* Specified by interface/super class. */
		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}

	HSSFWorkbook wb;
	CreationHelper crh;

	Font titleFont;
	Font sect1Font;
	Font sect2Font;
	CellStyle std;

	public void newDocument() {
		wb = new HSSFWorkbook();
		crh = wb.getCreationHelper();

		titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		titleFont.setFontHeightInPoints((short) 20);

		sect1Font = wb.createFont();
		sect1Font.setFontName("Arial");
		sect1Font.setFontHeightInPoints((short) 16);

		sect2Font = wb.createFont();
		sect2Font.setFontName("Arial");
		sect2Font.setFontHeightInPoints((short) 14);

		// default cell style
		std = wb.createCellStyle();
		std.setWrapText(true);
	}

	void buildScenarioSheet(DTOScenario scenario) {
		Sheet sheet;
		Row row;
		Row row2;
		Row tRow;
		Cell cell;
		CellStyle date;
		RichTextString str;

		// cell style for date
		date = wb.createCellStyle();
		date.setDataFormat(crh.createDataFormat().getFormat(
				trans.translate(Keys.DATEFORMAT)));
		date.setWrapText(true);

		// scenario data sheet
		sheet = wb.createSheet(trans.translate(Keys.SCENARIO_SHEET));
		sheet.setColumnWidth(0, 8000);
		sheet.setColumnWidth(1, 3500);

		// sheet content
		row = sheet.createRow(0);

		str = crh.createRichTextString(trans.translate(Keys.TITLE) + " - "
				+ scenario.get(DTOScenario.Key.NAME));
		str.applyFont(titleFont);

		row.createCell(0).setCellValue(str);

		row2 = sheet.createRow(1);
		str = crh.createRichTextString(trans.translate(Keys.CREATEDAT));
		row2.createCell(0).setCellValue(str);

		cell = row2.createCell(1);
		cell.setCellValue(new Date());
		cell.setCellStyle(date);

		int j = 4;
		for (Iterator<Entry<String, IValue>> i = scenario.iterator(); i
				.hasNext(); j++) {
			Map.Entry<String, IValue> val = i.next();
			tRow = sheet.createRow(j);

			str = crh.createRichTextString(trans.translate(val.getKey()));
			cell = tRow.createCell(0);
			cell.setCellStyle(std);
			cell.setCellValue(str);

			str = crh.createRichTextString(val.getValue().toString());
			cell = tRow.createCell(1);
			cell.setCellStyle(std);
			cell.setCellValue(val.getValue().toString());
		}
	}

	/**
	 * Adds a sheet for the given period to the xlsx file
	 * 
	 * @param scenario
	 */
	@SuppressWarnings("unchecked")
	void buildPeriodSheet(DTOScenario scenario) {
		Sheet sheet;
		Cell cell;
		Row row;
		RichTextString str;
		int rowCnt = 0;

		// period data sheet
		sheet = wb.createSheet(trans.translate(Keys.PERIOD_SHEET));
		sheet.setColumnWidth(0, 6500);
		sheet.setColumnWidth(1, 3000);

		// sheet content
		row = sheet.createRow(rowCnt);

		str = crh.createRichTextString(trans.translate(Keys.PERIOD_SHEET));
		str.applyFont(titleFont);
		row.createCell(0).setCellValue(str);

		for (DTOPeriod d : scenario.getChildren()) {
			rowCnt = rowCnt + 2;
			str = crh
					.createRichTextString(d.get(DTOPeriod.Key.NAME).toString());
			str.applyFont(sect1Font);
			row = sheet.createRow(rowCnt);
			row.createCell(0).setCellValue(str);

			for (IPeriodicalValuesDTO pv : d.getChildren()) {
				for (Iterator<Entry<String, IValue>> i = pv.iterator(); i
						.hasNext();) {
					Map.Entry<String, IValue> val = i.next();
					row = sheet.createRow(++rowCnt);

					str = crh.createRichTextString(trans
							.translate(val.getKey()));
					cell = row.createCell(0);
					cell.setCellStyle(std);
					cell.setCellValue(str);

					str = crh.createRichTextString(val.getValue().toString());
					cell = row.createCell(1);
					cell.setCellStyle(std);
					cell.setCellValue(str);
				}
			}
		}

	}

	/**
	 * * Adds a result sheet to the xlsx file
	 * 
	 * @param resultMap
	 */
	void buildResultSheet(Map<String, Calculable[]> resultMap) {
		Sheet sheet;
		Row row;
		Cell cell;
		RichTextString str;
		int rowCnt = 0;

		// result map sheet
		sheet = wb.createSheet(trans.translate(Keys.RESULT_SHEET));
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 6000);

		// sheet content
		row = sheet.createRow(rowCnt++);

		str = crh.createRichTextString(trans.translate(Keys.RESULT_SHEET));
		str.applyFont(titleFont);
		row.createCell(0).setCellValue(str);

		for (Entry<String, Calculable[]> e : resultMap.entrySet()) {
			Calculable[] val = e.getValue();
			if (val.length >= 1) {
				row = sheet.createRow(++rowCnt);
				str = crh.createRichTextString(trans.translate(e.getKey()));
				cell = row.createCell(0);
				cell.setCellStyle(std);
				cell.setCellValue(str);
				if (val[0] != null) {
					str = crh.createRichTextString(val[0].toString());
					cell = row.createCell(1);
					cell.setCellStyle(std);
					cell.setCellValue(str);
				}
			}
			if (val.length > 1) {
				for (int i = 1; i < val.length; i++) {
					if (val[i] != null) {
						row = sheet.createRow(++rowCnt);
						str = crh.createRichTextString(val[i].toString());
						cell = row.createCell(1);
						cell.setCellStyle(std);
						cell.setCellValue(str);
					}
				}
			}
		}
	}

	/**
	 * Adds a result sheet to the xlsx file
	 * 
	 * @param distMap
	 */
	void buildResultSheet(DistributionMap distMap) {
		Sheet sheet;
		Row row;
		Cell cell;
		RichTextString str;
		int rowCnt = 0;

		// distribution map sheet
		sheet = wb.createSheet(trans.translate(Keys.RESULT_SHEET));
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 6000);

		// sheet content
		row = sheet.createRow(rowCnt++);

		str = crh.createRichTextString(trans.translate(Keys.RESULT_SHEET));
		str.applyFont(titleFont);
		row.createCell(0).setCellValue(str);

		for (Iterator<Entry<Double, Integer>> i = distMap.iterator(); i
				.hasNext();) {
			row = sheet.createRow(++rowCnt);
			Entry<Double, Integer> val = i.next();

			str = crh.createRichTextString(val.getKey().toString());
			cell = row.createCell(0);
			cell.setCellStyle(std);
			cell.setCellValue(str);

			str = crh.createRichTextString(val.getValue().toString());
			cell = row.createCell(1);
			cell.setCellStyle(std);
			cell.setCellValue(str);
		}
	}

	/**
	 * finishes the xlsx file creation
	 * 
	 * @param path
	 */
	void closeDocument(String path) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
	}
}
