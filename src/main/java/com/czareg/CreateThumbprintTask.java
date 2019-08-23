package com.czareg;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.czareg.utils.ThumbprintGetter;
import com.czareg.utils.ThumbprintMaker;

import exceptions.GettingThumbprintException;
import javafx.concurrent.Task;

public class CreateThumbprintTask extends Task<String> {
	static final Logger LOG = LoggerFactory.getLogger(CreateThumbprintTask.class);
	private String userInput;

	public CreateThumbprintTask(String userInput) {
		this.userInput = userInput;
	}

	@Override
	protected String call() {
		String thumbprint = userInput;
		try {
			thumbprint = ThumbprintGetter.get(userInput);
		} catch (GettingThumbprintException e) {
			LOG.warn("User did not pass proper url: {}", userInput);
		} catch (CertificateEncodingException | NoSuchAlgorithmException | IOException e) {
			LOG.error("Could not create thumbprint for input: " + userInput, e);
		}
		return ThumbprintMaker.make(thumbprint);
	}
}