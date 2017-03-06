package com.cacheproxy.rediscloud.codec.response;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisResponseDecoder extends ReplayingDecoder<RedisResponseState>{

	/**
	 *  把redis 服务器返回的 数据 解码成  redis response
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}


