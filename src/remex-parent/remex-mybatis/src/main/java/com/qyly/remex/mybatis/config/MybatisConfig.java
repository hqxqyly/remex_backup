package com.qyly.remex.mybatis.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.qyly.remex.mybatis.properties.MybatisProperties;
import com.qyly.remex.utils.StringUtils;

/**
 * Mybatis config
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
@ConditionalOnBean(DataSource.class)
public class MybatisConfig {
	
	/**
	 * Mybatis
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public SqlSessionFactoryBean createSqlSessionFactory(MybatisProperties properties, DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setConfiguration(properties.getConfiguration());
		
		if (StringUtils.isNotBlank(properties.getResolveMapperLocations())) {
			bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(properties.getResolveMapperLocations()));
		}
		
		return bean;
	}
	
	/**
	 * Mybatis properties
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
	@ConditionalOnMissingBean
	public MybatisProperties createMybatisProperties() {
		MybatisProperties properties = new MybatisProperties();
		return properties;
	}
}
