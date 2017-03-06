package com.cacheproxy.rediscloud.server;

import com.cacheproxy.rediscloud.codec.request.IRedisRequest;

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
		/**
		 * TODO 
		 * 先把所有的命令 发送给 后端，不做处理--处理放到后面来做
		 * 
		 */
		
	}

}
