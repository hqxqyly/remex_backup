package com.qyly.remex.rocketmq.consumer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.qyly.remex.rocketmq.properties.MqConsumerGlobalProperties;

/**
 * mq默认消费者，自动填充mq消费者的{@link #mqConsumerGlobalProperties}公共属性
 * 
 * @author Qiaoxin.Hong
 *
 */
@EnableConfigurationProperties(value = MqConsumerGlobalProperties.class)
public class MqDefaultConsumer extends MqConsumer implements ApplicationContextAware {

	@Autowired
	protected MqConsumerGlobalProperties mqConsumerGlobalProperties;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		setNamesrvAddr(mqConsumerGlobalProperties.getNamesrvAddr());
		setConsumeThreadMin(mqConsumerGlobalProperties.getConsumeThreadMin());
		setConsumeThreadMax(mqConsumerGlobalProperties.getConsumeThreadMax());
	}
}
