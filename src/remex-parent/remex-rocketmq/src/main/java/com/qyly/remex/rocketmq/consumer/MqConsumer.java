package com.qyly.remex.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import com.qyly.remex.exception.RemexException;
import com.qyly.remex.utils.Assert;

/**
 * mq消费者
 * 
 * @author Qiaoxin.Hong
 *
 */
public class MqConsumer extends MqBaseConsumer implements ApplicationListener<ApplicationReadyEvent> {

	/** mq消费都监听器Class */
	protected Class<? extends MessageListenerConcurrently> listenerClass;
	
	/** 主题 */
	protected String topic;
	
	/** tag */
	protected String tag;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			ApplicationContext applicationContext = event.getApplicationContext();
			if (listenerClass != null) {
				MessageListenerConcurrently listener = getListener(applicationContext, listenerClass);
				registerMessageListener(listener);
			}
			subscribe(topic, tag);
			start();
		} catch (Exception e) {
			throw new RemexException("mq consumer start error", e);
		}
	}

	/**
	 * 取得mq消费者的监听器
	 * @param listenerClass
	 * @return
	 */
	protected MessageListenerConcurrently getListener(ApplicationContext applicationContext
			, Class<? extends MessageListenerConcurrently> listenerClass) {
		if (listenerClass == null) {
			return null;
		}
		
		MessageListenerConcurrently listener = applicationContext.getBean(listenerClass);
		Assert.notNull(listener, "listener cannot be null for applicationContext");
		
		return listener;
	}
	
	
	public Class<? extends MessageListenerConcurrently> getListenerClass() {
		return listenerClass;
	}

	public void setListenerClass(Class<? extends MessageListenerConcurrently> listenerClass) {
		this.listenerClass = listenerClass;
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
