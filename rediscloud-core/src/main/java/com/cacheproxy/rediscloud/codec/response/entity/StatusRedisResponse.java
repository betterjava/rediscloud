
package com.cacheproxy.rediscloud.codec.response.entity;

import io.netty.buffer.ByteBuf;

import com.cacheproxy.rediscloud.common.RedisResponseType;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-6
 */
public class StatusRedisResponse extends AbstractRedisResponse {

	public StatusRedisResponse() {
		super(RedisResponseType.STATUS);
	}

	@Override
	protected void doEncode(ByteBuf buf) {
		buf.writeBytes(value);
		writeCRLF(buf);
	}
	
}


