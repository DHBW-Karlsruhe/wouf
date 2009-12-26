package org.bh.platform.actionkeys;

public enum PlatformActionKey{
	
	
	
	/*--------------------------------
	 * ActionKeys of PlatformMenu
	 * -------------------------------
	 */
	FILENEW("Mnew","FILENEW"),
	FILEOPEN("Mopen","FILEOPEN"),
	FILECLOSE("Mclose","FILECLOSE"),
	FILESAVE("Msave","FILESAVE"),
	FILESAVEAS("MsaveAs","FILESAVEAS"),
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
	TOOLBARNEW("Tnew","TOOLBARNEW"),
	TOOLBAROPEN("Topen","TOOLBAROPEN"),
	TOOLBARSAVE("Tsave","TOOLBARSAVE"),
	TOOLBARADDP("TaddP","TOOLBARADDP"),
	TOOLBARADDS("TaddS","TOOLBARADDS"),
	TOOLBARREMOVE("Tremove","TOOLBARREMOVE"),
	TOOLBARDELETE("Tdelete","TOOLBARDELETE"),
	TOOLBARFIND("Tfind","TOOLBARFIND");
	
	
	
	String translationKey;
	String actionKey;
	
	/**
	 * 
	 */
	PlatformActionKey(String translationKey, String actionKey){
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
