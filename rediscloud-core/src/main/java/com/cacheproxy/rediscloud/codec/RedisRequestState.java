package com.cacheproxy.rediscloud.codec;

public enum RedisRequestState {
	ARG_NUM,			// 参数数量
	ARG_BYTE_LENGTH,	// 参数字节长度
	ARG_DATA; 			// 参数
}
