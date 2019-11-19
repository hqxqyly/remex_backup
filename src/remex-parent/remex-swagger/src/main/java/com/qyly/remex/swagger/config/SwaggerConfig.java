package com.qyly.remex.swagger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qyly.remex.swagger.properties.SwaggerProperties;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 * 
 * @author Qiaoxin.Hong
 *
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
	
	/** 项目名 */
	@Value("${spring.application.name:}")
	String applicationName;
	
	/**
	 * 创建swagger api
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public Docket createRestApi() {
		SwaggerProperties properties = createSwaggerProperties();
		
		String title = applicationName;
		if (properties.getTitle() != null) {
			title = properties.getTitle();
		}
		
		ApiInfo apiInfo = new ApiInfoBuilder()
				.title(title)
				.description(properties.getDescription())
				.version(properties.getVersion())
				.contact(new Contact(properties.getName(), properties.getUrl(), properties.getEmail()))
				.build();
		
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo)
				.select()
				.apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
				.paths(PathSelectors.any())
				.build();
		
		return docket;
	}
	
	/**
	 * 创建swagger配置属性文件
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = SwaggerProperties.SWAGGER_PREFIX)
	@ConditionalOnMissingBean
	public SwaggerProperties createSwaggerProperties() {
		SwaggerProperties properties = new SwaggerProperties();
		return properties;
	}
}
