package com.cacheproxy.rediscloud.balance;

import com.cacheproxy.rediscloud.cluster.RedisServerBean;
import com.cacheproxy.rediscloud.support.RedisCommandBean;

/**
 * @desc  负载均衡策略
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-8
 */
public interface LoadBalance {
	
	RedisServerBean select(RedisCommandBean redisCommand,RedisServerBean serverBean);
}


