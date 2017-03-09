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
public class DefaultLoadBalance extends AbstraceLoadBalance{
	

	
	@Override
	public void onChange(RedisCloudCluster cluster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RedisServerBean select(RedisCommandBean redisCommand,RedisServerBean serverBean,RedisCloudCluster cluster) {
		// TODO 暂时不实现
		return cluster.getServerClusterBeans().get(0).getMaster();
	}

}


