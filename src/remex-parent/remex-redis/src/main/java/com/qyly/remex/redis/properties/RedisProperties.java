package com.qyly.remex.redis.properties;

/**
 * redis properties
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RedisProperties {

	/** redis properties配置文件前缀 */
	public static final String REDIS_PREFIX = "redis";
	
	/** redis地址，支持单点和集群，多个地址以,号隔开，例：127.0.0.1:6379, 127.0.0.1:6378 */
	protected String[] hostName;
	
	public void setHostName(String[] hostName) {
		this.hostName = hostName;
	}
	
	public String[] getHostName() {
		return hostName;
	}
}
