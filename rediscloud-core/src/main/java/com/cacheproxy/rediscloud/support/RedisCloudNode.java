package com.cacheproxy.rediscloud.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.cacheproxy.rediscloud.balance.LoadBalance;

/**
 * @desc 
 * @author liya
 * @emial  lijiaqiya@163.com
 * @date 2017-3-8
 */
public class RedisCloudNode implements InitializingBean{
	
//	<redisProxy:redisProxyNode id="redisnode"  redisProxyHost="127.0.0.1"  algorithm-ref="loadMasterBalance" zkAddress="127.0.0.1:2181" redisProxyPort="6379" >
	
	private String host;
	private int port;
	private LoadBalance loadBalance;
	private String zkAddress;
	private List<RedisCloudMaster> masters = new ArrayList<RedisCloudMaster>();
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// TODO 配置加载，服务启动的入口--记住了，都是套路
		/**
		 * 1.初始化cluser
		 * 2.注册zk
		 * 3.启动server
		 */
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public LoadBalance getLoadBalance() {
		return loadBalance;
	}


	public void setLoadBalance(LoadBalance loadBalance) {
		this.loadBalance = loadBalance;
	}


	public String getZkAddress() {
		return zkAddress;
	}


	public void setZkAddress(String zkAddress) {
		this.zkAddress = zkAddress;
	}


	public List<RedisCloudMaster> getMasters() {
		return masters;
	}


	public void setMasters(List<RedisCloudMaster> masters) {
		this.masters = masters;
	}
	
	

}


