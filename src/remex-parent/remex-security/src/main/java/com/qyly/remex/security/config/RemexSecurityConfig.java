package com.qyly.remex.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.qyly.remex.security.component.modules.RemexAuthenticationEntryPoint;
import com.qyly.remex.security.component.modules.RemexAuthenticationFailureHandler;
import com.qyly.remex.security.component.modules.RemexAuthenticationSuccessHandler;
import com.qyly.remex.security.component.modules.RemexDaoAuthenticationProvider;
import com.qyly.remex.security.component.modules.RemexLogoutSuccessHandler;
import com.qyly.remex.security.component.modules.RemexWebSecurityConfigurerAdapter;
import com.qyly.remex.security.custom.RemexSecurityCustom;
import com.qyly.remex.utils.Assist;

/**
 * remex spring security config
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
public class RemexSecurityConfig {
	
	public RemexSecurityConfig() {
		//初始化
		init();
	}
	
	/**
	 * 初始化
	 */
	protected void init() {
		
	}
	
	@Bean
	@ConditionalOnMissingBean
	public RemexWebSecurityConfigurerAdapter createRemexWebSecurityConfigurerAdapter(RemexSecurityCustom remexSecurityCustom
			, RemexDaoAuthenticationProvider remexDaoAuthenticationProvider
			, RemexAuthenticationEntryPoint remexAuthenticationEntryPoint
			, RemexAuthenticationSuccessHandler remexAuthenticationSuccessHandler
			, RemexAuthenticationFailureHandler remexAuthenticationFailureHandler
			, RemexLogoutSuccessHandler remexLogoutSuccessHandler) {
		RemexWebSecurityConfigurerAdapter bean = new RemexWebSecurityConfigurerAdapter();
		bean.setRemexAuthenticationEntryPoint(remexAuthenticationEntryPoint);
		bean.setRemexAuthenticationFailureHandler(remexAuthenticationFailureHandler);
		bean.setRemexAuthenticationSuccessHandler(remexAuthenticationSuccessHandler);
		bean.setRemexLogoutSuccessHandler(remexLogoutSuccessHandler);
		bean.setRemexDaoAuthenticationProvider(remexDaoAuthenticationProvider);
		return bean;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public RemexSecurityCustom createCustom() {
		RemexSecurityCustom bean = new RemexSecurityCustom();
		return bean;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public RemexDaoAuthenticationProvider createRemexDaoAuthenticationProvider() {
		RemexDaoAuthenticationProvider bean = new RemexDaoAuthenticationProvider();
		
		//用户获取业务方法
		Assist.ifNotNull(RemexSecurityCustom.USER_DETAILS_SERVICE, bean::setUserDetailsService);
		//密码加密方式
		Assist.ifNotNull(RemexSecurityCustom.PASSWORD_ENCODER, bean::setPasswordEncoder);
		
		return bean;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public RemexAuthenticationEntryPoint createRemexAuthenticationEntryPoint() {
		RemexAuthenticationEntryPoint bean = new RemexAuthenticationEntryPoint();
		return bean;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public RemexAuthenticationSuccessHandler createRemexAuthenticationSuccessHandler() {
		RemexAuthenticationSuccessHandler bean = new RemexAuthenticationSuccessHandler();
		return bean;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public RemexAuthenticationFailureHandler createRemexAuthenticationFailureHandler() {
		RemexAuthenticationFailureHandler bean = new RemexAuthenticationFailureHandler();
		return bean;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public RemexLogoutSuccessHandler createRemexLogoutSuccessHandler() {
		RemexLogoutSuccessHandler bean = new RemexLogoutSuccessHandler();
		return bean;
	}
	
	public TokenBasedRememberMeServices createTokenBasedRememberMeServices() {
		TokenBasedRememberMeServices bean = new TokenBasedRememberMeServices(null, null);
		return bean;
	}
}
