package com.qyly.remex.security.component.modules;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.qyly.remex.security.custom.RemexSecurityCustom;
import com.qyly.remex.utils.CollectionUtils;

/**
 * 
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RemexWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	protected RemexAuthenticationEntryPoint remexAuthenticationEntryPoint;
	
	protected RemexAuthenticationSuccessHandler remexAuthenticationSuccessHandler;
	
	protected RemexAuthenticationFailureHandler remexAuthenticationFailureHandler;
	
	protected RemexLogoutSuccessHandler remexLogoutSuccessHandler;
	
	protected RemexDaoAuthenticationProvider remexDaoAuthenticationProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(remexDaoAuthenticationProvider);
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//不需要身份认证的url
		String[] authorizePermitUrl = CollectionUtils.toArr(String.class, RemexSecurityCustom.AUTHORIZE_PERMIT_URL);
//		http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		http
			.httpBasic().authenticationEntryPoint(remexAuthenticationEntryPoint)
			
			.and()
            .authorizeRequests()
            .antMatchers(authorizePermitUrl).permitAll()  //不需要身份认证的url
            .anyRequest().authenticated()  //其他url需要身份认证
            
            .and()
            .formLogin()  //开启登录
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler(remexAuthenticationSuccessHandler)
            .failureHandler(remexAuthenticationFailureHandler)
            .loginProcessingUrl(RemexSecurityCustom.LOGIN_PROCESSING_URL)
            .permitAll()
            
            .and()
            .rememberMe()
            .userDetailsService(RemexSecurityCustom.USER_DETAILS_SERVICE)
            .tokenValiditySeconds(RemexSecurityCustom.TOKEN_VALIDITY_SECONDS)  //过期时间
//            .alwaysRemember(true)  // 总是记住，会刷新过期时间
            
            .and()
            .logout()
            .logoutSuccessHandler(remexLogoutSuccessHandler)
            .permitAll()
            
            .and().csrf().disable();  //禁用csrf
		
	}
	
//	protected RemexUsernamePasswordAuthenticationFilter customAuthenticationFilter() throws Exception {
//		RemexUsernamePasswordAuthenticationFilter filter = new RemexUsernamePasswordAuthenticationFilter();
//		filter.setAuthenticationSuccessHandler(remexAuthenticationSuccessHandler);
//		filter.setAuthenticationFailureHandler(remexAuthenticationFailureHandler);
//		filter.setFilterProcessesUrl(RemexSecurityCustom.LOGIN_PROCESSING_URL);
//		filter.setUsernameParameter(RemexSecurityCustom.USERNAME_PARAMETER);
//		filter.setPasswordParameter(RemexSecurityCustom.PASSWORD_PARAMETER);
//		filter.setAuthenticationManager(authenticationManagerBean());
//		return filter;
//	}
	
	public void setRemexAuthenticationEntryPoint(RemexAuthenticationEntryPoint remexAuthenticationEntryPoint) {
		this.remexAuthenticationEntryPoint = remexAuthenticationEntryPoint;
	}
	
	public RemexAuthenticationEntryPoint getRemexAuthenticationEntryPoint() {
		return remexAuthenticationEntryPoint;
	}

	public RemexAuthenticationSuccessHandler getRemexAuthenticationSuccessHandler() {
		return remexAuthenticationSuccessHandler;
	}

	public void setRemexAuthenticationSuccessHandler(RemexAuthenticationSuccessHandler remexAuthenticationSuccessHandler) {
		this.remexAuthenticationSuccessHandler = remexAuthenticationSuccessHandler;
	}

	public RemexAuthenticationFailureHandler getRemexAuthenticationFailureHandler() {
		return remexAuthenticationFailureHandler;
	}

	public void setRemexAuthenticationFailureHandler(RemexAuthenticationFailureHandler remexAuthenticationFailureHandler) {
		this.remexAuthenticationFailureHandler = remexAuthenticationFailureHandler;
	}

	public RemexLogoutSuccessHandler getRemexLogoutSuccessHandler() {
		return remexLogoutSuccessHandler;
	}

	public void setRemexLogoutSuccessHandler(RemexLogoutSuccessHandler remexLogoutSuccessHandler) {
		this.remexLogoutSuccessHandler = remexLogoutSuccessHandler;
	}
	
	public void setRemexDaoAuthenticationProvider(RemexDaoAuthenticationProvider remexDaoAuthenticationProvider) {
		this.remexDaoAuthenticationProvider = remexDaoAuthenticationProvider;
	}
	
	public RemexDaoAuthenticationProvider getRemexDaoAuthenticationProvider() {
		return remexDaoAuthenticationProvider;
	}
}
