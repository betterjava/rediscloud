package com.cacheproxy.rediscloud.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-7
 */
public class ConnectionPoolConfig extends GenericObjectPoolConfig {

	public ConnectionPoolConfig(String host, int port) {
		this.host = host;
		this.port = port;
	}

	private String host;
	private int port;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
