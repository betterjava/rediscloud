package com.cacheproxy.rediscloud.spring.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.cacheproxy.rediscloud.spring.schema.RedisCloudConstant;
import com.cacheproxy.rediscloud.spring.schema.RedisCloudNodeParser;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-9
 */
public class RedisCloudHanderSupport extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser(RedisCloudConstant.REDISCLOUDNODE, new RedisCloudNodeParser());
	}

}


