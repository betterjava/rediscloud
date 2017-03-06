package com.cacheproxy.rediscloud.common;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 * 
 * 
 * 
 */
public enum RedisResponseType {

	/**
	 *  状态回复（status reply）的第一个字节是 "+"
		错误回复（error reply）的第一个字节是 "-"
		整数回复（integer reply）的第一个字节是 ":"
		批量回复（bulk reply）的第一个字节是 "$"
		多条批量回复（multi bulk reply）的第一个字节是 "*"
	 */
	
	STATUS((byte) '+'), 
	ERROR((byte) '-'), 
	INTEGER((byte) ':'), 
	BULK((byte) '$'),
	MULTIBULK((byte) '*');

	private byte head;

	RedisResponseType(byte head) {
		this.head = head;
	}

	public byte getHead() {
		return head;
	}

}
