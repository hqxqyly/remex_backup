package com.qyly.remex.cloud.feign.config;

import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qyly.remex.cloud.feign.ribbon.RemexLoadBalancerFeignClient;

import feign.Client;
import feign.Request;

/**
 * <pre>
 * FeignLoadBalanced配置类，目的在于重写一些逻辑
 * 目前重写逻辑：
 * 调整ribbon的重试机制，由于feign调用会出现连接失败和服务处理失败两种情况，目前只对连接失败进行重试
 * 主要重写了com.smart.remex.cloud.feign.ribbon.RemexLoadBalancerCommand<T>.retryPolicy(int, boolean)以实现这一效果
 * </pre>
 * 
 * @see org.springframework.cloud.netflix.feign.ribbon.DefaultFeignLoadBalancedConfiguration
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
public class RemexFeignLoadBalancedConfig {

	@Bean
	public Client feignClient(SpringClientFactory clientFactory) {
		return new RemexLoadBalancerFeignClient(new Client.Default(null, null), clientFactory);
	}
	
	@Bean
	public Request.Options feignRequestOptions() {
		return RemexLoadBalancerFeignClient.DEFAULT_OPTIONS;
	}
}
