package com.cacheproxy.rediscloud.client;

import com.cacheproxy.rediscloud.config.ConnectionPoolConfig;

/**
 * @desc 临时方法
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisClientPool {

	private static class RedisClientHolder {
		static RedisClient instance = initClient();
	}

	private static RedisClient initClient() {
		// 配置 应当在 server 启动的时候初始化 好
		// TODO 从配置中获取
		RedisClient instance = new RedisClient(new ConnectionPoolConfig("10.1.200.144",6379));

		instance.start();

		return instance;
	}
	
	public static RedisClient getInstance(){
		return RedisClientHolder.instance;
	}
}
