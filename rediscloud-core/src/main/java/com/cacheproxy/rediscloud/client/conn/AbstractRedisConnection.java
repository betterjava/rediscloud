package com.cacheproxy.rediscloud.client.conn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.cacheproxy.rediscloud.client.RedisClientHandler;
import com.cacheproxy.rediscloud.codec.request.RedisRequestEncoder;
import com.cacheproxy.rediscloud.codec.response.RedisResponseDecoder;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public abstract class AbstractRedisConnection implements IRedisConnection {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractRedisConnection.class);
	
	private final static String DEFAULT_HOST = "127.0.0.1";
	private final static int DEFAULT_PORT = 8080; 
	
	protected SocketChannel socketChannel;
	
	private String host = DEFAULT_HOST;
	private int port = DEFAULT_PORT;
	
	
	protected void start() {
		
		EventLoopGroup workGroup = new NioEventLoopGroup();
		Bootstrap boot = new Bootstrap();
		
		boot.group(workGroup);
		boot.channel(NioSocketChannel.class);
		boot.option(ChannelOption.SO_KEEPALIVE, true);// TODO socket 的参数 还有待商榷
		boot.option(ChannelOption.TCP_NODELAY, true);
		boot.remoteAddress(host, port);
		
		boot.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new RedisRequestEncoder());
				pipeline.addLast(new RedisResponseDecoder());
				pipeline.addLast(new RedisClientHandler());
			}
		});
		
		/**
		 * TODO 这里也不是这么写的
		 */
		ChannelFuture future = boot.connect(host, port);
		
		if(future.isSuccess()){
			LOGGER.info("client start success ,post:[{}]");
		}
	}
}
