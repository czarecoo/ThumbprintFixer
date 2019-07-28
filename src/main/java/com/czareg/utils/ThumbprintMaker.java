package com.czareg.utils;

import com.czareg.model.StringFormat;

public class ThumbprintMaker {
	private static final String UNSUPPORTED_THUMBPRINT_CHARS = "[^a-fA-F0-9]";
	private static final String COLON = ":";
	public static StringFormat format = StringFormat.UPPERCASE;

	private ThumbprintMaker() {
	}

	public static String make(String startingThumbPrint) {
		String cleanThumbPrint = cleanUserInput(startingThumbPrint);
		StringBuilder properThumbPrint = new StringBuilder(cleanThumbPrint);
		insertColons(cleanThumbPrint, properThumbPrint);
		return applyCurrentFormat(properThumbPrint.toString());
	}

	public static String applyCurrentFormat(String unformattedProperThumbprint) {
		return format.convertToFormat(unformattedProperThumbprint);
	}

	private static String cleanUserInput(String startingThumbPrint) {
		return startingThumbPrint.strip().replaceAll(UNSUPPORTED_THUMBPRINT_CHARS, "");
	}

	private static void insertColons(String startingThumbPrint, StringBuilder properThumbPrint) {
		int howManyColonsToAdd = countColons(startingThumbPrint);
		for (int colonCounter = 0, colonIndex = 2; colonCounter < howManyColonsToAdd; colonCounter++, colonIndex = colonIndex
				+ 3) {
			properThumbPrint.insert(colonIndex, COLON);
		}
	}

	private static int countColons(String startingThumbPrint) {
		int colonCount = startingThumbPrint.length();
		if (colonCount % 2 == 0) {
			return colonCount / 2 - 1;
		}
		return colonCount / 2;
	}
}
