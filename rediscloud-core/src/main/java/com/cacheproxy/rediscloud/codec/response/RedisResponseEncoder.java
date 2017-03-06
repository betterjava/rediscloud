package com.cacheproxy.rediscloud.codec.response;


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
	

	/**
	 * 把redis 响应 写入到 前端的编码
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, IRedisResponse msg,
			ByteBuf out) throws Exception {
		msg.encode(out);
	}

}


