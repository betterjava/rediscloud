package com.cacheproxy.rediscloud.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cacheproxy.rediscloud.balance.LoadBalance;
import com.cacheproxy.rediscloud.client.RedisClient;

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
	
	
	private List<RedisServerClusterBean> redisServerClusterBeans = new ArrayList<RedisServerClusterBean>();
	
	private Map<String, RedisClient> redisClientMap = new HashMap<String, RedisClient>();// 
	
	private Map<String, RedisServerClusterBean> serverClusterBeanMap = new HashMap<String, RedisServerClusterBean>();
	
	// 一主多从
	private Map<String,List<RedisServerBean>> masterClusters = new HashMap<String, List<RedisServerBean>>(); // 初始化的时候没有考虑
	
	
	public RedisCloudCluster(List<RedisServerClusterBean> redisServerClusterBeans){
		this.redisServerClusterBeans = redisServerClusterBeans;
		init();// 初始化关联 map
	}
	
	private void init() {
		for(RedisServerClusterBean serverClusterBean:redisServerClusterBeans){
			serverClusterBeanMap.put(serverClusterBean.getMaster().getKey(), serverClusterBean);
			masterClusters.put(serverClusterBean.getMaster().getKey(), serverClusterBean.getSlaves());
		}
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

	public List<RedisServerClusterBean> getMasters() {
		return redisServerClusterBeans;
	}

	public void setMasters(List<RedisServerClusterBean> masters) {
		this.redisServerClusterBeans = masters;
	}
	
	public Map<String, RedisClient> getRedisClientMap() {
		return redisClientMap;
	}
	
	public Map<String, List<RedisServerBean>> getMasterClusters() {
		return masterClusters;
	}
	
	public Map<String, RedisServerClusterBean> getServerClusterBeanMap() {
		return serverClusterBeanMap;
	}

}
