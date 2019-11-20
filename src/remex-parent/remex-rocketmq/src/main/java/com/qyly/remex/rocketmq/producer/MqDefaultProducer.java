package com.qyly.remex.rocketmq.producer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.qyly.remex.rocketmq.properties.MqProducerGlobalProperties;

/**
 * mq默认生产者，自动填充mq生产者的{@link #mqProducerGlobalProperties}公共属性
 * 
 * @author Qiaoxin.Hong
 *
 */
@EnableConfigurationProperties(value = MqProducerGlobalProperties.class)
public class MqDefaultProducer extends MqProducer implements ApplicationContextAware {
	
	@Autowired
	protected MqProducerGlobalProperties mqProducerGlobalProperties;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		setNamesrvAddr(mqProducerGlobalProperties.getNamesrvAddr());
		setMaxMessageSize(mqProducerGlobalProperties.getMaxMessageSize());
		setSendMsgTimeout(mqProducerGlobalProperties.getSendMsgTimeout());
	}
}
