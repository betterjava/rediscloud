package com.cacheproxy.rediscloud.support;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-8
 */
public class RedisCommandBean {
	
	private String command;
	private byte[] key;
	private boolean isWrite;
	
	
	public RedisCommandBean(String command, byte[] key, boolean isWrite) {
		super();
		this.command = command;
		this.key = key;
		this.isWrite = isWrite;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public byte[] getKey() {
		return key;
	}
	public void setKey(byte[] key) {
		this.key = key;
	}
	public boolean isWrite() {
		return isWrite;
	}
	public void setWrite(boolean isWrite) {
		this.isWrite = isWrite;
	}
	
	
}


