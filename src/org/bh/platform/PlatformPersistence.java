package org.bh.platform;

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
	
	private static final Logger log = Logger.getLogger(PlatformPersistence.class);
	
	
	public PlatformPersistence (File path) {
		this.path = path;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean saveFile(ArrayList<DTOProject> projectRepository) {
		try {
			
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
			
			return true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
/*
	public static void main (String args[]) {
		DTOProject project1 = new DTOProject();
		DTOScenario scenario1 = new DTOScenario(true);
		
		project1.addChild(scenario1);
		
		StringValue scenarioName = new StringValue("Scenario1");
		scenario1.put(DTOScenario.Key.NAME, scenarioName);
		
		DTOPeriod period1 = new DTOPeriod();
		StringValue p1 = new StringValue("p1");
		period1.put(DTOPeriod.Key.NAME, p1);
		
		DTOGCCBalanceSheet mysheet = new DTOGCCBalanceSheet();
		mysheet.put(DTOGCCBalanceSheet.Key.ABET, new IntegerValue(10));
		
		period1.addChild(mysheet);
		
		DTOPeriod period2 = period1.clone();
		
		
		try {
			FileOutputStream fileWriter = new FileOutputStream("/Users/michael/Documents/Studium/test.dat");
			ObjectOutputStream objectWriter = new ObjectOutputStream(fileWriter);
			
			objectWriter.writeObject(project1);
			System.out.println("Datei geschrieben");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			FileInputStream fileLoader = new FileInputStream("/Users/michael/Documents/Studium/test.dat");
			ObjectInputStream objectLoader = new ObjectInputStream(fileLoader);
			
			DTOProject loadedProject;
			
			loadedProject = (DTOProject) objectLoader.readObject();
			
			System.out.println("Datei geladen");
			
			loadedProject.regenerateMethodsList();
			
			DTOScenario loadedScenario = loadedProject.getChild(0);
			
			loadedScenario.regenerateMethodsList();
			
			StringValue loadedScenarioName = (StringValue) loadedScenario.get(DTOScenario.Key.NAME);
			System.out.println(loadedScenarioName.getString());
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	} */
}
