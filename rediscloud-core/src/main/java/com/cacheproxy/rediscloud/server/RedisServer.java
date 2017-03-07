package com.cacheproxy.rediscloud.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cacheproxy.rediscloud.codec.request.RedisRequestDecoder;
import com.cacheproxy.rediscloud.codec.request.RedisRequestEncoder;
import com.cacheproxy.rediscloud.codec.response.RedisResponseEncoder;

/**
 * 
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisServer {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(RedisServer.class);

	private final static int DEFAULT_PORT = 8080; 
	
	private int port = DEFAULT_PORT;// TODO 应该从配置中获取

	public void start() {

		ServerBootstrap boot = new ServerBootstrap();
		EventLoopGroup boosGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();

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
				pipeline.addLast(new RedisServerHandler());
			}
		});
		ChannelFuture future = boot.bind(port);
		future.syncUninterruptibly();// TODO
		LOGGER.info("RedisServer start success,post:[{}]", port);
	}
}