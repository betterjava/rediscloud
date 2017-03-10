package com.cacheproxy.rediscloud.spring.schema;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.cacheproxy.rediscloud.balance.LoadBalance;
import com.cacheproxy.rediscloud.common.RedisConstants;
import com.cacheproxy.rediscloud.config.ConnectionPoolConfig;
import com.cacheproxy.rediscloud.spring.schema.bean.RedisCloudMaster;
import com.cacheproxy.rediscloud.spring.schema.bean.RedisCloudNode;
import com.cacheproxy.rediscloud.spring.schema.bean.RedisCloudSlave;

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
	protected void doParse(Element element, ParserContext parserContext,BeanDefinitionBuilder builder) {
		// 解析的主要逻辑卸载这里...
//		<!--redis 主从配置  -->
//	    <redisCloud:redisCloudNode id="redisCloud"  serverHost="127.0.0.1"  loadBalance-ref="defaultLoadBalance" zkAddress="127.0.0.1:2181" prot="6379" >
//	    <!--主配置  -->      
//	      <redisCloud:redisCloudMaster id="redisMasters" host="10.1.200.144" port="6379"  loadBalance-ref="defaultLoadBalance" config-ref="redisPoolConfig">
//	      	  <!--从配置  -->     
//	      	  <redisCloud:redisCloudSlave id="redisSlaves" host="10.1.200.144" port="6379"  weight="1" config-ref="redisPoolConfig"></redisCloud:redisCloudSlave>
//	      </redisCloud:redisCloudMaster> 
//	    </redisCloud:redisCloudNode>
		
		try {
			String serverHost = element.getAttribute(RedisCloudConstant.CONFIG_SERVERHOST);
			String port = element.getAttribute(RedisCloudConstant.CONFIG_PORT);
			String zkAdress = element.getAttribute(RedisCloudConstant.CONFIG_ZKADDRESS);
			String loadBalanceRef = element.getAttribute(RedisCloudConstant.CONFIG_LOADBALANCE_REF);
			
			
			builder.setLazyInit(false);
			builder.addPropertyValue(RedisCloudConstant.PROP_HOST, serverHost);
			builder.addPropertyValue(RedisCloudConstant.PROP_PORT, Integer.parseInt(port));
			builder.addPropertyValue(RedisCloudConstant.PROP_ZKADDRESS, zkAdress);
			builder.addPropertyReference(RedisCloudConstant.PROP_LOADBALANCE, loadBalanceRef);
			
			builder.addPropertyValue(RedisCloudConstant.PROP_MASTERS, getRedisCloudMasters(element,parserContext,builder));
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.doParse(element, parserContext, builder);
	}

	private List<BeanDefinition> getRedisCloudMasters(Element element,ParserContext parserContext, BeanDefinitionBuilder builder) {
		List<?> entryEles = DomUtils.getChildElementsByTagName(element, RedisCloudConstant.CONFIG_REDISCLOUDMASTER);
		List<BeanDefinition> result = new ManagedList<BeanDefinition>(entryEles.size());
		
		if(CollectionUtils.isNotEmpty(entryEles)){
			Iterator<?> it = entryEles.iterator();
			while(it.hasNext()){
				BeanDefinitionBuilder masterBeanBuilder = BeanDefinitionBuilder.rootBeanDefinition(RedisCloudMaster.class);
				Element masterElement = (Element) it.next();
				parseMasterElement(masterElement,parserContext, masterBeanBuilder);
				result.add(masterBeanBuilder.getBeanDefinition());
			}
		}
		
		return result;
	}

	private void parseMasterElement(Element masterElement,ParserContext parserContext, BeanDefinitionBuilder masterBeanBuilder) {
//		<redisCloud:redisCloudMaster id="redisMasters" host="10.1.200.144" port="6379"  loadBalance-ref="defaultLoadBalance" config-ref="redisPoolConfig">
		String host = masterElement.getAttribute(RedisCloudConstant.CONFIG_HOST);
		String port = masterElement.getAttribute(RedisCloudConstant.CONFIG_PORT);
		String loadBalanceRef = masterElement.getAttribute(RedisCloudConstant.CONFIG_LOADBALANCE_REF);
		String configRef = masterElement.getAttribute(RedisCloudConstant.CONFIG_CONFIG_REF);
		
		
		
		masterBeanBuilder.setLazyInit(false);
		masterBeanBuilder.addPropertyValue(RedisCloudConstant.PROP_HOST, host);
		masterBeanBuilder.addPropertyValue(RedisCloudConstant.PROP_PORT, Integer.parseInt(port));
		masterBeanBuilder.addPropertyReference(RedisCloudConstant.PROP_LOADBALANCE, loadBalanceRef);
		masterBeanBuilder.addPropertyReference(RedisCloudConstant.PROP_POOLCONFIG, configRef);
		
		masterBeanBuilder.addPropertyValue(RedisCloudConstant.PROP_SLAVES,getRedisCloudSlaves(masterElement,parserContext,masterBeanBuilder));
	}

	private List<BeanDefinition> getRedisCloudSlaves(Element masterElement,ParserContext parserContext, BeanDefinitionBuilder masterBeanBuilder) {
		List<?> entryEles = DomUtils.getChildElementsByTagName(masterElement, RedisCloudConstant.CONFIG_REDISCLOUDSLAVE);
		List<BeanDefinition> result = new ManagedList<BeanDefinition>(entryEles.size());
		
		if(CollectionUtils.isNotEmpty(entryEles)){
			Iterator<?> it = entryEles.iterator();
			while(it.hasNext()){
				BeanDefinitionBuilder slaveBeanBuilder = BeanDefinitionBuilder.rootBeanDefinition(RedisCloudSlave.class);
				Element slaveElement = (Element) it.next();
				parseSlavelement(slaveElement,parserContext, slaveBeanBuilder);
				result.add(slaveBeanBuilder.getBeanDefinition());
			}
		}
		
		return result;
	}

	private void parseSlavelement(Element slaveElement,ParserContext parserContext, BeanDefinitionBuilder slaveBeanBuilder) {
		
		//  <redisCloud:redisCloudSlave id="redisSlaves" host="10.1.200.144" port="6379"  weight="1" config-ref="redisPoolConfig"></redisCloud:redisCloudSlave>
		String host = slaveElement.getAttribute(RedisCloudConstant.CONFIG_HOST);
		String port = slaveElement.getAttribute(RedisCloudConstant.CONFIG_PORT);
		String weight = slaveElement.getAttribute(RedisCloudConstant.CONFIG_WEIGHT);
		String configRef = slaveElement.getAttribute(RedisCloudConstant.CONFIG_CONFIG_REF);
		
		
		slaveBeanBuilder.setLazyInit(false);
		slaveBeanBuilder.addPropertyValue(RedisCloudConstant.PROP_HOST, host);
		slaveBeanBuilder.addPropertyValue(RedisCloudConstant.PROP_PORT, Integer.parseInt(port));
		slaveBeanBuilder.addPropertyValue(RedisCloudConstant.PROP_WEIGHT, Integer.parseInt(weight));
		slaveBeanBuilder.addPropertyReference(RedisCloudConstant.PROP_POOLCONFIG, configRef);
	}
	
}
