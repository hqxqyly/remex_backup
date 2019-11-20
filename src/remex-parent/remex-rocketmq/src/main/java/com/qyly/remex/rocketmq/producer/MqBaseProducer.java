package com.qyly.remex.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import com.alibaba.fastjson.JSONObject;
import com.qyly.remex.constant.BConst;
import com.qyly.remex.exception.RemexException;
import com.qyly.remex.utils.Assert;

/**
 * mq生产者基础类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class MqBaseProducer extends DefaultMQProducer {
	
	/**
	 * 发送，已判断发送结果正确性
	 * @param topic
	 * @param tag
	 * @param content
	 */
	public SendResult send(String topic, String content) {
		return send(topic, content);
	}
	
	/**
	 * 发送，已判断发送结果正确性
	 * @param topic
	 * @param tag
	 * @param content
	 */
	public SendResult send(String topic, String content, Integer delayTimeLevel) {
		SendResult result = send(topic, BConst.EMPTY, content, delayTimeLevel);
		judgeResult(result);
		return result;
	}
	
	/**
	 * 发送，已判断发送结果正确性
	 * @param topic
	 * @param tag
	 * @param content
	 */
	public SendResult send(String topic, String tag, Object content) {
		return send(topic, tag, content, null);
	}
	
	/**
	 * 发送，已判断发送结果正确性
	 * @param topic
	 * @param tag
	 * @param content
	 */
	public SendResult send(String topic, String tag, Object content, Integer delayTimeLevel) {
		String contentJson = null;
		if (content != null) {
			contentJson = JSONObject.toJSONString(content);
		}
		SendResult result = send(topic, tag, contentJson, delayTimeLevel);
		judgeResult(result);
		return result;
	}
	
	/**
	 * 发送，已判断发送结果正确性
	 * @param topic
	 * @param tag
	 * @param content
	 */
	public SendResult send(String topic, String tag, String content) {
		return send(topic, tag, content, null);
	}
	
	/**
	 * 发送，已判断发送结果正确性
	 * @param topic
	 * @param tag
	 * @param content
	 */
	public SendResult send(String topic, String tag, String content, Integer delayTimeLevel) {
		SendResult result = sendReal(topic, tag, content, delayTimeLevel);
		judgeResult(result);
		return result;
	}
	
	/**
	 * 发送
	 * @param topic
	 * @param tag
	 * @param content
	 */
	public SendResult sendReal(String topic, String tag, String content) {
		return sendReal(topic, tag, content, null);
	}
	
	/**
	 * 发送
	 * @param topic
	 * @param tag
	 * @param content
	 * @param delayTimeLevel 消息延迟级别：1：1s；2：5s；3：10s；4：30s；5：1m；6：2m；7：3m；8：4m；9：5m；10：6m；11：7m；12：8m；13：9m；14：10m；
	 * 									15：20m；16：30m；17：1h；18：2h
	 */
	public SendResult sendReal(String topic, String tag, String content, Integer delayTimeLevel) {
		Assert.notBlank(content, "mq send content cannot be blank");
		try {
			Message msg = new Message(topic, tag, content.getBytes());
			//消息延迟级别
			if (delayTimeLevel != null) {
				msg.setDelayTimeLevel(delayTimeLevel);;
			}
			SendResult result = send(msg);
			return result;
		} catch (Exception e) {
			throw new RemexException("mq send error!", e);
		}
	}
	
	/**
	 * 判断结果，失败则抛出异常
	 * @param result
	 */
	public void judgeResult(SendResult result) {
		Assert.notNull(result, "mq send result cannot be null");

		if (!SendStatus.SEND_OK.equals(result.getSendStatus())) {
			throw new RemexException(String.format("mq send error [result : %s]", result.toString()));
		}
	}
}
