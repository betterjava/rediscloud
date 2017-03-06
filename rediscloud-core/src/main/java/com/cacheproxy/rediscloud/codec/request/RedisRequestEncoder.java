package com.cacheproxy.rediscloud.codec.request;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisRequestEncoder extends MessageToByteEncoder<IRedisRequest> {

	@Override
	protected void encode(ChannelHandlerContext ctx, IRedisRequest msg,
			ByteBuf out) throws Exception {
		msg.encode(out);
	}

}
