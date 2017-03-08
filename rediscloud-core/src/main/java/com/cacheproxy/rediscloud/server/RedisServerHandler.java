package com.cacheproxy.rediscloud.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.cacheproxy.rediscloud.balance.LoadBalance;
import com.cacheproxy.rediscloud.client.RedisClient;
import com.cacheproxy.rediscloud.client.RedisClientPool;
import com.cacheproxy.rediscloud.cluster.RedisCloudCluster;
import com.cacheproxy.rediscloud.cluster.RedisServerBean;
import com.cacheproxy.rediscloud.cluster.RedisServerClusterBean;
import com.cacheproxy.rediscloud.codec.request.IRedisRequest;
import com.cacheproxy.rediscloud.codec.request.entity.RedisRequest;
import com.cacheproxy.rediscloud.codec.response.entity.StatusRedisResponse;
import com.cacheproxy.rediscloud.common.RedisConstants;
import com.cacheproxy.rediscloud.support.RedisCommandBean;
import com.cacheproxy.rediscloud.support.RedisCommandRW;

/**
 * 
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-6
 */
public class RedisServerHandler extends
		SimpleChannelInboundHandler<IRedisRequest> {
	
	private RedisCloudCluster redisCloudCluster;
	
	private Map<String, RedisClient> redisClientMap ;
	
	public RedisServerHandler(RedisCloudCluster redisCloudCluster){
		this.redisCloudCluster = redisCloudCluster;
		this.redisClientMap = redisCloudCluster.getRedisClientMap();
	}
	
	
	/* (non-Javadoc)
	 * @see io.netty.channel.SimpleChannelInboundHandler#messageReceived(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext context, IRedisRequest msg)
			throws Exception {
		
		// 解析出来 redis 命令
		RedisRequest request = (RedisRequest) msg; 
		
		String command = new String(request.getCommands().get(0)).toLowerCase();
		
		/**
		 * ping --返回 pong
		 * quit -- OK
		 * info 在只有 一个server 的时候才可以执行
		 * keys 在只有一个主的情况下执行
		 * 普通读写命令
		 */
		if(request.getCommands().size() == 1 && command.equals(RedisConstants.PING)){
			StatusRedisResponse response = new StatusRedisResponse();
			response.setValue(RedisConstants.PING.getBytes());
			context.writeAndFlush(response);
			
		}else if(request.getCommands().size() == 1 && command.equals(RedisConstants.QUIT)){
			StatusRedisResponse response = new StatusRedisResponse();
			response.setValue(RedisConstants.OK.getBytes());
			context.writeAndFlush(response);
			
		}else if(request.getCommands().size() == 1 && command.equals(RedisConstants.INFO)){
			if(redisClientMap.size() == 1){
				Collection<RedisClient> redisClients = redisClientMap.values();
				new ArrayList<RedisClient>(redisClients).get(0).write(request, context);
			}else { // TODO 自定义一些错误信息
				
			}
			
		}else if(request.getCommands().size()>1 && command.equals(RedisConstants.KEYS)){
			if(redisCloudCluster.getMasters().size() == 1){
				RedisServerClusterBean serverClusterBean = redisCloudCluster.getMasters().get(0);
				redisClientMap.get(serverClusterBean.getMaster().getKey()).write(request, context);
			}else { // TODO 自定义一些错误信息
				
			}
			
		}else if(request.getCommands().size()>1 && !command.equals(RedisConstants.KEYS)){
			
			boolean isWrite = RedisCommandRW.isWrite(command);
			RedisClient client = null;
			if(isWrite){
				 client = getMasterClient(request);
				
			}else {
				 client = getSlaveClient(request);
			}
			client.write(request, context);
		}else { // 直接返回错误编码 TODO 
			
		}
	}


	private RedisClient getSlaveClient(RedisRequest request) {
		// 选取从库
		/** 先选取主，再选取从*/
		RedisCommandBean commandBean = new RedisCommandBean(new String(request.getCommands().get(0)), request.getCommands().get(1), false);
		LoadBalance LoadBalance = redisCloudCluster.getLoadBalance();
		RedisServerBean redisServerBean = LoadBalance.select(commandBean, null); // 选取出来的主 要看看这个主有没有从
		List<RedisServerBean> slaves = redisCloudCluster.getMasterClusters().get(redisServerBean.getKey());
		
		if(CollectionUtils.isNotEmpty(slaves)){
			RedisServerClusterBean redisServerClusterBean = redisCloudCluster.getServerClusterBeanMap().get(redisServerBean.getKey());
			if(redisServerClusterBean != null){
				LoadBalance loadBalance = redisServerClusterBean.getLoadBalance();			
				RedisServerBean resultServerBean = loadBalance.select(commandBean, redisServerBean);
				if(redisServerBean != null){
					return redisClientMap.get(resultServerBean.getKey());
				}
			}
		}
		
		return redisClientMap.get(redisServerBean.getKey());
	}


	private RedisClient getMasterClient(RedisRequest request) {
		RedisCommandBean commandBean = new RedisCommandBean(new String(request.getCommands().get(0)), request.getCommands().get(1), true);
		List<RedisServerClusterBean> masters = redisCloudCluster.getMasters();
		if (masters.size() == 1) {
			String key = masters.get(0).getMaster().getKey();
			return redisClientMap.get(key);
		} else {
			LoadBalance loadBalance = redisCloudCluster.getLoadBalance();
			RedisServerBean redisServerBean = loadBalance.select(commandBean,null);
			return redisClientMap.get(redisServerBean.getKey());
		}
	}

}
