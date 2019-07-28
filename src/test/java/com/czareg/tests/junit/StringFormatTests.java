package com.czareg.tests.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.czareg.model.StringFormat;

public class StringFormatTests {

	@ParameterizedTest(name = "{0} => uppercase({0})")
	@ValueSource(strings = { "UPPERCASE", "lowercase", "MiXeDcAsE" })
	public void testUppercaseConvertToFormat(String text) {
		String expected = text.toUpperCase();
		String actual = StringFormat.UPPERCASE.convertToFormat(text);
		assertEquals(expected, actual);
	}

	@ParameterizedTest(name = "{0} => lowercase({0})")
	@ValueSource(strings = { "UPPERCASE", "lowercase", "MiXeDcAsE" })
	public void testLowercaseConvertToFormat(String text) {
		String expected = text.toLowerCase();
		String actual = StringFormat.LOWERCASE.convertToFormat(text);
		assertEquals(expected, actual);
	}

	@ParameterizedTest(name = "{0} => mixedcase({0})")
	@ValueSource(strings = { "UPPERCASE", "lowercase", "MiXeDcAsE" })
	public void testMixedcaseConvertToFormat(String text) {
		String expected = text;
		String actual = StringFormat.MIXEDCASE.convertToFormat(text);
		assertEquals(expected, actual);
	}
}
