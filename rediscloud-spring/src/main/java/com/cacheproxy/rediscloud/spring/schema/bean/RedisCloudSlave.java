package com.cacheproxy.rediscloud.spring.schema.bean;

import com.cacheproxy.rediscloud.config.ConnectionPoolConfig;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-8
 */
public class RedisCloudSlave {
	private String host;
	private int port;
	private int weight;
	private ConnectionPoolConfig poolConfig;
	
	
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
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public ConnectionPoolConfig getPoolConfig() {
		return poolConfig;
	}
	public void setPoolConfig(ConnectionPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
	
	
}


