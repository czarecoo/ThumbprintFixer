package com.czareg.tests.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.czareg.model.StringFormat;
import com.czareg.utils.ThumbprintMaker;

class ThumbprintMakerTests {

	@ParameterizedTest(name = "{0} => {1}")
	@CsvFileSource(resources = "/testCases.csv", delimiter = ';')
	void makeShouldNotChangeFormatOnMixedcaseThumbprint(String input, String expected) {
		ThumbprintMaker.format = StringFormat.MIXEDCASE;
		assertEquals(ThumbprintMaker.make(input), expected);
	}

	@ParameterizedTest(name = "{0} => uppercase({1})")
	@CsvFileSource(resources = "/testCases.csv", delimiter = ';')
	void makeShouldCreateUppercaseFormattedThumbprint(String input, String expected) {
		ThumbprintMaker.format = StringFormat.UPPERCASE;
		assertEquals(ThumbprintMaker.make(input), expected.toUpperCase());
	}

	@ParameterizedTest(name = "{0} => lowercase({1})")
	@CsvFileSource(resources = "/testCases.csv", delimiter = ';')
	void makeShouldCreateLowercaseFormattedThumbprint(String input, String expected) {
		ThumbprintMaker.format = StringFormat.LOWERCASE;
		assertEquals(ThumbprintMaker.make(input), expected.toLowerCase());
	}
}
