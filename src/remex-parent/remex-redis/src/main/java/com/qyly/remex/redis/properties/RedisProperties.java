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
	protected String[] hostName = new String[] {"localhost:6379"};
	
	/** 密码 */
	protected String password;
	
	/** db序列 */
	protected Integer database = 0;
	
	/** 超时时间，单位：毫秒 */
	protected Integer timeout;
	
	/** 集群，连接池获取失败 最大重定向次数 */
	protected Integer maxRedirects;
	
	/** 集群，连接池最大空闲数 */
	protected Integer maxIdle;
	
	/** 集群，连接池最大连接数 */
	protected Integer maxTotal;
	
	/** 集群，连接池最大阻塞等待时间，负值表示没有限制 */
	protected Integer maxWaitMillis;
	
	public void setHostName(String[] hostName) {
		this.hostName = hostName;
	}
	
	public String[] getHostName() {
		return hostName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getDatabase() {
		return database;
	}

	public void setDatabase(Integer database) {
		this.database = database;
	}

	public Integer getMaxRedirects() {
		return maxRedirects;
	}

	public void setMaxRedirects(Integer maxRedirects) {
		this.maxRedirects = maxRedirects;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}
	
	public void setMaxWaitMillis(Integer maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}
	
	public Integer getMaxWaitMillis() {
		return maxWaitMillis;
	}
}
