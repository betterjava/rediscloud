package com.cacheproxy.rediscloud.server;

import com.cacheproxy.rediscloud.codec.entity.IRedisRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisServerHandler extends
		SimpleChannelInboundHandler<IRedisRequest> {

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, IRedisRequest msg)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
