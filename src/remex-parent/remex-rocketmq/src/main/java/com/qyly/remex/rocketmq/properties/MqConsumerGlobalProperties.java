package com.qyly.remex.rocketmq.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mq消费者全局properties
 * 
 * @author Qiaoxin.Hong
 *
 */
@ConfigurationProperties(value = MqConsumerGlobalProperties.MQ_CONSUMER_GLOBAL_PREFIX)
public class MqConsumerGlobalProperties {
	
	/** mq消费者全局properties配置文件前缀 */
	public static final String MQ_CONSUMER_GLOBAL_PREFIX = "rocketmq.consumer.global";

	/** mq地址 */
	protected String namesrvAddr;
	
	/** mq消费最小线程数 */
	protected int consumeThreadMin = 20;
	
	/** mq消费最大线程数 */
	protected int consumeThreadMax = 64;

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public int getConsumeThreadMin() {
		return consumeThreadMin;
	}

	public void setConsumeThreadMin(int consumeThreadMin) {
		this.consumeThreadMin = consumeThreadMin;
	}

	public int getConsumeThreadMax() {
		return consumeThreadMax;
	}

	public void setConsumeThreadMax(int consumeThreadMax) {
		this.consumeThreadMax = consumeThreadMax;
	}
}