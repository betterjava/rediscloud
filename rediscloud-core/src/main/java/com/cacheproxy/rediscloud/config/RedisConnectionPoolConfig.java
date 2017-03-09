package com.cacheproxy.rediscloud.config;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-9
 */
public class RedisConnectionPoolConfig extends ConnectionPoolConfig {
	private String host;
	private int port;

	public RedisConnectionPoolConfig(String host, int port) {
		this.host = host;
		this.port = port;
	}

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
