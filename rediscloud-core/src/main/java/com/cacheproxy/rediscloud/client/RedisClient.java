package com.cacheproxy.rediscloud.client;

import io.netty.channel.ChannelHandlerContext;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.cacheproxy.rediscloud.client.conn.IRedisConnection;
import com.cacheproxy.rediscloud.codec.request.entity.RedisRequest;
import com.cacheproxy.rediscloud.pool.CollectionFactory;
import com.cacheproxy.rediscloud.pool.ConnectionPoolConfig;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisClient implements Client {
	// TODO 先实现为 单连接的，跑通之后，再实现为 多个连接的
	
	private GenericObjectPool<IRedisConnection> pool;
	
	public void start(){
		CollectionFactory factory = new CollectionFactory();
		ConnectionPoolConfig config = new ConnectionPoolConfig();
		config.setMinIdle(5);
		config.setMaxTotal(100);
		// TODO 这些都要从配置中获取
		pool = new GenericObjectPool<IRedisConnection>(factory, config);
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


