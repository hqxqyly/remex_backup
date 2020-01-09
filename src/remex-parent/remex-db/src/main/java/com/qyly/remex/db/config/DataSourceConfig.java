package com.qyly.remex.db.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.qyly.remex.db.properties.DataSourceProperties;
import com.qyly.remex.db.utils.DataSourceUtils;

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
		DataSourceUtils.fillDataSourceProperties(dataSource, properties);
	}
}
