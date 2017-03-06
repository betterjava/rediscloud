package com.cacheproxy.rediscloud.client;

import io.netty.channel.ChannelHandlerContext;

import com.cacheproxy.rediscloud.client.conn.RedisConnection;
import com.cacheproxy.rediscloud.codec.request.entity.RedisRequest;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisClient implements Client {
	// TODO 先实现为 单连接的，跑通之后，再实现为 多个连接的
	
	private RedisConnection connection;
	
	public void start(){
		// 连接 connect
		connection = new RedisConnection();
		connection.open();
	}
	
	public void write(RedisRequest request, ChannelHandlerContext context) {
		connection.write(request, context);
	}
}	


