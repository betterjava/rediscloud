package com.cacheproxy.rediscloud.server;

import java.util.ArrayList;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.cacheproxy.rediscloud.balance.DefaultLoadBalance;
import com.cacheproxy.rediscloud.client.RedisClient;
import com.cacheproxy.rediscloud.cluster.RedisCloudCluster;
import com.cacheproxy.rediscloud.cluster.RedisServerBean;
import com.cacheproxy.rediscloud.cluster.RedisServerClusterBean;
import com.cacheproxy.rediscloud.codec.request.RedisRequestDecoder;
import com.cacheproxy.rediscloud.codec.response.RedisResponseEncoder;
import com.cacheproxy.rediscloud.config.ConnectionPoolConfig;
import com.cacheproxy.rediscloud.config.RedisConnectionPoolConfig;

/**
 * 
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisServer {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(RedisServer.class);

	private RedisCloudCluster redisCloudCluster;

	public RedisServer(RedisCloudCluster cluster) {
		this.redisCloudCluster = cluster;
		init();// 进行 client 的初始化工作
	}

	public void start() {

		ServerBootstrap boot = new ServerBootstrap();
		EventLoopGroup boosGroup = new NioEventLoopGroup();// TODO accept 线程数
		EventLoopGroup workGroup = new NioEventLoopGroup();// TODO RW 线程数

		boot.group(boosGroup, workGroup);
		boot.channel(NioServerSocketChannel.class);
		boot.option(ChannelOption.TCP_NODELAY, true);
		boot.option(ChannelOption.SO_BACKLOG, 128);
		boot.option(ChannelOption.SO_KEEPALIVE, true);
		// TODO socket 的参数 还需要慢慢 商榷 和处理

		boot.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new RedisRequestDecoder());
				pipeline.addLast(new RedisResponseEncoder());
				pipeline.addLast(new RedisServerHandler(redisCloudCluster));
			}
		});
		ChannelFuture future = boot.bind(redisCloudCluster.getPort());
		future.syncUninterruptibly();// TODO
		LOGGER.info("RedisServer start success,post:[{}]",redisCloudCluster.getPort());
	}

	private void init() {
		if (redisCloudCluster == null || redisCloudCluster.getMasters() == null) {
			LOGGER.error(" redisCloudCluster 出错  redisCloudCluster:{} ",redisCloudCluster);
			return;
		}

		List<RedisServerClusterBean> mastsers = redisCloudCluster.getMasters();
		for (RedisServerClusterBean serverClusterBean : mastsers) {

			RedisServerBean master = serverClusterBean.getMaster();
			if (master != null) {
				RedisClient client = new RedisClient(converToRedisPoolConfig(master.getPoolConfig(),master));
				redisCloudCluster.getRedisClientMap().put(master.getKey(),client);
			}
			List<RedisServerBean> slaves = serverClusterBean.getSlaves();
			if (CollectionUtils.isEmpty(slaves)) {
				continue;
			}
			for (RedisServerBean serverBean : slaves) {
				RedisClient client = new RedisClient(converToRedisPoolConfig(master.getPoolConfig(),serverBean));
				redisCloudCluster.getRedisClientMap().put(serverBean.getKey(),client);
			}

		}
	}

	private RedisConnectionPoolConfig converToRedisPoolConfig(ConnectionPoolConfig poolConfig,RedisServerBean redisServerBean) {
		RedisConnectionPoolConfig config = new RedisConnectionPoolConfig(redisServerBean.getHost(), redisServerBean.getPort());
		BeanUtils.copyProperties(poolConfig, poolConfig);
		return config;
	}

	public static void main(String[] args) {
		
		RedisServerBean master = new RedisServerBean();
		master.setHost("10.1.200.144");
		master.setPort(6379);
		master.setWeight(1);
		master.setPoolConfig(new ConnectionPoolConfig());
		
		RedisServerClusterBean clusterBean = new RedisServerClusterBean();
		clusterBean.setLoadBalance(new DefaultLoadBalance());
		
		clusterBean.setMaster(master );
		
		
		List<RedisServerClusterBean> redisServerClusterBeans = new ArrayList<RedisServerClusterBean>();
		
		redisServerClusterBeans.add(clusterBean);
		RedisCloudCluster cluster = new RedisCloudCluster(redisServerClusterBeans );
		RedisServer server = new RedisServer(cluster);
		 server.start();
	}
}
