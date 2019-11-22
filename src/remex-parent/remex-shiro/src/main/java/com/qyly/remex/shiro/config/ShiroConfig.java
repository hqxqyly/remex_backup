package com.qyly.remex.shiro.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.qyly.remex.shiro.component.filter.ShiroAuthenticationFilter;
import com.qyly.remex.shiro.constant.ShiroOptions;
import com.qyly.remex.shiro.properties.ShiroProperties;
import com.qyly.remex.utils.BeanUtils;

/**
 * shiro config
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
public class ShiroConfig {
	
	/**
	 * shiro properties
	 * @return
	 */
	@Bean
	@ConfigurationProperties(ShiroProperties.SHIRO_PREFIX)
	@ConditionalOnMissingBean
	public ShiroProperties createFastShiroProperties() {
		ShiroProperties properties = new ShiroProperties();
		return properties;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, ShiroProperties properties) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);

		//取得shiro所用的拦截器列表
		Map<String, Filter> filters = getFilters();
		if (filters != null) {
			bean.setFilters(filters);
		}
		
		//配置访问权限
		Map<String, String> filterChainDefinitionMap = ShiroOptions.FILTER_CHAIN_DEFINITION_MAP;
		//开发模式，则放行所有请求
		if (properties.isDev()) {
			filterChainDefinitionMap = new HashMap<>();
			filterChainDefinitionMap.put("/**", "anon");
		}
		
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return bean;
	}
	
	/**
	 * shiro核心安全事务管理器
	 * @param shiroRealm
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public SecurityManager securityManager(AuthorizingRealm shiroRealm, SessionManager sessionManager) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setSessionManager(sessionManager);
		manager.setRealm(shiroRealm);
		return manager;
	}
	
	/**
	 * shiro SessionManager
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public DefaultSessionManager sessionManager(ShiroProperties properties) {
		DefaultSessionManager sessionManager = BeanUtils.newInstance(ShiroOptions.SESSION_MANAGER_CLASS);
		sessionManager.setGlobalSessionTimeout(properties.getGlobalSessionTimeout());
		sessionManager.setSessionValidationSchedulerEnabled(properties.isSessionValidationSchedulerEnabled());
		sessionManager.setDeleteInvalidSessions(properties.isDeleteInvalidSessions());
		return sessionManager;
	}
	
	/**
	 * 登录权限登录器
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public AuthorizingRealm shiroRealm(CredentialsMatcher credentialsMatcher) {
		AuthorizingRealm realm = BeanUtils.newInstance(ShiroOptions.AUTH_REALM_CLASS);
		realm.setCredentialsMatcher(getCredentialsMatcher());
		return realm;
	}
	
	/**
	 * 密码加密比对
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public CredentialsMatcher getCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName(ShiroOptions.ENCRYPT_ALGORITHM_NAME);
		credentialsMatcher.setHashIterations(ShiroOptions.ENCRYPT_HASH_ITERATIONS);
		return credentialsMatcher;
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
	    creator.setProxyTargetClass(true);
	    return creator;
	}
	
	@Bean(name = "lifecycleBeanPostProcessor")
	@ConditionalOnMissingBean
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	/**
	 * 取得shiro所用的拦截器列表
	 * @return
	 */
	protected Map<String, Filter> getFilters() {
		Map<String, Filter> filters = new HashMap<>();
		filters.put("authc", new ShiroAuthenticationFilter());
		return filters;
	}
}
