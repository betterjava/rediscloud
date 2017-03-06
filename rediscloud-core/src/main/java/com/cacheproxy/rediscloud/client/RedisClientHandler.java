package com.cacheproxy.rediscloud.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

import com.cacheproxy.rediscloud.codec.response.IRedisResponse;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisClientHandler extends SimpleChannelInboundHandler<IRedisResponse> {
	
	
	/**
	 * 前端 channel 上下 文
	 */
	private ChannelHandlerContext frontCtx;
	
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, IRedisResponse msg)
			throws Exception {
		
		// TODO  别人的不是这么写的，这么写有什么问题，有待商榷
		
		frontCtx.channel().writeAndFlush(msg);
	}
	
	public void setFrontCtx(ChannelHandlerContext frontCtx) {
		this.frontCtx = frontCtx;
	}
	
	

}


