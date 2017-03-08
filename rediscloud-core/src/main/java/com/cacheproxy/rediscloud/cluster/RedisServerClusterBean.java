package com.cacheproxy.rediscloud.cluster;

import java.util.List;

import com.cacheproxy.rediscloud.balance.LoadBalance;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-8
 */
public class RedisServerClusterBean {
	
	private RedisServerBean master;
	
	private List<RedisServerBean> slaves;
	
	private LoadBalance loadBalance;

	
	public void setMaster(RedisServerBean master) {
		this.master = master;
	}
	
	public RedisServerBean getMaster() {
		return master;
	}

	public List<RedisServerBean> getSlaves() {
		return slaves;
	}

	public void setSlaves(List<RedisServerBean> slaves) {
		this.slaves = slaves;
	}

	public LoadBalance getLoadBalance() {
		return loadBalance;
	}

	public void setLoadBalance(LoadBalance loadBalance) {
		this.loadBalance = loadBalance;
	}
	
}


