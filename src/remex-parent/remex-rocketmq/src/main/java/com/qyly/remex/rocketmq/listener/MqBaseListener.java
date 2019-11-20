package com.qyly.remex.rocketmq.listener;

import java.util.List;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * mq消费者监听器基础类
 * 
 * @author Qiaoxin.Hong
 *
 */
public abstract class MqBaseListener implements MessageListenerConcurrently {

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		for (MessageExt messageExt : msgs) {
			String message = new String(messageExt.getBody());
			
			boolean result = handleMessage(message, messageExt.getMsgId());
			if (!result) {
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
	
	/**
     * 处理消息
     * @param message
     * @return
     */
    protected abstract boolean handleMessage(String message, String msgId);
}
