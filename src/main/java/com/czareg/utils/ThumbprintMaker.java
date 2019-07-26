package com.czareg.utils;

public class ThumbprintMaker {
	private ThumbprintMaker() {
	}

	public static String make(String startingThumbPrint) {
		String cleanThumbPrint = cleanUserInput(startingThumbPrint);
		StringBuilder properThumbPrint = new StringBuilder(cleanThumbPrint);
		insertColons(cleanThumbPrint, properThumbPrint);
		return properThumbPrint.toString();
	}

	private static String cleanUserInput(String startingThumbPrint) {
		return startingThumbPrint.strip().replaceAll("[^a-fA-F0-9]", "");
	}

	private static void insertColons(String startingThumbPrint, StringBuilder properThumbPrint) {
		int howManyColonsToAdd = countColons(startingThumbPrint);
		for (int colonCounter = 0, colonIndex = 2; colonCounter < howManyColonsToAdd; colonCounter++, colonIndex = colonIndex
				+ 3) {
			properThumbPrint.insert(colonIndex, ":");
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
