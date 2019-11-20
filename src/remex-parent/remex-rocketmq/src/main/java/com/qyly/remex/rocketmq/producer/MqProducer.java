package com.qyly.remex.rocketmq.producer;

import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import com.qyly.remex.exception.RemexException;

/**
 * mq生产者
 * 
 * @author Qiaoxin.Hong
 *
 */
public class MqProducer extends MqBaseProducer implements ApplicationListener<ApplicationReadyEvent> {

	/** 主题 */
	protected String topic;
	
	/** tag */
	protected String tag;
	
	/**
	 * 发送，已判断发送结果正确性
	 * @param content
	 */
	public SendResult send(Object content) {
		return send(content, null);
	}
	
	/**
	 * 发送，已判断发送结果正确性
	 * @param content
	 */
	public SendResult send(Object content, Integer delayTimeLevel) {
		return send(topic, tag, content, delayTimeLevel);
	}
	
	/**
	 * 发送，已判断发送结果正确性
	 * @param content
	 */
	public SendResult send(String content) {
		return send(topic, tag, content);
	}
	
	/**
	 * 发送
	 * @param content
	 */
	public SendResult sendReal(String content) {
		return sendReal(topic, tag, content);
	}
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			start();
		} catch (Exception e) {
			throw new RemexException("mq producer start error", e);
		}
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
