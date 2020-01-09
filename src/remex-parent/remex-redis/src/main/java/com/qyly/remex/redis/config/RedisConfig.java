package com.qyly.remex.redis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.qyly.remex.redis.properties.RedisProperties;
import com.qyly.remex.utils.Assist;

import redis.clients.jedis.JedisPoolConfig;

/**
 * redis config
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RedisConfig {

	/**
	 * redis config
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = RedisProperties.REDIS_PREFIX)
	@ConditionalOnMissingBean
	public RedisProperties createRedisProperties() {
		RedisProperties properties = new RedisProperties();
		return properties;
	}
	
	/**
	 * JedisConnectionFactory
	 * @param properties
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public JedisConnectionFactory createJedisConnectionFactory(RedisProperties properties) {
		Assist.notEmpty(properties.getHostName(), "redis hostName cannot be null");
		
		String[] addressArr = properties.getHostName();
		JedisConnectionFactory bean = null;
		//集群
		if (addressArr.length > 1) {
			//集群配置
			RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
			Assist.forEach(addressArr, address -> {
				String[] hostNameArr = address.split(":");
				clusterConfig.clusterNode(hostNameArr[0], Integer.valueOf(hostNameArr[1]));
			});
			clusterConfig.setMaxRedirects(properties.getMaxRedirects());
			
			//连接池配置
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxTotal(properties.getMaxIdle());
			poolConfig.setMaxIdle(properties.getMaxIdle());
			poolConfig.setMaxWaitMillis(properties.getMaxWaitMillis());
			
			bean = new JedisConnectionFactory(clusterConfig, poolConfig);
		} else {  //单点
			bean = new JedisConnectionFactory();
			String[] hostNameArr = addressArr[0].split(":");
			
			bean.setHostName(hostNameArr[0]);
			bean.setPort(Integer.valueOf(hostNameArr[1]));
			Assist.ifNotBlank(properties.getPassword(), bean::setPassword);
			bean.setTimeout(properties.getTimeout());
		}
		
		bean.setDatabase(properties.getDatabase());
		
		return bean;
	}
	
	
	/**
	 * RedisTemplate
	 * @param properties
	 * @return
	 */
	@Bean("redisTemplate")
	@ConditionalOnMissingBean
	public RedisTemplate<String, Object> createRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
		RedisTemplate<String, Object> bean = new RedisTemplate<>();
		bean.setConnectionFactory(jedisConnectionFactory);
		bean.setDefaultSerializer(new StringRedisSerializer());
		return bean;
	}
}
