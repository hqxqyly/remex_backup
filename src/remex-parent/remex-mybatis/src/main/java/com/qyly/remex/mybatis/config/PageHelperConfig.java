package com.qyly.remex.mybatis.config;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageInterceptor;
import com.qyly.remex.mybatis.properties.PageHelperProperties;

/**
 * PageHelper config
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
public class PageHelperConfig {
	
	@Autowired
    protected List<SqlSessionFactory> sqlSessionFactoryList;
	
	/**
	 * 将PageHelper加入到Mybatis拦截器中里
	 */
	@PostConstruct
    public void addPageInterceptor() {
		PageInterceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        fillPageHelperProperties(properties, createPageHelperProperties());
        interceptor.setProperties(properties);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
	}

	/**
	 * PageHelper properties
	 * @return
	 */
	@Bean
	@ConfigurationProperties(PageHelperProperties.PAGEHELPER_PREFIX)
	@ConditionalOnMissingBean
	public PageHelperProperties createPageHelperProperties() {
		PageHelperProperties properties = new PageHelperProperties();
		return properties;
	}
	
	
	/**
	 * 填充PageHelper properties
	 * @param properties
	 * @param pageHelperProperties
	 */
	protected void fillPageHelperProperties(Properties properties, PageHelperProperties pageHelperProperties) {
		properties.put("offsetAsPageNum", pageHelperProperties.getOffsetAsPageNum());
		properties.put("rowBoundsWithCount", pageHelperProperties.getRowBoundsWithCount());
		properties.put("pageSizeZero", pageHelperProperties.getPageSizeZero());
		properties.put("reasonable", pageHelperProperties.getReasonable());
	}
}
