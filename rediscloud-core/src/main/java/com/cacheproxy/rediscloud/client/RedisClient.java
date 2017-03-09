package com.cacheproxy.rediscloud.client;

import io.netty.channel.ChannelHandlerContext;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.cacheproxy.rediscloud.client.conn.IRedisConnection;
import com.cacheproxy.rediscloud.codec.request.entity.RedisRequest;
import com.cacheproxy.rediscloud.config.RedisConnectionPoolConfig;
import com.cacheproxy.rediscloud.pool.CollectionFactory;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisClient implements Client {

	private GenericObjectPool<IRedisConnection> pool;

	private RedisConnectionPoolConfig poolConfig;

	public RedisClient(RedisConnectionPoolConfig config) {
		this.poolConfig = config;
		init();
	}

	private void init() {
		CollectionFactory factory = new CollectionFactory(poolConfig);
		
		pool = new GenericObjectPool<IRedisConnection>(factory, poolConfig);
	}

	public void write(RedisRequest request, ChannelHandlerContext context) {
		try {
			pool.borrowObject().write(request, context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
