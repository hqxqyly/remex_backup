package com.qyly.remex.shiro.redis.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisClusterManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qyly.remex.constant.BConst;
import com.qyly.remex.redis.properties.RedisProperties;
import com.qyly.remex.shiro.config.ShiroConfig;
import com.qyly.remex.shiro.properties.ShiroProperties;
import com.qyly.remex.utils.Assert;
import com.qyly.remex.utils.ObjectUtils;
import com.qyly.remex.utils.StringUtils;

/**
 * shiro config 以redis存储缓存数据，来实现分布式系统的shiro功能
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
public class ShiroRedisConfig extends ShiroConfig {
	
	/**
	 * shiro核心安全事务管理器
	 * @param shiroRealm
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public SecurityManager securityManager(AuthorizingRealm shiroRealm, SessionManager sessionManager
			, RedisCacheManager redisCacheManager) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setSessionManager(sessionManager);
		manager.setCacheManager(redisCacheManager);
		manager.setRealm(shiroRealm);
		return manager;
	}
	
	/**
	 * shiro SessionManager
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public DefaultSessionManager sessionManager(ShiroProperties properties, RedisSessionDAO redisSessionDAO) {
		DefaultSessionManager sessionManager = super.sessionManager(properties);
		sessionManager.setSessionDAO(redisSessionDAO);
		return sessionManager;
	}

	/**
	 * shiro redis manager，支持redis集群环境
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public IRedisManager createRedisManager(RedisProperties properties) {
		Assert.isTrue(ObjectUtils.isNotEmpty(properties.getHostName()), "redis hostName cannot be null");
		
		String[] addressArr = properties.getHostName();
		
		IRedisManager bean = null;

		//集群
		if (addressArr.length > 1) {
			bean = createRedisManagerCluster(properties);
		} else {  //单点
			bean = createRedisManagerSingle(properties);
		}
		
		return bean;
	}
	
	/**
	 * shiro redis session dao
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public RedisSessionDAO createRedisSessionDAO(IRedisManager redisManager, ShiroProperties shiroProperties) {
		RedisSessionDAO bean = new RedisSessionDAO();
		bean.setRedisManager(redisManager);
		
		//缓存数据所用的表名
		if (StringUtils.isNotBlank(shiroProperties.getCacheSessionTableName())) {
			bean.setKeyPrefix(shiroProperties.getCacheSessionTableName());
		}

		return bean;
	}
	
	/**
	 * shiro redis session dao
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public RedisCacheManager createRedisCacheManager(IRedisManager redisManager, ShiroProperties shiroProperties) {
		RedisCacheManager bean = new RedisCacheManager();
		bean.setRedisManager(redisManager);
		
		//缓存数据所用的表名
		if (StringUtils.isNotBlank(shiroProperties.getCacheDataTableName())) {
			bean.setKeyPrefix(shiroProperties.getCacheDataTableName());
		}
		
		//缓存数据所用的实体主键名
		if (StringUtils.isNotBlank(shiroProperties.getCacheDataEntityId())) {
			bean.setPrincipalIdFieldName(shiroProperties.getCacheDataEntityId());
		}
		
		return bean;
	}
	
	
	
	
	/**
	 * 创建集群的shiro redis manager
	 * @param redisProperties
	 * @return
	 */
	protected IRedisManager createRedisManagerCluster(RedisProperties properties) {
		Assert.isTrue(ObjectUtils.isNotEmpty(properties.getHostName()), "redis hostName cannot be null");
		
		RedisClusterManager bean = new RedisClusterManager();
		String newHostName = StringUtils.join(properties.getHostName(), BConst.COMMA);
		bean.setHost(newHostName);
		if (properties.getDatabase() != null) {
			bean.setDatabase(properties.getDatabase());
		}
		if (StringUtils.isNotBlank(properties.getPassword())) {
			bean.setPassword(properties.getPassword());
		}
		return bean;
	}
	
	/**
	 * 创建单点的shiro redis manager
	 * @param redisProperties
	 * @return
	 */
	protected IRedisManager createRedisManagerSingle(RedisProperties properties) {
		Assert.isTrue(ObjectUtils.isNotEmpty(properties.getHostName()), "redis hostName cannot be null");
		
		RedisManager bean = new RedisManager();
		bean.setHost(properties.getHostName()[0]);
		if (properties.getDatabase() != null) {
			bean.setDatabase(properties.getDatabase());
		}
		if (StringUtils.isNotBlank(properties.getPassword())) {
			bean.setPassword(properties.getPassword());
		}
		return bean;
	}
	
	/**
	 * 取得redis集群的hostName分割符
	 * @return
	 */
	protected String getRedisClusterHostNameSplit() {
		return BConst.COMMA;
	}
}