package com.cacheproxy.rediscloud.codec.entity;

import io.netty.buffer.ByteBuf;

import com.cacheproxy.rediscloud.common.RedisResponseType;

public interface IRedisResponse {

	/**
	 * 获取 redis 响应消息类型
	 * @return
	 */
	RedisResponseType getType();

	/**
	 * set redis  响应类型
	 * @param type
	 */
	void setType(RedisResponseType type);
	
	/**
	 * 编码
	 * @param buf
	 */
	void encode(ByteBuf buf);
	
}
