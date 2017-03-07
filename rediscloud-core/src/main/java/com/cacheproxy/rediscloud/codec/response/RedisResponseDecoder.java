package com.cacheproxy.rediscloud.codec.response;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.ArrayList;
import java.util.List;

import com.cacheproxy.rediscloud.codec.response.entity.AbstractRedisResponse;
import com.cacheproxy.rediscloud.codec.response.entity.BulkRedisResponse;
import com.cacheproxy.rediscloud.codec.response.entity.ErrorRedisResponse;
import com.cacheproxy.rediscloud.codec.response.entity.IntegerRedisResponse;
import com.cacheproxy.rediscloud.codec.response.entity.MultiBulkRedisResponse;
import com.cacheproxy.rediscloud.codec.response.entity.StatusRedisResponse;
import com.cacheproxy.rediscloud.common.RedisConstants;
import com.cacheproxy.rediscloud.common.RedisResponseType;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisResponseDecoder extends ReplayingDecoder<RedisResponseState> {

	public RedisResponseDecoder() {
		state(RedisResponseState.RESULT_TYPE);
	}

	/**
	 * 把redis 服务器返回的 数据 解码成 redis response
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {

		AbstractRedisResponse response = null;

		while (true) {
			switch (state()) {
			case RESULT_TYPE:
				byte head = in.readByte();
				if (RedisResponseType.BULK.getHead() == head) {
					response = new BulkRedisResponse();
				} else if (RedisResponseType.ERROR.getHead() == head) {
					response = new ErrorRedisResponse();
				} else if (RedisResponseType.INTEGER.getHead() == head) {
					response = new IntegerRedisResponse();
				} else if (RedisResponseType.MULTIBULK.getHead() == head) {
					response = new MultiBulkRedisResponse();
				} else if (RedisResponseType.STATUS.getHead() == head) {
					response = new StatusRedisResponse();
				} else {
					throw new UnsupportedOperationException(
							"can get a  head from response ...");
				}
				break;
			case RESULT_CONTENT:
				RedisResponseType type = response.getType();
				if (type.equals(RedisResponseType.BULK)) {
					readBulk(in, (BulkRedisResponse) response);
				} else if (type.equals(RedisResponseType.MULTIBULK)) {
					readMultBulk(in, (MultiBulkRedisResponse) response);
				} else if (type.equals(RedisResponseType.ERROR)) {
					response.setValue(readLine(in));
				} else if (type.equals(RedisResponseType.INTEGER)) {
					response.setValue(readLine(in));
				} else if (type.equals(RedisResponseType.STATUS)) {
					response.setValue(readLine(in));
				}
				break;
			default:
				throw new UnsupportedOperationException(
						"decode response wrong ....");
			}
		}

	}

	/**
	 * 读取多条批量回复
	 * 
	 * @param in
	 * @param response
	 */
	private void readMultBulk(ByteBuf in, MultiBulkRedisResponse response) {

		int count = Integer.valueOf(new String(readLine(in)));

		response.setCount(count);

		for (int i = 0; i < count; i++) {
			char type = in.readChar();
			if ((char) RedisResponseType.INTEGER.getHead() == type) {
				IntegerRedisResponse integerResponse = new IntegerRedisResponse();
				integerResponse.setValue(readLine(in));
				response.addResponse(integerResponse);
			} else if ((char) RedisResponseType.BULK.getHead() == type) {
				BulkRedisResponse bulkRedisResponse = new BulkRedisResponse();
				readBulk(in, bulkRedisResponse);
				response.addResponse(bulkRedisResponse);
			}
		}
	}

	/**
	 * 读取单条回复
	 * 
	 * @param in
	 * @param response
	 */
	private void readBulk(ByteBuf in, BulkRedisResponse response) {
		int length = Integer.valueOf(new String(readLine(in)));
		response.setLength(length);
		if (length <= 0) { // read null
			// nothing to do
		} else if (length == 0) {// read ""
			in.skipBytes(2);
		} else {

			byte[] value = new byte[length];

			in.readBytes(value);

			response.setValue(value);

			in.skipBytes(2);
		}
	}

	private byte[] readLine(ByteBuf in) {
		List<Byte> result = new ArrayList<Byte>();
		char ch = (char) in.readByte();
		while (ch != RedisConstants.CR_BYTE) {
			result.add((byte) ch);
			ch = (char) in.readByte();
		}
		in.readByte();

		byte[] bytes = new byte[result.size()];

		int i = 0;
		for (Byte eve : result) {
			bytes[i] = eve;
			i++;
		}
		return bytes;
	}
}
