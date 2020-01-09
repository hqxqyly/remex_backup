package com.qyly.remex.mybatis.plus.properties;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.qyly.remex.mybatis.properties.MybatisProperties;

/**
 * Mybatis plus properties
 * 
 * @author Qiaoxin.Hong
 *
 */
public class MybatisPlusProperties {
	
	/** Mybatis plus properties配置文件前缀 */
	public static final String MYBATIS_PLUS_PREFIX = MybatisProperties.MYBATIS_PREFIX;
	
	/** 默认的扫描xml的路径 */
	public static final String DEFAULT_RESOLVE_MAPPER_LOCATIONS = "classpath:mapper/*.xml";

	/** 扫描xml的路径 */
	protected String resolveMapperLocations = DEFAULT_RESOLVE_MAPPER_LOCATIONS;
	
	/** mybatis plus全局配置类 */
	protected GlobalConfig globalConfig = GlobalConfigUtils.defaults();
	
	protected MybatisConfiguration configuration = new MybatisConfiguration();
	
	public MybatisPlusProperties() {
		//默认驼峰命名转换字段
		configuration.setMapUnderscoreToCamelCase(true);
	}
	
	public void setResolveMapperLocations(String resolveMapperLocations) {
		this.resolveMapperLocations = resolveMapperLocations;
	}
	
	public String getResolveMapperLocations() {
		return resolveMapperLocations;
	}
	
	public void setConfiguration(MybatisConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public MybatisConfiguration getConfiguration() {
		return configuration;
	}
	
	public void setGlobalConfig(GlobalConfig globalConfig) {
		this.globalConfig = globalConfig;
	}
	
	public GlobalConfig getGlobalConfig() {
		return globalConfig;
	}
}
