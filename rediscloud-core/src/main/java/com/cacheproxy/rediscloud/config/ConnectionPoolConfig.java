package com.cacheproxy.rediscloud.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-7
 */
public class ConnectionPoolConfig extends GenericObjectPoolConfig {
	@Override
	public void setMaxTotal(int maxTotal) {
		// TODO Auto-generated method stub
		super.setMaxTotal(maxTotal);
	}
}
