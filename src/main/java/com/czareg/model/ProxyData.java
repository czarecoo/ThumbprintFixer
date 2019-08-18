package com.czareg.model;

public class ProxyData {
	private String server;
	private String port;

	@Override
	public String toString() {
		return "ProxyData [server=" + server + ", port=" + port + "]";
	}

	public ProxyData(String server, String port) {
		this.server = server;
		this.port = port;
	}

	public String getServer() {
		return server;
	}

	public String getPort() {
		return port;
	}

	public int getPortInt() {
		return Integer.valueOf(port);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((server == null) ? 0 : server.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProxyData other = (ProxyData) obj;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (server == null) {
			if (other.server != null)
				return false;
		} else if (!server.equals(other.server))
			return false;
		return true;
	}
}