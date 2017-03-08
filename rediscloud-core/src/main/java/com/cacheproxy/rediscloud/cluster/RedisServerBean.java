package com.cacheproxy.rediscloud.cluster;

import com.cacheproxy.rediscloud.common.RedisConstants;
import com.cacheproxy.rediscloud.config.ConnectionPoolConfig;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-8
 */
public class RedisServerBean {

	private String host;
	private int port;
	private ConnectionPoolConfig poolConfig;
	private int weight;
	

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

	public ConnectionPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(ConnectionPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public String getKey(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(RedisConstants.REDIS_CLOUD_CLIENT_PRE);
		buffer.append(host);
		buffer.append(RedisConstants.SEPERATOR_ACCESS_LOG);
		buffer.append(port);
		return buffer.toString();
	}

}
