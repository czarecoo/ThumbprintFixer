package com.czareg.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

import javax.net.ssl.HttpsURLConnection;

import com.czareg.model.ProxyData;

import exceptions.GettingThumbprintException;

public class ThumbprintGetter {
	private ThumbprintGetter() {
	}

	public static boolean isUrlValid(String url) {
		try {
			new URL(url).toURI();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static URL prepareUrl(String url) throws MalformedURLException, GettingThumbprintException {
		if (isUrlValid(url)) {
			return new URL(url);
		} else {
			if (isUrlValid("https://" + url)) {
				return new URL("https://" + url);
			} else {
				throw new GettingThumbprintException("Bad url given");
			}
		}
	}

	public static String get(String url)
			throws IOException, CertificateEncodingException, NoSuchAlgorithmException, GettingThumbprintException {
		URL preparedUrl = prepareUrl(url);
		Proxy proxy = createProxy();
		HttpsURLConnection con;
		if (proxy == null) {
			con = (HttpsURLConnection) preparedUrl.openConnection();
		} else {
			con = (HttpsURLConnection) preparedUrl.openConnection(proxy);
		}

		con.connect();
		MessageDigest mg = MessageDigest.getInstance("SHA-1");
		Certificate[] certificates = con.getServerCertificates();
		if (certificates.length < 1) {
			throw new GettingThumbprintException("No certificates avaliable for " + url);
		}
		mg.update(certificates[0].getEncoded());
		byte[] thumbPrintDec = mg.digest();
		String thumbprint = "";
		for (byte b : thumbPrintDec) {
			thumbprint = thumbprint.concat(String.format("%02x", b));
		}
		return thumbprint;
	}

	private static Proxy createProxy() {
		ProxyData proxyData;
		try {
			proxyData = PropertiesHandler.readProxyData();
			return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyData.getServer(), proxyData.getPortInt()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
