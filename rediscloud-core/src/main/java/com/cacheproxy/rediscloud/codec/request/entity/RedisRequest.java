package com.cacheproxy.rediscloud.codec.request.entity;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import com.cacheproxy.rediscloud.codec.request.IRedisRequest;


public class RedisRequest implements IRedisRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private int argCount;

	private List<byte[]> commands = new ArrayList<byte[]>();// 存放 redis 命令的每一个值

	public int getArgCount() {
		return argCount;
	}

	public void setArgCount(int argCount) {
		this.argCount = argCount;
	}

	public void appendCommand(byte[] bytes) {
		commands.add(bytes);
	}

	@Override
	public void encode(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}
}
