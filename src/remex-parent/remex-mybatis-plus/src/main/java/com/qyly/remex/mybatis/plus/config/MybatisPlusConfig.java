package com.qyly.remex.mybatis.plus.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.qyly.remex.mybatis.plus.properties.MybatisPlusProperties;
import com.smart.remex.utils.StringUtils;

/**
 * Mybatis plus config
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
@ConditionalOnBean(DataSource.class)
public class MybatisPlusConfig {

	/**
	 * Mybatis
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public MybatisSqlSessionFactoryBean createSqlSessionFactory(MybatisPlusProperties properties, DataSource dataSource) 
			throws Exception {
		MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setConfiguration(properties.getConfiguration());
		
		if (StringUtils.isNotBlank(properties.getResolveMapperLocations())) {
			bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(properties.getResolveMapperLocations()));
		}
		
		bean.setPlugins(createPaginationInterceptor());
		
		return bean;
	}
	
	/**
	 * 分页插件
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public PaginationInterceptor createPaginationInterceptor () {
		PaginationInterceptor bean = new PaginationInterceptor();
		return bean;
	}
	
	/**
	 * Mybatis plus properties
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = MybatisPlusProperties.MYBATIS_PLUS_PREFIX)
	@ConditionalOnMissingBean
	public MybatisPlusProperties createMybatisPlusProperties() {
		MybatisPlusProperties properties = new MybatisPlusProperties();
		return properties;
	}
}
