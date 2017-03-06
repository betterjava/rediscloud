package com.cacheproxy.rediscloud.codec;

import com.cacheproxy.rediscloud.codec.entity.IRedisResponse;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @desc redis 响应 编码器
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisResponseEncoder extends MessageToByteEncoder<IRedisResponse>{

	@Override
	protected void encode(ChannelHandlerContext ctx, IRedisResponse msg,
			ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		
	}

}


