package de.to.Enum;

public enum ComponentNames {
	BTN_FILESELECT("file_select"), //
	BOX_OPTIONS("options_box"), //
	PANEL_OPTIONALS("optional_panel"), //
	TEXT_FILEENDING("file_ending"), //
	TEXT_FILEDONTCONTAIN("file_dont_contain"), //
	CHECK_OPENFILE("openfile"), //
	CHECK_CASESENSITIVE("case_sensitive"), //
	TEXT_FIND("find_text"), //
	TEXT_REPLACE("replace_text");
	
	private String name;
	
	ComponentNames(String name) {
		this.setName(name);
	}
	
	public String getName() {
		return name;
	}
	
	private void setName(String name) {
		this.name = name;
	}
}
