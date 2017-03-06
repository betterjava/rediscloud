package com.cacheproxy.rediscloud.client.conn;

import io.netty.channel.ChannelHandlerContext;

import com.cacheproxy.rediscloud.codec.request.entity.RedisRequest;

/**
 * @desc 后端redis 连接
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public interface IRedisConnection {

	/**
	 * 将 redis 命令写入到 后端 redis
	 * 
	 * @param request
	 * @param context
	 */
	void write(RedisRequest request, ChannelHandlerContext context);
	
	/**
	 * 打开连接
	 */
	void open();

	/**
	 * 关闭连接
	 */
	void close();

	/**
	 * 关闭连接
	 * @param timeout
	 */
	void close(int timeout);
	
	/**
	 * 连接 是否存活
	 * @return
	 */
	boolean isAvailable();

}
