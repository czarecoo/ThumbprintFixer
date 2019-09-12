package com.czareg.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesHandler {
	private static final String PATH_TO_CONFIG_PROPERTIES = "ThumbprintFixerConfig.properties";
	private static final String DEFAULT_URL_PROPERTY = "default.url";
	private static final String COPY_TO_CLIPBOARD_PROPERTY = "copy.to.clipboard";
	static final Logger LOG = LoggerFactory.getLogger(PropertiesHandler.class);

	private PropertiesHandler() {
	}

	public static void writeDefaultUrl(String url) {
		writeToProperties(DEFAULT_URL_PROPERTY, url);
	}

	public static String readDefaultUrl() {
		return readFromProperties(DEFAULT_URL_PROPERTY);
	}

	public static void writeCopyToClipboard(boolean bool) {
		writeToProperties(COPY_TO_CLIPBOARD_PROPERTY, Boolean.toString(bool));
	}

	public static boolean readCopyToClipboard() {
		return Boolean.valueOf(readFromProperties(COPY_TO_CLIPBOARD_PROPERTY));
	}

	private static void writeToProperties(String property, String value) {
		createFileIfNotExists();
		Properties prop = new Properties();
		try {
			try (InputStream input = new FileInputStream(PATH_TO_CONFIG_PROPERTIES)) {
				prop.load(input);
			}
			try (OutputStream output = new FileOutputStream(PATH_TO_CONFIG_PROPERTIES)) {
				prop.setProperty(property, value);
				prop.store(output, null);
			}
		} catch (IOException e) {
			LOG.error(String.format("Failed to write property: %s with value: %s to file", property, value), e);
		}
	}

	private static String readFromProperties(String property) {
		createFileIfNotExists();
		try (InputStream input = new FileInputStream(PATH_TO_CONFIG_PROPERTIES)) {
			Properties prop = new Properties();
			prop.load(input);
			return prop.getProperty(property, null);
		} catch (IOException e) {
			LOG.error(String.format("Failed to read property: %s from file", property), e);
		}
		return null;
	}

	private static void createFileIfNotExists() {
		try {
			File file = new File(PATH_TO_CONFIG_PROPERTIES);
			if (!file.exists()) {
				LOG.info("Created file {}", file.createNewFile());
				try (OutputStream output = new FileOutputStream(PATH_TO_CONFIG_PROPERTIES)) {
					Properties prop = new Properties();
					prop.setProperty(DEFAULT_URL_PROPERTY, "https://10.172.193.:3170");
					prop.setProperty(COPY_TO_CLIPBOARD_PROPERTY, "true");
					LOG.debug("Saving properties: {}", prop);
					prop.store(output, null);
				}
			}
		} catch (IOException e) {
			LOG.error("Failed to create file", e);
		}
	}
}
