package com.cacheproxy.rediscloud.codec.response.entity;

import io.netty.buffer.ByteBuf;

import com.cacheproxy.rediscloud.codec.response.IRedisResponse;
import com.cacheproxy.rediscloud.common.RedisConstants;
import com.cacheproxy.rediscloud.common.RedisResponseType;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public abstract class AbstractRedisResponse implements IRedisResponse {
	
	private RedisResponseType type;
	
	protected byte[] value;

	public AbstractRedisResponse(RedisResponseType type) {
		setType(type);
	}

	@Override
	public RedisResponseType getType() {
		return type;
	}

	@Override
	public void setType(RedisResponseType type) {
		this.type = type;
	}
	
	public byte[] getValue() {
		return value;
	}
	
	public void setValue(byte[] value) {
		this.value = value;
	}
	
	public void writeCRLF(ByteBuf byteBuf) {
		byteBuf.writeByte(RedisConstants.CR_BYTE);
		byteBuf.writeByte(RedisConstants.LF_BYTE);
	}
	
	@Override
	public void encode(ByteBuf buf) {
		/**
		 * 先把 header 写进去，然后写真正的数据
		 */
		buf.writeByte(type.getHead());
		doEncode(buf);
	}
	protected abstract void doEncode(ByteBuf buf);
}
