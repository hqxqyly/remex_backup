package com.qyly.remex.ftp.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qyly.remex.ftp.client.FtpClient;
import com.qyly.remex.ftp.properties.FtpProperties;

/**
 * ftp config
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
public class FtpConfig {
	
	/**
	 * FtpManager
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public FtpClient createFtpManager(FtpProperties properties) {
		FtpClient bean = new FtpClient(properties);
		return bean;
	}

	/**
	 * ftp properties
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = FtpProperties.FTP_PREFIX)
	@ConditionalOnMissingBean
	public FtpProperties createFtpProperties() {
		FtpProperties properties = new FtpProperties();
		return properties;
	}
}
