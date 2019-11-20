package com.qyly.remex.redis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.qyly.remex.redis.properties.RedisProperties;
import com.qyly.remex.utils.Assert;
import com.qyly.remex.utils.ObjectUtils;

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
		Assert.isTrue(ObjectUtils.isNotEmpty(properties.getHostName()), "redis hostName cannot be null");
		
		String[] addressArr = properties.getHostName();
		JedisConnectionFactory bean = null;
		//集群
		if (addressArr.length > 1) {
			
		} else {  //单点
			bean = new JedisConnectionFactory();
			String[] hostNameArr = addressArr[0].split(":");
			bean.setHostName(hostNameArr[0]);
			bean.setPort(Integer.valueOf(hostNameArr[1]));
		}
		
		return bean;
	}
	
	
	/**
	 * RedisTemplate
	 * @param properties
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public RedisTemplate<String, Object> createRedisTemplate() {
		RedisTemplate<String, Object> bean = new RedisTemplate<>();
		bean.setDefaultSerializer(new StringRedisSerializer());
		return bean;
	}
}
