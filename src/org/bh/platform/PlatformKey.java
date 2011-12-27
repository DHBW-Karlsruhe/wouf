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

public enum PlatformKey{
	
	
	
	/*--------------------------------
	 * ActionKeys of PlatformMenu
	 * -------------------------------
	 */
	FILENEW("Mnew"),
	FILEOPEN("Mopen"),
	FILESAVE("Msave"),
	FILESAVEAS("MsaveAs"),
	FILEPRINT("Mprint"),
	FILESCEXP("Mscexp"),
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
	MAINTAIN_COMPANY_DATA("MMaintainCompData"),
	
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
	POPUPEXPORT("Treexport");
	
	String translationKey;
	
	PlatformKey(String translationKey){
		this.translationKey = translationKey;
	}
	
	@Override
	public String toString(){
		return translationKey;
	}

	
}
