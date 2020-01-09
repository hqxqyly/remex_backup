package com.qyly.remex.db.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.qyly.remex.db.properties.DataSourceProperties;
import com.qyly.remex.db.properties.MultiDataSourceContext;
import com.qyly.remex.db.source.MultiDataSourceRouting;
import com.qyly.remex.db.utils.DataSourceUtils;
import com.qyly.remex.utils.Assist;

/**
 * 多数据源config
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
public class MultiDataSourceConfig {
	
	/**
	 * 多数据路由器
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public MultiDataSourceRouting createMultiDataSourceRouting(MultiDataSourceContext multiDataSourceContext) {
		Assist.notNull(multiDataSourceContext, "multiDataSourceContext cannot be null");
		
		MultiDataSourceRouting bean = new MultiDataSourceRouting();
		
		//数据源公共属性
		DataSourceProperties globalProperties= multiDataSourceContext.getGlobal();
		//数据源属性上下文
		Map<String, DataSourceProperties> context = multiDataSourceContext.getContext();
		
		Assist.forEach(context, (key, properties) -> {
			DruidDataSource dataSource = createDataSource();
			fillDataSourceProperties(dataSource, globalProperties, properties);
			
			bean.addDataSource(key, dataSource);
		});
		
		return bean;
	}

	/**
	 * 多数据源属性上下文
	 * @return
	 */
	@Bean
	@ConfigurationProperties(MultiDataSourceContext.MULTI_DATA_SOURCE_PREFIX)
	@ConditionalOnMissingBean
	public MultiDataSourceContext createMultiDataSourceContext() {
		MultiDataSourceContext context = new MultiDataSourceContext();
		return context;
	}
	
	/**
	 * 填充DataSource properties
	 * @param dataSource
	 */
	protected void fillDataSourceProperties(DruidDataSource dataSource, DataSourceProperties globalProperties
			, DataSourceProperties properties) {
		DataSourceUtils.fillDataSourceProperties(dataSource, globalProperties, properties);
	}
	
	/**
	 * 创建数据源
	 * @return
	 */
	protected DruidDataSource createDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		return dataSource;
	}
}
