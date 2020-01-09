package com.qyly.remex.rocketmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qyly.remex.utils.Assist;

/**
 * mq消费者监听器默认基础类，json格式，会进行相关的异常处理及日志打印
 * 
 * @author Qiaoxin.Hong
 *
 * @param <T> 消息对象
 */
public abstract class MqDefaultJsonListener<T> extends MqJsonListener<T> {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
     * 处理消息
     * @param message
     * @return
     */
	@Override
	protected boolean handleMessage(String message, String msgId) {
		String listenerName = getClass().getName();
		
		if (Assist.isBlank(message)) {
			logger.error("mq handle message, message cannot be blank [listenerName : {}]", listenerName);
			return false;
		}
		
		logger.info("mq handle message [listenerName : {}] [msgId : {}] [message : {}]", listenerName, msgId, message);
		
		try {
			return super.handleMessage(message, msgId);
		} catch (Exception e) {
			logger.error("mq handle message error [listenerName : {}]", listenerName, e);
			return false;
		}
	}
}
