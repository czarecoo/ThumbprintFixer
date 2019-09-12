package com.czareg.utils;

import configs.Config;

public class ThumbprintMaker {

	private ThumbprintMaker() {
	}

	public static String make(String startingThumbPrint) {
		String cleanThumbPrint = cleanUserInput(startingThumbPrint);
		return insertColons(cleanThumbPrint);
	}

	private static String cleanUserInput(String startingThumbPrint) {
		return startingThumbPrint.trim().replaceAll(Config.UNSUPPORTED_THUMBPRINT_CHARS, "");
	}

	private static String insertColons(String startingThumbPrint) {
		int howManyColonsToAdd = countColons(startingThumbPrint);
		StringBuilder properThumbPrint = new StringBuilder(startingThumbPrint);
		for (int colonCounter = 0, colonIndex = 2; colonCounter < howManyColonsToAdd; colonCounter++, colonIndex = colonIndex
				+ 3) {
			properThumbPrint.insert(colonIndex, Config.COLON);
		}
		return properThumbPrint.toString();
	}

	private static int countColons(String startingThumbPrint) {
		int colonCount = startingThumbPrint.length();
		if (colonCount % 2 == 0) {
			return colonCount / 2 - 1;
		}
		return colonCount / 2;
	}
}
