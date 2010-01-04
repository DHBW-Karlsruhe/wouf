package org.bh.platform;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.bh.data.DTOProject;

/**
 * Platform Persistence
 *
 * <p>
 * Handling open and save functions
 *
 * @author Michael Löckelt
 * @version 0.1, 26.12.2009
 *
 */
public class PlatformPersistence {
	
	/**
	 * Path to File
	 */
	File path;
	
	/**
	 * 
	 */
	ProjectRepositoryManager projectRepositoryManager;
	
	private static final Logger log = Logger.getLogger(PlatformPersistence.class);
	
	
	public PlatformPersistence (File path, ProjectRepositoryManager projectRepo) {
		this.path = path;
		this.projectRepositoryManager = projectRepo;
	}
	
	public ArrayList<DTOProject> openFile () {
		ArrayList<DTOProject> returnRepository = new ArrayList<DTOProject>();
		
		try {
			FileInputStream fileLoader = new FileInputStream(path);
			ObjectInputStream objectLoader = new ObjectInputStream(fileLoader);
			
			Object whileObj = null;
			
			while ((whileObj = objectLoader.readObject()) != null) {
				if (whileObj instanceof DTOProject)
					returnRepository.add((DTOProject) whileObj);
			}
		
		} catch (FileNotFoundException e) {
			log.error("File "+ path + "not found!");
		} catch (EOFException e) {
			ProjectRepositoryManager.setChanged(false);
			return returnRepository;
		} catch (Exception e) {
			log.error("Exception while opening file");
			e.printStackTrace();
		} 
		
		return null;
	}
	
	public boolean saveFile(ArrayList<DTOProject> projectRepository) {
		try {
			
			// force .bh data extension
			if (!path.toString().endsWith(".bh")) {
				File newPath = new File(path + ".bh");
				this.path = newPath;
			}
			
			DTOProject iteratorObject;
			FileOutputStream fileWriter = new FileOutputStream(path);
			ObjectOutputStream objectWriter = new ObjectOutputStream(fileWriter);
			
			Iterator<DTOProject> projectIterator = projectRepository.iterator();
			
			while(projectIterator.hasNext()) {
				iteratorObject = projectIterator.next();
				objectWriter.writeObject(iteratorObject);
				log.debug("Object " + iteratorObject.toString() + "written to file " + path);
			}
			
			objectWriter.close();
			fileWriter.close();
			
			ProjectRepositoryManager.setChanged(false);
			return true;
			
		} catch (FileNotFoundException e) {
			log.error("File " + path + "not found!");
		} catch (IOException e) {
			log.error("IO Error occured wihle saving" + path);
			e.printStackTrace();
		}
		
		return false;
		
	}
}
