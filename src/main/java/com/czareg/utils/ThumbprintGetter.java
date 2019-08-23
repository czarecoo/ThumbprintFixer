package com.czareg.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.czareg.model.ProxyData;

import exceptions.GettingThumbprintException;

public class ThumbprintGetter {
	private static final String TLS = "TLS";
	static final Logger LOG = LoggerFactory.getLogger(ThumbprintGetter.class);

	private ThumbprintGetter() {
	}

	public static boolean isUrlValid(String url) {
		try {
			new URL(url).toURI();
			return true;
		} catch (Exception e) {
			LOG.warn("Could not create url from: {}", url);
			return false;
		}
	}

	public static URL prepareUrl(String url) throws MalformedURLException, GettingThumbprintException {
		if (isUrlValid(url)) {
			return new URL(url);
		} else if (isUrlValid("https://" + url)) {
			return new URL("https://" + url);
		} else {
			throw new GettingThumbprintException("Bad url given");
		}
	}

	public static String get(String url)
			throws GettingThumbprintException, IOException, CertificateEncodingException, NoSuchAlgorithmException {
		HttpsURLConnection con = connectToUrl(url);
		LOG.debug("Successfully connected ");
		Certificate[] certificates = getCertificates(url, con);
		LOG.debug("Successfully acquired certificates");
		String thumbprint = processCertificates(certificates);
		LOG.debug("Successfully created thumbprint");
		return thumbprint;
	}

	private static String processCertificates(Certificate[] certificates)
			throws NoSuchAlgorithmException, CertificateEncodingException {
		MessageDigest mg = MessageDigest.getInstance("SHA-1");
		mg.update(certificates[0].getEncoded());
		byte[] thumbPrintDec = mg.digest();
		String thumbprint = "";
		for (byte b : thumbPrintDec) {
			thumbprint = thumbprint.concat(String.format("%02x", b));
		}
		return thumbprint;
	}

	private static Certificate[] getCertificates(String url, HttpsURLConnection con)
			throws SSLPeerUnverifiedException, GettingThumbprintException {
		Certificate[] certificates = con.getServerCertificates();
		if (certificates.length < 1) {
			throw new GettingThumbprintException("No certificates avaliable for " + url);
		}
		return certificates;
	}

	private static HttpsURLConnection connectToUrl(String url) throws GettingThumbprintException, IOException {
		URL preparedUrl = prepareUrl(url);
		LOG.debug("Prepared url: {}", preparedUrl);
		trustAllHosts();
		Proxy proxy = createProxy();
		LOG.debug("Created proxy: {}", proxy);

		HttpsURLConnection con = openConnection(preparedUrl, proxy);
		con.setHostnameVerifier(DO_NOT_VERIFY);
		con.connect();
		return con;
	}

	private static HttpsURLConnection openConnection(URL preparedUrl, Proxy proxy) throws IOException {
		HttpsURLConnection con;
		if (proxy == null) {
			con = (HttpsURLConnection) preparedUrl.openConnection();
		} else {
			con = (HttpsURLConnection) preparedUrl.openConnection(proxy);
		}
		return con;
	}

	static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			// skip verification
			return true;
		}
	};

	private static void trustAllHosts() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				// remove checks
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				// remove checks
			}
		} };

		try {
			SSLContext sc = SSLContext.getInstance(TLS);
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Could not get SSLContext for " + TLS, e);
		} catch (KeyManagementException e) {
			LOG.error("Could not get innitialize SSLContext", e);
		}
	}

	private static Proxy createProxy() throws IOException {
		ProxyData proxyData = PropertiesHandler.readProxyData();
		if (proxyData == null) {
			LOG.warn("Empty properties detected. Proceeding without proxy");
			return null;
		}
		return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyData.getServer(), proxyData.getPortInt()));
	}
}
