package de.to.Enum;

import java.util.HashMap;
import java.util.Map;

public enum EditType {
	
	REPLACE("Replace");
	
	private static final Map<String, EditType> lookup = new HashMap<String, EditType>();
	
	private String display;
	
	EditType(String display) {
		this.display = display;
	}

    static {
        for (EditType type : EditType.values()) {
            lookup.put(type.getDisplay(), type);
        }
    }

    public String getDisplay() {
        return display;
    }
	
	public static String[] getDisplayString() {
		EditType[] types = EditType.values();
		String[] strings = new String[types.length];
		for (int i = 0; i < types.length; i++) {
			strings[i] = types[i].display;
		}
		return strings;
	}
	
    public static EditType get(String display) {
        return lookup.get(display);
    }
}
