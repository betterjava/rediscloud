package com.cacheproxy.rediscloud.codec.request;

import io.netty.buffer.ByteBuf;

import java.io.Serializable;

public interface IRedisRequest extends Serializable{
		
	/**
	 * 编码
	 * @param buf
	 */
	void encode(ByteBuf buf);
}
