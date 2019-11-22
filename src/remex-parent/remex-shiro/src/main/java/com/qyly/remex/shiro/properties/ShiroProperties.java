package com.qyly.remex.shiro.properties;

import org.springframework.beans.factory.InitializingBean;

import com.qyly.remex.shiro.constant.ShiroOptions;

/**
 * shiro properties
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ShiroProperties implements InitializingBean {

	/** shiro properties配置文件前缀 */
	public static final String SHIRO_PREFIX = "shiro";
	
	/** 是否启用开发模式 */
	protected boolean dev = false;
	
	/** 是否开启权限验证 */
	protected boolean disabledPermit = false;
	
	/** session超时时间，单位秒，默认30分钟 */
	protected long globalSessionTimeout = 1800000;
	
	/** 是否定时检查失效的session */
	protected boolean sessionValidationSchedulerEnabled = true;
	
	/** 是否删除过期的session */
	protected boolean deleteInvalidSessions = true;
	
	/** 缓存数据session所用的表名，在某些组件下使用 */
	protected String cacheSessionTableName;
	
	/** 缓存数据所用的表名，在某些组件下使用 */
	protected String cacheDataTableName;
	
	/** 缓存数据所用的实体主键名，在某些组件下使用 */
	protected String cacheDataEntityId;
	

	@Override
	public void afterPropertiesSet() throws Exception {
		ShiroOptions.DEV = this.dev;
		ShiroOptions.DISABLED_PERMIT = this.disabledPermit;
	}
	

	public boolean isDev() {
		return dev;
	}

	public void setDev(boolean dev) {
		this.dev = dev;
	}

	public long getGlobalSessionTimeout() {
		return globalSessionTimeout;
	}

	public void setGlobalSessionTimeout(long globalSessionTimeout) {
		this.globalSessionTimeout = globalSessionTimeout;
	}

	public boolean isSessionValidationSchedulerEnabled() {
		return sessionValidationSchedulerEnabled;
	}

	public void setSessionValidationSchedulerEnabled(boolean sessionValidationSchedulerEnabled) {
		this.sessionValidationSchedulerEnabled = sessionValidationSchedulerEnabled;
	}

	public boolean isDeleteInvalidSessions() {
		return deleteInvalidSessions;
	}

	public void setDeleteInvalidSessions(boolean deleteInvalidSessions) {
		this.deleteInvalidSessions = deleteInvalidSessions;
	}

	public String getCacheSessionTableName() {
		return cacheSessionTableName;
	}

	public void setCacheSessionTableName(String cacheSessionTableName) {
		this.cacheSessionTableName = cacheSessionTableName;
	}

	public String getCacheDataTableName() {
		return cacheDataTableName;
	}

	public void setCacheDataTableName(String cacheDataTableName) {
		this.cacheDataTableName = cacheDataTableName;
	}
	
	public void setCacheDataEntityId(String cacheDataEntityId) {
		this.cacheDataEntityId = cacheDataEntityId;
	}
	
	public String getCacheDataEntityId() {
		return cacheDataEntityId;
	}

	public boolean isDisabledPermit() {
		return disabledPermit;
	}

	public void setDisabledPermit(boolean disabledPermit) {
		this.disabledPermit = disabledPermit;
	}
}
