package com.qyly.remex.rocketmq.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mq生产者全局properties
 * 
 * @author Qiaoxin.Hong
 *
 */
@ConfigurationProperties(value = MqProducerGlobalProperties.MQ_PRODUCER_GLOBAL_PREFIX)
public class MqProducerGlobalProperties {
	
	/** mq生产者全局properties配置文件前缀 */
	public static final String MQ_PRODUCER_GLOBAL_PREFIX = "rocketmq.producer.global";

	/** mq地址 */
	protected String namesrvAddr;
	
	/** mq maxMessageSize */
	protected Integer maxMessageSize;
	
	/** mq sendMsgTimeout */
	protected Integer sendMsgTimeout;

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public Integer getMaxMessageSize() {
		return maxMessageSize;
	}

	public void setMaxMessageSize(Integer maxMessageSize) {
		this.maxMessageSize = maxMessageSize;
	}

	public Integer getSendMsgTimeout() {
		return sendMsgTimeout;
	}

	public void setSendMsgTimeout(Integer sendMsgTimeout) {
		this.sendMsgTimeout = sendMsgTimeout;
	}
}