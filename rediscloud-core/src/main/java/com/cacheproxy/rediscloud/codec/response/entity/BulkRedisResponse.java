package com.cacheproxy.rediscloud.codec.response.entity;

import io.netty.buffer.ByteBuf;

import com.cacheproxy.rediscloud.common.RedisResponseType;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-6
 */
public class BulkRedisResponse extends AbstractRedisResponse {

	private int length;
	
	public BulkRedisResponse(RedisResponseType type) {
		super(RedisResponseType.BULK);
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * 服务器发送的内容中：
		第一字节为 "$" 符号
		接下来跟着的是表示实际回复长度的数字值
		之后跟着一个 CRLF
		再后面跟着的是实际回复数据
		最末尾是另一个 CRLF
		对于前面的 GET 命令，服务器实际发送的内容为：
		
		"$6\r\nfoobar\r\n"
		
		
		如果被请求的值不存在， 那么批量回复会将特殊值 -1 用作回复的长度值， 就像这样：
		客户端：GET non-existing-key
		服务器：$-1
	 */
	@Override
	protected void doEncode(ByteBuf buf) {
		buf.writeBytes(String.valueOf(length).getBytes());
		writeCRLF(buf);
		if(length > -1 && value != null){
			buf.writeBytes(value);
			writeCRLF(buf);
		}
	}
}
