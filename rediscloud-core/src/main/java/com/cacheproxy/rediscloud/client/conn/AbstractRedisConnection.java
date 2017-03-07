package com.cacheproxy.rediscloud.client.conn;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cacheproxy.rediscloud.codec.request.RedisRequestEncoder;
import com.cacheproxy.rediscloud.codec.response.IRedisResponse;
import com.cacheproxy.rediscloud.codec.response.RedisResponseDecoder;
import com.cacheproxy.rediscloud.config.ConnectionPoolConfig;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public abstract class AbstractRedisConnection implements IRedisConnection {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractRedisConnection.class);
	
	protected SocketChannel socketChannel;
	
	private ChannelHandlerContext frontCtx;
	
	private ConnectionPoolConfig poolConfig;
	
	public AbstractRedisConnection(ConnectionPoolConfig config){
		this.poolConfig = config;
		initClientBootstrap();
	}
	
	
	protected void initClientBootstrap() {
		
		EventLoopGroup workGroup = new NioEventLoopGroup();
		Bootstrap boot = new Bootstrap();
		
		boot.group(workGroup);
		boot.channel(NioSocketChannel.class);
		boot.option(ChannelOption.SO_KEEPALIVE, true);// TODO socket 的参数 还有待商榷
		boot.option(ChannelOption.TCP_NODELAY, true);
		boot.remoteAddress(poolConfig.getHost(), poolConfig.getPort());
		
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
		ChannelFuture future = null;
		try {
			future = boot.connect(poolConfig.getHost(), poolConfig.getPort()).sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(future.isSuccess()){
			socketChannel = (SocketChannel) future.channel();
			LOGGER.info("client start success ,host:[{}],post:[{}]",poolConfig.getHost(), poolConfig.getPort());
		}
	}
	
	private class RedisClientHandler extends SimpleChannelInboundHandler<IRedisResponse> {
		
		@Override
		protected void messageReceived(ChannelHandlerContext ctx, IRedisResponse msg)
				throws Exception {
			
			// TODO  别人的不是这么写的，这么写有什么问题，有待商榷
			
			frontCtx.channel().writeAndFlush(msg);
		}

	}
	
	public void setFrontCtx(ChannelHandlerContext frontCtx) {
		this.frontCtx = frontCtx;
	}

}
