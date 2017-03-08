package com.cacheproxy.rediscloud.cluster;

import java.util.List;

import com.cacheproxy.rediscloud.balance.LoadBalance;

/**
 * @desc 集群配置
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-8
 */
public class RedisCloudCluster {
	private String host;
	private int port;
	private LoadBalance loadBalance;
	private List<RedisServerClusterBean> masters;

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

	public List<RedisServerClusterBean> getMasters() {
		return masters;
	}

	public void setMasters(List<RedisServerClusterBean> masters) {
		this.masters = masters;
	}

}
