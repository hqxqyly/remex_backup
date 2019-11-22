package com.qyly.remex.shiro.bean;

/**
 * 登录结果集
 * 
 * @author Qiaoxin.Hong
 *
 * @param <T> shiro中的用户缓存实体
 */
public class LoginResult<T> {

	/** 登录的用户信息 */
	private T user;
	
	/** sessionId */
	private String sessionId;
	
	public void setUser(T user) {
		this.user = user;
	}
	
	public T getUser() {
		return user;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
