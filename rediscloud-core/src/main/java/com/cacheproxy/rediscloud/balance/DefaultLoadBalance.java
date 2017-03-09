package com.cacheproxy.rediscloud.balance;

import com.cacheproxy.rediscloud.cluster.RedisCloudCluster;
import com.cacheproxy.rediscloud.cluster.RedisServerBean;
import com.cacheproxy.rediscloud.support.RedisCommandBean;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-9
 */
public class DefaultLoadBalance implements LoadBalance{
	
	private RedisCloudCluster redisCloudCluster;
	
	public DefaultLoadBalance(RedisCloudCluster cluster){
		this.redisCloudCluster = cluster;
	}
	
	@Override
	public void onChange(RedisCloudCluster cluster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RedisServerBean select(RedisCommandBean redisCommand,RedisServerBean serverBean) {
		// TODO 暂时不实现
		return redisCloudCluster.getMasters().get(0).getMaster();
	}

	@Override
	public void SetCluster(RedisCloudCluster cluster) {
		// TODO Auto-generated method stub
		
	}

}


