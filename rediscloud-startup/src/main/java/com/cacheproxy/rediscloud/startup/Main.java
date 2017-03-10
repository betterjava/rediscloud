package com.cacheproxy.rediscloud.startup;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-9
 */
public class Main {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext(new String[] { "classpath*:redisCloud.xml" });
	}
}
