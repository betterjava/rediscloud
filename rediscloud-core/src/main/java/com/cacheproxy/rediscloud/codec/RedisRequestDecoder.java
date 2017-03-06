package com.cacheproxy.rediscloud.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

import com.cacheproxy.rediscloud.codec.entity.request.RedisRequest;
import com.cacheproxy.rediscloud.common.RedisConstants;

/**
 * @desc redis 请求解码器
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisRequestDecoder extends ReplayingDecoder<RedisRequestState> {

	/**
	 * redis 命令的协议格式如下：
	 * 
	 * SET mykey myvalue--->*3\r\n$3\r\nSET\r\n$5\r\nmykey\r\n$7\r\nmyvalue\r\n
	 * *<参数数量> CR LF $<参数 1 的字节数量> CR LF <参数 1 的数据> CR LF ... $<参数 N 的字节数量> CR
	 * LF <参数 N 的数据> CR LF
	 */

	public RedisRequestDecoder() {
		state(RedisRequestState.ARG_NUM);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buff,
			List<Object> out) throws Exception {

		// byte[] bb = new byte[buff.readableBytes()];
		// buff.readBytes(bb);
		// System.out.println(new String(bb));

		int argCount = 0;
		int currentArgLength = 0;
		RedisRequest request = new RedisRequest();
		while (true) {
			switch (state()) {
			case ARG_NUM:
				byte first = buff.readByte();
				if (first != '*') {
					throw new UnsupportedOperationException(
							"can  not  find  * in redis command!");
				}

				argCount = parseNum(buff); // 解析出数字
				request.setArgCount(argCount);

				checkpoint(RedisRequestState.ARG_BYTE_LENGTH);

				break;
			case ARG_BYTE_LENGTH:
				if (buff.readByte() != '$') {
					throw new UnsupportedOperationException(
							"can  not  find  $ in redis command!");
				}

				currentArgLength = parseNum(buff);

				checkpoint(RedisRequestState.ARG_DATA);

				break;
			case ARG_DATA:

				request.appendCommand(buff.readBytes(currentArgLength).array());
				if (buff.readByte() != RedisConstants.CR_BYTE || buff.readByte() != RedisConstants.LF_BYTE) {
					throw new UnsupportedOperationException(
							"can  not  find  CR  LF after arg  in redis command!");
				}
				if ((--argCount) <= 0) {
					out.add(request);
					checkpoint(RedisRequestState.ARG_NUM);
					return;
				} else {
					checkpoint(RedisRequestState.ARG_BYTE_LENGTH);
				}
				break;

			default:
				throw new UnsupportedOperationException("不支持的 redis 命令");
			}
		}

	}

	/**
	 * 
	 * 从缓存区中 读取到 数字,记住 ，都是char 类型，不是int
	 * 
	 * @param buff
	 * @return
	 */
	private int parseNum(ByteBuf buff) {
		StringBuffer sb = new StringBuffer();

		char ch = (char) buff.readByte();
		while (ch != RedisConstants.CR_BYTE) {
			sb.append(ch);
			ch = (char) buff.readByte();
		}
		buff.readByte();// 跳过 LF
		int num = Integer.parseInt(sb.toString());
		return num;
	}

}
