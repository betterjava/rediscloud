package com.cacheproxy.rediscloud.client.conn;

import io.netty.channel.ChannelHandlerContext;

import com.cacheproxy.rediscloud.codec.request.entity.RedisRequest;
import com.cacheproxy.rediscloud.config.ConnectionPoolConfig;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisConnection extends AbstractRedisConnection {

	public RedisConnection(ConnectionPoolConfig config) {
		super(config);
	}

	@Override
	public void write(RedisRequest request, ChannelHandlerContext context) {
		setFrontCtx(context);
		socketChannel.writeAndFlush(request);
	}

	@Override
	public void open() {
		initClientBootstrap();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close(int timeout) {
		
	}

	@Override
	public boolean isAvailable() {
		// TODO 这块 还没有实现
		return true;
	}
	
	

}


