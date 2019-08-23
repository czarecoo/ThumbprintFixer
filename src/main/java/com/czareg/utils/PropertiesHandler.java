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

import com.czareg.model.ProxyData;

public class PropertiesHandler {
	private static final String PATH_TO_CONFIG_PROPERTIES = "ThumbprintFixerConfig.properties";
	private static final String PROXY_PORT_PROPERTY = "proxy.port";
	private static final String PROXY_SERVER_PROPERTY = "proxy.server";
	static final Logger LOG = LoggerFactory.getLogger(PropertiesHandler.class);

	private PropertiesHandler() {
	}

	public static void writeProxyData(ProxyData proxyData) throws IOException {
		createFileIfNotExists();
		try (OutputStream output = new FileOutputStream(PATH_TO_CONFIG_PROPERTIES)) {
			Properties prop = new Properties();
			prop.setProperty(PROXY_SERVER_PROPERTY, proxyData.getServer());
			prop.setProperty(PROXY_PORT_PROPERTY, proxyData.getPort());
			LOG.debug(prop.toString());
			prop.store(output, null);
		}
	}

	public static ProxyData readProxyData() throws IOException {
		createFileIfNotExists();
		try (InputStream input = new FileInputStream(PATH_TO_CONFIG_PROPERTIES)) {
			Properties prop = new Properties();
			prop.load(input);
			String server = prop.getProperty(PROXY_SERVER_PROPERTY);
			String port = prop.getProperty(PROXY_PORT_PROPERTY);
			if (!server.isEmpty() && !port.isEmpty()) {
				LOG.debug("Server property read: {}", server);
				LOG.debug("Port property read: {}", port);
				return new ProxyData(prop.getProperty(PROXY_SERVER_PROPERTY), prop.getProperty(PROXY_PORT_PROPERTY));
			} else {
				return null;
			}
		}
	}

	private static void createFileIfNotExists() {
		try {
			File file = new File(PATH_TO_CONFIG_PROPERTIES);
			if (!file.exists()) {
				LOG.info("Created file {}", file.createNewFile());
				try (OutputStream output = new FileOutputStream(PATH_TO_CONFIG_PROPERTIES)) {
					Properties prop = new Properties();
					prop.setProperty(PROXY_SERVER_PROPERTY, "");
					prop.setProperty(PROXY_PORT_PROPERTY, "");
					LOG.debug("Saving properties: {}", prop);
					prop.store(output, null);
				}
			}
		} catch (IOException e) {
			LOG.error("Failed to create file", e);
		}
	}
}
