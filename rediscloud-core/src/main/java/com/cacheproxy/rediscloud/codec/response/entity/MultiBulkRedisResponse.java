package com.cacheproxy.rediscloud.codec.response.entity;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import com.cacheproxy.rediscloud.codec.response.IRedisResponse;
import com.cacheproxy.rediscloud.common.RedisResponseType;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public class MultiBulkRedisResponse extends AbstractRedisResponse {

	private int count;

	protected List<IRedisResponse> list = new ArrayList<IRedisResponse>();

	public MultiBulkRedisResponse(RedisResponseType type) {
		super(RedisResponseType.MULTIBULK);
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setList(List<IRedisResponse> list) {
		this.list = list;
	}

	@Override
	protected void doEncode(ByteBuf buf) {
		buf.writeBytes(String.valueOf(count).getBytes());
		writeCRLF(buf);

		for (IRedisResponse response : list) {

			// 返回的是数值结果

			if (response instanceof IntegerRedisResponse) {
				buf.writeByte(RedisResponseType.INTEGER.getHead());
				if (count == 0 && value == null) {
					buf.writeBytes(String.valueOf(-1).getBytes());
					writeCRLF(buf);
				} else {
					buf.writeBytes(String.valueOf(value.length).getBytes());
					writeCRLF(buf);
					buf.writeBytes(value);
					writeCRLF(buf);
				}

			} else if (response instanceof BulkRedisResponse) {
				buf.writeByte(RedisResponseType.BULK.getHead());
				if (count == 0 && value == null) {
					buf.writeBytes(String.valueOf(-1).getBytes());
					writeCRLF(buf);
				} else {
					buf.writeBytes(String.valueOf(value.length).getBytes());
					writeCRLF(buf);
					buf.writeBytes(value);
					writeCRLF(buf);
				}
			}
		}

	}
}
