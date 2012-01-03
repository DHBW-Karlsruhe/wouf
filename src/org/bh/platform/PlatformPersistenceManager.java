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
package org.bh.platform;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.bh.data.DTOProject;
import org.bh.gui.swing.BHMainFrame;
import org.bh.gui.swing.BHOptionPane;
import org.bh.platform.i18n.ITranslator;

/**
 * Platform Persistence
 * 
 * <p>
 * Handling open and save functions
 * 
 * @author Michael Löckelt
 * @version 1.0, 26.12.2009
 * 
 */
public class PlatformPersistenceManager {

	/**
	 * Path to File
	 */
	File path;

	/**
	 * reference to repomanager
	 */
	ProjectRepositoryManager projectRepositoryManager;

	/**
	 * reference to bh main frame
	 */
	BHMainFrame bhmf;

	private static final Logger log = Logger
			.getLogger(PlatformPersistenceManager.class);

	public static SaveActionListener sAL = new SaveActionListener();

	/**
	 * initialize the persistence manager
	 * 
	 * @param bhmf
	 * @param projectRepositoryManager
	 */
	public PlatformPersistenceManager(BHMainFrame bhmf,
			ProjectRepositoryManager projectRepositoryManager) {
		this.bhmf = bhmf;
		this.projectRepositoryManager = projectRepositoryManager;
	}

	/**
	 * openFile method handles all necessary operations for opening a file it
	 * returns a arraylist with DTOProjects
	 * 
	 * @param path
	 * @return ArrayList<DTOProject>
	 */

	public ArrayList<DTOProject> openFile(File path) {
		if (!path.toString().endsWith(".bh"))
			path = new File(path.toString() + ".bh");
		else
			this.path = path;

		ArrayList<DTOProject> returnRepository = new ArrayList<DTOProject>();

		try {
			FileInputStream fileLoader = new FileInputStream(path);
			ObjectInputStream objectLoader = new ObjectInputStream(fileLoader) {
				@Override
				protected Class<?> resolveClass(ObjectStreamClass desc)
						throws IOException, ClassNotFoundException {
					String name = desc.getName();
					try {
						return Class.forName(name, true, PluginManager
								.getInstance().getPluginClassLoader());
					} catch (ClassNotFoundException ex) {
						return super.resolveClass(desc);
					}
				}
			};

			Object whileObj = null;

			while ((whileObj = objectLoader.readObject()) != null) {
				if (whileObj instanceof DTOProject)
					returnRepository.add((DTOProject) whileObj);
			}

		} catch (FileNotFoundException e) {
			log.error("File " + path + " not found!");
			PlatformUserDialog.getInstance().showErrorDialog(
					Services.getTranslator().translate("PfileNotFound",
							ITranslator.LONG),
					Services.getTranslator().translate("PfileNotFound"));
		} catch (EOFException e) {

			// replace ProjectRepository
			projectRepositoryManager.replaceProjectList(returnRepository);

			// Save path to preferences
			PlatformController.preferences.put("path", path.toString());

			// Set isChanged
			ProjectRepositoryManager.setChanged(false);

			// Refresh title
			bhmf.resetTitle();

			log.debug("file " + path.toString() + " successfully opened");

			return returnRepository;
		} catch (InvalidClassException e) {
			log.error("InvalidClassException while opening " + path, e);
			PlatformUserDialog.getInstance().showErrorDialog(
					Services.getTranslator().translate(
							"PInvalidClassException_long"),
					Services.getTranslator()
							.translate("PInvalidClassException"));
		} catch (IOException e) {
			log.error("IOException while opening " + path, e);
			PlatformUserDialog.getInstance().showErrorDialog(e.toString(),
					Services.getTranslator().translate("PIOException"));
		} catch (Exception e) {
			log.error("Exception while opening file" + path, e);
			PlatformUserDialog.getInstance().showErrorDialog(e.toString(),
					Services.getTranslator().translate("PException"));
		}

		return null;
	}

	/**
	 * Prepare a path to be saved. if forcedSaveAs is used, the SaveAs dialog
	 * will be used
	 * 
	 * @param forcedSaveAs
	 * @throws Exception
	 */

	public void prepareSaveFile(boolean forcedSaveAs) throws Exception {
		// if no path exists in property file or saveAs is forced, show a save
		// dialog
		if (PlatformController.preferences.get("path", "").equals("")
				|| forcedSaveAs == true) {

			File dummyFile = new File((PlatformController.preferences.get(
					"path", "businesshorizon.bh")));

			bhmf.getChooser().setSelectedFile(dummyFile);
			int returnVal = bhmf.getChooser().showSaveDialog(bhmf);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
				path = bhmf.getChooser().getSelectedFile();
				// force .bh data extension
				if (!path.toString().endsWith(".bh")) {
					path = new File(path + ".bh");
				}
				log.debug("You choose to save this file: "
						+ bhmf.getChooser().getSelectedFile().getName());

				if(checkFile(path)) {
					PlatformController.preferences.put("path", path.getAbsolutePath());
					saveFile();
				}else {
					throw new Exception("save canceled");
				}
				
//				// check if file already exists
//				if (bhmf.getChooser().getSelectedFile().exists()
//						|| new File(bhmf.getChooser().getSelectedFile() + ".bh")
//								.exists()) {
//
//					// if yes, ask if its okay to overwrite it
//					int returnConfirmVal = BHOptionPane.showConfirmDialog(bhmf,
//							Services.getTranslator().translate(
//									"PoverwriteFile_long"), Services
//									.getTranslator()
//									.translate("PoverwriteFile"),
//							JOptionPane.YES_NO_OPTION);
//
//					// if yes - do it!
//					if (returnConfirmVal == JOptionPane.YES_OPTION) {
//						this.path = bhmf.getChooser().getSelectedFile();
//
//						// if no, try again to prepareSaveFile
//					} else {
//						this.prepareSaveFile(forcedSaveAs);
//					}
//
//					// if file does not exist, just take it
//				} else {
//					this.path = bhmf.getChooser().getSelectedFile();
//				}

			} else {
				throw new Exception("save canceled");
			}

			// if path already exists in properties file and no saveAs wasnt
			// forced, use already existing path
		} else {
			File path = new File(PlatformController.preferences.get("path", ""));
			this.path = path;
		}

		this.saveFile();

	}

	/**
	 * save the file
	 */

	public void saveFile() {

		try {

			DTOProject iteratorObject;
			FileOutputStream fileWriter = new FileOutputStream(path);
			ObjectOutputStream objectWriter = new ObjectOutputStream(fileWriter);

			Iterator<DTOProject> projectIterator = projectRepositoryManager
					.getRepositoryList().iterator();

			while (projectIterator.hasNext()) {
				iteratorObject = projectIterator.next();
				objectWriter.writeObject(iteratorObject);
				log.debug("Object " + iteratorObject.toString()
						+ "written to file " + path);
			}

			objectWriter.close();
			fileWriter.close();

			log.debug("ProjectRepository successfully saved to " + path);

			//TODO save DTOBusinessData
			saveDTOBusinessData();
			
			// Set isChanged
			ProjectRepositoryManager.setChanged(false);

			// Save path to preferences
			PlatformController.preferences.put("path", this.path.toString());

			// Refresh title
			bhmf.resetTitle();

		} catch (FileNotFoundException e) {
			log.error(e);
			PlatformUserDialog.getInstance().showErrorDialog(e.toString(),
					Services.getTranslator().translate("PfileNotFound"));
			
		} catch (IOException e) {
			log.error("IOException while saving " + path, e);
			PlatformUserDialog.getInstance().showErrorDialog(e.toString(),
					Services.getTranslator().translate("PIOException"));

		}
	}
	
	public boolean saveDTOBusinessData(){
		//TODO implement
		PlatformController.preferences.put("branches", "");
		return true;
	}
	
	protected boolean checkFile(File f) {
		if(f == null) {
			return false;
		}
		if (f.exists()) {
			if (!f.canWrite()) {
				PlatformUserDialog.getInstance().showErrorDialog(
						Services.getTranslator().translate("PfileNotWritable",
								ITranslator.LONG),
						Services.getTranslator().translate("PfileNotWritable"));
				return false;
			}
			int res = PlatformUserDialog.getInstance().showYesNoDialog(Services.getTranslator().translate("PoverwriteFile",ITranslator.LONG),Services.getTranslator().translate("PoverwriteFile"));
			if (res == JOptionPane.YES_OPTION) {
				return true;
			}
			return false;
		}
		// f does not exist
		try {
			f.createNewFile();
		} catch (IOException e) {
			// no write rights 
			PlatformUserDialog.getInstance().showErrorDialog(
					Services.getTranslator().translate("PfileNotWritable",
							ITranslator.LONG),
					Services.getTranslator().translate("PfileNotWritable"));
			return false;
		}
		// can write
		f.delete();
		return true;
	}

	/**
	 * last edited file dialog
	 */
	public void lastEditedFile() {
		String lastFile = PlatformController.preferences.get("path", "");
		if (!lastFile.equals("")) {
			String title = Services.getTranslator().translate("PlastFile");
			String message = "<html>"
					+ Services.getTranslator().translate("PlastFile",
							ITranslator.LONG) + "<br /><i>(" + lastFile
					+ ")</i></html>";

			int action = BHOptionPane.showConfirmDialog(bhmf, message, title,
					JOptionPane.YES_NO_OPTION);
			if (action == JOptionPane.YES_OPTION) {
				File tmpFile = new File(lastFile);
				PlatformController.platformPersistenceManager.openFile(tmpFile);

			} else if (action == JOptionPane.NO_OPTION) {
				PlatformController.preferences.remove("path");
				bhmf.resetTitle();
			}
		}
	}
}

class SaveActionListener implements IPlatformListener {

	private static final Logger log = Logger
			.getLogger(PlatformPersistenceManager.class);

	public SaveActionListener() {
		Services.addPlatformListener(this);
	}

	@Override
	public void platformEvent(PlatformEvent e) {
		if (PlatformEvent.Type.SAVE == e.getEventType()) {
			try {
				PlatformController.platformPersistenceManager
						.prepareSaveFile(false);
			} catch (Exception e1) {
				log.debug("User canceled save operation");
			}
		} else if (PlatformEvent.Type.SAVEAS == e.getEventType()) {
			try {
				PlatformController.platformPersistenceManager
						.prepareSaveFile(true);
			} catch (Exception e1) {
				log.debug("User canceled save operation");
			}
		}
	}
}
