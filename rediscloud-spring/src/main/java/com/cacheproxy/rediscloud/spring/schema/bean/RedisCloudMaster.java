package com.cacheproxy.rediscloud.spring.schema.bean;

import java.util.ArrayList;
import java.util.List;

import com.cacheproxy.rediscloud.balance.LoadBalance;
import com.cacheproxy.rediscloud.config.ConnectionPoolConfig;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-8
 */
public class RedisCloudMaster {

//	<redisProxy:redisProxyMaster id="redismasters" host="127.0.0.1" port="6380"  algorithm-ref="loadSlaveBalance" config-ref="redisPoolConfig">
	private List<RedisCloudSlave> slaves = new ArrayList<RedisCloudSlave>();
	
	private String host;
	private int port;
	private LoadBalance loadBalance;
	private ConnectionPoolConfig poolConfig;
	
	
	public List<RedisCloudSlave> getSlaves() {
		return slaves;
	}
	public void setSlaves(List<RedisCloudSlave> slaves) {
		this.slaves = slaves;
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
	public LoadBalance getLoadBalance() {
		return loadBalance;
	}
	public void setLoadBalance(LoadBalance loadBalance) {
		this.loadBalance = loadBalance;
	}
	public ConnectionPoolConfig getPoolConfig() {
		return poolConfig;
	}
	public void setPoolConfig(ConnectionPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
	
	
	
	
}


