package com.czareg.tests.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.czareg.utils.ThumbprintMaker;

class ThumbprintMakerTests {

	@ParameterizedTest(name = "{0} => {1}")
	@CsvFileSource(resources = "/testCases.csv", delimiter = ';')
	void makeShouldCreateValidThumbprint(String input, String expected) {
		assertEquals(ThumbprintMaker.make(input), expected);
	}
}
