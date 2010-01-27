package org.bh.platform;

public enum PlatformKey{
	
	
	
	/*--------------------------------
	 * ActionKeys of PlatformMenu
	 * -------------------------------
	 */
	FILENEW("Mnew"),
	FILEOPEN("Mopen"),
	FILESAVE("Msave"),
	FILESAVEAS("MsaveAs"),
	FILECLOSE("Mclose"),
	FILEQUIT("Mquit"),
	PROJECTCREATE("Mcreate"),
	PROJECTDUPLICATE("Mduplicate"),
	PROJECTIMPORT("Mimport"),
	PROJECTEXPORT("Mexport"),
	PROJECTREMOVE("Mremove"),
	SCENARIOCREATE("Mcreate"),
	SCENARIODUPLICATE("Mduplicate"),
	SCENARIOREMOVE("Mremove"),
	PERIODCREATE("Mcreate"),
	PERIODDUPLICATE("Mduplicate"),
	PERIODREMOVE("Mremove"),
	OPTIONSCHANGE("Mchange"),
	HELPUSERHELP("MuserHelp"),
	HELPMATHHELP("MmathHelp"),
	HELPINFO("Minfo"),
	HELPDEBUG("Mdebug"),
	
	/*--------------------------------
	 * ActionKeys of PlatformToolbar
	 * -------------------------------
	 */
	TOOLBARNEW("TnewWorkspace"),
	TOOLBAROPEN("Topen"),
	TOOLBARSAVE("Tsave"),
	TOOLBARADDPRO("TaddPro"),
	TOOLBARADDS("TaddS"),
	TOOLBARADDPER("TaddPer"),
	TOOLBARREMOVE("Tremove"),
	
	/*--------------------------------
	 * ActionKeys of Tree's Popup
	 * -------------------------------
	 */
	POPUPREMOVE("Tremove"),
	POPUPADD("Treadd"),
	POPUPDUPLICATE("Treduplicate"),
	POPUPEXPORT("Treexport"),
	
	
	CALCSHAREHOLDERVALUE("BcalculateShareholderValue"),
	CALCDASHBOARD("BcalculateDashBoard");
	
	String translationKey;
	
	PlatformKey(String translationKey){
		this.translationKey = translationKey;
	}
	
	@Override
	public String toString(){
		return translationKey;
	}

	
}
