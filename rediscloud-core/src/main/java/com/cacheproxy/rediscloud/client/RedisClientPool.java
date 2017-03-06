package com.cacheproxy.rediscloud.client;

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

		RedisClient instance = new RedisClient();

		instance.start();

		return instance;
	}
	
	public static RedisClient getInstance(){
		return RedisClientHolder.instance;
	}
}
