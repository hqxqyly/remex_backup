package com.qyly.remex.db.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.qyly.remex.db.properties.DataSourceProperties;
import com.smart.remex.exception.RemexException;

/**
 * DataSource config
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
public class DataSourceConfig {
	
	/**
	 * DataSource
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public DataSource createDataSource(DataSourceProperties properties) {
		DruidDataSource dataSource = new DruidDataSource();
		//填充DataSource properties
		fillDataSourceProperties(dataSource, properties);
		
		return dataSource;
	}
	
	/**
	 * DataSource properties
	 * @return
	 */
	@Bean
	@ConfigurationProperties(DataSourceProperties.DATA_SOURCE_PREFIX)
	@ConditionalOnMissingBean
	public DataSourceProperties createDataSourceProperties() {
		DataSourceProperties properties = new DataSourceProperties();
		return properties;
	}
	
	
	/**
	 * 填充DataSource properties
	 * @param dataSource
	 */
	protected void fillDataSourceProperties(DruidDataSource dataSource, DataSourceProperties properties) {
		if (properties != null) {
			if (properties.getUrl() != null) {
				dataSource.setUrl(properties.getUrl());
			}
			if (properties.getUsername() != null) {
				dataSource.setUsername(properties.getUsername());
			}
			if (properties.getPassword() != null) {
				dataSource.setPassword(properties.getPassword());
			}
			if (properties.getDriverClassName() != null) {
				dataSource.setDriverClassName(properties.getDriverClassName());
			}
			if (properties.getInitialSize() != null) {
				dataSource.setInitialSize(properties.getInitialSize());
			}
			if (properties.getMinIdle() != null) {
				dataSource.setMinIdle(properties.getMinIdle());
			}
			if (properties.getMaxActive() != null) {
				dataSource.setMaxActive(properties.getMaxActive());
			}
			if (properties.getMaxWait() != null) {
				dataSource.setMaxWait(properties.getMaxWait());
			}
			if (properties.getTimeBetweenEvictionRunsMillis() != null) {
				dataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
			}
			if (properties.getMinEvictableIdleTimeMillis() != null) {
				dataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
			}
			if (properties.getValidationQuery() != null) {
				dataSource.setValidationQuery(properties.getValidationQuery());
			}
			if (properties.getTestWhileIdle() != null) {
				dataSource.setTestWhileIdle(properties.getTestWhileIdle());
			}
			if (properties.getTestOnBorrow() != null) {
				dataSource.setTestOnBorrow(properties.getTestOnBorrow());
			}
			if (properties.getTestOnReturn() != null) {
				dataSource.setTestOnReturn(properties.getTestOnReturn());
			}
			if (properties.getRemoveAbandoned() != null) {
				dataSource.setRemoveAbandoned(properties.getRemoveAbandoned());
			}
			if (properties.getRemoveAbandonedTimeoutMillis() != null) {
				dataSource.setRemoveAbandonedTimeoutMillis(properties.getRemoveAbandonedTimeoutMillis());
			}
			if (properties.getFilters() != null) {
				try {
					dataSource.setFilters(properties.getFilters());
				} catch (Exception e) {
					throw new RemexException("DataSource init set filters error", e);
				}
			}
			if (properties.getLogAbandoned() != null) {
				dataSource.setLogAbandoned(properties.getLogAbandoned());
			}
		}
	}
}
