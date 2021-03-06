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

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.bh.data.DTOProject;

/**
 *This manager provides necessary classes for global project repository management.
 *
 *It's designed to be mainly used by the PlatformController.
 *
 *
 * @author Marco Hammel
 * @author Alexander Schmalzhaf
 * @author Michael Löckelt
 * 
 * @version 0.1 2009/12/26 Alexander Schmalzhaf
 * @version 0.2 2010/01/04 Michael Löckelt
 */

public class ProjectRepositoryManager {
	
    private ArrayList<DTOProject> repositoryList = new ArrayList<DTOProject>();
	
    private static boolean isChanged;
    
    public ChangedListener cL = new ChangedListener(this);
    
    private static final Logger log = Logger.getLogger(ProjectRepositoryManager.class);
    
    private static ProjectRepositoryManager singletonRepo;
    
    private ProjectRepositoryManager () {
    	super();
    }
    
    public static ProjectRepositoryManager getInstance() {
    	if (singletonRepo == null)
    		singletonRepo = new ProjectRepositoryManager();
    	return singletonRepo;
    }

	/**
     * Adds a new project to the actual repository.
     * 
     * @param newProject
     */
    public void addProject(DTOProject newProject){
    	repositoryList.add(newProject);
    	Services.firePlatformEvent(new PlatformEvent(ProjectRepositoryManager.class,PlatformEvent.Type.DATA_CHANGED));
    }
    
    /**
     * Removes a project out of the actual repository if it exists. 
     * Identifier for selection of the project which should be deleted is its name. 
     * 
     * @param name name of project to delete
     */
    public void removeProject(String name){
    	
    	Iterator<DTOProject> rLIterator = repositoryList.iterator();
    	DTOProject tempProject;
    	
    	//walk through repository and check projects
    	while(rLIterator.hasNext()){
    		tempProject = rLIterator.next();
    		if(tempProject.get("NAME").toString().equalsIgnoreCase(name)){
    			repositoryList.remove(tempProject);
    			Services.firePlatformEvent(new PlatformEvent(ProjectRepositoryManager.class,PlatformEvent.Type.DATA_CHANGED));
    			break;
    		}
    	}
    }
    
    
    /**
     * Removes a project out of the actual repository if it exists. 
     * Identifier for selection of the project which should be deleted is its reference.
     * 
     * @param project DTOProject which should be removed from repository
     */
    public void removeProject(DTOProject project){
    		
    	repositoryList.remove(project);
    	
    	if (repositoryList.isEmpty() && PlatformController.preferences.get("path", "").equals("")) 
    		ProjectRepositoryManager.setChanged(false);
    	
    		
    	Services.firePlatformEvent(new PlatformEvent(ProjectRepositoryManager.class,PlatformEvent.Type.DATA_CHANGED));
    	
    }
    
    
    /**
     * Allows the replacement of the complete repository.
     * Necessary e.g. while loading another repo-file.
     * 
     * 
     * @param newRepoList
     */
    protected void replaceProjectList(ArrayList<DTOProject> newRepoList){
    	
    	this.repositoryList = newRepoList;
    }
    
    /**
     * Creates a new, clean and empty repository.
     */
    protected void clearProjectList(){
    	
    	this.repositoryList = new ArrayList<DTOProject>();
    	Services.firePlatformEvent(new PlatformEvent(ProjectRepositoryManager.class,PlatformEvent.Type.DATA_CHANGED));
    }
    
    /**
     * Allocates the complete repository in the form of a reference onto the list.
     * Necessary e.g. for saving the current status of the repository.
     * 
     * @return repository List
     */
    public ArrayList<DTOProject> getRepositoryList(){
    	
    	return this.repositoryList;
    }
    
    public static boolean isChanged() {
		return isChanged;
	}

	public static void setChanged(boolean isChanged) {
		if (ProjectRepositoryManager.isChanged == isChanged)
			return;
		ProjectRepositoryManager.isChanged = isChanged;
		log.debug("Flag isChanged set to " + isChanged);
	}
  
}

class ChangedListener implements IPlatformListener {
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ProjectRepositoryManager.class);
	
	ProjectRepositoryManager projectRepositoryManager;
	
	public ChangedListener (ProjectRepositoryManager projectRepositoryManager) {
		this.projectRepositoryManager = projectRepositoryManager;
		Services.addPlatformListener(this);
	}

	@Override
	public void platformEvent(PlatformEvent e) {
		if (PlatformEvent.Type.DATA_CHANGED == e.getEventType())
		//	if (!projectRepositoryManager.getRepositoryList().isEmpty()) {
				ProjectRepositoryManager.setChanged(true);
		//	} else {
		//		ProjectRepositoryManager.setChanged(false);
		//	}
				
	}
	
}