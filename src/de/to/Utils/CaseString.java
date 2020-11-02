package de.to.Utils;

public class CaseString {
	
	public static String checkCaseSens(String checkCaseSens) {
		boolean caseSens = Optionals.getInstance().isCaseSensitive();
		
		return caseSens ? checkCaseSens : checkCaseSens.toLowerCase();
	}
	
	public static String replaceAll(String toReplace, String regex, String replacement) {
		return checkCaseSens(toReplace).replaceAll(checkCaseSens(regex), checkCaseSens(replacement));
	}
}
