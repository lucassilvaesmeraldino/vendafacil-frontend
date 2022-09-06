package br.com.wmw.vendafacil_frontend.util;

public class StringUtils {
	
	private StringUtils() {
	}

	public static String reduceLength(String str, int maxLength) {
		return str.length() > maxLength ? str.substring(0, maxLength-2).concat("...") : str;
	}
}
