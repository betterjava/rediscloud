package com.cacheproxy.rediscloud.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.cacheproxy.rediscloud.balance.LoadBalance;
import com.cacheproxy.rediscloud.cluster.RedisCloudCluster;
import com.cacheproxy.rediscloud.cluster.RedisServerBean;
import com.cacheproxy.rediscloud.cluster.RedisServerClusterBean;
import com.cacheproxy.rediscloud.server.RedisServer;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-8
 */
public class RedisCloudNode implements InitializingBean {

	private String host;
	private int port;
	private LoadBalance loadBalance;
	private String zkAddress;
	private List<RedisCloudMaster> masters = new ArrayList<RedisCloudMaster>();

	@Override
	public void afterPropertiesSet() throws Exception {
		/**
		 * 配置加载，服务启动的入口--记住了，都是套路 1.初始化cluser 2.注册zk TODO 暂时不实现 3.启动server
		 */
		RedisCloudCluster cluster = initRedisCloudCluster();
															
		// TODO 把节点数据放入到 zk
		new RedisServer(cluster).start();
	}

	/**
	 * 初始化 rediscloudcluster
	 * 
	 * @return
	 */
	private RedisCloudCluster initRedisCloudCluster() {


		List<RedisServerClusterBean> clusterMasters = new ArrayList<RedisServerClusterBean>();

		for (RedisCloudMaster master : masters) {
			RedisServerClusterBean serverClusterBean = new RedisServerClusterBean();
			serverClusterBean.setLoadBalance(master.getLoadBalance());

			RedisServerBean masterServerBean = convertToServerBean(master);

			serverClusterBean.setMaster(masterServerBean);

			List<RedisServerBean> slaveServerBeans = new ArrayList<RedisServerBean>();
			for (RedisCloudSlave cloudSlave : master.getSlaves()) {
				RedisServerBean slaveServerBean = convertToServerBean(cloudSlave);
				slaveServerBeans.add(slaveServerBean);
			}
			serverClusterBean.setSlaves(slaveServerBeans);

			clusterMasters.add(serverClusterBean);
		}
		
		RedisCloudCluster redisCluster = new RedisCloudCluster(clusterMasters);

		redisCluster.setHost(this.getHost());
		redisCluster.setPort(this.getPort());
		redisCluster.setLoadBalance(this.getLoadBalance());
		return redisCluster;
	}

	private RedisServerBean convertToServerBean(RedisCloudSlave cloudSlave) {
		RedisServerBean serverBean = new RedisServerBean();
		serverBean.setHost(cloudSlave.getHost());
		serverBean.setPort(cloudSlave.getPort());
		serverBean.setPoolConfig(cloudSlave.getPoolConfig());
		serverBean.setWeight(cloudSlave.getWeight());
		return serverBean;
	}

	private RedisServerBean convertToServerBean(RedisCloudMaster master) {
		RedisServerBean serverBean = new RedisServerBean();
		serverBean.setHost(master.getHost());
		serverBean.setPort(master.getPort());
		serverBean.setPoolConfig(master.getPoolConfig());
		return serverBean;
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
