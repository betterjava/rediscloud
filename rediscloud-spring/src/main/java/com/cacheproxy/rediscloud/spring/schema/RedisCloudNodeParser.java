package com.cacheproxy.rediscloud.spring.schema;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.cacheproxy.rediscloud.spring.schema.bean.RedisCloudNode;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-9
 */
public class RedisCloudNodeParser extends AbstractSimpleBeanDefinitionParser {

	
	@Override
	protected Class<?> getBeanClass(Element element) {
		return RedisCloudNode.class;
	}
	
	@Override
	protected void doParse(Element element, ParserContext parserContext,BeanDefinitionBuilder beanBuilder) {
		// 解析的主要逻辑卸载这里...
	}
	
}
