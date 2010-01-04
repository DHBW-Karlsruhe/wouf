package org.bh.platform;

public enum PlatformKey{
	
	
	
	/*--------------------------------
	 * ActionKeys of PlatformMenu
	 * -------------------------------
	 */
	FILENEW("Mnew","FILENEW"),
	FILEOPEN("Mopen","FILEOPEN"),
	FILESAVE("Msave","FILESAVE"),
	FILESAVEAS("MsaveAs","FILESAVEAS"),
	FILECLOSE("Mclose", "FILECLOSE"),
	FILEQUIT("Mquit","FILEQUIT"),
	PROJECTCREATE("Mcreate","PROJECTCREATE"),
	PROJECTRENAME("Mrename","PROJECTRENAME"),
	PROJECTDUPLICATE("Mduplicate","PROJECTDUPLICATE"),
	PROJECTIMPORT("Mimport","PROJECTIMPORT"),
	PROJECTEXPORT("Mexport","PROJECTEXPORT"),
	PROJECTREMOVE("Mremove","PROJECTREMOVE"),
	SCENARIOCREATE("Mcreate","SCENARIOCREATE"),
	SCENARIORENAME("Mrename","SCENARIORENAME"),
	SCENARIODUPLICATE("Mduplicate","SCENARIODUPLICATE"),
	SCENARIOMOVE("Mmove","SCENARIOMOVE"),
	SCENARIOREMOVE("Mremove","SCENARIOREMOVE"),
	BILANZGUVSHOW("Mshow","BILANZGUVSHOW"),
	BILANZGUVCREATE("Mcreate","BILANZGUVCREATE"),
	BILANZGUVIMPORT("Mimport","BILANZGUVIMPORT"),
	BILANZGUVREMOVE("Mremove","BILANZGUVREMOVE"),
	OPTIONSCHANGE("Mchange","OPTIONSCHANGE"),
	HELPUSERHELP("MuserHelp","HELPUSERHELP"),
	HELPMATHHELP("MmathHelp","HELPMATHHELP"),
	HELPINFO("Minfo","HELPINFO"),
	
	/*--------------------------------
	 * ActionKeys of PlatformToolbar
	 * -------------------------------
	 */
	TOOLBAROPEN("Topen","TOOLBAROPEN"),
	TOOLBARSAVE("Tsave","TOOLBARSAVE"),
	TOOLBARADDPRO("TaddPro","TOOLBARADDPRO"),
	TOOLBARADDS("TaddS","TOOLBARADDS"),
	TOOLBARADDPER("TaddPer","TOOLBARADDPER"),
	TOOLBARREMOVE("Tremove","TOOLBARREMOVE");

	
	String translationKey;
	String actionKey;
	
	/**
	 * 
	 */
	PlatformKey(String translationKey, String actionKey){
		this.translationKey = translationKey;
		this.actionKey = actionKey;
	}
	
	@Override
	public String toString(){
		return translationKey;
	}
	
	public String getActionKey(){
		return this.actionKey;
	}
	
}
