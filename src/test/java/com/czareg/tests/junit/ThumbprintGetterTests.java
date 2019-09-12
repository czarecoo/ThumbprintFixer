package com.czareg.tests.junit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.czareg.utils.ThumbprintGetter;

import configs.Config;
import exceptions.GettingThumbprintException;

public class ThumbprintGetterTests {

	@Test
	public void testHttpUrlValidation3() {
		String goodUrl = "http://google.com";

		boolean result = ThumbprintGetter.isUrlValid(goodUrl);

		System.out.println(result + " " + goodUrl);
	}

	@Test
	public void testHttpUrlValidation() {
		String goodUrl = "http://google.com";

		boolean result = ThumbprintGetter.isUrlValid(goodUrl);

		Assertions.assertTrue(result);
	}

	@Test
	public void testHttpsUrlValidation() {
		String goodUrl = "https://google.com";

		boolean result = ThumbprintGetter.isUrlValid(goodUrl);

		Assertions.assertTrue(result);
	}

	@Test
	public void testNoHttpUrlValidation() {
		String badUrl = "google.com";

		boolean result = ThumbprintGetter.isUrlValid(badUrl);

		Assertions.assertFalse(result);
	}

	@Test
	public void tesGoodUrlPreparation() throws MalformedURLException, GettingThumbprintException {
		String goodUrl = "https://google.com";

		URL result = ThumbprintGetter.prepareUrl(goodUrl);

		Assertions.assertTrue(ThumbprintGetter.isUrlValid(result.toString()));
	}

	@Test
	public void testNoHttpUrlPreparation() throws MalformedURLException, GettingThumbprintException {
		String badUrl = "google.com";

		URL result = ThumbprintGetter.prepareUrl(badUrl);

		Assertions.assertTrue(ThumbprintGetter.isUrlValid(result.toString()));
	}

	@Test
	public void testGetThumbprint()
			throws CertificateEncodingException, NoSuchAlgorithmException, IOException, GettingThumbprintException {
		String url = "facebook.com";

		String thumbprint = ThumbprintGetter.get(url);

		validateThumbprint(thumbprint);
	}

	private void validateThumbprint(String thumbprint) {
		String regex = Config.UNSUPPORTED_THUMBPRINT_CHARS;
		Assertions.assertFalse(thumbprint.matches(regex));
	}
}
